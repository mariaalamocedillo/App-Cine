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

    public List<Asiento_reservado> asientosProyeccion(Long idProyeccion){
        List<Asiento_reservado> todosReservados = findAll();
        List<Asiento_reservado> asientos = new ArrayList<>();
        for (Asiento_reservado reservado: todosReservados) {
            if (Objects.equals(reservado.getProyeccion().getId(), idProyeccion)){
                asientos.add(reservado);
            }
        }
        return asientos;
    }

    public List<String> sacarEstadosAsientos(Proyeccion proyeccion){
        Sala sala = proyeccion.getSala();
        //sacar todas las reservas con el id de esta proyeccion
        List<Asiento_reservado> asientosProyeccion = asientosProyeccion(proyeccion.getId());
        List<Asiento> asientosSala = sala.getAsientos();
        List<String> array = new ArrayList<String>();
        for (int i = 0; i < asientosSala.size(); i++) {
            Asiento asiento = asientosSala.get(i);
            Boolean encontrado = false;
            for (Asiento_reservado reservado: asientosProyeccion ) {
                if (Objects.equals(reservado.getAsiento().getId(), asiento.getId())) {
                    encontrado = true;
                    break;
                }
            }
            if (encontrado) array.add(asiento.getName().toLowerCase());
        }
        System.out.println(array);
        return array;
    }

}
