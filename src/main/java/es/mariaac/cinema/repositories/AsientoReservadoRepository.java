package es.mariaac.cinema.repositories;

import es.mariaac.cinema.entities.Asiento_reservado;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface AsientoReservadoRepository extends EntityRepository<Asiento_reservado, Long> {
}