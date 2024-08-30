package dao;

import connection.ConnectionFactory;
import model.Usuario;
import model.Veiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {
    private Connection conexao;

    public VeiculoDAO() {
        this.conexao = new ConnectionFactory().obterConexao();
    }

    private Long obterProximoId() {
        Long id = null;
        try {
            String sql = "SELECT tb_veiculo_id_veiculo_seq.NEXTVAL FROM DUAL";
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

    public void persistirVeiculo(Veiculo veiculo) {
        String sqlInsertTbVeiculo = """
                INSERT INTO TB_VEICULO (id_veiculo, placa_veiculo, marca_veiculo, modelo_veiculo, tipo_veiculo, ano_veiculo, id_usuario) VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try {
            PreparedStatement statementUsuario = conexao.prepareStatement(sqlInsertTbVeiculo);
            statementUsuario.setLong(1, obterProximoId());
            statementUsuario.setString(2, veiculo.getPlaca());
            statementUsuario.setString(3, veiculo.getMarca());
            statementUsuario.setString(4, veiculo.getModelo());
            statementUsuario.setString(5, veiculo.getTipo());
            statementUsuario.setInt(6, veiculo.getAno());
            statementUsuario.setLong(7, veiculo.getIdUsuario());
            statementUsuario.execute();
            statementUsuario.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Veículo " + veiculo.getModelo() + " " + veiculo.getMarca() + " (" + veiculo.getPlaca() + ") persistido!");
    }

    public List<Veiculo> pegarVeiculos(Long idUsuario) {
        String sqlSelect = "SELECT * FROM TB_VEICULO WHERE id_usuario = ?";
        List<Veiculo> veiculos = new ArrayList<>();
        try {
            PreparedStatement statement = conexao.prepareStatement(sqlSelect);
            statement.setLong(1, idUsuario);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Veiculo veiculo = new Veiculo();
                veiculo.setPlaca(rs.getString("placa_veiculo"));
                veiculo.setMarca(rs.getString("marca_veiculo"));
                veiculo.setModelo(rs.getString("modelo_veiculo"));
                veiculo.setAno(rs.getInt("ano_veiculo"));
                veiculo.setTipo(rs.getString("tipo_veiculo"));
                veiculo.setIdUsuario(idUsuario);
                veiculos.add(veiculo);
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return veiculos;
    }

    public void fecharConexao() {
        try {
            conexao.close();
            System.out.println("UsuárioDAO fechado!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
