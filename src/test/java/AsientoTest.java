import es.mariaac.cinema.entities.Asiento;
import es.mariaac.cinema.entities.Sala;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AsientoTest {

    @Test
    @DisplayName("test crea asiento")
    void testConstructor() {
        Asiento asiento = new Asiento();
        asiento.setId(Data.GENERAL_ID);
        asiento.setFila(Data.SEAT_NUMBER_1);
        asiento.setLetra(Data.SEAT_LETTER_1);
        assertAll("los datos del asiento son incorrectos",
                () -> assertNotNull(asiento),
                () -> assertEquals(Data.GENERAL_ID, asiento.getId()),
                () -> assertEquals(Data.SEAT_LETTER_1, asiento.getLetra()),
                () -> assertEquals(Data.SEAT_NUMBER_1, asiento.getFila()),
                () -> assertEquals(Data.SEAT_NUMBER_1 + Data.SEAT_LETTER_1, asiento.getName()));
    }
}
