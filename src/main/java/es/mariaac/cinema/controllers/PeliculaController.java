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
        models.put("peliculas", peliculaService.findProyectandoQ());
        return "list-peliculas";
    }

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
