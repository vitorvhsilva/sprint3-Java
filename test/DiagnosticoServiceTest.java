import dao.DescricaoProblemaDAO;
import dao.DiagnosticoDAO;
import dao.VeiculoDAO;
import model.DescricaoProblema;
import model.Diagnostico;
import model.Usuario;
import org.junit.jupiter.api.Test;
import service.DiagnosticoService;
import service.UsuarioService;
import service.VeiculoService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiagnosticoServiceTest {

    @Test
    void testarPersistenciaDasDescricoes() {
        UsuarioService usuarioService = new UsuarioService();
        VeiculoService veiculoService = new VeiculoService();
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        DescricaoProblemaDAO descricaoProblemaDAO = new DescricaoProblemaDAO();
        DiagnosticoService diagnosticoService = new DiagnosticoService();
        DiagnosticoDAO diagnosticoDAO = new DiagnosticoDAO();

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

        Long idVeiculo = veiculoDAO.pegarIdPelaPlaca(placa);

        LocalDateTime data = LocalDateTime.now();
        String descricao = "Descrição xxx";

        DescricaoProblema descricaoProblema = new DescricaoProblema(descricao, data, idVeiculo);
        descricaoProblemaDAO.persistirDescricao(descricaoProblema);

        diagnosticoService.persistirDiagnostico(descricaoProblema);
        List<Diagnostico> diagnosticos = diagnosticoDAO.pegarDiagnosticos(idVeiculo);

        assertEquals(1, diagnosticos.size());
    }
}
