package es.mariaac.cinema.repositories;

import es.mariaac.cinema.entities.Proyeccion;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface ProyeccionRepository extends EntityRepository<Proyeccion, Long> {
}