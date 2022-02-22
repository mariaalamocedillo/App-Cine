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

    public HashMap<Asiento, Boolean> sacarEstadosAsientos(Proyeccion proyeccion){
        Sala sala = proyeccion.getSala();
        //sacar todas las reservas con el id de esta proyeccion
        List<Asiento_reservado> asientosProyeccion = asientosProyeccion(proyeccion.getId());
        HashMap<Asiento, Boolean> resultado = new HashMap<>();
        List<Asiento> asientosSala = sala.getAsientos();
        for (int i = 0; i < asientosSala.size(); i++) {
            Asiento asiento = asientosSala.get(i);
            Boolean encontrado = false;

            if (i%8==0){
                resultado.put(new Asiento(), false);
            }

            for (Asiento_reservado reservado: asientosProyeccion ) {
                if (reservado.getAsiento() == asiento){
                    encontrado = true;
                    break;
                }
            }
            if (encontrado) resultado.put(asiento, true);
            else  resultado.put(asiento, false);

        }


        return resultado;
    }

}
