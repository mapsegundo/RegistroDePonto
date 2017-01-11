/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ponto.ejb;

import br.com.ponto.entidade.Funcionario;
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
public class FuncionarioFacade {

    private static final String PERSISTENCE_UNIT_NAME = "RegistroDePontoPU";
    private static EntityManagerFactory factory;
    
    public List<Funcionario> listaFuncionarios(){
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        Query query = em.createNamedQuery("Funcionario.listaTodos");
        return query.getResultList();
    }
}
