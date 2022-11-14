package es.mariaac.cinema.controllers;
import es.mariaac.cinema.entities.Pelicula;
import es.mariaac.cinema.entities.Proyeccion;
import es.mariaac.cinema.entities.Sala;
import es.mariaac.cinema.services.PeliculaService;
import es.mariaac.cinema.services.ProyeccionService;
import es.mariaac.cinema.services.SalaService;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Path("/admin/proyeccion")
@Controller
@RequestScoped
public class ProyeccionController {
    @Inject
    private Models models;

    @Inject
    ProyeccionService proyeccionService;

    @Inject
    private Mensaje mensaje;

    @Inject
    private PeliculaService peliculaService;

    @Inject
    private SalaService salaService;

    /**
     * Metodo envia a listado de proyecciones
     * Metodo que obtiene todas las proyecciones para mostrarlas en una datatable
     *
     * @return página listado de proyecciones
     */
    @GET
    @Path("/")
    public String index() {
        models.put("proyecciones", proyeccionService.findProyectandoActual());
        return "admin/list-proyecciones";
    }


    /**
     * Metodo elimina proyeccion
     *  Método que elimina una proyección según su id si dicha proyección existe
     *
     * @param id Long id de la proyección a eliminar
     * @return redireccion metodo index()
     */
    @GET
    @Path("borrar/{id}")
    public String borrar(@PathParam("id") @NotNull Long id) {
        log.debug("Borrando proyección {}", id);

        Optional<Proyeccion> proyeccionOpt = proyeccionService.buscarPorId(id);

        if (proyeccionOpt.isPresent()) {
            Proyeccion proyeccion = proyeccionOpt.get();
            try {
                proyeccionService.borrar(proyeccion);
                mensaje.setTexto("La proyeccion " + proyeccion.getId() + " se eliminó satisfactoriamente ! ");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                mensaje.setTexto("Ocurró un error y la proyeccion " + proyeccion.getId() + " no se pudo eliminar.");
            }
        }

        return "redirect:admin/proyeccion";
    }

    /**
     * Metodo editar proyeccion
     *  Método que envia al formulario de edicion de una proyeccion dado un id
     *
     * @param id Long id de la proyección a editar
     * @return formulario proyeccion
     */
    @GET
    @Path("editar/{id}")
    public String editar(@PathParam("id") @NotNull Long id) {

        Optional<Proyeccion> proyeccion = proyeccionService.buscarPorId(id);

        if (proyeccion.isPresent()) {
            models.put("salas", salaService.findAll());
            models.put("proyeccion", proyeccion.get());
            return "admin/form-proyeccion";
        }

        return "redirect:admin/proyeccion";
    }

    /**
     * Metodo crea una nueva proyeccion
     *  Método que crea una proyección según su el id de la pelicula recibido por parametro
     *
     * @param id Long id de la proyección a eliminar
     * @return formulario proyeccion
     */
    @GET
    @Path("nueva/{idPelicula}")
    public String nueva(@PathParam("idPelicula") Long id) {
        List<Sala> salas = salaService.findAll();
        Optional<Pelicula> pelicula = peliculaService.buscarPorId(id);
        models.put("salas", salas);
        if (id != null && pelicula.isPresent()){
            models.put("pelicula", pelicula.get());
        }else {
            models.put("peliculas", peliculaService.findProyectandoR());
        }

        return "admin/form-proyeccion";
    }


