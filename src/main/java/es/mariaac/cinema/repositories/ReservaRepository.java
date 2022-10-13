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


    @Query("select sum(r.precio) from Reserva r where pagada = true" +
            " and proyeccion.dia = current_date")
    public abstract Double ventasHoy();
//TODO falta el semanal
    @Query("select sum(r.precio) from Reserva r where pagada = true" +
            " and MONTH(proyeccion.dia) = MONTH(current_date) and YEAR(proyeccion.dia) = YEAR(current_date)")
    public abstract Double ventasEsteMes();

    @Query("select sum(r.precio) from Reserva r where pagada = true" +
            " and YEAR(proyeccion.dia) = YEAR(current_date)")
    public abstract Double ventasEsteAño();

/*
    @Query("select count(proyeccion.sala.asientos) from Reserva r where pagada = true" +
            " and proyeccion.dia = current_date")
*/
    @Query( "SELECT s.id "
            + "FROM Reserva r JOIN r.proyeccion.sala s where pagada = true and DAY(r.proyeccion.dia) = DAY(current_date)")
    public abstract Float ocupacionHoy();


}