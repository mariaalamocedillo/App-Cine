package es.mariaac.cinema.services;

import es.mariaac.cinema.entities.Pelicula;
import es.mariaac.cinema.entities.Proyeccion;
import es.mariaac.cinema.repositories.PeliculaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

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

    public List<Pelicula> findProyectando() {
        List<Pelicula> peliculas = findAll();

        for (int i = 0; i < peliculas.size(); i++) {
            if (!peliculas.get(i).getEnProyeccion()){
                peliculas.remove(peliculas.get(i));
            }
        }

        return peliculas;
    }

    public void guardar(Pelicula pelicula){peliculaRepository.save(pelicula);}

}
