package model;

import java.time.LocalDateTime;
import java.util.Date;

public class Diagnostico {
    private String palavrasChaveDiagnostico;
    private LocalDateTime dataDiagnostico;
    private long idVeiculo;
    private long idDescricaoProblema;

    public Diagnostico() {

    }

    public Diagnostico(String palavras_chave_diagnostico, LocalDateTime data_diagnostico, long id_veiculo, long id_descricao_problema) {
        this.palavrasChaveDiagnostico = palavras_chave_diagnostico;
        this.dataDiagnostico = data_diagnostico;
        this.idVeiculo = id_veiculo;
        this.idDescricaoProblema = id_descricao_problema;
    }

    public String getPalavrasChaveDiagnostico() {
        return palavrasChaveDiagnostico;
    }

    public void setPalavrasChaveDiagnostico(String palavrasChaveDiagnostico) {
        this.palavrasChaveDiagnostico = palavrasChaveDiagnostico;
    }

    public LocalDateTime getDataDiagnostico() {
        return dataDiagnostico;
    }

    public void setDataDiagnostico(LocalDateTime dataDiagnostico) {
        this.dataDiagnostico = dataDiagnostico;
    }

    public long getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(long idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public long getIdDescricaoProblema() {
        return idDescricaoProblema;
    }

    public void setIdDescricaoProblema(long idDescricaoProblema) {
        this.idDescricaoProblema = idDescricaoProblema;
    }
}
