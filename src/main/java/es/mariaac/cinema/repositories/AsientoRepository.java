package es.mariaac.cinema.repositories;

import es.mariaac.cinema.entities.Asiento;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface AsientoRepository extends EntityRepository<Asiento, Long> {
}