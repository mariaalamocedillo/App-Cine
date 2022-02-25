package es.mariaac.cinema.controllers;


//TODO Falta: funcionar validacion formularios


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
import jakarta.mvc.binding.BindingResult;
import jakarta.mvc.binding.ParamError;
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

import static java.util.stream.Collectors.toList;
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
    private BindingResult bindingResult;

    @Inject
    private Mensaje mensaje;

    @Inject
    private Errores errores;

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
            models.put("peliculas", peliculaService.findAll());
        }

        models.put("salas", salas);
        return "admin/form-proyeccion";
    }

    @GET
    @Path("nueva/submit")
    @ValidateOnExecution(type = ExecutableType.NONE)
    public String submitNueva(@FormParam("id") Long id, @FormParam("comienzo") String comienzo,
                              @FormParam("dia") String dia, @FormParam("sala") String salaId,
                              @FormParam("pelicula") String peliculaId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        Optional<Sala> sala = salaService.buscarPorId(Long.valueOf(salaId));
        Optional<Pelicula> pelicula = peliculaService.buscarPorId(Long.valueOf(peliculaId));
        if (sala.isEmpty() || pelicula.isEmpty()){
            mensaje.setTexto("Ocurrió un error; la sala o película no se encuentra disponible.");
            return "admin/proyeccion";
        }

        Optional<Proyeccion> proyeccionOpt = proyeccionService.buscarPorId(id);
        Proyeccion proyeccion = new Proyeccion();
        if (proyeccionOpt.isPresent()){   //actualiza
            proyeccion = proyeccionOpt.get();
        }
        proyeccion.setDia(LocalDate.parse(dia, formatter));
        proyeccion.setComienzo(LocalTime.parse(comienzo));
        proyeccion.setPelicula(pelicula.get());
        proyeccion.setSala(sala.get());

        try {
            proyeccionService.guardar(proyeccion);
            mensaje.setTexto("La proyeccion " + proyeccion.getId() + " de la película " + proyeccion.getPelicula().getTitulo() + " se guardó satisfactoriamente ! ");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mensaje.setTexto("Ocurrió un error y la proyección " + proyeccion.getId() + " de la película " + proyeccion.getPelicula().getTitulo() + " no se pudo almacenar.");
        }
        return "redirect:admin/proyeccion";
    }


    private void setErrores() {
        errores.setErrores(bindingResult.getAllErrors()
                .stream()
                .collect(toList()));
    }

    private void logErrores() {
        bindingResult.getAllErrors()
                .stream()
                .forEach((ParamError t) ->
                        log.debug("Error de validación: {} {}", t.getParamName(), t.getMessage()));
    }

}
