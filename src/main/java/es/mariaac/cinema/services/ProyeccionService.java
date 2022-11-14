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
import java.util.*;
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

    /**
     * Método que guarda una proyeccion y la devuelve una vez guardada
     *
     * @return Proyeccion
     * @since 1.0
     * @throws  SystemException
     */
    public Proyeccion guardar(Proyeccion proyeccion)  throws SystemException {
        log.debug("Guardando proyeccion");
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

    /**
     * Método que elimina una proyeccion
     *
     * @exception SystemException
     * @since 1.0
     */
    public void borrar(Proyeccion proyeccion) throws SystemException {
        try {
            transaction.begin();
            proyeccionRepository.attachAndRemove(proyeccion);
            transaction.commit();
        } catch (Exception e) {
            log.debug ("Se ha producido una excepción", e);
            if (transaction != null ) {
                transaction.rollback();
            }
        }
    }

    /**
     * Metodo que devuelve listado de fechas con proyecciones
     * Método que obtiene el listado de fechas con proyecciones de aquí a 7 dias diferentes.
     * Obtiene 7 fechas diferentes, no fechas de aquí a 7 dias. Devuelve un Hashmap con el valor de
     * LocalDate como key y el valor formateado en string como value
     *
     *
     * @return HashMap<LocalDate, String>
     * @since 1.0
     */
    public HashMap<LocalDate, String> diasDeProyecciones(){
        List<LocalDate> dias = split(proyeccionRepository.findDiasProyecciones(), 7);
        HashMap<LocalDate, String> diasString = new HashMap<>();
        for (LocalDate dia: dias) {
            diasString.put(dia, dia.format(DateTimeFormatter.ofPattern("dd/MM")));
        }
        return diasString;
    }


    /**
     * Metodo que devuelve las proyecciones en cada sala en un dia
     * Método que saca un LinkedHashMap de todas las proyecciones dado un dia.
     * Devuelve el hasmap con las salas como keys y los listados de proyecciones
     * de ese dia en cada sala como respectivo value
     *
     * @param dia LocalDate dia
     * @return LinkedHashMap<Sala, List<Proyeccion>>
     * @since 2.0
     */
    public LinkedHashMap<Sala, List<Proyeccion>> horariosProyecciones(LocalDate dia){
        LinkedHashMap<Sala, List<Proyeccion>> resultados = new LinkedHashMap<>();
        List<Sala> salas = salaRepository.findSalasOrdByNombre();
        for (Sala sala: salas) {
            resultados.put(sala,
                    proyeccionRepository.findProyeccionesSalaFecha(dia, sala.getId()));
        }
        return resultados;
    }


    /**
     * Metodo que divide una lista
     * Método genérico que divide una List hasta cierto índice
     *
     * @param list List<LocalDate> listado a dividir
     * @param lim Integer limite donde se quiere partir la lista
     * @return List<LocalDate>
     * @since 2.0
     */
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
