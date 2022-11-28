import es.mariaac.cinema.entities.Cliente;
import es.mariaac.cinema.entities.Pelicula;
import es.mariaac.cinema.entities.Proyeccion;
import es.mariaac.cinema.entities.Reserva;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

    @Test
    @DisplayName("test crea reserva sin parametros")
    void testConstructorNoArgs() {
        Reserva reserva = new Reserva();
        assertNotNull(reserva);
    }
}
