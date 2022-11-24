package es.mariaac.cinema.controllers;

import es.mariaac.cinema.entities.*;
import es.mariaac.cinema.services.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.executable.ExecutableType;
import jakarta.validation.executable.ValidateOnExecution;
import jakarta.ws.rs.*;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    HttpServletRequest request;

    @Inject
    EntradaService entradaService;

    @Inject
    ProyeccionService proyeccionService;


    @Inject
    private Mensaje mensaje;


    /**
     *  Método que obtiene una proyección según el id de la proyección obtenido
     *  por parametro, si no se encuentra dicha proyección, se redirige a la
     *  cartelera. En caso de sí encontrar dicha proyección, redirige al primer
     *  paso de la reserva
     *
     * @param idProyeccion      id de una proyeccion
     * @return página primer paso de reserva
     * @since 1.0
     */
    @GET
    @Path("pelicula/{id}")
    public String paso1(@PathParam("id") Long idProyeccion) {
        Optional<Proyeccion> proyeccion = proyeccionService.buscarPorId(idProyeccion);
        if (proyeccion.isEmpty())
            return "redirect:cartelera";
        models.put("proyeccion", proyeccion.get());
        return "reserva/paso-1";
    }

    /**
     *  Método que obtiene una proyección según el id de la proyección pasado
     *  por parámetro tras completar el paso 1 de una reserva. Se comprueba
     *  si existe dicha proyección, en caso de que no, se devuelve al paso 1.
     *  Si la proyección es correcta, se procede a identificar al usuario; se
     *  envia a la pagina de login si no ha entrado en su cuenta aun, con un
     *  enlace para establecer la redirección a esta página de nuevo. Tras esto,
     *  se crea la reserva y se establece como activa. Obtenemos y añadimos al
     *  modelo los asientos ya ocupados de dicha proyeccion, el precio por defecto
     *  de la entrada según el día de la semana (por el dia del expectaador en
     *  miercoles), y se procede a almacenar la reserva, a la cual se crea una
     *  sesión, y un timer. Si tod va correctamente, se envia a la segunda
     *  pantalla de reserva
     *
     *
     * @param idProyeccion      id de una proyeccion
     * @return página siguiente paso de reserva
     * @since 1.0
     */
    @POST
    @Path("paso2")
    public String paso1Submit(@FormParam("id") Long idProyeccion) {
        //comprobamos si está loggeado (si no lo está, le redigirá automáticamente a login)
        Optional<Proyeccion> proyeccion = proyeccionService.buscarPorId(idProyeccion);
        if (proyeccion.isEmpty()){
            mensaje.setTexto("Parece que hubo un problema con su reserva, inténtelo más tarde");
            return "reserva/paso-1";
        }
        Cliente cliente = compruebaSesion();
        if (cliente == null) {
            HttpSession session = request.getSession();
            session.setAttribute("rutaOrigen", "redirect:reserva/pelicula/" + proyeccion.get().getId());
            return "redirect:usuario";
        }

        Reserva reserva = new Reserva();
        //actualizamos la reserva
        reserva.setProyeccion(proyeccion.get());
        reserva.setPagada(false);
        reserva.setActiva(true);
        reserva.setReservada(false);

        //configuramos los datos que necesitaremos
        reserva.setCliente(cliente);
        models.put("asientosOcupados", entradaService.sacarEstadosAsientos(proyeccion.get()));

        models.put("precio", reservaService.selectPrice().get().getPrecioFinal());

        //guardamos la reserva
        try {
            reservaService.guardar(reserva);
            HttpSession session = request.getSession();
            session.setAttribute("reservaId", reserva.getId());
            if (session.getAttribute("reservaTimeCreation") == null)
                session.setAttribute("reservaTimeCreation", LocalDateTime.now());
            models.put("timerTime", session.getAttribute("reservaTimeCreation"));
            models.put("reserva", reserva);
            return "reserva/paso-2";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mensaje.setTexto("Ocurrió un error y la reserva no se pudo realizar.");
            return "reserva/paso-1";
        }
    }


    /**
     *  Método que comprueba la sesión del cliente (redirige al login si no se encuentra)
     *  y la reserva, así como el tiempo que ha pasado desde que se comenzó la reserva, pues
     *  a los clientes se les dan 10 minutos para poder liberar los asientos si no la completan.
     *  Se obtiene el id de reserva de la sesión y se procede a obtener dicha sesion,
     *  se redirige al paso anterior si no se encuentra un id o la sesión del id.Se
     *  comprueba que el cliente de la sesion obtenida concuerda con el de la reserva
     *  En caso de no tener ningun asiento seleccionado, se redirecciona al paso 2.
     *  Se procede a obtener el listado de descuentos, eliminando el 'dia del espectador' si
     *  es necesario. Actualizamos la reserva como reservada al tener ya asientos asignados
     *  y procedemos a crear las entradas con dicha información, y finalmente guardamos la
     *  reserva
     *
     *
     * @param idsSeats      id de una proyeccion
     * @param precio        precio total provisional
     * @return página siguiente paso de reserva
     * @since 1.0
     */
    @POST
    @Path("paso3")
    public String paso2Submit(@FormParam("ids") String idsSeats, @FormParam("precio") String precio) {
        //obtencion sesiones usuario y reserva
        Cliente cliente = compruebaSesion();
        if (cliente == null)
            return "redirect:usuario";
        HttpSession session = request.getSession();
        Reserva reserva ;
        try {
            Optional<Reserva> reservaOpt = reservaService.buscarPorId(Long
                    .parseLong(session.getAttribute("reservaId").toString()));
            if (reservaOpt.isEmpty()) {
                mensaje.setTexto("Hubo un problema con su reserva, inténtelo más tarde");
                return "reserva/paso-1";
            }
            reserva = reservaOpt.get();
        } catch (NullPointerException n){
            mensaje.setTexto("Hubo un problema con su reserva, inténtelo más tarde");
            return "reserva/paso-1";
        }
        if (compruebaTimerReserva()){
            reservaService.delete(reserva);
            mensaje.setTexto("Se expiró el tiempo para completar la reserva, inténtelo de nuevo");
            return "redirect:reserva/pelicula/" + reserva.getProyeccion().getId();
        }
        //comprueba si cliente de la reserva es igual al de la sesión
        if (!Objects.equals(cliente.getId(), reserva.getCliente().getId())){
            mensaje.setTexto("Hubo un error con su reserva, inténtelo de nuevo más tarde");
            return "redirect:reserva/paso-1/"+reserva.getProyeccion().getId();
        }

        //comprobacion de asientos seleccionados
        if (idsSeats == null || idsSeats.equals("")){
            models.put("asientosOcupados", entradaService.sacarEstadosAsientos(reserva.getProyeccion()));
            mensaje.setTexto("Debe seleccionar al menos un asiento");                                                       //TODO comprobar que haya filtro de esto en parte de js
            models.put("reserva", reserva);
            return "reserva/paso-2";
        }

        //obtencion del listado de precios
        Precios precioHoy = reservaService.selectPrice().get();
        models.put("precio", precioHoy);
        if (!Objects.equals(precioHoy.getNombre(), "Dia del espectador"))
            models.put("listadoPrecios", reservaService.listadoPrecios());

        //actualizamos la reserva
        reserva.setReservada(true);
        models.put("reserva", reserva);
        models.put("precioTempTotal", precio);

        String[] sitios = idsSeats.trim().replaceAll(" ", "").split(",");
        //lista de asientos reservados (hay que asociarlo a asientoreservado) -> método de Reserva de asiento
        List<Asiento> asientos = new ArrayList<>();

        for (String id: sitios) {
            Asiento asientoTemp = asientoService.buscarPorName(id);
            if (asientoTemp != null){
                asientos.add(asientoTemp);
                try{
                    entradaService.reservarAsiento(reserva, asientoTemp, precioHoy);
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
            session.setAttribute("entradas", asientos);
            models.put("timerTime", session.getAttribute("reservaTimeCreation"));
            return "reserva/paso-3";
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            mensaje.setTexto("Ocurrió un error y la reserva " + reserva.getId() + " no se pudo realizar.");
            return "reserva/paso-2";
        }
    }



    /**
     *  Método que comprueba la sesión del cliente (redirige al login si no se encuentra)
     *  y la reserva. Se obtiene el id de reserva de la sesión y se procede a obtener dicha
     *  sesion, se redirige al paso anterior si no se encuentra un id o la sesión del id.
     *  Procedemos a almacenar los precio de las entradas. Se comprueban los datos de la
     *  tarjeta y si es válida para confirmar el pago de la reserva (se actualiza) y se guarda
     *
     *
     * @param numTarj               numero de la tarjeta de pago
     * @param numCvv                numero de la seguridad de la tarjeta
     * @param listPreciosEntradas   lista de id de entrada y el precio seleccionado
     * @return pagina dashboard usuario
     * @since 1.0
     */
    @POST
    @Path("/pagada")
    @ValidateOnExecution(type = ExecutableType.NONE)
    public String pagadaSubmit(@FormParam("num-tarjeta") String numTarj, @FormParam("cc-cvv") String numCvv,
                              @FormParam("listPreciosEntradas") String listPreciosEntradas) {
        //obtenemos la sesión de cliente y reserva
        HttpSession session = request.getSession();
        System.out.println("---------------- " + listPreciosEntradas);

        Reserva reserva;
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
        if (compruebaTimerReserva()){
            reservaService.delete(reserva);
            mensaje.setTexto("Se expiró el tiempo para completar la reserva, inténtelo de nuevo");
            return "redirect:reserva/pelicula/" + reserva.getProyeccion().getId();
        }
        Cliente cliente = compruebaSesion();
        if (cliente == null)
            return "redirect:usuario";
        //comprueba si cliente de la reserva es igual al de la sesión
        if (!Objects.equals(cliente.getId(), reserva.getCliente().getId())){
            mensaje.setTexto("Hubo un error con su reserva, inténtelo de nuevo más tarde");
            return "redirect:reserva/paso-1/"+reserva.getProyeccion().getId();
        }

        //almacenamos el precio de las entradas
        String[] infoEntradas = listPreciosEntradas.trim().replaceAll(" ", "").split(",");
        for (String idPlusName: infoEntradas) {
            String[] info = idPlusName.split("-");  //infoEntrada: idEntrada - idPrecio
            Optional<Entrada> entradaOptional = entradaService.buscarPorId(Long.valueOf(info[0]));
            Optional<Precios> precioOptional = reservaService.buscarPrecioPorId(Long.valueOf(info[1]));
            if (entradaOptional.isPresent() && precioOptional.isPresent()){
                Entrada entrada = entradaOptional.get();
                entrada.setPrecio(precioOptional.get());
                try{
                    entradaService.guardar(entrada);
                }catch (Exception e) {
                    log.error(e.getMessage(), e);
                    mensaje.setTexto("Ocurrió un error y no pudimos almacenar la reserva de los asientos.");
                    return "reserva/paso-2";
                }
            }
        }

        //comprobar si datos de pago fueron rellenados y son válidos y se almacena
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
        session.removeAttribute("entradas");
        return "redirect:usuario/perfil";
    }


    /**
     *  Método que comprueba la validez de una tarjeta de crédito
     *  con respecto el cvc introducido
     *  12345678903555 y 012850003580200 son numeros validos
     *  5061 y 561 son cvc validos
     *
     * @return boolean
     * @since 1.0
     */
    private Boolean validateCreditCard(String card, String cvc) {
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

    /**
     *  Método que comprueba la sesión del cliente y devuelve el mismo como objeto
     *
     * @return Cliente
     * @since 1.0
     */
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

    /**
     *  Método que comprueba que la sesión de una reserva no ha excedido el tiempo
     *  límite permitido para realizarla, borrando los datos de dicha reserva en
     *  la sesión Http si ya excedió el tiempo.
     *
     * @return boolean según si se pasó el tiempo
     * @since 1.0
     */
    private Boolean compruebaTimerReserva(){
        HttpSession session = request.getSession();
        LocalDateTime creacion = (LocalDateTime) session.getAttribute("reservaTimeCreation");
        LocalDateTime ahora = LocalDateTime.now();
        Duration duration = Duration.between(creacion, ahora);
        boolean estado = duration.toMinutes() > 10;
        if (estado){
            session.removeAttribute("reservaId");
            session.removeAttribute("reservaTimeCreation");
        }
        return estado;
    }
}
