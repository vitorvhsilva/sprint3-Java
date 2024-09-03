package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public Connection obterConexao() {
        try {
            return DriverManager.getConnection("jdbc:oracle:thin:@//oracle.fiap.com.br:1521/ORCL",
                    "rm558961", "280306");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
