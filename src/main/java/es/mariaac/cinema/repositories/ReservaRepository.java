package es.mariaac.cinema.repositories;

import es.mariaac.cinema.entities.Reserva;
import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository
public abstract class ReservaRepository extends AbstractFullEntityRepository<Reserva, Long> {
    @Query("select r from Reserva r where cliente.id = ?1 " +
            "and proyeccion.dia > current_date and pagada = true")
    public abstract List<Reserva> findReservas(Long id);

    @Query("select r from Reserva r where cliente.id = ?1 " +
            "and proyeccion.dia < current_date and pagada = true")
    public abstract List<Reserva> findReservasAntiguas(Long id);

}