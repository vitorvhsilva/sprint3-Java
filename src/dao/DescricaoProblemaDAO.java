package dao;

import connection.ConnectionFactory;
import dto.VeiculoDTO;
import model.DescricaoProblema;
import model.Veiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DescricaoProblemaDAO {
    private Connection conexao;

    public DescricaoProblemaDAO() {
        this.conexao = new ConnectionFactory().obterConexao();
    }

    private Long obterProximoId() {
        Long id = null;
        try {
            String sql = "SELECT tb_descricao_problema_id_descricao_problema_seq.NEXTVAL FROM DUAL";
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

    public void persistirDescricao(DescricaoProblema descricao) {
        String sqlInsertTbDescricaoProblema = """
                INSERT INTO TB_DESCRICAO_PROBLEMA (id_descricao_problema, descricao_problema, data_problema, id_veiculo) VALUES (?, ?, ?, ?)
                """;

        try {
            PreparedStatement statementUsuario = conexao.prepareStatement(sqlInsertTbDescricaoProblema);
            statementUsuario.setLong(1, obterProximoId());
            statementUsuario.setString(2, descricao.getDescricaoProblema());
            statementUsuario.setTimestamp(3, Timestamp.valueOf(descricao.getDataProblema()));
            statementUsuario.setLong(4, descricao.getIdVeiculo());

            statementUsuario.execute();
            statementUsuario.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Descrição do problema feita");
    }

    public List<DescricaoProblema> pegarDescricoes(Long idVeiculo) {
        String sqlSelect = "SELECT * FROM TB_DESCRICAO_PROBLEMA WHERE id_veiculo = ?";
        List<DescricaoProblema> descricoes = new ArrayList<>();
        try {
            PreparedStatement statement = conexao.prepareStatement(sqlSelect);
            statement.setLong(1, idVeiculo);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                DescricaoProblema descricaoProblema = new DescricaoProblema();
                descricaoProblema.setDescricaoProblema(rs.getString("descricao_problema"));
                descricaoProblema.setDataProblema(rs.getTimestamp("data_problema").toLocalDateTime());
                descricaoProblema.setIdVeiculo(idVeiculo);
                descricoes.add(descricaoProblema);
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return descricoes;
    }

    public void fecharConexao() {
        try {
            conexao.close();
            System.out.println("DescricaoProblemaDAO fechado!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
