
import es.mariaac.cinema.entities.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    @DisplayName("probando creacion de cliente")
    void testCreationClient() {
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
    @DisplayName("probando creacion de cliente con setters")
    void testCreationClientSetters() {
        Cliente cliente = new Cliente();
        cliente.setId(Data.GENERAL_ID);
        cliente.setNombre(Data.CLIENT_NAME);
        cliente.setEmail(Data.CLIENT_EMAIL);
        cliente.setTlfn(Data.CLIENT_TELEPHONE);
        cliente.setContrasena(Data.CLIENT_PSSWD);

        assertAll("los datos del cliente son incorrectos",
                () -> assertEquals(Data.GENERAL_ID, cliente.getId()),
                () -> assertEquals(Data.CLIENT_NAME, cliente.getNombre()),
                () -> assertEquals(Data.CLIENT_TELEPHONE, cliente.getTlfn()),
                () -> assertEquals(Data.CLIENT_EMAIL, cliente.getEmail()),
                () -> assertEquals(Data.CLIENT_PSSWD, cliente.getContrasena()));
    }


    @Test
    @DisplayName("probando realizacion reserva")
    void testClientBooking() {
        Cliente cliente = new Cliente(Data.CLIENT_NAME, Data.CLIENT_TELEPHONE,
                Data.CLIENT_EMAIL, Data.CLIENT_PSSWD);

        Pelicula pelicula = new Pelicula(Data.FILM_TITLE, Data.FILM_DIRECTOR,
                Data.FILM_SIPNOSIS, Data.FILM_DURATION, Data.FILM_STUDIO, Data.FILM_POSTER, Data.FILM_SHOWING);

        Sala sala = new Sala();
        sala.setNombre(Data.ROOM_NAME);

        Asiento asiento_1 = new Asiento(Data.SEAT_NUMBER_1, Data.SEAT_LETTER_1);
        Asiento asiento_2 = new Asiento(Data.SEAT_NUMBER_3, Data.SEAT_LETTER_2);
        Asiento asiento_3 = new Asiento(Data.SEAT_NUMBER_3, Data.SEAT_LETTER_3);
        sala.setAsientos(new ArrayList<>(List.of(asiento_1, asiento_2, asiento_3)));

        Proyeccion proyeccion = new Proyeccion(pelicula, sala, Data.PROJ_DATE, Data.PROJ_HOUR);

        Reserva reserva = new Reserva(Data.BOOKING_ACTIVE, Data.BOOKING_BOOKED,
                Data.BOOKING_PAID, proyeccion, cliente);

        Precios precio = new Precios(Data.PRICE_NAME, Data.PRICE_CONDITION, Data.PRICE_PRICE);

        Entrada entrada_1 = new Entrada(asiento_1, precio);
        Entrada entrada_2 = new Entrada(asiento_2, precio);
        Entrada entrada_3 = new Entrada(asiento_3, precio);

        reserva.setEntradas(new ArrayList<>(List.of(entrada_1, entrada_2, entrada_3)));

        cliente.addReserva(reserva);

        assertFalse(cliente.getReserva().isEmpty());

    }


}
