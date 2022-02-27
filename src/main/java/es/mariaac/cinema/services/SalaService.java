package es.mariaac.cinema.services;

import es.mariaac.cinema.entities.Asiento;
import es.mariaac.cinema.entities.Proyeccion;
import es.mariaac.cinema.entities.Sala;
import es.mariaac.cinema.repositories.SalaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.*;

@ApplicationScoped
public class SalaService {
    @Inject
    SalaRepository salaRepository;

    public List<Sala> findAll () {
        return salaRepository.findAll();
    }

    public Optional<Sala> buscarPorId(Long id) {
        return salaRepository.findOptionalBy(id);
    }

    //Método usado para cargar los datos de los asientos (en lugar de manualmente)
    /*public Boolean establecerAsientos(Long idSala){
        Optional<Sala> salaOpt = buscarPorId(idSala);
        if (salaOpt.isEmpty()){
            return false;
        }

        Sala sala = salaOpt.get();
        String[] filaNum = {
                "1A", "1B", "1C", "1D", "1E", "1F",
                "2A", "2B", "2C", "2D", "2E", "2F",
                "3A", "3B", "3C", "3D", "3E", "3F",
                "4A", "4B", "4C", "4D", "4E", "4F",
                "5A", "5B", "5C", "5D", "5E", "5F",
                "6A", "6B", "6C", "6D", "6E", "6F", 
                "7A", "7B", "7C", "7D", "7E", "7F",
                "8A", "8B", "8C", "8D", "8E", "8F" 
                };
        for (int i = 0; i < filaNum.length-1; i++) {
            Asiento asiento = new Asiento();
            asiento.setFila(filaNum[i].substring(0,1));
            asiento.setLetra(filaNum[i].substring(1));
            asiento.setSala(sala);
            asientoService.guardar(asiento);
        }
        System.out.println(sala);

        return true;
    }*/

}
