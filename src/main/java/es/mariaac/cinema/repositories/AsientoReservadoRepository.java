package es.mariaac.cinema.repositories;

import es.mariaac.cinema.entities.Asiento_reservado;
import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository
public abstract class AsientoReservadoRepository extends AbstractFullEntityRepository<Asiento_reservado, Long> {

    @Query("select a from Asiento_reservado a where proyeccion.id = ?1")//buscar jpql
    public abstract List<Asiento_reservado> findAsientosReservadosProyeccion(Long id);

}