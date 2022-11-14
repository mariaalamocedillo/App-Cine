package es.mariaac.cinema.repositories;

import es.mariaac.cinema.entities.Sala;
import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository
public abstract class SalaRepository extends AbstractFullEntityRepository<Sala, Long> {
    @Query("select s from Sala s order by nombre")
    public abstract List<Sala> findSalasOrdByNombre();
}