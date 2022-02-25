package es.mariaac.cinema.controllers;

import es.mariaac.cinema.entities.Pelicula;
import es.mariaac.cinema.services.PeliculaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Path("/api/pelicula")
public class PeliculaRestController {

  @Inject
  PeliculaService peliculaService;

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getById(@PathParam("id") @NotNull Long id) {
    Optional<Pelicula> pelicula = peliculaService.buscarPorId(id);

    return pelicula.map(value -> Response.ok(value).build())
      .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response add(@Valid Pelicula pelicula) {
    try {
      Long id = peliculaService.guardar(pelicula).getId();
      return Response.ok(Collections.singletonMap("id", id)).status(Response.Status.CREATED).build();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return Response.notModified().build();
    }
  }

  @PUT
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Response update(@Valid Pelicula pelicula, @PathParam("id") @NotNull Long id) {
    try {
      Optional<Pelicula> peli = peliculaService.buscarPorId(id);
      if (peli.isPresent()) {
        peliculaService.guardar(pelicula);
        return Response.ok().build();
      } else
        return Response.status(Response.Status.NOT_FOUND).build();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return Response.notModified().build();
    }
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response delete(@PathParam("id") @NotNull Long id) {
    try {
      Optional<Pelicula> pelicula = peliculaService.buscarPorId(id);
      if (pelicula.isPresent()) {
        peliculaService.borrar(pelicula.get());
        return Response.ok().build();
      } else
        return Response.status(Response.Status.NOT_FOUND).build();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return Response.notModified().build();
    }
  }

}
