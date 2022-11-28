import es.mariaac.cinema.entities.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    @DisplayName("probando creacion de cliente con parametros")
    void testCreateClientParam() {
        Cliente cliente = new Cliente(Data.CLIENT_NAME, Data.CLIENT_TELEPHONE,
                Data.CLIENT_EMAIL, Data.CLIENT_PSSWD);
        cliente.setId(Data.GENERAL_ID);
        assertAll("los datos del cliente son incorrectos",
                () -> assertEquals(Data.GENERAL_ID, cliente.getId()),
                () -> assertEquals(Data.CLIENT_NAME, cliente.getNombre()),
                () -> assertEquals(Data.CLIENT_TELEPHONE, cliente.getTlfn()),
                () -> assertEquals(Data.CLIENT_EMAIL, cliente.getEmail()),
                () -> assertEquals(Data.CLIENT_PSSWD, cliente.getContrasena()));
    }

    @Test
    @DisplayName("test crea cliente sin parametros")
    void testCreateClientNoParam() {
        Cliente cliente = new Cliente();
        assertNotNull(cliente);
    }

    @Test
    @DisplayName("probando realizacion reserva")
    void testClientBooking() {
        Cliente cliente = new Cliente(Data.CLIENT_NAME, Data.CLIENT_TELEPHONE,
                Data.CLIENT_EMAIL, Data.CLIENT_PSSWD);

        Pelicula pelicula = new Pelicula(Data.FILM_TITLE, Data.FILM_DIRECTOR,
                Data.FILM_SIPNOSIS, Data.FILM_DURATION, Data.FILM_STUDIO, Data.FILM_POSTER, Data.FILM_SHOWING);

        Sala sala = new Sala();

        Proyeccion proyeccion = new Proyeccion(pelicula, sala, Data.PROJ_DATE, Data.PROJ_HOUR);

        Reserva reserva = new Reserva(Data.BOOKING_ACTIVE, Data.BOOKING_BOOKED,
                Data.BOOKING_PAID, proyeccion, cliente);

        assertTrue(cliente.addReserva(reserva));
    }

    
}
