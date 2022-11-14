package es.mariaac.cinema.services;

import es.mariaac.cinema.entities.Pelicula;
import es.mariaac.cinema.entities.Proyeccion;
import es.mariaac.cinema.repositories.PeliculaRepository;
import es.mariaac.cinema.repositories.ProyeccionRepository;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@ApplicationScoped
public class PeliculaService {
    @Inject
    PeliculaRepository peliculaRepository;
    @Inject
    ProyeccionRepository proyeccionRepository;
    @Resource
    UserTransaction transaction;

    public List<Pelicula> findAll () {
        return peliculaRepository.findAll();
    }

    public Optional<Pelicula> buscarPorId(Long id) {
        return peliculaRepository.findOptionalBy(id);
    }

    public List<Pelicula> buscarPorDia(LocalDate dia) {
        List<Long> idsPeliculasDia = peliculaRepository.findIdsByDay(dia);

        List<Pelicula> resultados = new ArrayList<>();
        for (Long id: idsPeliculasDia) {
            Optional<Pelicula> pelicula = peliculaRepository.findOptionalBy(id);
            pelicula.ifPresent(resultados::add);
        }
        return resultados;
    }

    public Pelicula guardar(Pelicula pelicula){ return peliculaRepository.save(pelicula);}

    public void borrar(Pelicula pelicula) throws SystemException {
        log.debug("Borrando pelicula y sus proyecciones asociadas");
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
    //sacamos el listado de peliculas en proyeccion y las proyecciones para comprobar ambos factores
    public List<Pelicula> findProyectandoR() {
        List<Proyeccion> proyecciones = proyeccionRepository.findProyectandoActual();
        List<Pelicula> peliculas = peliculaRepository.findProyectando();
        List<Pelicula> peliculasProyectando = new ArrayList<Pelicula>(); ;
        for (Pelicula pelicula: peliculas) {
            boolean peliSiProyectando = false;
            for (Proyeccion proyeccion: proyecciones) {
                if (Objects.equals(proyeccion.getPelicula().getId(), pelicula.getId())) {
                    peliSiProyectando = true;
                    break;
                }
            }
            if (peliSiProyectando)
                peliculasProyectando.add(pelicula);
        }
        log.info("Se han encontrado " + peliculasProyectando.size() + " en proyeccion actualmente");
        return peliculasProyectando;
    }


    public HashMap<LocalDate, List<Long>> proyeccionesDiasPelicula(Pelicula pelicula){
        List<Proyeccion> proyecciones = proyeccionRepository.findActualId(pelicula.getId());
        List<LocalDate> dias =  new ArrayList<>();
        List<Long> ids;
        HashMap<LocalDate, List<Long>> resultados = new HashMap<>();
        for (Proyeccion proyeccion: proyecciones) {
            LocalDate dia = LocalDate.parse(proyeccion.getDia());
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