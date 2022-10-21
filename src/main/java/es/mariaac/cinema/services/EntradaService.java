package es.mariaac.cinema.services;

import es.mariaac.cinema.entities.*;
import es.mariaac.cinema.repositories.EntradaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.*;

@ApplicationScoped
public class EntradaService {
    @Inject
    EntradaRepository entradaRepository;

    public List<Entrada> findAll () {
        return entradaRepository.findAll();
    }

    public Optional<Entrada> buscarPorId(Long id) {
        return entradaRepository.findOptionalBy(id);
    }

    public Entrada guardar(Entrada asiento){
        return entradaRepository.save(asiento);
    }

    public void reservarAsiento(Reserva reserva, Asiento asiento,Precios precioHoy){
        Entrada entrada = new Entrada();
        entrada.setAsiento(asiento);
        entrada.setReserva(reserva);
        entrada.setPrecio(precioHoy);
        guardar(entrada);
    }

    public List<String> sacarEstadosAsientos(Proyeccion proyeccion){
        //sacar todas las reservas con el id de esta proyeccion
        List<Entrada> asientosProyeccion = entradaRepository.findEntradasProyeccion(proyeccion.getId());
        List<String> asientosNames = new ArrayList<String>();
        for (Entrada entrada : asientosProyeccion) {
            Asiento reservado = entrada.getAsiento();
            asientosNames.add(reservado.getName().toLowerCase());
        }
        return asientosNames;
    }

}
