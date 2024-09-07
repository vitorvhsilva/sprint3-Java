package dao;

import model.Agendamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {
    private Connection conexao;



    public void fazerAgendamento(Agendamento agendamento){
        String sqlInsertTbAgendamento = """
                INSERT INTO TB_AGENDAMENTO(id_veiculo, id_mecanica, dia_data_agendamento) VALUES(?, ?, ?)
                """;

        try {
            PreparedStatement insercaoAgendamento = conexao.prepareStatement(sqlInsertTbAgendamento);
            insercaoAgendamento.setLong(1, agendamento.getIdVeiculo());
            insercaoAgendamento.setLong(2, agendamento.getIdMecanica());
            insercaoAgendamento.setTimestamp(3, Timestamp.valueOf(agendamento.getDiaDataAgendamento()));

            insercaoAgendamento.execute();
            insercaoAgendamento.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Agendamento> pegarAgendamentos(long idVeiculo, long idMecanica){
        String sqlSelect = "SELECT * FROM TB_AGENDAMENTO WHERE id_veiculo = ? AND id_mecanica = ?";
        List<Agendamento> agendamentos = new ArrayList<>();
        try {
            PreparedStatement selectAgendamento = conexao.prepareStatement(sqlSelect);
            selectAgendamento.setLong(1, idVeiculo);
            selectAgendamento.setLong(2, idMecanica);
            ResultSet rs = selectAgendamento.executeQuery();
            while (rs.next()){
                Agendamento agendamento = new Agendamento();
                agendamento.setDiaDataAgendamento(rs.getTimestamp("dia_data_agendamento").toLocalDateTime());
                agendamento.setIdVeiculo(idVeiculo);
                agendamento.setIdMecanica(idMecanica);
                agendamentos.add(agendamento);
            }

            rs.close();
            selectAgendamento.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return agendamentos;
    }

    public void fecharConexao(){
        try {
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
