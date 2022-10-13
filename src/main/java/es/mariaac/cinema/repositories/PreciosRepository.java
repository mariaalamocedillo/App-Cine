package es.mariaac.cinema.repositories;

import es.mariaac.cinema.entities.Precios;
import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.Optional;


@Repository
public abstract class PreciosRepository extends AbstractFullEntityRepository<Precios, Long> {

    @Query("select p from Precios p where nombre like ?1")
    public abstract Optional<Precios> findByNombre(String nombre);

}