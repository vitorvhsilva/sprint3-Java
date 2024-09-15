import dao.UsuarioDAO;
import dao.VeiculoDAO;
import model.Usuario;
import model.Veiculo;
import org.junit.jupiter.api.Test;
import service.UsuarioService;
import service.VeiculoService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VeiculoServiceTest {

    @Test
    void testarPersistenciaDeVeiculos() {

        VeiculoDAO veiculoDAO = new VeiculoDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        UsuarioService usuarioService = new UsuarioService(usuarioDAO);
        VeiculoService veiculoService = new VeiculoService(veiculoDAO, usuarioDAO);

        //gerando cpf aleatorio
        StringBuilder cpfString = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            cpfString.append((int) (Math.random() * 10));
        }
        String cpf = cpfString.toString();

        // gerando email aleatorio
        StringBuilder emailString = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            emailString.append((char) ('a' + Math.random() * ('z' - 'a' + 1)));
        }
        String email = emailString + "@email.com";

        // gerando placa aleatorio
        StringBuilder placaString = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            placaString.append((char) ('a' + Math.random() * ('z' - 'a' + 1)));
        }
        String placa = placaString.toString();

        Usuario usuario = usuarioService.persistirUsuario("Vitor", email, "12345678", "M", "11987492156", cpf);
        veiculoService.persistirVeiculo(usuario, "Toyota", "Corolla", 2018, "C", placa);

        List<Veiculo> veiculos = veiculoDAO.pegarVeiculos(usuarioDAO.retornarIdPorCpf(usuario.getCpf()));

        assertEquals(1, veiculos.size());
    }
}
