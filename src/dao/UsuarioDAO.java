package dao;

import connection.ConnectionFactory;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    private Connection conexao;

    public UsuarioDAO() {
        this.conexao = new ConnectionFactory().obterConexao();
    }

    private Long obterProximoId() {
        Long id = null;
        try {
            String sql = "SELECT tb_usuario_id_usuario_seq.NEXTVAL FROM DUAL";
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

    public void persistirUsuario(Usuario usuario) {
        String sqlInsertTbUsuario = """
                INSERT INTO TB_USUARIO (id_usuario, nome_usuario, email_usuario, senha_usuario, genero_usuario, telefone_usuario, cpf_usuario) VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try {
            PreparedStatement statementUsuario = conexao.prepareStatement(sqlInsertTbUsuario);
            statementUsuario.setLong(1, obterProximoId());
            statementUsuario.setString(2, usuario.getNome());
            statementUsuario.setString(3, usuario.getEmail());
            statementUsuario.setString(4, usuario.getSenha());
            statementUsuario.setString(5, usuario.getGenero());
            statementUsuario.setString(6, usuario.getTelefone());
            statementUsuario.setString(7, usuario.getCpf());
            statementUsuario.execute();
            statementUsuario.close();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Usuário " + usuario.getNome() + " (" + usuario.getCpf() + ") persistido!");
    }

    public boolean usuarioExistePorCpf(String cpf) {
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

    public Long retornarIdPorCpf(String cpf) {
        String sqlSelect = "SELECT * FROM TB_USUARIO WHERE cpf_usuario = ?";
        Long id = null;
        try {
            PreparedStatement statement = conexao.prepareStatement(sqlSelect);
            statement.setString(1, cpf);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                id = rs.getLong("id_usuario");
            }

            statement.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public Usuario buscarPorLogin(String email, String senha) {
        String sqlSelect = "SELECT * FROM TB_USUARIO WHERE email_usuario = ? AND senha_usuario = ?";
        Usuario usuario = new Usuario();
        try {
            PreparedStatement statement = conexao.prepareStatement(sqlSelect);
            statement.setString(1, email);
            statement.setString(2, senha);
            ResultSet rs = statement.executeQuery();

            if (!rs.next()) {
                System.out.println("Login não encontrado!");
                return null;
            }

            usuario.setNome(rs.getString("nome_usuario"));
            usuario.setEmail(rs.getString("email_usuario"));
            usuario.setSenha(rs.getString("senha_usuario"));
            usuario.setGenero(rs.getString("genero_usuario"));
            usuario.setTelefone(rs.getString("telefone_usuario"));
            usuario.setCpf(rs.getString("cpf_usuario"));

            statement.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
