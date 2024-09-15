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

    public OrcamentoService(DiagnosticoDAO diagnosticoDAO, DescricaoProblemaDAO descricaoProblemaDAO, VeiculoDAO veiculoDAO, UsuarioDAO usuarioDAO, OrcamentoDAO orcamentoDAO) {
        this.diagnosticoDAO = diagnosticoDAO;
        this.descricaoProblemaDAO = descricaoProblemaDAO;
        this.veiculoDAO = veiculoDAO;
        this.usuarioDAO = usuarioDAO;
        this.orcamentoDAO = orcamentoDAO;
        this.scanner = new Scanner(System.in);
    }

    public void fazerOrcamento(Usuario usuario) {
        VeiculoService veiculoService = new VeiculoService(veiculoDAO, usuarioDAO);
        String placa = veiculoService.pegarPlacaDoVeiculoEscolhido(usuario);
        veiculoService.fecharConexoes();

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

        // valor vai vir da IA tambem
        Orcamento orcamento = new Orcamento(idVeiculo, diagnosticoDAO.buscarIdPorDataEIds(diagnostico), 10,
                "{Peças vindo da IA}", LocalDateTime.now());

        orcamentoDAO.persistirOrcamento(orcamento);
        System.out.println(orcamento);
    }

    public void fazerOrcamento(Usuario usuario, Long idVeiculo, Diagnostico diagnostico) {

        // valor vai vir da IA tambem
        Orcamento orcamento = new Orcamento(idVeiculo, diagnosticoDAO.buscarIdPorDataEIds(diagnostico), 10,
                "{Peças vindo da IA}", LocalDateTime.now());

        orcamentoDAO.persistirOrcamento(orcamento);
        System.out.println(orcamento);
    }

    public void mostrarOrcamentos(Usuario usuario) {
        VeiculoService veiculoService = new VeiculoService(veiculoDAO, usuarioDAO);
        String placa = veiculoService.pegarPlacaDoVeiculoEscolhido(usuario);
        veiculoService.fecharConexoes();

        Long idVeiculo = veiculoDAO.pegarIdPelaPlaca(placa);

        List<Orcamento> orcamentos = orcamentoDAO.pegarOrcamentos(idVeiculo);
        orcamentos.forEach(System.out::println);
        System.out.println();
    }

    public void fecharConexoes() {
        descricaoProblemaDAO.fecharConexao();
        diagnosticoDAO.fecharConexao();
        orcamentoDAO.fecharConexao();
        usuarioDAO.fecharConexao();
        veiculoDAO.fecharConexao();
    }
}
