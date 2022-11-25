import es.mariaac.cinema.entities.Pelicula;
import es.mariaac.cinema.entities.Proyeccion;
import es.mariaac.cinema.entities.Sala;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProyeccionTest {

    @Test
    @DisplayName("test creacion de proyeccion")
    void testConstructor() {
        Pelicula pelicula = new Pelicula(Data.FILM_TITLE, Data.FILM_DIRECTOR,
                Data.FILM_SIPNOSIS, Data.FILM_DURATION, Data.FILM_STUDIO, Data.FILM_POSTER, Data.FILM_SHOWING);

        Sala sala = new Sala();
        sala.setNombre(Data.ROOM_NAME);

        Proyeccion proyeccion = new Proyeccion(pelicula, sala, Data.PROJ_DATE, Data.PROJ_HOUR);
        proyeccion.setId(Data.GENERAL_ID);

        assertAll("los datos de la proyeccion son incorrectos",
                () -> assertEquals(Data.GENERAL_ID, proyeccion.getId()),
                () -> assertEquals(pelicula, proyeccion.getPelicula()),
                () -> assertEquals(sala, proyeccion.getSala()),
                () -> assertEquals(String.valueOf(Data.PROJ_DATE), proyeccion.getDia()),
                () -> assertEquals(String.valueOf(Data.PROJ_HOUR), proyeccion.getComienzo()));
    }

    @Test
    @DisplayName("test crea proyeccion sin parametros")
    void testConstructorNoArgs() {
        Proyeccion proyeccion = new Proyeccion();
        assertNotNull(proyeccion);
    }

}
