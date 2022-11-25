import es.mariaac.cinema.entities.Precios;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PreciosTest {

    @Test
    @DisplayName("test crea precio")
    void testConstructor() {
        Precios precio = new Precios(Data.PRICE_NAME, Data.PRICE_CONDITION, Data.PRICE_PRICE);
        precio.setId(Data.GENERAL_ID);
        assertAll("los datos del precio son incorrectos",
                () -> assertNotNull(precio),
                () -> assertEquals(Data.GENERAL_ID, precio.getId()),
                () -> assertEquals(Data.PRICE_NAME, precio.getNombre()),
                () -> assertEquals(Data.PRICE_CONDITION, precio.getCondiciones()),
                () -> assertEquals(Data.PRICE_PRICE, precio.getPrecioFinal()));
    }

    @Test
    @DisplayName("test crea precio sin parametros")
    void testConstructorNoArgs() {
        Precios precio = new Precios();
        assertNotNull(precio);
    }
}
