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

    public Cliente buscarPorEmail(String email){
        List<Cliente> clientes = findAll();
        for (Cliente cliente: clientes) {
            if (cliente.getEmail().equals(email)){
                return cliente;
            }
        }
        return null;
    }

    public Boolean logear(String email, String contrasena){
        if (buscarPorEmail(email) == null){
            return false;
        }
        return Objects.equals(buscarPorEmail(email).getContrasena(), contrasena);
    }

/*    public Optional<Cliente> buscarPorEmail(String email) {
        //return un cliente + contraseña; en logear se busca si coinciden;
    }

    public boolean logear(Cliente cliente){

    }*/
}
