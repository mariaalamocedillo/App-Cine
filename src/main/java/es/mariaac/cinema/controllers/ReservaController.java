package es.mariaac.cinema.controllers;

import es.mariaac.cinema.entities.*;
import es.mariaac.cinema.repositories.AsientoReservadoRepository;
import es.mariaac.cinema.repositories.ReservaRepository;
import es.mariaac.cinema.services.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.binding.BindingResult;
import jakarta.mvc.binding.ParamError;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.executable.ExecutableType;
import jakarta.validation.executable.ValidateOnExecution;
import jakarta.ws.rs.*;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.extern.slf4j.Slf4j;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

@Slf4j
@Path("/reserva")
@Controller
@RequestScoped
public class ReservaController {
    @Inject
    private Models models;

    @Inject
    ReservaService reservaService;

    @Inject
    ClienteService clienteService;

    @Inject
    AsientoService asientoService;

    @Inject
    SalaService salaService;

    @Inject
    AsientoReservadoService asientoReservadoService;

    @Inject
    ProyeccionService proyeccionService;

    @Inject
    private BindingResult bindingResult;

    @Inject
    private Mensaje mensaje;

    @Inject
    private Errores errores;

    @GET
    @Path("pelicula/{id}")
    public String paso1(@PathParam("id") Long id) {
        Optional<Proyeccion> proyeccion = proyeccionService.buscarPorId(id);

        if (!proyeccion.isPresent()){
            models.put("mensajeError", "Hubo un problema con su reserva, inténtelo más tarde");
            return "redirect:pelicula";
        }

        models.put("proyeccion", proyeccion.get());
        return "reserva/paso-1";
    }

    @GET
    @Path("sala/{id}")
    public String asientosSalas(@PathParam("id") Long id) {
        Sala sala = new Sala();
        if (salaService.buscarPorId(id).isPresent())
            sala = salaService.buscarPorId(id).get();
        salaService.establecerAsientos(sala.getId());
        models.put("sala", sala);
        return "admin/edit-pelicula";
    }


    @POST
    @Path("paso2")
    public String paso1Submit(@FormParam("id") Long id, @FormParam("email") String email,
                        @FormParam("psswd") String psswd) {
        Reserva reserva = new Reserva();
        Optional<Proyeccion> proyeccion = proyeccionService.buscarPorId(id);
        if (proyeccion.isEmpty()){
            mensaje.setTexto("Hubo un problema con su reserva, inténtelo más tarde");
            return "reserva/paso-1";
        }
        models.put("asientosOcupados", asientoReservadoService.sacarEstadosAsientos(proyeccion.get()));
        reserva.setProyeccion(proyeccion.get());
        reserva.setPagada(false);
        reserva.setActiva(true);
        reserva.setReservada(false);
        reserva.setPrecio(0F);

        //si no encontramos los datos del cliente, volvemos al paso 1
        if (!clienteService.logear(email, psswd)){
            models.put( "proyeccion", proyeccion.get());
            mensaje.setTexto("Email o contraseña no válidos");
            return "reserva/paso-1";
        }
        Cliente cliente = clienteService.buscarPorEmail(email);
        reserva.setCliente(cliente);
        models.put("precio", "8.5");

        //guardamos la reserva
        try {
            reservaService.guardar(reserva);
            models.put("reserva", reserva);
            return "reserva/paso-2";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mensaje.setTexto("Ocurrió un error y la reserva "+ e +" " + reserva.getId() + " no se pudo realizar.");
            return "reserva/paso-1";
        }
    }


