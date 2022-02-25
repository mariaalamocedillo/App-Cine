package es.mariaac.cinema.services;

import es.mariaac.cinema.entities.Pelicula;
import es.mariaac.cinema.entities.Proyeccion;
import es.mariaac.cinema.repositories.PeliculaRepository;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@ApplicationScoped
public class PeliculaService {
    @Inject
    PeliculaRepository peliculaRepository;
    @Resource
    UserTransaction transaction;

    public List<Pelicula> findAll () {
        return peliculaRepository.findAll();
    }

    public Optional<Pelicula> buscarPorId(Long id) {
        return peliculaRepository.findOptionalBy(id);
    }

    public List<Pelicula> findProyectandoQ() {
        return peliculaRepository.findProyectando();
    }

    public Pelicula guardar(Pelicula pelicula){ return peliculaRepository.save(pelicula);}

    public void borrar(Pelicula pelicula) throws SystemException {
        log.debug("Borrando peli y sus proyecciones asociadas");
        try {
            transaction.begin();
            peliculaRepository.attachAndRemove(pelicula);
            transaction.commit();
        } catch (Exception e) {
            log.debug ("Se ha producido una excepción", e);
            if (transaction != null ) {
                transaction.rollback();
            }
        }
    }

    public HashMap<LocalDate, List<Long>> proyeccionesDias(Pelicula pelicula){
        List<Proyeccion> proyecciones = pelicula.getProyecciones();
        List<LocalDate> dias =  new ArrayList<>();
        List<Long> ids;
        HashMap<LocalDate, List<Long>> resultados = new HashMap<>();
        for (Proyeccion proyeccion: proyecciones) {
            LocalDate dia = proyeccion.getDia();
            ids = new ArrayList<>();
            if (!dias.contains(dia)) {//comprobamos que no pasamos por ello
                dias.add(dia);
                //recorremos las proyecciones de ese día
                for (Proyeccion proyeccionInt : proyecciones) {
                    if (proyeccionInt.getDia().equals(proyeccion.getDia())) {
                        ids.add(proyeccionInt.getId());
                    }
                }
                resultados.put(dia, ids);
            }
            System.out.println(resultados);
        }
        return resultados;
    }

}