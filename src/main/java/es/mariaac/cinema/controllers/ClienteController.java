package es.mariaac.cinema.controllers;

import es.mariaac.cinema.entities.Cliente;
import es.mariaac.cinema.entities.Reserva;
import es.mariaac.cinema.services.ClienteService;
import es.mariaac.cinema.services.ReservaService;
import es.mariaac.cinema.services.TestQRCode;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.executable.ExecutableType;
import jakarta.validation.executable.ValidateOnExecution;
import jakarta.ws.rs.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Objects;
import java.util.Optional;


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
    private Mensaje mensaje;

    @GET
    @Path("/")
    public String index() {return "perfil/login";}

    @POST
    @Path("login")
    public String login(@FormParam("email") String email, @FormParam("contrasena") String psswd) {
        if (clienteService.logear(email, psswd)) {
            HttpSession session = request.getSession();
            session.setAttribute("clienteId", clienteService.buscarPorEmail(email).get().getId());
            session.setAttribute("clienteName", clienteService.buscarPorEmail(email).get().getNombre());
            if (session.getAttribute("rutaOrigen") != null){
                String rutaOrigen = session.getAttribute("rutaOrigen").toString();
                session.removeAttribute("rutaOrigen");
                return rutaOrigen;
            }
            return "redirect:usuario/perfil";
        } else{
            mensaje.setTexto("Nombre de usuario o contraseña inválido");
            return "perfil/login";
        }
    }
    @GET
    @Path("logout")
    public String logout() {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:usuario";
    }

    @GET
    @Path("perfil")
    public String perfil() {
        try {
            HttpSession session = request.getSession();
            Optional<Cliente> cliente = clienteService.buscarPorId(Long.parseLong(session.getAttribute("clienteId").toString()));
            if (cliente.isPresent()) {
                models.put("cliente", cliente.get());
                models.put("reservas", reservaService.findReservas(cliente.get().getId()));
                models.put("reservasAntiguas", reservaService.findReservasAntiguas(cliente.get().getId()));
                return "perfil/perfil";
            }
        } catch (NullPointerException ignored){
        }
        return "redirect:usuario";
    }

    @GET
    @Path("canjeo/{id}")
    public String canjeo(@PathParam("id") Long id) {
        Cliente cliente;
        try {
            HttpSession session = request.getSession();
            Optional<Cliente> clienteOpt = clienteService.buscarPorId(Long.parseLong(session.getAttribute("clienteId").toString()));
            cliente = clienteOpt.orElseGet(Cliente::new);
            if (clienteOpt.isEmpty()){
                return "redirect:usuario";
            }
        } catch (NullPointerException ignored){
            return "redirect:usuario";
        }
        Optional<Reserva> reserva = reservaService.buscarPorId(id);
        if (reserva.isEmpty())
            return "redirect:usuario/perfil";
        if (!Objects.equals(reserva.get().getCliente().getId(), cliente.getId()))
            return "redirect:usuario/logout";

        TestQRCode qr = new TestQRCode();

        ServletContext sc = request.getServletContext();
        String reportPath = sc.getRealPath("/resources/canjeos");

        File f = new File(reportPath+"/qrCode"+ reserva.get().getId() +".png");
        String text = "Reserva num.: " + id +
                " Sala: " + reserva.get().getProyeccion().getSala().getId()+
                "Num. asientos: " + reserva.get().getEntradas().size();
        try {
            qr.generateQR(f, text, 300, 300);
            System.out.println("QRCode Generated: " + f.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        models.put("reserva", reserva.get());
        return "reserva/canjeo";
    }

    @GET
    @Path("registro")
    public String nueva() {
        try {
            HttpSession session = request.getSession();
            Optional<Cliente> cliente = clienteService.buscarPorId(Long.parseLong(session.getAttribute("clienteId").toString()));
            if (cliente.isPresent()) {
                return "redirect:usuario/perfil";
            }
        } catch (NullPointerException ignored){
        }
        Cliente user = new Cliente();
        models.put("cliente", user);
        return "perfil/signup";
    }

    @POST
    @Path("/registro/submit")
    @ValidateOnExecution(type = ExecutableType.NONE)
    public String nuevaSubmit(@FormParam("email") String email, @FormParam("contrasena") String psswd,
                              @FormParam("tlfn") Long tlfn, @FormParam("nombre") String nombre) {
        Cliente cliente = new Cliente(nombre, tlfn, email, psswd);
        System.out.println(cliente);
        log.debug("Nuevo cliente recibido: {}", cliente);
        if (clienteService.buscarPorEmail(cliente.getEmail()).isPresent()){
            mensaje.setTexto("Este email ya está asociado a una cuenta");
            return "perfil/signup";
        }

        try {
            clienteService.guardar(cliente);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mensaje.setTexto("Ocurrió un error y la cuenta de " + cliente.getEmail() + " (" + cliente.getNombre() + ") no se pudo almacenar.");
            return "perfil/signup";
        }
        HttpSession session = request.getSession();
        session.setAttribute("clienteId", clienteService.buscarPorEmail(email).get().getId());
        session.setAttribute("clienteName", clienteService.buscarPorEmail(email).get().getNombre());
        //comprobamos si viene de otra página (del primer paso de una reserva)
        if (session.getAttribute("rutaOrigen") != null){
            String rutaOrigen = session.getAttribute("rutaOrigen").toString();
            session.removeAttribute("rutaOrigen");
            return rutaOrigen;
        }
        mensaje.setTexto("La cuenta de " + cliente.getEmail() + " se creó satisfactoriamente ! ");
        return "perfil/perfil";
    }

}
