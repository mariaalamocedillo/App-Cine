package es.mariaac.cinema.repositories;

import es.mariaac.cinema.entities.Cliente;
import es.mariaac.cinema.entities.Pelicula;
import es.mariaac.cinema.entities.Reserva;
import es.mariaac.cinema.entities.Sala;
import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository
public abstract class ReservaRepository extends AbstractFullEntityRepository<Reserva, Long> {
    @Query("select r from Reserva r where cliente.id = ?1")
    public abstract List<Reserva> findReservas(Long id);


}