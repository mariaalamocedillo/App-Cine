package es.mariaac.cinema.controllers;

import es.mariaac.cinema.entities.Pelicula;
import es.mariaac.cinema.services.PeliculaService;
import es.mariaac.cinema.services.ProyeccionService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@Path("/cartelera")
@Controller
@RequestScoped
public class PeliculaController {
    @Inject
    private Models models;

    @Inject
    PeliculaService peliculaService;

    @Inject
    private ProyeccionService proyeccionService;


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
        models.put("diasPeliculas", proyeccionService.diasPeliculas());
        models.put("proyecciones7Dias", proyeccionService.proyecciones7Dias());
        models.put("diasDeProyecciones", proyeccionService.diasDeProyecciones());
        return "list-peliculas";
    }


    @GET
    @Path("/prueba")
    public String cartelera() {
        models.put("diasPeliculas", proyeccionService.diasPeliculas());
        models.put("proyecciones7Dias", proyeccionService.proyecciones7Dias());
        models.put("diasDeProyecciones", proyeccionService.diasDeProyecciones());
        return "cartelera";
    }


    /**
     *  Método que obtiene todas las películas que están siendo proyectadas y las
     *  almacenadas en la base de datos para mostrarlas en la pagina del listado
     *
     * @return página listado de películas
     * @since 1.0
     */
    @GET
    @Path("/descubrir")
    public String proyectando() {
        models.put("peliculas", peliculaService.findAll());
        return "descubrir-peliculas";
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
            models.put("organizacionProyecciones", peliculaService.proyeccionesDiasPelicula(pelicula.get()));
            models.put("proyecciones", proyeccionService.findActualId(pelicula.get().getId()));
            return "detalle-pelicula";
        }
        return "redirect:cartelera";
    }
}
