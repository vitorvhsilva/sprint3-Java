package model;

import java.util.Date;

public class Diagnostico {
    private String palavras_chave_diagnostico;
    private Date data_diagnostico;
    private long id_veiculo;
    private long id_descricao_problema;

    public Diagnostico() {

    }

    public Diagnostico(String palavras_chave_diagnostico, Date data_diagnostico, long id_veiculo, long id_descricao_problema) {
        this.palavras_chave_diagnostico = palavras_chave_diagnostico;
        this.data_diagnostico = data_diagnostico;
        this.id_veiculo = id_veiculo;
        this.id_descricao_problema = id_descricao_problema;
    }

    public String getPalavras_chave_diagnostico() {
        return palavras_chave_diagnostico;
    }

    public void setPalavras_chave_diagnostico(String palavras_chave_diagnostico) {
        this.palavras_chave_diagnostico = palavras_chave_diagnostico;
    }

    public Date getData_diagnostico() {
        return data_diagnostico;
    }

    public void setData_diagnostico(Date data_diagnostico) {
        this.data_diagnostico = data_diagnostico;
    }

    public long getId_veiculo() {
        return id_veiculo;
    }

    public void setId_veiculo(long id_veiculo) {
        this.id_veiculo = id_veiculo;
    }

    public long getId_descricao_problema() {
        return id_descricao_problema;
    }

    public void setId_descricao_problema(long id_descricao_problema) {
        this.id_descricao_problema = id_descricao_problema;
    }
}
