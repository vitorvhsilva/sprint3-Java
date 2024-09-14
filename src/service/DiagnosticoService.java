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
        System.out.println("Diagnóstico do veículo feito");
    }


    public void mostrarDescricoesJaFeita(Usuario usuario) {
        String placa = new VeiculoService().pegarPlacaDoVeiculoEscolhido(usuario);
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
