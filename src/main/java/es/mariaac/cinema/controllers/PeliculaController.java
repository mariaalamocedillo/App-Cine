package es.mariaac.cinema.controllers;

import es.mariaac.cinema.configuration.LoaderDB;
import es.mariaac.cinema.entities.Pelicula;
import es.mariaac.cinema.repositories.PeliculaRepository;
import es.mariaac.cinema.services.PeliculaService;
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
@Path("/admin/pelicula")
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
    @Path("{id}")
    public String detalle(@PathParam("id") @NotNull Long id) {

        Optional<Pelicula> pelicula = peliculaService.buscarPorId(id);

        if (pelicula.isPresent()) {
            models.put("pelicula", pelicula.get());
            return "detalle-pelicula";
        }
        return "redirect:admin/pelicula";
    }

    @GET
    @Path("nueva")
    public String nueva() {
        log.debug("Añadir nueva pelicula");
        Pelicula pelicula = new Pelicula();
        models.put("pelicula", pelicula);
        return "admin/form-pelicula";
    }

    @POST
    @Path("/nueva/submit")
    @ValidateOnExecution(type = ExecutableType.NONE)
    public String nuevaSubmit(@Valid @BeanParam Pelicula pelicula) {
        log.debug("Pelicula recibida: {}", pelicula);
        if (bindingResult.isFailed()) {
            logErrores();
            setErrores();
            models.put("pelicula", pelicula);
            return "admin/form-pelicula";
        }
        peliculaService.guardar(pelicula);
        mensaje.setTexto("La peli " + pelicula.getTitulo() + " se guardó satisfactoriamente ! ");
        return "redirect:admin/pelicula";
    }


    @GET
    @Path("{id}/editar")
    public String editar(@PathParam("id") Long id) {
        log.debug("Edit pelicula {}", id);
        Optional<Pelicula> pelicula = peliculaService.buscarPorId(id);
        if (pelicula.isPresent()) {
            models.put("pelicula", pelicula.get());
            return "admin/form-pelicula";
        }
        return "redirect:admin/pelicula";
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
