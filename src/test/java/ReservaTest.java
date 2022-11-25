import es.mariaac.cinema.entities.Cliente;
import es.mariaac.cinema.entities.Pelicula;
import es.mariaac.cinema.entities.Proyeccion;
import es.mariaac.cinema.entities.Reserva;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

    @Test
    @DisplayName("test creacion de reserva")
    void testCreationReserva() {
        Cliente cliente = new Cliente(Data.CLIENT_NAME, Data.CLIENT_TELEPHONE,
                Data.CLIENT_EMAIL, Data.CLIENT_PSSWD);
        Pelicula pelicula = new Pelicula(Data.FILM_TITLE, Data.FILM_DIRECTOR,
                Data.FILM_SIPNOSIS, Data.FILM_DURATION, Data.FILM_STUDIO, Data.FILM_POSTER, Data.FILM_SHOWING);
        //Reserva reserva = new Reserva(1L, true, true, true);

        assertAll("los datos del cliente son incorrectos",
                () -> assertEquals(Data.CLIENT_NAME, cliente.getNombre()),
                () -> assertEquals(Data.CLIENT_TELEPHONE, cliente.getTlfn()),
                () -> assertEquals(Data.CLIENT_EMAIL, cliente.getEmail()),
                () -> assertEquals(Data.CLIENT_PSSWD, cliente.getContrasena()));
    }

    @Test
    @DisplayName("test crea reserva sin parametros")
    void testConstructorNoArgs() {
        Reserva reserva = new Reserva();
        assertNotNull(reserva);
    }
}
