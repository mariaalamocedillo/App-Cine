package es.mariaac.cinema.controllers;

import es.mariaac.cinema.entities.Proyeccion;
import es.mariaac.cinema.services.ProyeccionService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.binding.BindingResult;
import jakarta.mvc.binding.ParamError;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static java.util.stream.Collectors.toList;
//TODO ESTA SIN EDITAR NA
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

    @GET
    @Path("/")  //TODO no deberia haber una lista con las proyecciones, deberia haber una lista de las peliculas que se proyectan, o una tabla??
    public String index() {
        models.put("proyecciones", proyeccionService.findAll());
        return "admin/list-proyecciones";
    }

    @GET
    @Path("{id}")
    public String detalle(@PathParam("id") @NotNull Long id) {

        Optional<Proyeccion> proyeccion = proyeccionService.buscarPorId(id);

        if (proyeccion.isPresent()) {
            models.put("proyecciones", proyeccion.get());
            return "detalle-proyeccion";
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
