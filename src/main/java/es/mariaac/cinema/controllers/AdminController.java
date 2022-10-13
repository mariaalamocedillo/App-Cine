package es.mariaac.cinema.controllers;

import es.mariaac.cinema.entities.Asiento;
import es.mariaac.cinema.entities.Cliente;
import es.mariaac.cinema.entities.Pelicula;
import es.mariaac.cinema.entities.Reserva;
import es.mariaac.cinema.services.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.executable.ExecutableType;
import jakarta.validation.executable.ValidateOnExecution;
import jakarta.ws.rs.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Path("/admin")
@Controller
@RequestScoped
public class AdminController {
    @Inject
    private Models models;

    @Inject
    ClienteService clienteService;

    @Inject
    PeliculaService peliculaService;

    @Inject
    ProyeccionService proyeccionService;

    @Inject
    AsientoService asientoService;

    @Inject
    ReservaService reservaService;

    @Inject
    HttpServletRequest request;

    @Inject
    private Mensaje mensaje;

    @GET
    @Path("/")
    public String index() {
        models.put("ventasHoy", reservaService.listadoVentas().get(0));
        models.put("ventasSemana", reservaService.listadoVentas().get(1));
        models.put("ventasMes", reservaService.listadoVentas().get(2));


        return "admin/dashboard";
    }




//  *********   CONTROL DE PELÍCULAS   *********
    /**
     *  Método que obtiene todas las películas proyectandose y las que no
     *  para mostrarlas en la pagina del listado para el administrador (donde
     *  se puede editar la informacion de estas)
     *
     * @return página listado de películas administrador
     * @since 1.0
     */
    @GET
    @Path("peliculas")
    public String listadoPeliculas() {
        models.put("peliculas", peliculaService.findAll());
        models.put("peliculasProyectando", peliculaService.findProyectandoR());
        return "admin/list-peliculas";
    }

    /**
     *  Método que crea una nueva película en la base de datos y la envia
     *  en el modelo para usarla en la página de formulario, donde se
     *  definirá su informacion
     *
     * @return página formulario de película
     * @since 1.0
     */
    @GET
    @Path("pelicula/nueva")
    public String nueva() {
        log.debug("Añadir nueva pelicula");
        Pelicula pelicula = new Pelicula();
        models.put("pelicula", pelicula);
        return "admin/form-pelicula";
    }

    /**
     * Método que recoge toda la información de un formulario de una película
     * para subirla a la base de datos. Muestra un mensaje corroborando si se
     * pudo añadir o no a la base de datos y devuelve a la página de listado
     * de peliculas
     *
     * @param id            id de una película
     * @param titulo        titulo de la película
     * @param director      director de la película
     * @param duracion      duracion de la película en minutos
     * @param poster        url del poster de la película
     * @param descripcion   descripcion de la película
     * @param estudio       estudio de la película
     * @param enProyeccion  enProyeccion boolean si está siendo proyectada o no
     *
     * @return
     * @since 1.0
     */
    @POST
    @Path("nueva/submit")
    @ValidateOnExecution(type = ExecutableType.NONE)
    public String nuevaSubmit(@FormParam("id") Long id, @FormParam("titulo") String titulo,
                              @FormParam("director") String director, @FormParam("duracion") Integer duracion,
                              @FormParam("poster") String poster, @FormParam("descripcion") String descripcion,
                              @FormParam("estudio") String estudio, @FormParam("enProyeccion") String enProyeccion) {

        Optional<Pelicula> peliculaOpt = peliculaService.buscarPorId(id);
        Pelicula pelicula;
        //nueva
        pelicula = peliculaOpt.orElseGet(Pelicula::new);
        pelicula.setDescripcion(descripcion);
        pelicula.setTitulo(titulo);
        pelicula.setDirector(director);
        pelicula.setEstudio(estudio);
        pelicula.setDuracion(duracion);
        pelicula.setPoster(poster);
        System.out.println("--------------------------" + enProyeccion);
        pelicula.setEnProyeccion(enProyeccion != null);

        try {
            peliculaService.guardar(pelicula);
            mensaje.setTexto("La película " + pelicula.getId() + " " + pelicula.getTitulo() + " se guardó satisfactoriamente ! ");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mensaje.setTexto("Ocurrió un error y la película " + pelicula.getId() + " " + pelicula.getTitulo() + " no se pudo almacenar.");
        }
        return "redirect:admin/peliculas";
    }

    /**
     * Método que comprueba si una película existe según su id para
     * mostrar el formulario con la informacion para editar la necesaria
     *
     * @param id id de una película
     * @return página formulario de película
     * @since 1.0
     */
    @GET
    @Path("editar/{id}")
    public String editar(@PathParam("id") Long id) {
        log.debug("Edit pelicula {}", id);
        Optional<Pelicula> pelicula = peliculaService.buscarPorId(id);
        if (pelicula.isPresent()) {
            models.put("pelicula", pelicula.get());
            return "admin/form-pelicula";
        }
        return "redirect:admin/peliculas";
    }

    /**
     * Método que elimina una película tras comprobar si existe, mostrando
     * un mensaje correspondiente a si se hizo o no en la pantalla de listado
     * de películas
     *
     * @param id id de una película (obligatorio que no sea nulo)
     * @return página de listado de películas con mensaje correspondiente
     * @since 1.0
     */
    @GET
    @Path("borrar/{id}")
    public String borrar(@PathParam("id") @NotNull Long id) {
        log.debug("Borrando película {}", id);
        Optional<Pelicula> pelicula = peliculaService.buscarPorId(id);

        if (pelicula.isPresent()) {
            Pelicula p = pelicula.get();
            try {
                peliculaService.borrar(p);
                mensaje.setTexto("La película de la pelicula num. " + p.getId()  + " (" + p.getTitulo() + ") se eliminó satisfactoriamente ! ");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                mensaje.setTexto("Ocurrió un error y la proyección de la pelicula num. " + p.getId() + " (" + p.getTitulo() + ") no se pudo eliminar.");
            }
        }

        return "redirect:admin/pelicula";
    }
//  *********   CONTROL DE PROYECCIONES   *********


}
