import es.mariaac.cinema.entities.Asiento;
import es.mariaac.cinema.entities.Entrada;
import es.mariaac.cinema.entities.Precios;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntradaTest {

    @Test
    @DisplayName("test crea entrada")
    void testConstructor() {
        Asiento asiento_1 = new Asiento(Data.SEAT_NUMBER_1, Data.SEAT_LETTER_1);
        Precios precio = new Precios(Data.PRICE_NAME, Data.PRICE_CONDITION, Data.PRICE_PRICE);

        Entrada entrada = new Entrada(asiento_1, precio);
        entrada.setId(Data.GENERAL_ID);

        assertAll("los datos de la entrada son incorrectos",
                () -> assertEquals(Data.GENERAL_ID, entrada.getId()),
                () -> assertEquals(asiento_1, entrada.getAsiento()),
                () -> assertEquals(precio, entrada.getPrecio()));
    }

    @Test
    @DisplayName("test crea entrada sin parametros")
    void testConstructorNoArgs() {
        Entrada entrada = new Entrada();
        assertNotNull(entrada);
    }
}
