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

    /**
     * Metodo envia al login
     *  Método que envia a la pagina de login
     *
     * @return pagina de login
     */
    @GET
    @Path("/")
    public String index() {return "perfil/login";}

    /**
     * Metodo logea usuario
     *  Método que almacena logea al usuario. En caso de ser el email de administrador,
     *  se redirige a la página de administrador
     *
     * @param email String email del user
     * @param psswd String contraseña del user
     * @return redireccion al login o al perfil del usuario si ya está logeado
     */
    @POST
    @Path("login")
    public String login(@FormParam("email") String email, @FormParam("contrasena") String psswd) {
        if (clienteService.logear(email, psswd)) {
            String rutaOrigen = compruebaRedireccion(email);
            if (!Objects.equals(rutaOrigen, "")){
                return rutaOrigen;
            }
            if (Objects.equals(email, "administrador@cinespetri.com")) {
                return "redirect:admin";
            }
            return "redirect:usuario/perfil";
        } else{
            mensaje.setTexto("Nombre de usuario o contraseña inválido");
            return "perfil/login";
        }
    }


    /**
     * Metodo logea usuario
     *  Método que almacena la proyección recibida y actualiza la informacion de la
     *  pelicula recibida (la correspondiente a dicha proyección)
     *
     * @return redireccion a horariosProyeccion() de AdminController
     */
    @GET
    @Path("logout")
    public String logout() {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:usuario";
    }

    /**
     * Metodo envia al perfil del usuario
     *  Método que obtiene la informacion del usuario, un listado de las reservas
     *  aun activas del cliente y un listado de las antiguas para mostrarlas en la
     *  dashboard
     *
     * @return redireccion a perfil de usuario
     */
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

    /**
     * Metodo que canjea una reserva
     *  Método que obtiene la información de una reserva, comprueba que pertenece al cliente que la canjea
     *  y crea un documento con un qr para el chequeo de la entrada.
     *
     * @return pagina con QR para canjeo
     */
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

    /**
     * Metodo que envia al formulario de registro
     *  Método que comprueba si hay una sesión de un cliente y la recupera para redirigir al perfil
     *  en caso de que sí, o crea un usuario provisional sin datos que envia al formulario de registro
     *
     * @return formulario de registro
     */
    @GET
    @Path("registro")
    public String nueva() {
        try {
            HttpSession session = request.getSession();
            Optional<Cliente> cliente = clienteService.buscarPorId(Long
                    .parseLong(session.getAttribute("clienteId").toString()));
            if (cliente.isPresent()) {
                return "redirect:usuario/perfil";
            }
        } catch (NullPointerException ignored){
        }
        Cliente user = new Cliente();
        models.put("cliente", user);
        return "perfil/signup";
    }

    /**
     * Metodo que registra al usuario
     *  Método que registra a un nuevo usuario segun los datos enviados desde el formulario
     *  de registro. Comprueba que no hay ningun usuario con el mismo email, en tal caso reenvia
     *  a l formulario para indicarselo. Si viene de otra pagina, se reenvia a ella, si no, se
     *  envia a la dashboard de usuario una vez se registra
     *
     * @param email String email del usuario
     * @param psswd String contraseña del usuario
     * @param tlfn Long numero de telefono
     * @param nombre String nombre del usuario
     * @return pagina de registro o perfil del usuario
     */
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
        String rutaOrigen = compruebaRedireccion(email);
        if (!Objects.equals(rutaOrigen, "")){
            return rutaOrigen;
        }
        mensaje.setTexto("La cuenta de " + cliente.getEmail() + " se creó satisfactoriamente ! ");
        return "perfil/perfil";
    }


    /**
     * Metodo que canjea una reserva
     *  Método que añade la informacion del usuario en la sesión y devuelve
     *  una string con la ruta desde donde viene el usuario en caso de que venga
     *  de otra pagina y nada si no
     *
     * @param email String email del usuario
     * @return String ruta de origen o vacia
     */
    public String compruebaRedireccion(String email){
        HttpSession session = request.getSession();
        session.setAttribute("clienteId", clienteService.buscarPorEmail(email).get().getId());
        session.setAttribute("clienteName", clienteService.buscarPorEmail(email).get().getNombre());
        if (session.getAttribute("rutaOrigen") != null){
            String rutaOrigen = session.getAttribute("rutaOrigen").toString();
            session.removeAttribute("rutaOrigen");
            return rutaOrigen;
        }
        return "";
    }

}
