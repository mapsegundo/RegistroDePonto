/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ponto.DAO;

import br.com.ponto.conexao.ConectaSQL;
import br.com.ponto.entidade.Batida;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Shall
 */
public class BatidaDAO {

    private Connection conexao;

    public BatidaDAO() throws SQLException {
        this.conexao = (Connection) new ConectaSQL().getConnection();
    }
    
    public void gerarBatida(Batida batida){
        try {
            //Criar comando SQL
            String cmdSql = "INSERT INTO sigi.funcionario (bat_fk_funcionario_id, bat_data, bat_hora) values (?,?,?)";

            //Estruturar o comando
            PreparedStatement stmt = conexao.prepareStatement(cmdSql);

            stmt.setInt(1, batida.getBatFkFuncionarioId().getFuncPkId());
            stmt.setDate(2, batida.getBatData());
            stmt.setTime(3, batida.getBatHora());
            
            //Executar o comando
            stmt.execute();

            //Fechar a conexao
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
}
