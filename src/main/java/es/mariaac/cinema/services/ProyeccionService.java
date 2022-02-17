package es.mariaac.cinema.services;

import es.mariaac.cinema.entities.Proyeccion;
import es.mariaac.cinema.repositories.ProyeccionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProyeccionService {
    @Inject
    ProyeccionRepository proyeccionRepository;

    public List<Proyeccion> findAll () {
        return proyeccionRepository.findAll();
    }

    public Optional<Proyeccion> buscarPorId(Long id) {
        return proyeccionRepository.findOptionalBy(id);
    }

    public void guardar(Proyeccion proyeccion){proyeccionRepository.save(proyeccion);}


}
