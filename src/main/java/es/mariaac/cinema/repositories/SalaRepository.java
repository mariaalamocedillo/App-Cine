package es.mariaac.cinema.repositories;

import es.mariaac.cinema.entities.Sala;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface SalaRepository extends EntityRepository<Sala, Long> {
}