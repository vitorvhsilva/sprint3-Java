package dao;

import connection.ConnectionFactory;
import model.Usuario;
import model.Veiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public boolean buscarPorCpf(String cpf) {
        String sqlSelect = "SELECT * FROM TB_USUARIO WHERE cpf_usuario = ?";

        try {
            PreparedStatement statement = conexao.prepareStatement(sqlSelect);
            statement.setString(1, cpf);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public Usuario buscarPorLogin(String email, String senha) {
        String sqlSelect = "SELECT * FROM TB_USUARIO WHERE email_usuario = ? AND senha_usuario = ?";
        Usuario usuario = new Usuario();
        try {
            PreparedStatement statement = conexao.prepareStatement(sqlSelect);
            statement.setString(1, email);
            statement.setString(2, senha);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                usuario.setNome(rs.getString("nome_usuario"));
                usuario.setEmail(rs.getString("email_usuario"));
                usuario.setSenha(rs.getString("senha_usuario"));
                usuario.setGenero(rs.getString("genero_usuario"));
                usuario.setTelefone(rs.getString("telefone_usuario"));
                usuario.setCpf(rs.getString("cpf_usuario"));
            }
        } catch (SQLException e) {
            return null;
        }
        return usuario;
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
