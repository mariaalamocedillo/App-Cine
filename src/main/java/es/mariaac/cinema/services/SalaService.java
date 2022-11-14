package es.mariaac.cinema.services;

import es.mariaac.cinema.entities.Sala;
import es.mariaac.cinema.repositories.SalaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.*;

@ApplicationScoped
public class SalaService {
    @Inject
    SalaRepository salaRepository;

    public List<Sala> findAll () {
        return salaRepository.findSalasOrdByNombre();
    }

    public Optional<Sala> buscarPorId(Long id) {
        return salaRepository.findOptionalBy(id);
    }

    public void guardar(Sala sala){salaRepository.save(sala);}

}
