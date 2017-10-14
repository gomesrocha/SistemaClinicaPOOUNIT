/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import infra.Endereco;
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
import org.primefaces.event.FlowEvent;

/**
 *
 * @author root
 */
@Named(value = "medicoBeans")
@RequestScoped
public class medicoBeans {

    private List<Medico> mds;
    private Endereco end;
    private Medico md;

    public Endereco getEnd() {
        return end;
    }

    public void setEnd(Endereco end) {
        this.end = end;
    }
    
    private boolean skip;

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    
    public Medico getMd() {
        return md;
    }

    public void setMd(Medico md) {
        this.md = md;
    }
    
    /**
     * Creates a new instance of medicoBeans
     */
    public medicoBeans() {
        end = new Endereco();
        md = new Medico();
    }

    public String salvar() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PJSFPU");
        EntityManager em = emf.createEntityManager();
        md.setEnd(end);
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
        md.setEnd(end);
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
        FacesMessage msg = new FacesMessage("O usuário " + this.md.getNome() + " foi cadastrado com sucesso");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "O usuário " + this.md.getNome() + " foi cadastrado"));
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
