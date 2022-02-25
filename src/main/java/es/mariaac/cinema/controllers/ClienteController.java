package es.mariaac.cinema.controllers;

import es.mariaac.cinema.entities.Cliente;
import es.mariaac.cinema.entities.Reserva;
import es.mariaac.cinema.services.ClienteService;
import es.mariaac.cinema.services.ReservaService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.binding.BindingResult;
import jakarta.mvc.binding.ParamError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.executable.ExecutableType;
import jakarta.validation.executable.ValidateOnExecution;
import jakarta.ws.rs.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Slf4j
@Path("/usuario")
@Controller
@RequestScoped
public class ClienteController {
    @Inject
    private Models models;

    @Inject
    ClienteService clienteService;

    @Inject
    ReservaService reservaService;

    @Inject
    HttpServletRequest request;

    @Inject
    private BindingResult bindingResult;

    @Inject
    private Mensaje mensaje;

    @Inject
    private Errores errores;

    @GET
    @Path("/")
    public String index() {
        return "perfil/login";
    }

    @POST
    @Path("login")
    public String login(@FormParam("email") String email, @FormParam("contrasena") String psswd) {
        if (clienteService.logear(email, psswd)) {
            models.put("reservas", reservaService.findReservas(clienteService.buscarPorEmail(email).getId()));
            models.put("reservasAntiguas", reservaService.findReservasAntiguas(clienteService.buscarPorEmail(email).getId()));
            models.put("cliente", clienteService.buscarPorEmail(email));
            //HttpSession session = request.getSession();
            //session.setAttribute("cliente", clienteService.buscarPorEmail(email).getId());
            return "perfil/perfil";
        } else{
            models.put("mensajeError", "Nombre de usuario o contraseña inválido");
            return "perfil/login";
        }
    }
    @POST
    @Path("logout")
    public String logout() {
        //HttpSession session = request.getSession();
        //session.invalidate();
        return "perfil/login";
    }

    @GET
    @Path("perfil")
    public String perfil() {
        HttpSession session = request.getSession();
        Optional<Cliente> cliente = clienteService.buscarPorId(Long.parseLong(session.getAttribute("cliente").toString()));
        if (cliente.isPresent()) {
            models.put("cliente", cliente.get());
            return "perfil/signup";
        }
        return "redirect:login";
    }

    @GET
    @Path("registro")
    public String nueva() {
        Cliente user = new Cliente();
        models.put("cliente", user);
        return "perfil/signup";
    }

    @GET
    @Path("canjeo/{id}")
    public String canjeo(@PathParam("id") Long id) {
        Optional<Reserva> reserva = reservaService.buscarPorId(id);
        if (reserva.isEmpty())
            return "redirect:perfil";
        models.put("reserva", reserva.get());
        return "reserva/canjeo";
    }

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
        //HttpSession session = request.getSession();
        //session.setAttribute("cliente", clienteService.buscarPorEmail(email).getId());

        return "perfil/perfil";
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
