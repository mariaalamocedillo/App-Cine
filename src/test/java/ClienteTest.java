
import es.mariaac.cinema.entities.Cliente;
import es.mariaac.cinema.repositories.ClienteRepository;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    private static final String NAME = "Ana";
    private static final Long TELEPHONE = 644877774L;
    private static final String EMAIL = "ana@gmail.com";
    private static final String PSSWD = "anasoyyo";

    @Test
    @DisplayName("probando creacion de cliente")
    void testCreationClient() {
        Cliente cliente = new Cliente(NAME, TELEPHONE, EMAIL, PSSWD);

        assertAll("los datos del cliente son incorrectos",
                () -> assertEquals(NAME, cliente.getNombre()),
                () -> assertEquals(EMAIL, cliente.getEmail()),
                () -> assertEquals(TELEPHONE, cliente.getTlfn()),
                () -> assertEquals(PSSWD, cliente.getContrasena()));
        assertTrue(NAME == cliente.getNombre());
    }
}
