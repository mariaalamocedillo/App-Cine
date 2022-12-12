import es.mariaac.cinema.entities.*;
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

    @Test
    @DisplayName("test añade entrada a la reserva")
    void testAddEntrada() {
        Reserva reserva = new Reserva();
        Asiento asiento_1 = new Asiento(Data.SEAT_NUMBER_1, Data.SEAT_LETTER_1);
        Precios precio = new Precios(Data.PRICE_NAME, Data.PRICE_CONDITION, Data.PRICE_PRICE);

        Entrada entrada = new Entrada(asiento_1, precio);

        assertTrue(reserva.addEntrada(entrada));
    }
}
