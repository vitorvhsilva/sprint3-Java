package dao;

import model.Diagnostico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticoDAO {
    private Connection conexao;

    public void fazerDiagnostico(Diagnostico diagnostico){
        String sqlInsert = """
                INSERT INTO TB_DIAGNOSTICO(palavras_chaves_diagnostico, data_diagnostico, id_veiculo, id_descricao_problema) VALUES(?, ?, ?, ?)
                """;

        try {
            PreparedStatement insercaoDiagnostico = conexao.prepareStatement(sqlInsert);
            insercaoDiagnostico.setString(1, diagnostico.getPalavrasChaveDiagnostico());
            insercaoDiagnostico.setTimestamp(2, Timestamp.valueOf(diagnostico.getDataDiagnostico()));
            insercaoDiagnostico.setLong(3, diagnostico.getIdVeiculo());
            insercaoDiagnostico.setLong(4, diagnostico.getIdDescricaoProblema());

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
                diagnostico.setPalavrasChaveDiagnostico(rs.getString("palavras_chaves_diagnostico"));
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
