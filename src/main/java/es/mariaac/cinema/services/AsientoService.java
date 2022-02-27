package es.mariaac.cinema.services;

import es.mariaac.cinema.entities.Asiento;
import es.mariaac.cinema.repositories.AsientoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
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
            String name = asiento.getName();
            if (name.trim().equalsIgnoreCase(string.trim())){
                return asiento;
            }
        }
        return null;
    }
}