    @POST
    @Path("paso3")
    public String paso2Submit(@FormParam("reserva") Long reservaId, @FormParam("precio") String precio,
                        @FormParam("ids") String idsSeats) {
        Optional<Reserva> reservaOpt = reservaService.buscarPorId(reservaId);
        if (reservaOpt.isEmpty()){
            mensaje.setTexto("Hubo un problema con su reserva, inténtelo más tarde");
            return "reserva/paso-2";
        }
        Reserva reserva = reservaOpt.get();
        models.put("asientosOcupados", asientoReservadoService.sacarEstadosAsientos(reserva.getProyeccion()));
        if (idsSeats == null || idsSeats.equals("")){
            mensaje.setTexto("Debe seleccionar al menos un asiento");
            models.put("reserva", reserva);
            return "reserva/paso-2";
        }
        models.put("precio", "8.5");

        reserva.setPrecio(Float.valueOf(precio));
        reserva.setReservada(true);
        reservaService.guardar(reserva);
        models.put("reserva", reserva);

        System.out.println(idsSeats);

        String[] sitios = idsSeats.trim().replaceAll(" ", "").split(",");

        //lista de asientos reservados (hay que asocierlo a asientoreservado) -> método de Reservade asiento

        List<Asiento> asientos = new ArrayList<>();

        System.out.println("Num sitios: "+sitios.length);

        for (String id: sitios) {
            Asiento asientoTemp = asientoService.buscarPorName(id);
            if (asientoTemp != null){
                asientos.add(asientoTemp);
                try{
                    asientoReservadoService.reservarAsiento(reserva, asientoTemp);
                }catch (Exception e) {
                    log.error(e.getMessage(), e);
                    mensaje.setTexto("Ocurrió un error y no pudimos almacenar la reserva de los asientos. ");
                    return "reserva/paso-2";
                }
            }
        }
        //guardamos la reserva
        try {
            reservaService.guardar(reserva);
            models.put("entradas", asientos);
            return "reserva/paso-3";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mensaje.setTexto("Ocurrió un error y la reserva " + reserva.getId() + " no se pudo realizar.");
            return "reserva/paso-2";
        }
    }



    @POST
    @Path("/pagada")
    @ValidateOnExecution(type = ExecutableType.NONE)
    public String nuevaSubmit(@FormParam("num-tarjeta") String numTarj, @FormParam("cc-cvv") String numCvv,
                              @FormParam("reserva") Long idReserva) {
        Optional<Reserva> reservaOpt = reservaService.buscarPorId(idReserva);
        if (reservaOpt.isEmpty()){
            mensaje.setTexto("Hubo un problema con su reserva, inténtelo más tarde");
            return "reserva/paso-3";
        }
        Reserva reserva = reservaOpt.get();
        if (numTarj == null){
            mensaje.setTexto("Debe introducir el número de su tarjeta");
            models.put("reserva", reserva);
            return "reserva/paso-3";
        }
        if (!validateCreditCard(numTarj, numCvv)){
            mensaje.setTexto("Debe introducir un número de tarjeta válido");
            models.put("reserva", reserva);
            return "reserva/paso-3";
        }

        reserva.setPagada(true);    //la establecemos como pagada

        models.put("entradas", models.get("entradas"));

        try {
            reservaService.guardar(reserva);

            mensaje.setTexto("La reserva de " + reserva.getProyeccion().getPelicula().getTitulo() + " se guardó satisfactoriamente ! ");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mensaje.setTexto("Ocurrió un error y la reserva de " + reserva.getProyeccion().getPelicula().getTitulo() + ") no se pudo almacenar.");
            return "reserva/paso-3";
        }
        models.put("reserva", reservaService.findReservas(reserva.getCliente().getId()));

        return "perfil/perfil";
    }
    private Boolean validateCreditCard(String card, String cvc) {
        //12345678903555 is a valid credit card number
        //012850003580200 is a valid credit card number
        //5061      561  are cvc valid number

        int[] ints = new int[card.length()];
        for (int i = 0; i < card.length(); i++) {
            ints[i] = Integer.parseInt(card.substring(i, i + 1));
        }
        for (int i = ints.length - 2; i >= 0; i = i - 2) {
            int j = ints[i];
            j = j * 2;
            if (j > 9) {
                j = j % 10 + 1;
            }
            ints[i] = j;
        }
        int sum = 0;
        for (int anInt : ints) {
            sum += anInt;
        }

        // Regex to check valid CVV number.
        String regex = "^[0-9]{3,4}$";
        Pattern p = Pattern.compile(regex);
        if (cvc == null)
            return false;
        Matcher m = p.matcher(cvc);

        return sum % 10 == 0 && m.matches();
    }


        private void setErrores() {
        errores.setErrores(bindingResult.getAllErrors()
                .stream()
                .collect(toList()));
    }

    private void logErrores() {
        bindingResult.getAllErrors()
                .stream()
                .forEach((ParamError t) ->
                        log.debug("Error de validación: {} {}", t.getParamName(), t.getMessage()));
    }

}
