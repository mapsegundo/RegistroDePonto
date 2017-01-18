package br.com.ponto.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaSQL {

    public Connection getConnection() throws SQLException {
        try {
            //Responsavel pela conexao
            return DriverManager.getConnection("jdbc:postgresql://localhost/nutec", "root", "debian23");
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
