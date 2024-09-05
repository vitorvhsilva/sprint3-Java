package model;

import java.util.Date;

public class Orcamento {
    private long id_veiculo;
    private long id_diagnostico;
    private double valor_orcamento;
    private String pecas_danificadas_orcamento;
    private Date data_orcamento;

    public Orcamento(){

    }

    public Orcamento(long id_veiculo, long id_diagnostico, double valor_orcamento, String pecas_danificadas_orcamento, Date data_orcamento) {
        this.id_veiculo = id_veiculo;
        this.id_diagnostico = id_diagnostico;
        this.valor_orcamento = valor_orcamento;
        this.pecas_danificadas_orcamento = pecas_danificadas_orcamento;
        this.data_orcamento = data_orcamento;
    }

    public long getId_veiculo() {
        return id_veiculo;
    }

    public void setId_veiculo(long id_veiculo) {
        this.id_veiculo = id_veiculo;
    }

    public long getId_diagnostico() {
        return id_diagnostico;
    }

    public void setId_diagnostico(long id_diagnostico) {
        this.id_diagnostico = id_diagnostico;
    }

    public double getValor_orcamento() {
        return valor_orcamento;
    }

    public void setValor_orcamento(double valor_orcamento) {
        this.valor_orcamento = valor_orcamento;
    }

    public String getPecas_danificadas_orcamento() {
        return pecas_danificadas_orcamento;
    }

    public void setPecas_danificadas_orcamento(String pecas_danificadas_orcamento) {
        this.pecas_danificadas_orcamento = pecas_danificadas_orcamento;
    }

    public Date getData_orcamento() {
        return data_orcamento;
    }

    public void setData_orcamento(Date data_orcamento) {
        this.data_orcamento = data_orcamento;
    }
}
