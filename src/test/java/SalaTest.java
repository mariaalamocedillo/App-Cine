import es.mariaac.cinema.entities.Asiento;
import es.mariaac.cinema.entities.Pelicula;
import es.mariaac.cinema.entities.Proyeccion;
import es.mariaac.cinema.entities.Sala;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SalaTest {

    @Test
    @DisplayName("test crea sala")
    void testConstructorNoArgs() {
        Sala sala = new Sala();
        sala.setId(Data.GENERAL_ID);
        sala.setNombre(Data.ROOM_NAME);
        assertAll("los datos de la sala son incorrectos",
                () -> assertNotNull(sala),
                () -> assertEquals(Data.GENERAL_ID, sala.getId()),
                () -> assertEquals(Data.ROOM_NAME, sala.getNombre()));
    }

    @Test
    @DisplayName("test añade asientos a la sala")
    void testAddAsientos() {
        Sala sala = new Sala();
        sala.setId(Data.GENERAL_ID);
        sala.setNombre(Data.ROOM_NAME);

        Asiento asiento_1 = new Asiento(Data.SEAT_NUMBER_1, Data.SEAT_LETTER_1);
        Asiento asiento_2 = new Asiento(Data.SEAT_NUMBER_2, Data.SEAT_LETTER_2);

        sala.setAsientos(new ArrayList<>(List.of(asiento_1)));

        assertAll("los datos de los asientos son incorrectos",
                () -> assertTrue(sala.addAsiento(asiento_2)),
                () -> assertEquals(Data.SEAT_NUMBER_1, sala.getAsientos().get(0).getFila()));
    }

}
