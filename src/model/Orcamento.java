package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class Orcamento {
    private long idVeiculo;
    private long idDiagnostico;
    private double valorOrcamento;
    private String pecasDanificadasOrcamento;
    private LocalDateTime dataOrcamento;

    public Orcamento(){

    }

    public Orcamento(long idVeiculo, long idDiagnostico, double valorOrcamento, String pecasDanificadasOrcamento, LocalDateTime dataOrcamento) {
        this.idVeiculo = idVeiculo;
        this.idDiagnostico = idDiagnostico;
        this.valorOrcamento = valorOrcamento;
        this.pecasDanificadasOrcamento = pecasDanificadasOrcamento;
        this.dataOrcamento = dataOrcamento;
    }

    public long getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(long idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public long getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(long idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public double getValorOrcamento() {
        return valorOrcamento;
    }

    public void setValorOrcamento(double valorOrcamento) {
        this.valorOrcamento = valorOrcamento;
    }

    public String getPecasDanificadasOrcamento() {
        return pecasDanificadasOrcamento;
    }

    public void setPecasDanificadasOrcamento(String pecasDanificadasOrcamento) {
        this.pecasDanificadasOrcamento = pecasDanificadasOrcamento;
    }

    public LocalDateTime getDataOrcamento() {
        return dataOrcamento;
    }

    public void setDataOrcamento(LocalDateTime dataOrcamento) {
        this.dataOrcamento = dataOrcamento;
    }
}
