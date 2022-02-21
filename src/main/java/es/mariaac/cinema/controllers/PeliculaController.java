package es.mariaac.cinema.controllers;

import es.mariaac.cinema.entities.Pelicula;
import es.mariaac.cinema.entities.Proyeccion;
import es.mariaac.cinema.services.PeliculaService;
import es.mariaac.cinema.services.ProyeccionService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.binding.BindingResult;
import jakarta.mvc.binding.ParamError;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.executable.ExecutableType;
import jakarta.validation.executable.ValidateOnExecution;
import jakarta.ws.rs.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static java.util.stream.Collectors.toList;

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
    private BindingResult bindingResult;

    @Inject
    private Mensaje mensaje;

    @Inject
    private Errores errores;


    @GET
    @Path("/")
    public String index() {
        models.put("peliculas", peliculaService.findAll());
        return "list-peliculas";
    }

    @GET
    @Path("/admin")
    public String listado() {
        models.put("peliculas", peliculaService.findAll());
        return "admin/list-peliculas";
    }

    @GET
    @Path("/proyectando")
    public String proyectando() {
        models.put("peliculas", peliculaService.findProyectando());
        return "list-peliculas";
    }

    @GET
    @Path("{id}")
    public String detalle(@PathParam("id") @NotNull Long id) {

        Optional<Pelicula> pelicula = peliculaService.buscarPorId(id);

        if (pelicula.isPresent()) {
            models.put("pelicula", pelicula.get());
            models.put("organizacionProyecciones", peliculaService.proyeccionesDias(pelicula.get()));
            models.put("proyecciones", pelicula.get().getProyecciones());
            return "detalle-pelicula";
        }
        return "redirect:pelicula";
    }

    @GET
    @Path("admin/nueva")
    public String nueva() {
        log.debug("Añadir nueva pelicula");
        Pelicula pelicula = new Pelicula();
        models.put("pelicula", pelicula);
        return "admin/form-pelicula";
    }

    @POST
    @Path("admin/nueva/submit")
    @ValidateOnExecution(type = ExecutableType.NONE)
    public String nuevaSubmit(@Valid @BeanParam Pelicula pelicula) {
            log.debug("Pregunta recibida: {}", pelicula);
            if (bindingResult.isFailed()) {
                logErrores();
                setErrores();
                models.put("pelicula", pelicula);
                return "admin/form-pelicula";
            }
            try {
                peliculaService.guardar(pelicula);
                mensaje.setTexto("La pregunta " + pelicula.getId() + " " + pelicula.getTitulo() + " se guardó satisfactoriamente ! ");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                mensaje.setTexto("Ocurrió un error y la peli " + pelicula.getId() + " " + pelicula.getTitulo() + " no se pudo almacenar.");
            }
            return "redirect:pelicula/admin";

        }


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

    @GET
    @Path("borrar/{id}")
    public String borrar(@PathParam("id") @NotNull Long id) {
        log.debug("Borrando pregunta {}", id);
        Optional<Pelicula> pelicula = peliculaService.buscarPorId(id);

        if (pelicula.isPresent()) {
            Pelicula p = pelicula.get();
            try {
                peliculaService.borrar(p);
                mensaje.setTexto("La pregunta " + p.getId()  + " " + p.getTitulo() + " se eliminó satisfactoriamente ! ");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                mensaje.setTexto("Ocurró un error y la pregunta " + p.getId() + " " + p.getTitulo() + " no se pudo eliminar.");
            }
        }

        return "redirect:pelicula/admin";
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
