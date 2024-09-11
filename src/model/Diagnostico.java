package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Diagnostico {
    private String diagnosticoVeiculo;
    private LocalDateTime dataDiagnostico;
    private long idVeiculo;
    private long idDescricaoProblema;

    public Diagnostico() {

    }

    public Diagnostico(String diagnosticoVeiculo, LocalDateTime dataDiagnostico, long idVeiculo, long idDescricaoProblema) {
        this.diagnosticoVeiculo = diagnosticoVeiculo;
        this.dataDiagnostico = dataDiagnostico;
        this.idVeiculo = idVeiculo;
        this.idDescricaoProblema = idDescricaoProblema;
    }

    public String getDiagnosticoVeiculo() {
        return diagnosticoVeiculo;
    }

    public void setDiagnosticoVeiculo(String diagnosticoVeiculo) {
        this.diagnosticoVeiculo = diagnosticoVeiculo;
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

    @Override
    public String toString() {
        return "Diagnostico: " + diagnosticoVeiculo + ", data: " + DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss").format(dataDiagnostico);
    }
}
