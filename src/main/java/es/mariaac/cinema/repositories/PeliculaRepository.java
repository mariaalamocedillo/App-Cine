package es.mariaac.cinema.repositories;

import es.mariaac.cinema.entities.Pelicula;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface PeliculaRepository extends EntityRepository<Pelicula, Long> {

}