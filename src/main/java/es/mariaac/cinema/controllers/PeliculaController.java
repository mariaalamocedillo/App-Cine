package es.mariaac.cinema.controllers;

import es.mariaac.cinema.entities.Pelicula;
import es.mariaac.cinema.services.PeliculaService;
import es.mariaac.cinema.services.ProyeccionService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.executable.ExecutableType;
import jakarta.validation.executable.ValidateOnExecution;
import jakarta.ws.rs.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@Path("/pelicula")
@Controller
@RequestScoped
public class PeliculaController {
    @Inject
    private Models models;

    @Inject
    PeliculaService peliculaService;

    @Inject
    private ProyeccionService proyeccionService;


    @Inject
    private Mensaje mensaje;


    /**
     *  Método que obtiene todas las películas que están siendo proyectadas
     *  para mostrarlas en la pagina del listado
     *
     * @return página listado de películas
     * @since 1.0
     */
    @GET
    @Path("/")
    public String index() {
        models.put("peliculas", peliculaService.findAll());
        return "list-peliculas";
    }

    /**
     *  Método que obtiene todas las películas proyectandose y las que no
     *  para mostrarlas en la pagina del listado para el administrador (donde
     *  se puede editar la informacion de estas)
     *
     * @return página listado de películas administrador
     * @since 1.0
     */
    @GET
    @Path("/admin")
    public String listado() {
        models.put("peliculas", peliculaService.findAll());
        return "admin/list-peliculas";
    }

    /**
     *  Método que obtiene todas las películas que están siendo proyectadas
     *  para mostrarlas en la pagina del listado
     *
     * @return página listado de películas
     * @since 1.0
     */
    @GET
    @Path("/proyectando")
    public String proyectando() {
        models.put("peliculas", peliculaService.findProyectandoQ());
        return "list-peliculas";
    }

    /**
     *  Método que busca si existe una pelicula con ese id para obtener sus
     *  detalles y las proyecciones de la misma. Devuelve dicha información
     *  en caso de que exista, si no redirige de nuevo al listado de peliculas
     *
     *
     * @param id id de la peícula (obligatorio no nulo)
     * @return página detalle de una película
     * @since 1.0
     */
    @GET
    @Path("{id}")
    public String detalle(@PathParam("id") @NotNull Long id) {

        Optional<Pelicula> pelicula = peliculaService.buscarPorId(id);

        if (pelicula.isPresent()) {
            models.put("pelicula", pelicula.get());
            models.put("organizacionProyecciones", peliculaService.proyeccionesDias(pelicula.get()));
            models.put("proyecciones", proyeccionService.findActualId(pelicula.get().getId()));
            return "detalle-pelicula";
        }
        return "redirect:pelicula";
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
    @Path("admin/nueva")
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
    @Path("admin/nueva/submit")
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
        return "redirect:pelicula/admin";
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
    @Path("admin/editar/{id}")
    public String editar(@PathParam("id") Long id) {
        log.debug("Edit pelicula {}", id);
        Optional<Pelicula> pelicula = peliculaService.buscarPorId(id);
        if (pelicula.isPresent()) {
            models.put("pelicula", pelicula.get());
            return "admin/form-pelicula";
        }
        return "redirect:pelicula";
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
    @Path("admin/borrar/{id}")
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

        return "redirect:pelicula/admin";
    }

}
