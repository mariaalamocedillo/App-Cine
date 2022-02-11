package es.mariaac.cinema.services;

import es.mariaac.cinema.configuration.LoaderDB;
import es.mariaac.cinema.entities.Pelicula;
import es.mariaac.cinema.repositories.PeliculaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.dom4j.tree.SingleIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PeliculaService {
    @Inject
    PeliculaRepository peliculaRepository;

    public List<Pelicula> findAll () {
        return peliculaRepository.findAll();
    }

    public Optional<Pelicula> buscarPorId(Long id) {
        return peliculaRepository.findOptionalBy(id);
    }

    public void guardar(Pelicula pelicula){peliculaRepository.save(pelicula);}
}
