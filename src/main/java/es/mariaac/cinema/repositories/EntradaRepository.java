package es.mariaac.cinema.repositories;

import es.mariaac.cinema.entities.Entrada;
import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository
public abstract class EntradaRepository extends AbstractFullEntityRepository<Entrada, Long> {

    @Query("select e from Entrada e where reserva.proyeccion.id = ?1")//buscar jpql
    public abstract List<Entrada> findEntradasProyeccion(Long id);

}