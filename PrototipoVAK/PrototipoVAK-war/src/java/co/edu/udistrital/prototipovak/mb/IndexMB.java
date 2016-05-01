/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.mb;

import co.edu.udistrital.prototipovak.entity.Rol;
import co.edu.udistrital.prototipovak.entity.Usuario;
import co.edu.udistrital.prototipovak.session.UsuarioFacadeLocal;
import co.edu.udistrital.prototipovak.util.Constantes;
import co.edu.udistrital.prototipovak.util.Seguridad;
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

    // /////////////////////////////////////////////////////////////////////////
    // EJB de la clase
    // /////////////////////////////////////////////////////////////////////////
    @EJB
    private UsuarioFacadeLocal usuarioFacade;

    // /////////////////////////////////////////////////////////////////////////
    // Logger de la clase
    // /////////////////////////////////////////////////////////////////////////
    private static Logger _logger = Logger.getLogger(IndexMB.class);

    // /////////////////////////////////////////////////////////////////////////
    // Atributos de la clase
    // /////////////////////////////////////////////////////////////////////////
    private String codigoUsuario;
    private String contrasenia;
    private List<Rol> lstRoles;
    private Boolean menuVisible;
    private Boolean administrador;
    private Boolean profesor;
    private Boolean estudiante;
    private Boolean variosRoles;

    // /////////////////////////////////////////////////////////////////////////
    // Metodos de la clase
    // /////////////////////////////////////////////////////////////////////////
    @PostConstruct
    public void inint() {
        limpiarValores();
    }

    /**
     * Método para realizar el inicio de sesión de la aplicación.
     *
     * @return
     */
    public String iniciarSesion() {
        try {
            limpiarValores();
            if (codigoUsuario == null || contrasenia == null || codigoUsuario.equals("") || contrasenia.equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Código y/o contraseña no Digitado", ""));
                return null;
            }
            //Consulta las credenciales del usuario
            Usuario usuario = usuarioFacade.usuarioByEmailYPass(codigoUsuario, Seguridad.Sha(contrasenia));
            if (usuario != null) {
                _logger.info("Login " + codigoUsuario);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idUsuario", usuario.getUsrId());
                lstRoles = usuario.getRolList();
                String tmpReturn = "";
                if (lstRoles != null && lstRoles.size() > 0) {
                    menuVisible = true;
                    for (Rol rolTemp : lstRoles) {
                        if (rolTemp.getRolCodigo().equals(Constantes.ROL_CODIGO_ADMINISTRADOR)) {
                            administrador = true;
                            tmpReturn = "bienvenida";
                        } else if (rolTemp.getRolCodigo().equals(Constantes.ROL_CODIGO_PROFESOR)) {
                            profesor = true;
                            tmpReturn = "profesor";
                        } else if (rolTemp.getRolCodigo().equals(Constantes.ROL_CODIGO_ESTUDIANTE)) {
                            estudiante = true;
                            tmpReturn = "estudiante";
                        }
                    }
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El usuario no tiene rol asociado", ""));
                }
                if (lstRoles != null && lstRoles.size() > 1) {
                    variosRoles = true;
                    return "bienvenida";
                } else {
                    return tmpReturn;
                }
            } else {
                _logger.info("Login errado " + codigoUsuario);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Código y/o contraseña incorrecta", ""));
                return null;
            }
        } catch (Exception ex) {
            _logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR, ocurrió un error", ""));
            return null;
        }
    }

    /**
     * Reset de valores en sesion e inicializacion de variables
     * que controlan visualizacion del menu
     */
    public void limpiarValores() {
        variosRoles = false;
        administrador = false;
        profesor = false;
        estudiante = false;
        menuVisible = false;
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("idUsuario");
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
        return lstRoles;
    }

    public void setRol(List<Rol> rol) {
        this.lstRoles = rol;
    }

    public Boolean getMenuVisible() {
        return menuVisible;
    }

    public void setMenuVisible(Boolean menuVisible) {
        this.menuVisible = menuVisible;
    }

    public Boolean getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Boolean administrador) {
        this.administrador = administrador;
    }

    public Boolean getProfesor() {
        return profesor;
    }

    public void setProfesor(Boolean profesor) {
        this.profesor = profesor;
    }

    public Boolean getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Boolean estudiante) {
        this.estudiante = estudiante;
    }

    public Boolean getVariosRoles() {
        return variosRoles;
    }

    public void setVariosRoles(Boolean variosRoles) {
        this.variosRoles = variosRoles;
    }

}
