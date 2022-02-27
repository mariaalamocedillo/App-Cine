package es.mariaac.cinema.services;

import es.mariaac.cinema.entities.Reserva;
import es.mariaac.cinema.repositories.ReservaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ReservaService {
    @Inject
    ReservaRepository reservaRepository;

    public List<Reserva> findAll () {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> buscarPorId(Long id) {
        return reservaRepository.findOptionalBy(id);
    }

    public void guardar(Reserva reserva){reservaRepository.save(reserva);}

    public List<Reserva> findReservas(Long id){return reservaRepository.findReservas(id);}

    public List<Reserva> findReservasAntiguas(Long id){return reservaRepository.findReservasAntiguas(id);}

}
