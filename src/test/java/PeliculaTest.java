import es.mariaac.cinema.entities.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PeliculaTest {

    @Test
    @DisplayName("test creacion de pelicula")
    void testCreationPelicula() {
        Pelicula pelicula = new Pelicula(Data.FILM_TITLE, Data.FILM_DIRECTOR,
                Data.FILM_SIPNOSIS, Data.FILM_DURATION, Data.FILM_STUDIO, Data.FILM_POSTER, Data.FILM_SHOWING);
        pelicula.setId(Data.GENERAL_ID);

        assertAll("los datos de la pelicula son incorrectos",
                () -> assertEquals(Data.GENERAL_ID, pelicula.getId()),
                () -> assertEquals(Data.FILM_TITLE, pelicula.getTitulo()),
                () -> assertEquals(Data.FILM_DIRECTOR, pelicula.getDirector()),
                () -> assertEquals(Data.FILM_SIPNOSIS, pelicula.getDescripcion()),
                () -> assertEquals(Data.FILM_STUDIO, pelicula.getEstudio()),
                () -> assertEquals(Data.FILM_POSTER, pelicula.getPoster()),
                () -> assertEquals(Data.FILM_DURATION, pelicula.getDuracion()),
                () -> assertEquals(Data.FILM_SHOWING, pelicula.getEnProyeccion()));
    }

    @Test
    @DisplayName("test añade proyecciones a la pelicula")
    void testAddProyecciones() {
        Pelicula pelicula = new Pelicula(Data.FILM_TITLE, Data.FILM_DIRECTOR,
                Data.FILM_SIPNOSIS, Data.FILM_DURATION, Data.FILM_STUDIO, Data.FILM_POSTER, Data.FILM_SHOWING);
        pelicula.setId(Data.GENERAL_ID);

        Sala sala = new Sala();
        sala.setNombre(Data.ROOM_NAME);

        Proyeccion proyeccion = new Proyeccion(pelicula, sala, Data.PROJ_DATE, Data.PROJ_HOUR);

        assertAll("los datos de la proyeccion son incorrectos",
                () -> assertTrue(pelicula.addProyeccion(proyeccion)),
                () -> assertEquals(proyeccion, pelicula.getProyecciones().get(0)));
    }

    @Test
    @DisplayName("test crea pelicula sin parametros")
    void testConstructorNoArgs() {
        Pelicula pelicula = new Pelicula();
        assertNotNull(pelicula);
    }
}
