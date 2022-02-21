package es.mariaac.cinema.controllers;

import es.mariaac.cinema.entities.Pelicula;
import es.mariaac.cinema.entities.Proyeccion;
import es.mariaac.cinema.entities.Sala;
import es.mariaac.cinema.services.PeliculaService;
import es.mariaac.cinema.services.ProyeccionService;
import es.mariaac.cinema.services.SalaService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.annotation.RequestParameterMap;
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
import jakarta.ws.rs.core.Request;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Parameter;

import java.time.LocalDate;
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
        models.put("proyecciones", proyeccionService.findAll());
        return "admin/list-proyecciones";
    }

    @GET
    @Path("borrar/{id}")
    public String borrar(@PathParam("id") @NotNull Long id) {
        log.debug("Borrando pregunta {}", id);

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
            models.put("proyeccion", proyeccion.get());
            return "admin/form-proyeccion";
        }

        return "redirect:admin/proyeccion";
    }

    @GET
    @Path("nueva/{idPelicula}")
    public String nueva(@PathParam("idPelicula") Long id, @FormParam(value="dia") String dia) {
        List<Sala> salas = salaService.findAll();
        System.out.println(dia);
        Optional<Pelicula> pelicula = peliculaService.buscarPorId(id);

        if (id != null && pelicula.isPresent()){
            models.put("pelicula", pelicula.get());
            models.put("salas", salas);
        }else {
            models.put("peliculas", peliculaService.findAll());
            models.put("salas", salas);
        }
        return "admin/form-proyeccion";

    }

    @GET
    @Path("nueva/submit")
    @ValidateOnExecution(type = ExecutableType.NONE)
    public String submitNueva(@Valid @BeanParam Proyeccion proyeccion) {

        log.debug("Proyeccion recibida: {}", proyeccion);

        if (bindingResult.isFailed()) {
            logErrores();
            setErrores();
            models.put("proyeccion", proyeccion);
            return "admin/form-proyeccion";
        }
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
