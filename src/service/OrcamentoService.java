package service;

import dao.*;
import model.Diagnostico;
import model.Orcamento;
import model.Usuario;
import model.Veiculo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class OrcamentoService {
    private DiagnosticoDAO diagnosticoDAO;
    private DescricaoProblemaDAO descricaoProblemaDAO;
    private VeiculoDAO veiculoDAO;
    private UsuarioDAO usuarioDAO;
    private OrcamentoDAO orcamentoDAO;
    private Scanner scanner;

    public OrcamentoService() {
        this.diagnosticoDAO = new DiagnosticoDAO();
        this.descricaoProblemaDAO = new DescricaoProblemaDAO();
        this.veiculoDAO = new VeiculoDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.orcamentoDAO = new OrcamentoDAO();
        this.scanner = new Scanner(System.in);
    }

    public void fazerOrcamento(Usuario usuario) {
        String placa = new VeiculoService().pegarPlacaDoVeiculoEscolhido(usuario);
        Long idVeiculo = veiculoDAO.pegarIdPelaPlaca(placa);

        List<Diagnostico> diagnosticos = diagnosticoDAO.pegarDiagnosticos(idVeiculo);
        for (int i = 0; i < diagnosticos.size(); i++) {
            System.out.println((i + 1) + " - " + diagnosticos.get(i));
        }
        System.out.println("Selecione um diagnóstico desse veículo");
        int opcao = scanner.nextInt();scanner.nextLine();

        //em caso de erro
        if (opcao <= 0 || opcao > diagnosticos.size()) {
            System.out.println("Opção inválida! Deseja tentar novamente ou encerrar o programa? \n1 - Tentar novamente, 2 - Encerrar");
            int opcaoErro = scanner.nextInt();scanner.nextLine();
            if (opcaoErro != 1) {
                System.exit(0);
            }

            fazerOrcamento(usuario);
        }

        Diagnostico diagnostico = diagnosticos.get(opcao - 1);

        Orcamento orcamento = new Orcamento(idVeiculo, diagnosticoDAO.buscarIdPorDataEIds(diagnostico), 10,
                "{Peças vindo da IA}", LocalDateTime.now());

        orcamentoDAO.persistirOrcamento(orcamento);
        System.out.println(orcamento);
    }
}
