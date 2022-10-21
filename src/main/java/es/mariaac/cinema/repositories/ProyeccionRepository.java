package es.mariaac.cinema.repositories;

import es.mariaac.cinema.entities.Proyeccion;
import jakarta.persistence.NamedNativeQuery;
import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;


@Repository
public abstract class ProyeccionRepository extends AbstractFullEntityRepository<Proyeccion, Long> {

    public abstract Proyeccion findById(String id);

    @Query("select p from Proyeccion p where dia > current_date")//jpql
    public abstract List<Proyeccion> findProyectandoActual();

    @Query("select p from Proyeccion p where dia > current_date and pelicula.id = ?1")
    public abstract List<Proyeccion> findActualId(Long id);

    @Query("select DISTINCT p.dia from Proyeccion p where dia > current_date order by p.dia")
    public abstract List<LocalDate> findDiasProyecciones();

    @Query("select p from Proyeccion p where dia = ?1 order by p.comienzo")
    public abstract List<Proyeccion> findProyeccionDia(LocalDate fecha);

}