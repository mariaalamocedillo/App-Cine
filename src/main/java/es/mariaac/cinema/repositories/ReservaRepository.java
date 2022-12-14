package es.mariaac.cinema.repositories;

import es.mariaac.cinema.entities.Entrada;
import es.mariaac.cinema.entities.Reserva;
import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository
public abstract class ReservaRepository extends AbstractFullEntityRepository<Reserva, Long> {
    @Query("select r from Reserva r where cliente.id = ?1 " +
            "and r.proyeccion.dia >= current_date and r.pagada = true")
    public abstract List<Reserva> findReservas(Long id);

    @Query("select r from Reserva r where cliente.id = ?1 " +
            "and proyeccion.dia < current_date and pagada = true")
    public abstract List<Reserva> findReservasAntiguas(Long id);

    @Query("select r.entradas FROM Reserva r where r.proyeccion.id = ?1")//buscar jpql
    public abstract List<Entrada> findEntradasProyeccion(Long id);

    @Query( "SELECT s.id "
            + "FROM Reserva r JOIN r.proyeccion.sala s where pagada = true and DAY(r.proyeccion.dia) = DAY(current_date)")
    public abstract Float ocupacionHoy();

}