import model.Usuario;
import org.junit.jupiter.api.Test;
import service.UsuarioService;

import static org.junit.jupiter.api.Assertions.assertNull;

public class UsuarioServiceTest {

    @Test
    void ValidarSeOUsuarioExistePeloCPF() {

        //gerando cpf aleatorio
        StringBuilder numberString = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            numberString.append((int) (Math.random() * 10));
        }
        String cpf = numberString.toString();

        // gerando primeiro email aleatorio
        StringBuilder letterString = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            letterString.append((char) ('a' + Math.random() * ('z' - 'a' + 1)));
        }
        String email1 = letterString + "@email.com";

        // gerando segundo email aleatorio
        StringBuilder letterString2 = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            letterString2.append((char) ('a' + Math.random() * ('z' - 'a' + 1)));
        }
        String email2 = letterString2 + "@email.com";

        UsuarioService usuarioService = new UsuarioService();
        Usuario usuarioUm = usuarioService.persistirUsuario("Vitor", email1, "12345678", "M", "11987492156", cpf);
        Usuario usuarioDois = usuarioService.persistirUsuario("Vitor", email2, "12345678", "M", "11987492156", cpf);

        assertNull(usuarioDois);

    }
}
