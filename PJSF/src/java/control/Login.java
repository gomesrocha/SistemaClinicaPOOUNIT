/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import infra.SessionUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author root
 */
@Named(value = "login")
@SessionScoped
public class Login implements Serializable {

    /**
     * Creates a new instance of Login
     */
    private String login, senha;
    public Login() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String loginUser(){
        String url = "";
        if (this.login.equals("admin") && this.senha.equals("admin")) {
            url = "/admin/principal?faces-redirect=true";
            Object b = new Object();
            SessionUtil.setParam("UsuarioLogado", b);
        } else {
            url = "index?faces-redirect=true";
        }
        return url;

    }
    
}
