package es.mariaac.cinema.repositories;

import es.mariaac.cinema.entities.Pelicula;
import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public abstract class PeliculaRepository extends AbstractFullEntityRepository<Pelicula, Long> {

    @Query("select p from Pelicula p where enProyeccion = true")
    public abstract List<Pelicula> findProyectando();

    @Query("select distinct p.pelicula.id from Proyeccion p where p.dia = ?1")
    public abstract List<Long> findIdsByDay(LocalDate dia);


}