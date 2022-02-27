package es.mariaac.cinema.repositories;

import es.mariaac.cinema.entities.Cliente;
import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.Optional;


@Repository
public abstract class ClienteRepository extends AbstractFullEntityRepository<Cliente, Long> {

    @Query("select c from Cliente c where email like ?1")
    public abstract Optional<Cliente> findByEmail(String email);

    @Query("select c from Cliente c where email like ?1 and contrasena like ?2")
    public abstract Cliente logear(String email, String psswd);


}