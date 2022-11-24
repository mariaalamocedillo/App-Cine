package es.mariaac.cinema.services;

import es.mariaac.cinema.entities.Cliente;
import es.mariaac.cinema.repositories.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ClienteService {
    @Inject
    ClienteRepository clienteRepository;

    public List<Cliente> findAll () {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findOptionalBy(id);
    }

    public Cliente guardar(Cliente cliente){return clienteRepository.save(cliente);}

    public Optional<Cliente> buscarPorEmail(String email){return clienteRepository.findByEmail(email);}

    public Boolean logear(String email, String contrasena){
        Cliente cliente;
        try{
            cliente = clienteRepository.logear(email, contrasena);
            return cliente != null;
        } catch (NoResultException nre){
            return false;
        }
    }

}
