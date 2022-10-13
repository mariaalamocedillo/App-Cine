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
        models.put("peliculasProyectando", peliculaService.findProyectandoR());
        return "list-peliculas";
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
        models.put("peliculas", peliculaService.findProyectandoR());
        models.put("peliculasProyectando", peliculaService.findProyectandoR());
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



}
