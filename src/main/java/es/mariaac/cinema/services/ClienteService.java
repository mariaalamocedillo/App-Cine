package es.mariaac.cinema.services;

import es.mariaac.cinema.entities.Cliente;
import es.mariaac.cinema.entities.Pelicula;
import es.mariaac.cinema.repositories.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Objects;
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

    public void guardar(Cliente cliente){clienteRepository.save(cliente);}

    public Cliente buscarPorEmail(String email){return clienteRepository.findByEmail(email);}

    public Boolean logear(String email, String contrasena){return clienteRepository.logear(email, contrasena) != null;}

}
