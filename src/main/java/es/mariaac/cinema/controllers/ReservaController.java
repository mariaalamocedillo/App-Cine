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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.executable.ExecutableType;
import jakarta.validation.executable.ValidateOnExecution;
import jakarta.ws.rs.*;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.extern.slf4j.Slf4j;

import javax.swing.text.html.Option;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    HttpServletRequest request;

    @Inject
    AsientoReservadoService asientoReservadoService;

    @Inject
    ProyeccionService proyeccionService;


    @Inject
    private Mensaje mensaje;


    @GET
    @Path("pelicula/{id}")
    public String paso1(@PathParam("id") Long id) {
        Optional<Proyeccion> proyeccion = proyeccionService.buscarPorId(id);
        if (proyeccion.isEmpty()){
            models.put("mensajeError", "Hubo un problema con su reserva, inténtelo más tarde");
            return "redirect:pelicula";
        }
        models.put("proyeccion", proyeccion.get());
        return "reserva/paso-1";
    }

//TODO BORRAR ESTO y el método de servicio que usa

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
    public String paso1Submit(@FormParam("id") Long id) {
        //comprobamos si está loggeado (si no lo está, le redigirá automáticamente a login)
        Cliente cliente = compruebaSesion();
        if (cliente == null)
            return "redirect:usuario";

        Reserva reserva = new Reserva();
        Optional<Proyeccion> proyeccion = proyeccionService.buscarPorId(id);
        if (proyeccion.isEmpty()){
            mensaje.setTexto("Hubo un problema con su reserva, inténtelo más tarde");
            return "reserva/paso-1";
        }

        //actualizamos la reserva
        reserva.setProyeccion(proyeccion.get());
        reserva.setPagada(false);
        reserva.setActiva(true);
        reserva.setReservada(false);
        reserva.setPrecio(0F);


        //configuramos los datos que necesitaremos
        reserva.setCliente(cliente);
        models.put("asientosOcupados", asientoReservadoService.sacarEstadosAsientos(proyeccion.get()));
        models.put("precio", "8.5");

        //guardamos la reserva
        try {
            reservaService.guardar(reserva);
            HttpSession session = request.getSession();
            session.setAttribute("reservaId", reserva.getId());
            models.put("reserva", reserva);
            return "reserva/paso-2";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mensaje.setTexto("Ocurrió un error y la reserva no se pudo realizar.");
            return "reserva/paso-1";
        }
    }


    @POST
    @Path("paso3")
    public String paso2Submit(@FormParam("precio") String precio, @FormParam("ids") String idsSeats) {
        Cliente cliente = compruebaSesion();
        if (cliente == null)
            return "redirect:usuario";
        HttpSession session = request.getSession();
        Reserva reserva ;
        try {
            Optional<Reserva> reservaOpt = reservaService.buscarPorId(Long.parseLong(session.getAttribute("reservaId").toString()));
            if (reservaOpt.isEmpty()) {
                mensaje.setTexto("Hubo un problema con su reserva, inténtelo más tarde");
                return "reserva/paso-2";
            }
            reserva = reservaOpt.get();
        } catch (NullPointerException n){
            mensaje.setTexto("Hubo un problema con su reserva, inténtelo más tarde");
            return "reserva/paso-2";
        }
        models.put("asientosOcupados", asientoReservadoService.sacarEstadosAsientos(reserva.getProyeccion()));
        if (idsSeats == null || idsSeats.equals("")){
            mensaje.setTexto("Debe seleccionar al menos un asiento");
            models.put("reserva", reserva);
            return "reserva/paso-2";
        }
        models.put("precio", "8.5");
        //comprueba si cliente de la reserva es igual al de la sesión
        if (!Objects.equals(cliente.getId(), reserva.getCliente().getId())){
            mensaje.setTexto("Hubo un error con su reserva, inténtelo de nuevo más tarde");
            return "redirect:reserva/paso-1/"+reserva.getProyeccion().getId();
        }

        reserva.setPrecio(Float.valueOf(precio));
        reserva.setReservada(true);
        reservaService.guardar(reserva);
        models.put("reserva", reserva);

        String[] sitios = idsSeats.trim().replaceAll(" ", "").split(",");
        //lista de asientos reservados (hay que asociarlo a asientoreservado) -> método de Reserva de asiento
        List<Asiento> asientos = new ArrayList<>();

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
    public String nuevaSubmit(@FormParam("num-tarjeta") String numTarj, @FormParam("cc-cvv") String numCvv) {
        HttpSession session = request.getSession();
        Reserva reserva ;
        try {
            Optional<Reserva> reservaOpt = reservaService.buscarPorId(Long.parseLong(session.getAttribute("reservaId").toString()));
            if (reservaOpt.isEmpty()) {
                mensaje.setTexto("Hubo un problema con su reserva, inténtelo más tarde");
                return "reserva/paso-3";
            }
            reserva = reservaOpt.get();
        } catch (NullPointerException n){
            mensaje.setTexto("Hubo un problema con su reserva, inténtelo más tarde");
            return "reserva/paso-3";
        }

        Cliente cliente = compruebaSesion();
        if (cliente == null)
            return "redirect:usuario";
        //comprueba si cliente de la reserva es igual al de la sesión
        if (!Objects.equals(cliente.getId(), reserva.getCliente().getId())){
            mensaje.setTexto("Hubo un error con su reserva, inténtelo de nuevo más tarde");
            return "redirect:reserva/paso-1/"+reserva.getProyeccion().getId();
        }

        if (numTarj == null){
            mensaje.setTexto("Debe introducir el número de su tarjeta");
            models.put("reserva", reserva);
            return "reserva/paso-3";
        }else if (!validateCreditCard(numTarj, numCvv)){
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
        session.removeAttribute("reservaId");

        return "redirect:usuario/perfil";
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

    //TODO: que pase el enlace de donde qeuría ir (id de peli para reservar)
     //           mensaje.setTexto("Debe entrar en su cuenta antes de hacer una reserva");


    private Cliente compruebaSesion(){
        Cliente cliente;
        try {
            HttpSession session = request.getSession();
            Optional<Cliente> clienteOpt = clienteService.buscarPorId(Long.parseLong(session.getAttribute("clienteId").toString()));
            if (clienteOpt.isEmpty())
                return null;
            cliente = clienteOpt.get();
        } catch (NullPointerException n){
            return null;
        }
        return cliente;
    }
}
