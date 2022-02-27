package es.mariaac.cinema.services;

import es.mariaac.cinema.entities.*;
import es.mariaac.cinema.repositories.ProyeccionRepository;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

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
}
