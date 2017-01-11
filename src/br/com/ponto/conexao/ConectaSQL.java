package br.com.ponto.conexao;

import br.com.ponto.entidade.Funcionario;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ConectaSQL {
        private static final String PERSISTENCE_UNIT_NAME = "RegistroDePontoPU";
        private static EntityManagerFactory factory;

        public static void main(String[] args) {
                factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
                EntityManager em = factory.createEntityManager();
                // Listar Funcionarios
                Query q = em.createQuery("select f from Funcionario f where f.funcStatus = true");
                List<Funcionario> todoList = q.getResultList();
                for (Funcionario todo : todoList) {
                        System.out.println(todo.getFuncNome()+" - "+todo.getFuncRg());
                }
                System.out.println("Size: " + todoList.size());

                
        }
}