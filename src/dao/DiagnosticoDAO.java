package dao;

import connection.ConnectionFactory;
import model.Diagnostico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticoDAO {
    private Connection conexao;

    public DiagnosticoDAO() {
        this.conexao = new ConnectionFactory().obterConexao();
    }

    private Long obterProximoId() {
        Long id = null;
        try {
            String sql = "SELECT tb_diagnostico_id_diagnostico_seq.NEXTVAL FROM DUAL";
            PreparedStatement comandoDeGeracao = conexao.prepareStatement(sql);
            ResultSet rs = comandoDeGeracao.executeQuery();
            while(rs.next()) {
                id = rs.getLong(1);
            }
            rs.close();
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public void persistirDiagnostico(Diagnostico diagnostico){
        String sqlInsert = """
                INSERT INTO TB_DIAGNOSTICO(id_diagnostico, diagnostico_veiculo, data_diagnostico, id_veiculo, id_descricao_problema) VALUES(?, ?, ?, ?, ?)
                """;

        try {
            PreparedStatement insercaoDiagnostico = conexao.prepareStatement(sqlInsert);
            insercaoDiagnostico.setLong(1, obterProximoId());
            insercaoDiagnostico.setString(2, diagnostico.getDiagnosticoVeiculo());
            insercaoDiagnostico.setTimestamp(3, Timestamp.valueOf(diagnostico.getDataDiagnostico()));
            insercaoDiagnostico.setLong(4, diagnostico.getIdVeiculo());
            insercaoDiagnostico.setLong(5, diagnostico.getIdDescricaoProblema());

            insercaoDiagnostico.execute();
            insercaoDiagnostico.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Diagnostico> pegarDiagnosticos(long idVeiculo, long idDescricaoProblema){
        String sqlSelect = "SELECT * FROM TB_DIAGNOSTICO WHERE id_veiculo = ? AND id_descricao_problema = ?";
        List<Diagnostico> diagnosticos = new ArrayList<>();
        try{
            PreparedStatement selectDiagnostico = conexao.prepareStatement(sqlSelect);
            selectDiagnostico.setLong(1, idVeiculo);
            selectDiagnostico.setLong(2, idDescricaoProblema);
            ResultSet rs = selectDiagnostico.executeQuery();
            while(rs.next()){
                Diagnostico diagnostico = new Diagnostico();
                diagnostico.setDataDiagnostico(rs.getTimestamp("data_diagnostico").toLocalDateTime());
                diagnostico.setDiagnosticoVeiculo(rs.getString("palavras_chaves_diagnostico"));
                diagnostico.setIdVeiculo(idVeiculo);
                diagnostico.setIdDescricaoProblema(idDescricaoProblema);
                diagnosticos.add(diagnostico);


            }

            rs.close();
            selectDiagnostico.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return diagnosticos;
    }

    public void fecharConexao(){
        try {
            conexao.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
