
import dao.*;
import model.DescricaoProblema;
import model.Diagnostico;
import model.Orcamento;
import model.Usuario;
import org.junit.jupiter.api.Test;
import service.DiagnosticoService;
import service.OrcamentoService;
import service.UsuarioService;
import service.VeiculoService;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrcamentoServiceTest {
    @Test
    void testarPersistenciaDasDescricoes() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        DescricaoProblemaDAO descricaoProblemaDAO = new DescricaoProblemaDAO();
        DiagnosticoDAO diagnosticoDAO = new DiagnosticoDAO();
        OrcamentoDAO orcamentoDAO = new OrcamentoDAO();

        UsuarioService usuarioService = new UsuarioService(usuarioDAO);
        VeiculoService veiculoService = new VeiculoService(veiculoDAO, usuarioDAO);
        DiagnosticoService diagnosticoService = new DiagnosticoService(diagnosticoDAO, descricaoProblemaDAO, veiculoDAO, usuarioDAO);
        OrcamentoService orcamentoService = new OrcamentoService(diagnosticoDAO, descricaoProblemaDAO, veiculoDAO, usuarioDAO, orcamentoDAO);

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

        orcamentoService.fazerOrcamento(usuario, idVeiculo, diagnosticos.getFirst());

        List<Orcamento> orcamentos = orcamentoDAO.pegarOrcamentos(idVeiculo);

        assertEquals(1, orcamentos.size());
    }
}
