package dao;

import model.Orcamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrcamentoDAO {
    private Connection conexao;

    private Long obterProximoId(){
        Long id = null;
        try{
            String sql = "SELECT tb_orcamento_id_orcamento_seq.NEXTVAL FROM DUAL";
            PreparedStatement obterId = conexao.prepareStatement(sql);
            ResultSet rs = obterId.executeQuery();
            while (rs.next()){
                id = rs.getLong(1);
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public void fazerOrcamento(Orcamento orcamento){
        String sqlInsertTbOrcamento = """
                INSERT INTO TB_ORCAMENTO(id_orcamento, id_veiculo, id_diagnostico, valor_orcamento, pecas_danificadas_orcamento, data_orcamento)
                VALUES(?, ?, ?, ?, ?, ?)
                """;

        try {
            PreparedStatement insercaoOrcamento = conexao.prepareStatement(sqlInsertTbOrcamento);
            insercaoOrcamento.setLong(1, obterProximoId());
            insercaoOrcamento.setLong(2, orcamento.getIdVeiculo());
            insercaoOrcamento.setLong(3, orcamento.getIdDiagnostico());
            insercaoOrcamento.setDouble(4, orcamento.getValorOrcamento());
            insercaoOrcamento.setString(5, orcamento.getPecasDanificadasOrcamento());
            insercaoOrcamento.setTimestamp(6, Timestamp.valueOf(orcamento.getDataOrcamento()));

            insercaoOrcamento.execute();
            insercaoOrcamento.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Orcamento> pegarOrcamentos(long idVeiculo, long idDiagnostico){
        String sqlSelect = "SELECT * FROM TB_ORCAMENTO WHERE id_veiculo = ? AND id_diagnostico = ?";
        List<Orcamento> orcamentos = new ArrayList<>();

        try {
            PreparedStatement statement = conexao.prepareStatement(sqlSelect);
            statement.setLong(1, idVeiculo);
            statement.setLong(2, idDiagnostico);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Orcamento orcamento = new Orcamento();
                orcamento.setIdVeiculo(idVeiculo);
                orcamento.setIdDiagnostico(idDiagnostico);
                orcamento.setValorOrcamento(rs.getDouble("valor_orcamento"));
                orcamento.setPecasDanificadasOrcamento(rs.getString("pecas_danificadas_orcamento"));
                orcamento.setDataOrcamento(rs.getTimestamp("data_orcamento").toLocalDateTime());
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orcamentos;
    }

    public void fecharConexao(){
        try {
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
