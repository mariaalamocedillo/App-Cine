package es.mariaac.cinema.repositories;

import es.mariaac.cinema.entities.Cliente;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface ClienteRepository extends EntityRepository<Cliente, Long> {
}