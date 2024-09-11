package service;

import dao.DescricaoProblemaDAO;
import dao.DiagnosticoDAO;
import dao.UsuarioDAO;
import dao.VeiculoDAO;
import model.DescricaoProblema;
import model.Diagnostico;
import model.Usuario;
import model.Veiculo;

import java.util.List;
import java.util.Scanner;

public class DiagnosticoService {
    private DiagnosticoDAO diagnosticoDAO;
    private DescricaoProblemaDAO descricaoProblemaDAO;
    private VeiculoDAO veiculoDAO;
    private UsuarioDAO usuarioDAO;
    private Scanner scanner;

    public DiagnosticoService() {
        this.diagnosticoDAO = new DiagnosticoDAO();
        this.descricaoProblemaDAO = new DescricaoProblemaDAO();
        this.veiculoDAO = new VeiculoDAO();
        this.usuarioDAO = new UsuarioDAO();
        this.scanner = new Scanner(System.in);
    }

    public void persistirDiagnostico(DescricaoProblema dp){
        Long idDescricaoProblema = descricaoProblemaDAO.buscarIdPorVeiculoEDescricao(dp);
        Diagnostico diagnostico = new Diagnostico("{Diagnóstico vindo da IA}", dp.getDataProblema(), dp.getIdVeiculo(), idDescricaoProblema);
        diagnosticoDAO.persistirDiagnostico(diagnostico);
        System.out.println(diagnostico);
        System.out.println("Diagnóstico do veículo feito");
    }

    public String pegarPlacaDoVeiculoEscolhido(Usuario usuario) {
        List<Veiculo> veiculos = veiculoDAO.pegarVeiculos(usuarioDAO.retornarIdPorCpf(usuario.getCpf()));
        veiculos.forEach(System.out::println);
        System.out.println("Escolha um veículo pela placa");
        String placa = scanner.nextLine();

        for (Veiculo veiculo : veiculos) {
            if (placa.equals(veiculo.getPlaca())) {
                return placa;
            }
        }

        System.out.println("Opção inválida! Deseja tentar novamente ou encerrar o programa? \n1 - Tentar novamente, 2 - Encerrar");
        int opcao = scanner.nextInt();scanner.nextLine();
        if (opcao != 1) {
            System.exit(0);
        }

        return pegarPlacaDoVeiculoEscolhido(usuario);
    }

    public void mostrarDescricoesJaFeita(Usuario usuario) {
        String placa = pegarPlacaDoVeiculoEscolhido(usuario);
        Long idVeiculo = veiculoDAO.pegarIdPelaPlaca(placa);

        List<Diagnostico> diagnosticos = diagnosticoDAO.pegarDiagnosticos(idVeiculo);

        if (diagnosticos.isEmpty()) {
            System.out.println("Nenhum diagnóstico foi feita para esse veículo!");
            return;
        }

        diagnosticos.forEach(System.out::println);
    }
}
