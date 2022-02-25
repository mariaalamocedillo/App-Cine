package es.mariaac.cinema.services;

import es.mariaac.cinema.entities.*;
import es.mariaac.cinema.repositories.AsientoReservadoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.*;

@ApplicationScoped
public class AsientoReservadoService {
    @Inject
    AsientoReservadoRepository asientoReservadoRepository;

    public List<Asiento_reservado> findAll () {
        return asientoReservadoRepository.findAll();
    }

    public Optional<Asiento_reservado> buscarPorId(Long id) {
        return asientoReservadoRepository.findOptionalBy(id);
    }

    public Asiento_reservado guardar(Asiento_reservado asiento){
        return asientoReservadoRepository.save(asiento);
    }

    public void reservarAsiento(Reserva reserva, Asiento asiento){
        Asiento_reservado asiento_reservado = new Asiento_reservado();
        asiento_reservado.setAsiento(asiento);
        asiento_reservado.setReserva(reserva);
        asiento_reservado.setProyeccion(reserva.getProyeccion());
        guardar(asiento_reservado);
    }

    public List<String> sacarEstadosAsientos(Proyeccion proyeccion){
        //sacar todas las reservas con el id de esta proyeccion
        List<Asiento_reservado> asientosProyeccion = asientoReservadoRepository.findAsientosReservadosProyeccion(proyeccion.getId());
        List<String> asientosNames = new ArrayList<String>();
        for (Asiento_reservado asiento_reservado : asientosProyeccion) {
            Asiento reservado = asiento_reservado.getAsiento();
            asientosNames.add(reservado.getName().toLowerCase());
        }
        return asientosNames;
    }

}
