package es.mariaac.cinema.services;

import es.mariaac.cinema.entities.*;
import es.mariaac.cinema.repositories.EntradaRepository;
import es.mariaac.cinema.repositories.ReservaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.*;

@ApplicationScoped
public class EntradaService {
    @Inject
    EntradaRepository entradaRepository;

    @Inject
    ReservaRepository reservaRepository;

    public List<Entrada> findAll () {
        return entradaRepository.findAll();
    }

    public Optional<Entrada> buscarPorId(Long id) {
        return entradaRepository.findOptionalBy(id);
    }

    public Entrada guardar(Entrada asiento){
        return entradaRepository.save(asiento);
    }

    public Entrada reservarAsiento(Asiento asiento,Precios precio){
        Entrada entrada = new Entrada();
        entrada.setAsiento(asiento);
        entrada.setPrecio(precio);
        return guardar(entrada);
    }

    public List<String> sacarEstadosAsientos(Proyeccion proyeccion){
        //sacar todas las reservas con el id de esta proyeccion
        List<Entrada> asientosProyeccion = reservaRepository.findEntradasProyeccion(proyeccion.getId());
        List<String> asientosNames = new ArrayList<String>();
        for (Entrada entrada : asientosProyeccion) {
            Asiento reservado = entrada.getAsiento();
            asientosNames.add(reservado.getName().toLowerCase());
        }
        return asientosNames;
    }

}
