package es.mariaac.cinema.services;

import es.mariaac.cinema.entities.*;
import es.mariaac.cinema.repositories.ProyeccionRepository;
import es.mariaac.cinema.repositories.SalaRepository;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    @Inject
    SalaRepository salaRepository;

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


    public HashMap<LocalDate, String> diasDeProyecciones(){
        List<LocalDate> dias = split(proyeccionRepository.findDiasProyecciones(), 7);
        HashMap<LocalDate, String> diasString = new HashMap<>();
        for (LocalDate dia: dias) {
            diasString.put(dia, dia.format(DateTimeFormatter.ofPattern("dd/MM")));
        }
        return diasString;
    }


    public List<Proyeccion> proyecciones7Dias(){
        List<Proyeccion> proyecciones = new ArrayList<>();
        List<LocalDate> dias = split(proyeccionRepository.findDiasProyecciones(), 7);
        for (LocalDate dia: dias) {
            proyecciones.addAll(proyeccionRepository.findProyeccionDia(dia));
        }
        return proyecciones;
    }

    public HashMap<LocalDate, List<Pelicula>> diasPeliculas(){
        List<LocalDate> dias = split(proyeccionRepository.findDiasProyecciones(), 7);
        HashMap<LocalDate, List<Pelicula>> resultados = new HashMap<>();
        for (LocalDate dia: dias) {
            resultados.put(dia, proyeccionRepository.findPeliculasProyecciones(dia));
        }
        return resultados;
    }

    //metodo que saca un hasmap de todas las proyecciones dado un dia en todas las salas
    public HashMap<Sala, List<Proyeccion>> horariosProyecciones(LocalDate dia){
        HashMap<Sala, List<Proyeccion>> resultados = new HashMap<>();
        List<Sala> salas = salaRepository.findSalasOrdByNombre();
        for (Sala sala: salas) {
            resultados.put(sala,
                    proyeccionRepository.findProyeccionesSalaFecha(dia, sala.getId()));
        }
        return resultados;
    }


    // Generic method to split a list from a certain position
    public static<LocalDate> List<LocalDate> split(List<LocalDate> list, Integer lim)
    {
        int max = list.size();
        // endpoints to use in `list.subList()` method
        if(max > lim )
            max = lim;
        int[] endpoints = {0, max, list.size()};
        List<LocalDate> listaFinal =
                IntStream.rangeClosed(0, 1)
                        .mapToObj(i -> list.subList(endpoints[i], endpoints[i + 1]))
                        .collect(Collectors.toList()).get(0);
        return listaFinal;
    }
}
