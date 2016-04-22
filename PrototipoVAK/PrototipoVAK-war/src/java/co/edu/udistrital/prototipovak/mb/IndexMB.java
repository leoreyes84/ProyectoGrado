/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.mb;

import co.edu.udistrital.prototipovak.entity.Rol;
import co.edu.udistrital.prototipovak.entity.Usuario;
import co.edu.udistrital.prototipovak.session.UsuarioFacadeLocal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.jboss.logging.Logger;

/**
 *
 * @author Gerson Cespedes
 */
@ManagedBean
@SessionScoped
public class IndexMB {

    private String codigoUsuario;
    private String contrasenia;

    private List<Rol> rol;

    private Boolean menuVisible;

    @EJB
    private UsuarioFacadeLocal usuarioFacade;

    private static Logger _logger = Logger.getLogger(IndexMB.class);

    public IndexMB() {
    }

    @PostConstruct
    public void inint() {

        menuVisible = false;

    }

    /**
     * Método para realizar el inicio de sesión de la aplicación.
     *
     * @return
     */
    public String iniciarSesion() {
        try {
            if (codigoUsuario == null || contrasenia == null || codigoUsuario.equals("") || contrasenia.equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Código y/o contraseña no Digitado", ""));
                return null;
            }

            Usuario usuario = usuarioFacade.usuarioByEmailYPass(codigoUsuario, contrasenia);
            if (usuario != null) {
                _logger.info("Login " + codigoUsuario);
                //menuVisible = true;
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idUsuario", usuario.getUsrId());

                rol = usuario.getRolList();
                if (rol != null && rol.size() > 0) {
                    for (Rol rolTemp : rol) {

                        if (rolTemp.getRolNombre().equals("Administrador")) {
                            menuVisible = true;
                            return "administrador";
                        } else if (rolTemp.getRolNombre().equals("Profesor")) {
                            menuVisible = false;
                            return "profesor";
                        } else if (rolTemp.getRolNombre().equals("Estudiante")) {
                            menuVisible = false;
                            return "estudiante";
                        }

                    }
                }
            } else {
                _logger.info("Login errado " + codigoUsuario);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Código y/o contraseña incorrecta", ""));
                return null;
            }
            return null;
        } catch (Exception ex) {
            _logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR, ocurrió un error", ""));
            return null;
        }
    }

    /**
     * Pone la variable que controla el menú en falso para ocultarlo al salir de
     * la aplicación.
     */
    public void salir() {
        menuVisible = false;
    }

    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public List<Rol> getRol() {
        return rol;
    }

    public void setRol(List<Rol> rol) {
        this.rol = rol;
    }

    public Boolean getMenuVisible() {
        return menuVisible;
    }

    public void setMenuVisible(Boolean menuVisible) {
        this.menuVisible = menuVisible;
    }

}
