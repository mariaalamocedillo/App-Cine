package es.mariaac.cinema.controllers;

import es.mariaac.cinema.entities.*;
import es.mariaac.cinema.services.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.executable.ExecutableType;
import jakarta.validation.executable.ValidateOnExecution;
import jakarta.ws.rs.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Path("/admin")
@Controller
@RequestScoped
public class AdminController {
    @Inject
    private Models models;

    @Inject
    PeliculaService peliculaService;

    @Inject
    SalaService salaService;

    @Inject
    ProyeccionService proyeccionService;

    @Inject
    AsientoService asientoService;

    @Inject
    ReservaService reservaService;

    @Inject
    private Mensaje mensaje;

/**
     * Método dirige a panel de administración
     *
     *
     * @return página formulario de película
     */

    @GET
    @Path("/")
    public String index() {
        return "admin/panel-admin";
    }



/* *********   CONTROL DE SALAS   ********* */

    /**
     * Metodo envia a crear una nueva sala
     *
     * @return página formulario de película
     */
    @GET
    @Path("sala/nueva")
    public String irCrearSala() {
        log.debug("Añadir nueva sala");
        Sala sala = new Sala();
        models.put("sala", sala);
        return "admin/creacion-sala";
    }

    /**
     * Metodo envia al listado de horarios por dia
     *  Método que busca las proyecciones segun el dia indicado. Obtiene un listado de la informacion
     *  de las salas con sus respectivas proyecciones, así como las películas disponibles. Se mostrará
     *  una tabla con los horarios en columnas y las salas en filas, con desplegables de todas las peliculas
     *  en cada celda.
     *
     *
     * @param fecha String dia a buscar proyecciones
     * @return página formulario de película
     */
    @GET
    @Path("horarios{dia:(/dia/[^/]+?)?}")
    public String horariosProyeccion(@PathParam("dia") String fecha) {
        String dia;
        if (fecha == null || fecha.equals(""))
            dia = String.valueOf(LocalDate.now());
        else
            dia = fecha.split("/")[2];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

        String[] horarios = {"16:00", "18:30", "21:00", "23:30"};
        models.put("horarios", horarios);
        models.put("dia", dia);
        models.put("infoSalas", proyeccionService.horariosProyecciones(LocalDate.parse(dia, formatter)));
        models.put("peliculas", peliculaService.findAll());

        return "admin/horarios-sala";
    }

    /**
     * [no funciona] Metodo creacion de sala dinamica
     *  Método que crea una nueva sala en la base de datos de forma dinámica desde el jsp
     *  de creacion-sala. Al finalizar, mostraria el resultado en sala-mostrar
     *
     * @return página formulario de película
     */
    @POST
    @Path("sala/submit")
    @ValidateOnExecution(type = ExecutableType.NONE)
    public String guardarSala(@FormParam("id") Long id, @FormParam("nombre") String nombre, @FormParam("listado") String listado) {

        Optional<Sala> salaOpt = salaService.buscarPorId(id);
        Sala sala;
        //nueva sala
        sala = salaOpt.orElseGet(Sala::new);
        sala.setNombre(nombre);

        try {
            salaService.guardar(sala);
            mensaje.setTexto("La sala " + sala.getId() + " " + sala.getNombre() + " se guardó satisfactoriamente ! ");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mensaje.setTexto("Ocurrió un error y la sala " + sala.getId() + " " + sala.getNombre() + " no se pudo almacenar.");
        }

        String[] sitios = listado.trim().replaceAll(" ", "").split(",");
        //recorremos los ids para crear nuevos sitios
        for (String idSitio: sitios) {
            Asiento newAsiento = new Asiento();
            newAsiento.setLetra(String.valueOf(idSitio.charAt(0)));
            newAsiento.setFila(String.valueOf(idSitio.charAt(1)));
            sala.addAsiento(newAsiento);
            try {
                asientoService.guardar(newAsiento);
                mensaje.setTexto("El asiento " + newAsiento.getId() + " " + newAsiento.getName() + " se guardó satisfactoriamente ! ");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                mensaje.setTexto("Ocurrió un error y el asiento " + newAsiento.getName() + " no se pudo almacenar.");
            }

        }

        //TODO - los ids que recibimos en el form nos indican el num de filas y columnas maximo que habrá y la ubicación exacta. Se pueden renombrar despues, pero la idea es poder mostrarlos en esa misma disosicion

        return "admin/sala-mostrar";
    }


/*  *********   CONTROL DE PELÍCULAS   *********  */
    /**
     * Metodo obtiene todas las pelicuals de la BD
     *  Método que obtiene todas las películas proyectandose y las que no
     *  para mostrarlas en la pagina del listado para el administrador (donde
     *  se puede editar la informacion de estas)
     *
     * @return página listado de películas administrador
     */
    @GET
    @Path("peliculas")
    public String listadoPeliculas() {
        models.put("peliculas", peliculaService.findAll());
        models.put("peliculasProyectando", peliculaService.findProyectandoR());
        return "admin/list-peliculas";
    }

    /**
     * Metodo envia al formulario de pelicula
     *  Método que crea una nueva película en la base de datos y la envia
     *  en el modelo para usarla en la página de formulario, donde se
     *  definirá su informacion
     *
     * @return página formulario de película
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
     * Metodo que carga una nueva pelicula
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
     * @return redirección al metodo listadoPeliculas
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
     * Metodo que envia al formulario de edicion de pelicula
     * Método que comprueba si una película existe según su id para
     * mostrar el formulario con la informacion para editar la necesaria
     *
     * @param id id de una película
     * @return página formulario de película
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
     * Metodo que elimina pelicula por id
     * Método que elimina una película tras comprobar si existe, mostrando
     * un mensaje correspondiente a si se hizo o no en la pantalla de listado
     * de películas
     *
     * @param id id de una película (obligatorio que no sea nulo)
     * @return página de listado de películas con mensaje correspondiente
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
/*  *********   CONTROL DE PROYECCIONES   *********  */


}
