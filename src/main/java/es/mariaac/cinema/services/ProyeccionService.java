package es.mariaac.cinema.services;

import es.mariaac.cinema.entities.*;
import es.mariaac.cinema.repositories.ProyeccionRepository;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@ApplicationScoped
public class ProyeccionService {
    @Inject
    ProyeccionRepository proyeccionRepository;

    @Resource
    UserTransaction transaction;

    public List<Proyeccion> findAll () {
        return proyeccionRepository.findAll();
    }

    public Optional<Proyeccion> buscarPorId(Long id) {
        return proyeccionRepository.findOptionalBy(id);
    }

    public List<Proyeccion> findProyectandoActual(){return proyeccionRepository.findProyectandoActual();}

    public List<Proyeccion> findActualId(Long id){return proyeccionRepository.findActualId(id);}

    public Proyeccion guardar(Proyeccion proyeccion)  throws SystemException {
        log.debug("Guardando pregunta y sus relaciones asociadas");
        Proyeccion proyeccionGuardada = null;
        try {
            transaction.begin();
            proyeccionGuardada = proyeccionRepository.save(proyeccion);
            transaction.commit();
        } catch (Exception e) {
            log.debug("Se ha producido una excepción", e);
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return proyeccionGuardada;
    }

    public void borrar(Proyeccion proyeccion) throws SystemException {
        log.debug("Borrando proyeccion y sus respuestas asociadas");
        try {
            transaction.begin();
            proyeccionRepository.attachAndRemove(proyeccion);
            //preguntaRepository.remove(pregunta); // da excepción de intento de borrado de una entidad desligada
            transaction.commit();
        } catch (Exception e) {
            log.debug ("Se ha producido una excepción", e);
            if (transaction != null ) {
                transaction.rollback();
            }
        }
    }


    public HashMap<LocalDate, List<Proyeccion>> diasProyecciones(){
        List<Proyeccion> proyecciones = new ArrayList<>();
        List<LocalDate> dias = split(proyeccionRepository.findDiasProyecciones());
        HashMap<LocalDate, List<Proyeccion>> resultados = new HashMap<>();
        for (LocalDate dia: dias) {
            resultados.put(dia, proyeccionRepository.findProyeccionDia(dia));
        }
        return resultados;
    }


    // Generic method to split a list from a certain position
    public static<LocalDate> List<LocalDate> split(List<LocalDate> list)
    {
        // endpoints to use in `list.subList()` method
        int[] endpoints = {0, 7, list.size()};
        List<LocalDate> listaFinal =
                IntStream.rangeClosed(0, 1)
                        .mapToObj(i -> list.subList(endpoints[i], endpoints[i + 1]))
                        .collect(Collectors.toList()).get(0);
        return listaFinal;
    }
}
