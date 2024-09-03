package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DescricaoProblema {
    private String descricaoProblema;
    private LocalDateTime dataProblema;
    private Long idVeiculo;

    public DescricaoProblema() {
    }

    public DescricaoProblema(String descricaoProblema, LocalDateTime dataProblema, Long idVeiculo) {
        this.descricaoProblema = descricaoProblema;
        this.dataProblema = dataProblema;
        this.idVeiculo = idVeiculo;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public LocalDateTime getDataProblema() {
        return dataProblema;
    }

    public void setDataProblema(LocalDateTime dataProblema) {
        this.dataProblema = dataProblema;
    }

    public Long getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Long idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    @Override
    public String toString() {
        return "Descricao do Problema ocorrido em " + dataProblema + ": " + descricaoProblema;
    }
}
