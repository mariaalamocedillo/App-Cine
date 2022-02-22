package es.mariaac.cinema.services;

import es.mariaac.cinema.entities.Asiento;
import es.mariaac.cinema.entities.Cliente;
import es.mariaac.cinema.entities.Sala;
import es.mariaac.cinema.repositories.AsientoRepository;
import es.mariaac.cinema.repositories.AsientoReservadoRepository;
import es.mariaac.cinema.repositories.SalaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@ApplicationScoped
public class AsientoService {
    @Inject
    AsientoRepository asientoRepository;

    public List<Asiento> findAll () {
        return asientoRepository.findAll();
    }

    public Optional<Asiento> buscarPorId(Long id) {
        return asientoRepository.findOptionalBy(id);
    }

    public void guardar(Asiento asiento){asientoRepository.save(asiento);}

    public Asiento buscarPorName(String string){
        List<Asiento> asientos = findAll();
        for (Asiento asiento: asientos) {
            String name = asiento.getFila() + asiento.getLetra();
            if (name.trim().equalsIgnoreCase(string.trim())){
                return asiento;
            }
        }
        return null;
    }

}
