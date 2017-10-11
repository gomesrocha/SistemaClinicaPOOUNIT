/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import infra.Medico;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author root
 */
@Named(value = "medicoBeans")
@RequestScoped
public class medicoBeans {
    private String nome;
    private String email;
    private String senha;
    private List<Medico> mds;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    /**
     * Creates a new instance of medicoBeans
     */
    public medicoBeans() {
    }
    public String salvar(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PJSFPU");
        EntityManager em = emf.createEntityManager();
        Medico md = new Medico();
        md.setNome(this.nome);
        md.setEmail(this.email);
        md.setSenha(this.senha);
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(md);
        tx.commit();
        em.close();
        emf.close();
        return"index";
        
    }

    public List<Medico> getMds() {    
         EntityManagerFactory emf = Persistence.createEntityManagerFactory("PJSFPU");
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("select m from Medico m", Medico.class);
        this.mds = q.getResultList();
        em.close();
        return mds;
    }
    
}