    /**
     * Metodo crea una nueva proyeccion por GET
     *  Método que crea una proyección según la información enviada por GET de la pelicula,
     *  fecha, hora y sala. Este método se utiliza desde el jsp horarios-sala.jsp, donde desde
     *  la posición en la propia tabla y con el desplegable del listado de películas disponibles
     *  se crea una proyeccion con oslo seleccionar dicha pelicula y clicar 'enviar'
     *
     * @param idPelicula Long id de la pelicula a proyectar
     * @param idSala Long id sala a utilizar
     * @param dia String dia a proyectar
     * @param hora String hora a proyectar
     * @return redireccion a horariosProyeccion() de AdminController
     */
    @GET
    @Path("datosNueva/{idPelicula}/{dia}/{hora}/{idSala}")
    public String nuevaIdPelicula(@PathParam("idPelicula") Long idPelicula, @PathParam("idSala") Long idSala,
                                  @PathParam("dia") String dia, @PathParam("hora") String hora) {

        Optional<Sala> salaOpt = salaService.buscarPorId(idSala);
        Optional<Pelicula> peliculaOpt = peliculaService.buscarPorId(idPelicula);
        if (salaOpt.isEmpty() || peliculaOpt.isEmpty()){
            mensaje.setTexto("Ocurrió un error; la sala o pelicula introducida es incorrecta.");
            return "redirect:admin/horarios";
        }
        Pelicula pelicula = peliculaOpt.get();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        Proyeccion proyeccion = new Proyeccion(peliculaOpt.get(), salaOpt.get(),
                LocalDate.parse(dia, formatter), LocalTime.parse(hora));

        almacenarProyeccion(pelicula, proyeccion);
        return "redirect:admin/horarios";

    }


    /**
     * Metodo crea una nueva proyeccion por POST
     *  Método que crea o edita una proyección según los datos recibidos por el id
     *  de la misma. Este método se utiliza desde el jsp del formulario de creacion
     *  de una proyeccion
     *
     * @param id Long id de la proyeccion
     * @param salaId Long id sala a utilizar
     * @param dia String dia a proyectar
     * @param comienzo String hora a proyectar
     * @param peliculaId Long id de la pelicula a proyectar
     * @return redireccion a horariosProyeccion() de AdminController
     */
    @POST
    @Path("submit")
    @ValidateOnExecution(type = ExecutableType.NONE)
    public String submitNueva(@FormParam("id") Long id, @FormParam("comienzo") String comienzo,
                              @FormParam("dia") String dia, @FormParam("sala") Long salaId,
                              @FormParam("peliculaId") Long peliculaId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

        Optional<Sala> salaOpt = salaService.buscarPorId(salaId);
        Optional<Pelicula> peliculaOpt = peliculaService.buscarPorId(peliculaId);
        if (salaOpt.isEmpty() || peliculaOpt.isEmpty()){
            models.put("salas", salaService.findAll());
            models.put("peliculas", peliculaService.findAll());
            mensaje.setTexto("Ocurrió un error; la sala o película no se encuentra disponible.");
            return "admin/form-proyeccion";
        }

        Optional<Proyeccion> proyeccionOpt = proyeccionService.buscarPorId(id);
        Proyeccion proyeccion;
        //actualiza
        proyeccion = proyeccionOpt.orElseGet(Proyeccion::new);
        Pelicula pelicula = peliculaOpt.get();
        proyeccion.setDia(LocalDate.parse(dia, formatter));
        proyeccion.setComienzo(LocalTime.parse(comienzo));
        proyeccion.setPelicula(pelicula);
        proyeccion.setSala(salaOpt.get());

        almacenarProyeccion(pelicula, proyeccion);
        return "redirect:admin/proyeccion";
    }

    /**
     * Metodo almacena una proyeccion
     *  Método que almacena la proyección recibida y actualiza la informacion de la
     *  pelicula recibida (la correspondiente a dicha proyección)
     *
     * @param pelicula Pelicula a actualizar
     * @param proyeccion Proyeccion a crear
     * @return redireccion a horariosProyeccion() de AdminController
     */
    public void almacenarProyeccion(Pelicula pelicula, Proyeccion proyeccion){
        try {
            proyeccionService.guardar(proyeccion);
            pelicula.setEnProyeccion(true);
            peliculaService.guardar(pelicula);
            mensaje.setTexto("La proyeccion " + proyeccion.getId() + " de la película "
                    + proyeccion.getPelicula().getTitulo() + " se guardó satisfactoriamente ! ");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mensaje.setTexto("Ocurrió un error y la proyección " + proyeccion.getId()
                    + " de la película " + proyeccion.getPelicula().getTitulo() + " no se pudo almacenar.");
        }
    }

}
