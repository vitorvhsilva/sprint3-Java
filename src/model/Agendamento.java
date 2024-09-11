package model;

import java.time.LocalDateTime;

public class Agendamento {
    private long idVeiculo;
    private long idMecanica;
    private LocalDateTime diaDataAgendamento;

    public Agendamento(){

    }

    public Agendamento(long id_veiculo, long id_mecanica, LocalDateTime diaDataAgendamento) {
        this.idVeiculo = id_veiculo;
        this.idMecanica = id_mecanica;
        this.diaDataAgendamento = diaDataAgendamento;
    }

    public long getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(long idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public long getIdMecanica() {
        return idMecanica;
    }

    public void setIdMecanica(long idMecanica) {
        this.idMecanica = idMecanica;
    }

    public LocalDateTime getDiaDataAgendamento() {
        return diaDataAgendamento;
    }

    public void setDiaDataAgendamento(LocalDateTime diaDataAgendamento) {
        this.diaDataAgendamento = diaDataAgendamento;
    }
}
