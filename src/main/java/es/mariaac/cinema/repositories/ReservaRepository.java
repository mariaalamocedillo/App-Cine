package es.mariaac.cinema.repositories;

import es.mariaac.cinema.entities.Reserva;
import es.mariaac.cinema.entities.Sala;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface ReservaRepository extends EntityRepository<Reserva, Long> {
}