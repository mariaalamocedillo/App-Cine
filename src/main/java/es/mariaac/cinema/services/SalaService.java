package es.mariaac.cinema.services;

import es.mariaac.cinema.entities.Asiento;
import es.mariaac.cinema.entities.Sala;
import es.mariaac.cinema.repositories.SalaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.*;

@ApplicationScoped
public class SalaService {
    @Inject
    SalaRepository salaRepository;
    @Inject
    AsientoService asientoService;

    public List<Sala> findAll () {
        return salaRepository.findAll();
    }

    public Optional<Sala> buscarPorId(Long id) {
        return salaRepository.findOptionalBy(id);
    }

    public Boolean establecerAsientos(Integer numAsientos, Long idSala){
        Optional<Sala> salaOpt = buscarPorId(idSala);
        if (salaOpt.isEmpty()){
            return false;
        }

        Sala sala = salaOpt.get();
        List<Asiento> asientos = new ArrayList<>();
            //8 asientos or fila
            //  5A 5B 5C 5D 5E 5F 5G 5H 5I
            //  "4A", "4B", "4C", "4D", "4E", "4F", "4G", "4H", "4I"
            //  3A 3B 3C 3D 3E 3F 3G 3H 3I
            //  2A 2B 2C 2D 2E 2F 2G 2H 2I
            //  1A 1B 1C 1D 1E 1F 1G 1H 1I
/*        do{        String[] letras = {"A", "B", "C", "D", "E", "F", "G", "H"};

            for (int i = 0; i < letras.length; i++) {
                Asiento asiento = new Asiento();
                asiento.setFila(letras[i]);
                asiento.setNumero(i++);
                asiento.setSala(sala);
                asientoService.guardar(asiento);
                asientos.add(asiento);
                numAsientos--;
            }
        } while (numAsientos>0);*/
        String[] filaNum = {
                "6A", "6B", "6C", "6D", "6E", "6F", "6G", "6H", "6I",
                "5A", "5B", "5C", "5D", "5E", "5F", "5G", "5H", "5I",
                "4A", "4B", "4C", "4D", "4E", "4F", "4G", "4H", "4I",
                "3A", "3B", "3C", "3D", "3E", "3F", "3G", "3H", "3I",
                "2A", "2B", "2C", "2D", "2E", "2F", "2G", "2H", "2I",
                "1A", "1B", "1C", "1D", "1E", "1F", "1G", "1H", "1I"
                };
        for (int i = 0; i < filaNum.length-1; i++) {
            Asiento asiento = new Asiento();
            asiento.setFila(filaNum[i].substring(1));
            asiento.setNumero(Integer.parseInt(filaNum[i].substring(0,1)));
            asiento.setSala(sala);
            asientoService.guardar(asiento);
            asientos.add(asiento);

            System.out.println(asiento);
        }
        sala.setAsientos(asientos);
        salaRepository.save(sala);
        return true;
    }
}
