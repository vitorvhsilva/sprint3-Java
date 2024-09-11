package service;

import dao.DescricaoProblemaDAO;
import dao.DiagnosticoDAO;
import model.DescricaoProblema;
import model.Diagnostico;

import java.util.Scanner;

public class DiagnosticoService {
    private DiagnosticoDAO diagnosticoDAO;
    private DescricaoProblemaDAO descricaoProblemaDAO;
    private Scanner scanner;

    public DiagnosticoService() {
        this.diagnosticoDAO = new DiagnosticoDAO();
        this.descricaoProblemaDAO = new DescricaoProblemaDAO();
        this.scanner = new Scanner(System.in);
    }

    public void persistirDiagnostico(DescricaoProblema dp){
        Long idDescricaoProblema = descricaoProblemaDAO.buscarIdPorVeiculoEDescricao(dp);
        Diagnostico diagnostico = new Diagnostico("{Diagnóstico vindo da IA}", dp.getDataProblema(), dp.getIdVeiculo(), idDescricaoProblema);
        diagnosticoDAO.persistirDiagnostico(diagnostico);
        System.out.println(diagnostico);
    }
}
