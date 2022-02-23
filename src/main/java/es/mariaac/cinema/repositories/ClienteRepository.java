package es.mariaac.cinema.repositories;

import es.mariaac.cinema.entities.Cliente;
import es.mariaac.cinema.entities.Pelicula;
import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends EntityRepository<Cliente, Long> {
}