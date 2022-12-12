package es.mariaac.cinema.repositories;

import es.mariaac.cinema.entities.Entrada;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface EntradaRepository extends EntityRepository<Entrada, Long> {


}