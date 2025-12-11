package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    // Dados de conexão com o MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/brasileirao";
    private static final String USER = "root";
    private static final String PASS = "123456";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    // Abre e retorna uma nova conexão
    public static Connection getConnection() {

        try {
            Class.forName(DRIVER);

            // Aviso simples no console
            System.out.println("Conectando ao banco de dados...");

            Connection conn = DriverManager.getConnection(URL, USER, PASS);

            System.out.println("Conectado!");

            return conn;

        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC não encontrado.");
            throw new RuntimeException("Driver JDBC ausente.", e);

        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados.");
            e.printStackTrace();
            throw new RuntimeException("Falha ao abrir conexão.", e);
        }
    }
}
