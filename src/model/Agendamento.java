package model;

import java.util.Date;

public class Agendamento {
    private long id_veiculo;
    private long id_mecanica;
    private Date dia_data_agendamento;

    public Agendamento(){

    }

    public Agendamento(long id_veiculo, long id_mecanica, Date dia_data_agendamento) {
        this.id_veiculo = id_veiculo;
        this.id_mecanica = id_mecanica;
        this.dia_data_agendamento = dia_data_agendamento;
    }

    public long getId_veiculo() {
        return id_veiculo;
    }

    public void setId_veiculo(long id_veiculo) {
        this.id_veiculo = id_veiculo;
    }

    public long getId_mecanica() {
        return id_mecanica;
    }

    public void setId_mecanica(long id_mecanica) {
        this.id_mecanica = id_mecanica;
    }

    public Date getDia_data_agendamento() {
        return dia_data_agendamento;
    }

    public void setDia_data_agendamento(Date dia_data_agendamento) {
        this.dia_data_agendamento = dia_data_agendamento;
    }
}
