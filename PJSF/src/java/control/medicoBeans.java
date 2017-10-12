/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import infra.Medico;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectManyCheckbox;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.primefaces.context.RequestContext;

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

    public String salvar() {
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
        return "sucMedico";

    }

    public void salvar1() {
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
        md.setEmail("");
        md.setNome("");
        md.setSenha("");
        md = null;
        FacesMessage msg = new FacesMessage("O usuário " + this.nome + " foi cadastrado com sucesso");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "O usuário " + this.nome + " foi cadastrado"));
        UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
        //return view.getViewId() + "?faces-redirect=true";
        Iterator<UIComponent> filhos = view.getFacetsAndChildren();
        clearComponents(filhos);

    }

    private static void clearComponents(Iterator<UIComponent> filhosid) {
        while (filhosid.hasNext()) {
            UIComponent component = filhosid.next();
            if (component instanceof HtmlInputText) {
                HtmlInputText com = (HtmlInputText) component;
                com.resetValue();
            }
            if (component instanceof HtmlSelectOneMenu) {
                HtmlSelectOneMenu com = (HtmlSelectOneMenu) component;
                com.resetValue();
            }
            if (component instanceof HtmlSelectBooleanCheckbox) {
                HtmlSelectBooleanCheckbox com = (HtmlSelectBooleanCheckbox) component;
                com.resetValue();
            }
            if (component instanceof HtmlSelectManyCheckbox) {
                HtmlSelectManyCheckbox com = (HtmlSelectManyCheckbox) component;
                com.resetValue();
            }

            clearComponents(component.getFacetsAndChildren());

        }
    }

    public List<Medico> getMds() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PJSFPU");
        EntityManager em = emf.createEntityManager();
        Query q = em.createQuery("select m from Medico m", Medico.class);
        this.mds = q.getResultList();
        em.close();
        return mds;
    }

    public void excluir(Medico md) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PJSFPU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        md = em.merge(md);
        em.remove(md);
        tx.commit();
        em.close();
    }

}
