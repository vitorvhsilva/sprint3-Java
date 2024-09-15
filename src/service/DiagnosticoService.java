package service;

import dao.DescricaoProblemaDAO;
import dao.DiagnosticoDAO;
import dao.UsuarioDAO;
import dao.VeiculoDAO;
import model.DescricaoProblema;
import model.Diagnostico;
import model.Usuario;

import java.util.List;
import java.util.Scanner;

public class DiagnosticoService {
    private DiagnosticoDAO diagnosticoDAO;
    private DescricaoProblemaDAO descricaoProblemaDAO;
    private VeiculoDAO veiculoDAO;
    private UsuarioDAO usuarioDAO;
    private Scanner scanner;

    public DiagnosticoService(DiagnosticoDAO diagnosticoDAO, DescricaoProblemaDAO descricaoProblemaDAO, VeiculoDAO veiculoDAO, UsuarioDAO usuarioDAO) {
        this.diagnosticoDAO = diagnosticoDAO;
        this.descricaoProblemaDAO = descricaoProblemaDAO;
        this.veiculoDAO = veiculoDAO;
        this.usuarioDAO = usuarioDAO;
        this.scanner = new Scanner(System.in);
    }

    public void persistirDiagnostico(DescricaoProblema dp){
        Long idDescricaoProblema = descricaoProblemaDAO.buscarIdPorVeiculoEDescricao(dp);
        Diagnostico diagnostico = new Diagnostico("{Diagnóstico vindo da IA}", dp.getDataProblema(), dp.getIdVeiculo(), idDescricaoProblema);
        diagnosticoDAO.persistirDiagnostico(diagnostico);
        System.out.println("Diagnóstico do veículo feito");
    }


    public void mostrarDescricoesJaFeita(Usuario usuario) {
        VeiculoService veiculoService = new VeiculoService(veiculoDAO, usuarioDAO);
        String placa = veiculoService.pegarPlacaDoVeiculoEscolhido(usuario);
        veiculoService.fecharConexoes();

        Long idVeiculo = veiculoDAO.pegarIdPelaPlaca(placa);

        List<Diagnostico> diagnosticos = diagnosticoDAO.pegarDiagnosticos(idVeiculo);

        if (diagnosticos.isEmpty()) {
            System.out.println("Nenhum diagnóstico foi feita para esse veículo!");
            return;
        }

        diagnosticos.forEach(System.out::println);
    }

    public void fecharConexoes() {
        descricaoProblemaDAO.fecharConexao();
        diagnosticoDAO.fecharConexao();
        usuarioDAO.fecharConexao();
        veiculoDAO.fecharConexao();
    }
}
