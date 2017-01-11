/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ponto.DAO;

import br.com.ponto.conexao.ConectaSQL;
import br.com.ponto.entidade.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Shall
 */
public class FuncionarioDAO {

    private Connection conexao;

    public FuncionarioDAO() throws SQLException {
        this.conexao = (Connection) new ConectaSQL().getConnection();
    }
    
    public void alterar(Funcionario funcionario){
        try {
            //Criar comando SQL
            String cmdSql = "UPDATE sigi.funcionario set func_digital1=?, func_digital2=? where func_pk_id =?";

            //Estruturar o comando
            PreparedStatement stmt = conexao.prepareStatement(cmdSql);

            stmt.setBytes(1, funcionario.getFuncDigital1());
            stmt.setBytes(2, funcionario.getFuncDigital2());
            stmt.setInt(3, funcionario.getFuncPkId());
            

            //Executar o comando
            stmt.execute();

            //Fechar a conexao
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Funcionario> listarTodosFuncionarios() {
        List<Funcionario> listaFuncionarios = null;
        try {
            //Criar vetor que vai armazenar os usuarios
            listaFuncionarios = new ArrayList<>();

            //Criar comando SQL
            String cmdSql = "SELECT * from sigi.funcionario where func_status = true Order By func_nome";

            //Executar o comando
            PreparedStatement stmt = conexao.prepareStatement(cmdSql);
            ResultSet rs = stmt.executeQuery();
            
            //Percorrer os resultados no RS e colocados dentro da lista
            while(rs.next()){
                Funcionario funcionario = new Funcionario();
                funcionario.setFuncPkId(rs.getInt("func_pk_id"));
                funcionario.setFuncNome(rs.getString("func_nome"));
                listaFuncionarios.add(funcionario);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao montar a lista: "+e);
        }
        return listaFuncionarios;
    }
    
    public List<Funcionario> listarTodosFuncionariosComBiometria() {
        List<Funcionario> listaFuncionarios = null;
        try {
            //Criar vetor que vai armazenar os usuarios
            listaFuncionarios = new ArrayList<>();

            //Criar comando SQL
            String cmdSql = "SELECT * from sigi.funcionario where func_status = true and (func_digital1 not like '' or func_digital2 not like '') Order By func_nome";

            //Executar o comando
            PreparedStatement stmt = conexao.prepareStatement(cmdSql);
            ResultSet rs = stmt.executeQuery();
            
            //Percorrer os resultados no RS e colocados dentro da lista
            while(rs.next()){
                Funcionario funcionario = new Funcionario();
                funcionario.setFuncPkId(rs.getInt("func_pk_id"));
                funcionario.setFuncNome(rs.getString("func_nome"));
                funcionario.setFuncDigital1(rs.getBytes("func_digital1"));
                funcionario.setFuncDigital2(rs.getBytes("func_digital2"));
                listaFuncionarios.add(funcionario);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao montar a lista: "+e);
        }
        return listaFuncionarios;
    }
}
