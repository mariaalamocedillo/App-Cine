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

    @GET
    @Path("/")
    public String index() {
        models.put("proyecciones", proyeccionService.findProyectandoActual());
        return "admin/list-proyecciones";
    }

    @GET
    @Path("borrar/{id}")
    public String borrar(@PathParam("id") @NotNull Long id) {
        log.debug("Borrando proyección {}", id);

        Optional<Proyeccion> proyeccion = proyeccionService.buscarPorId(id);

        if (proyeccion.isPresent()) {
            Proyeccion pro = proyeccion.get();
            try {
                proyeccionService.borrar(pro);
                mensaje.setTexto("La pregunta " + pro.getId() + " se eliminó satisfactoriamente ! ");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                mensaje.setTexto("Ocurró un error y la pregunta " + pro.getId() + " no se pudo eliminar.");
            }
        }

        return "redirect:admin/proyeccion";
    }

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

    @GET
    @Path("nueva/{idPelicula}")
    public String nueva(@PathParam("idPelicula") Long id) {
        List<Sala> salas = salaService.findAll();
        Optional<Pelicula> pelicula = peliculaService.buscarPorId(id);
        if (id != null && pelicula.isPresent()){
            models.put("pelicula", pelicula.get());
        }else {
            models.put("peliculas", peliculaService.findProyectandoQ());
        }

        models.put("salas", salas);
        return "admin/form-proyeccion";
    }

    @POST
    @Path("nueva/submit")
    @ValidateOnExecution(type = ExecutableType.NONE)
    public String submitNueva(@FormParam("id") Long id, @FormParam("comienzo") String comienzo,
                              @FormParam("dia") String dia, @FormParam("sala") Long salaId,
                              @FormParam("peliculaId") Long peliculaId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");

        Optional<Sala> sala = salaService.buscarPorId(salaId);
        Optional<Pelicula> pelicula = peliculaService.buscarPorId(peliculaId);
        if (sala.isEmpty() || pelicula.isEmpty()){
            models.put("salas", salaService.findAll());
            models.put("peliculas", peliculaService.findAll());
            mensaje.setTexto("Ocurrió un error; la sala o película no se encuentra disponible.");
            return "admin/form-proyeccion";
        }

        Optional<Proyeccion> proyeccionOpt = proyeccionService.buscarPorId(id);
        Proyeccion proyeccion;
        //actualiza
        proyeccion = proyeccionOpt.orElseGet(Proyeccion::new);
        Pelicula pelicula1 = pelicula.get();
        proyeccion.setDia(LocalDate.parse(dia, formatter));
        proyeccion.setComienzo(LocalTime.parse(comienzo));
        proyeccion.setPelicula(pelicula1);
        proyeccion.setSala(sala.get());

        try {
            proyeccionService.guardar(proyeccion);
            pelicula1.setEnProyeccion(true);
            peliculaService.guardar(pelicula1);
            mensaje.setTexto("La proyeccion " + proyeccion.getId() + " de la película " + proyeccion.getPelicula().getTitulo() + " se guardó satisfactoriamente ! ");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mensaje.setTexto("Ocurrió un error y la proyección " + proyeccion.getId() + " de la película " + proyeccion.getPelicula().getTitulo() + " no se pudo almacenar.");
        }
        return "redirect:admin/proyeccion";
    }

}
