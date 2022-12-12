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

    public List<Sala> findAll () {
        return salaRepository.findSalasOrdByNombre();
    }

    public Optional<Sala> buscarPorId(Long id) {
        return salaRepository.findOptionalBy(id);
    }

    public void guardar(Sala sala){salaRepository.save(sala);}

    public Sala salaDelAsiento(Asiento asiento){
        List<Sala> salas = salaRepository.findAll();
        for (Sala sala: salas) {
            for (Asiento asientoSala: sala.getAsientos()) {
                if (asientoSala.getId().equals(asiento.getId())) {
                    return sala;
                }
            }
        }
        /*for (int i = 0; i < salas.size(); i++) {
            System.out.println(asiento.getId() + " ----------------------------" +salas.get(i).getAsientos().size()+
                    "-------------------------------------- ");
            for (int j = 0; j < salas.get(i).getAsientos().size(); j++) {
                Asiento asientoBucle = salas.get(i).getAsientos().get(i);
                System.out.println(asiento.getId() + " ------------------------------------------------------------------ " + asientoBucle.getId());
                if (asientoBucle.getId().equals(asiento.getId())) {
                    System.out.println("NFUNSOIOONSAONSN");
                    return salas.get(i);
                }
            }
        }*///todo UNIR LAS SALAS CON SUS ASIENTOS EN LA TABLA SALA_ASIENTO
        return null;
    }

}
