package dto;

public class VeiculoDTO {

    private Long idVeiculo;
    private String placa;
    private String marca;
    private String modelo;
    private Integer ano;
    private String tipo;
    private Long idUsuario;

    public VeiculoDTO() {
    }

    public void setIdVeiculo(Long idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdVeiculo() {
        return idVeiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public String getTipo() {
        return tipo;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    @Override
    public String toString() {
        return idVeiculo + " - Veículo " + marca + " " + modelo + " " + ano + " (" + placa + ")";
    }
}

