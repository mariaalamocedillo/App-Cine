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

        salaService.establecerAsientos(2, sala.getId());
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
        reserva.setProyeccion(proyeccion.get());
        reserva.setPagada(false);
        reserva.setActiva(false);
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

        //guardamos la reserva
        try {
            models.put("asientos-estados", asientoReservadoService.sacarEstadosAsientos(reserva.getProyeccion()));
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
        if (idsSeats == null || idsSeats.equals("")){
            mensaje.setTexto("Debe seleccionar al menos un asiento");
            models.put("reserva", reserva);
            return "reserva/paso-2";
        }

        reserva.setActiva(true);    //la establecemos como activa y actualizamos el precio
        reserva.setPrecio(Float.valueOf(precio));
        reserva.setReservada(true);
        reservaService.guardar(reserva);

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
                    mensaje.setTexto("Ocurrió un error y la reserva de los asientos no se pudo almacenar. ");
                    return "reserva/paso-2";
                }
            }
        }
        //guardamos la reserva
        try {
            reservaService.guardar(reserva);
            models.put("reserva", reserva);
            return "reserva/paso-3";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mensaje.setTexto("Ocurrió un error y la reserva " + reserva.getId() + " no se pudo realizar.");
            return "reserva/paso-1";
        }

    }


    /*
    @POST
    @Path("/registro/submit")
    @ValidateOnExecution(type = ExecutableType.NONE)
    public String nuevaSubmit(@FormParam("email") String email, @FormParam("contrasena") String psswd,
                              @FormParam("tlfn") Long tlfn, @FormParam("nombre") String nombre) {
        Cliente cliente = new Cliente(nombre, tlfn, email, psswd);
        System.out.println(cliente);
        log.debug("Nuevo cliente recibido: {}", cliente);
        if (clienteService.buscarPorEmail(cliente.getEmail()) != null){
            mensaje.setTexto("Este email ya está asociado a una cuenta");
            return "perfil/signup";
        }

        try {
            clienteService.guardar(cliente);
            mensaje.setTexto("La cuenta de " + cliente.getEmail() + " se guardó satisfactoriamente ! ");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mensaje.setTexto("Ocurrió un error y la cuenta de " + cliente.getEmail() + " (" + cliente.getNombre() + ") no se pudo almacenar.");
            return "perfil/signup";
        }

        return "perfil/perfil";
    }



    @POST
    @Path("/registro/submit")
    @ValidateOnExecution(type = ExecutableType.NONE)
    public String nuevaSubmit(@Valid @BeanParam Cliente cliente) {
        log.debug("Nuevo cliente recibido: {}", cliente);
        if (clienteService.buscarPorEmail(cliente.getEmail()) != null){
            mensaje.setTexto("Este email ya está asociado a una cuenta");
            return "perfil/signup";
        }
        if (bindingResult.isFailed()) {
            logErrores();
            setErrores();
            models.put("cliente", cliente);
            return "perfil/signup";
        }
        try {
            clienteService.guardar(cliente);
            mensaje.setTexto("La cuenta de " + cliente.getEmail() + " se guardó satisfactoriamente ! ");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mensaje.setTexto("Ocurrió un error y la cuenta de " + cliente.getEmail() + " (" + cliente.getNombre() + ") no se pudo almacenar.");
            return "perfil/signup";
        }

        return "perfil/perfil";
    }
*/

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
