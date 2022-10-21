package es.mariaac.cinema.services;

import es.mariaac.cinema.entities.Precios;
import es.mariaac.cinema.entities.Reserva;
import es.mariaac.cinema.repositories.PreciosRepository;
import es.mariaac.cinema.repositories.ReservaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.*;

@ApplicationScoped
public class ReservaService {
    @Inject
    ReservaRepository reservaRepository;

    @Inject
    PreciosRepository preciosRepository;

    public List<Reserva> findAll () {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> buscarPorId(Long id) {
        return reservaRepository.findOptionalBy(id);
    }

    public void guardar(Reserva reserva){reservaRepository.save(reserva);}

    public List<Reserva> findReservas(Long id){return reservaRepository.findReservas(id);}

    public List<Reserva> findReservasAntiguas(Long id){return reservaRepository.findReservasAntiguas(id);}

    public Optional<Precios> selectPrice () {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        int diaSemana = cal.get(Calendar.DAY_OF_WEEK);
        if (diaSemana == 4)
            return preciosRepository.findByNombre("Dia del espectador");
        return preciosRepository.findByNombre("Normal");
    }

    public List<Precios> listadoPrecios() {
        List<Precios> precios = preciosRepository.findAll();
        if (preciosRepository.findByNombre("Dia del espectador").isPresent()){
            precios.remove(preciosRepository.findByNombre("Dia del espectador").get());
            precios.remove(preciosRepository.findByNombre("Normal").get());
        }
        return precios;
    }

    public Optional<Precios> buscarPrecioPorId(Long id) {
        return preciosRepository.findOptionalBy(id);
    }


    public List<Double> listadoVentas () {
        List<Double> listaVentas = new ArrayList<>();
        listaVentas.add(reservaRepository.ventasHoy());
        listaVentas.add(reservaRepository.ventasEsteMes());
        listaVentas.add(reservaRepository.ventasEsteAño());
        return listaVentas;
    }

    public Float listadoOcupacion () {
        List<Float> listado = new ArrayList<>();
        listado.add(reservaRepository.ocupacionHoy());



        return reservaRepository.ocupacionHoy();
    }


}
