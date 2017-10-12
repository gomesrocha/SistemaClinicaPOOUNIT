/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infra;

import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author root
 */
public class SessionUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    public static HttpSession getSession() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) ctx.getExternalContext().getSession(
                false);
        return sessao;
    }

    public static void setParam(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getParam(String key) {
        return getSession().getAttribute(key);
    }

    public static void remove(String key) {
        getSession().removeAttribute(key);
    }

    public static void invalidate() {
        getSession().invalidate();
    }
}
