package br.com.ponto.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaSQL {

    public Connection getConnection() throws SQLException {
        try {
            //Responsavel pela conexao
            return DriverManager.getConnection("jdbc:postgresql://localhost/nutec", "root", "debian23");
            //return DriverManager.getConnection("jdbc:postgresql://192.168.1.11:5432/nutec", "sigi", "NtGeCsIgI");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
