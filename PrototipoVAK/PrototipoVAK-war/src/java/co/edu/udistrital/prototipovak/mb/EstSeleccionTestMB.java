/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.mb;

import co.edu.udistrital.prototipovak.entity.Grupo;
import co.edu.udistrital.prototipovak.entity.UsuarioRespuesta;
import co.edu.udistrital.prototipovak.session.GrupoFacadeLocal;
import co.edu.udistrital.prototipovak.session.UsuarioRespuestaFacadeLocal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.jboss.logging.Logger;

/**
 *
 * @author Gerson Cespedes
 */
@ManagedBean
@RequestScoped
public class EstSeleccionTestMB {

    // /////////////////////////////////////////////////////////////////////////
    // Logger de la clase
    // /////////////////////////////////////////////////////////////////////////
    private static Logger _logger = Logger.getLogger(EstSeleccionTestMB.class);

    // /////////////////////////////////////////////////////////////////////////
    // EJB de la clase
    // /////////////////////////////////////////////////////////////////////////
    @EJB
    private GrupoFacadeLocal grupoFacade;
    @EJB
    private UsuarioRespuestaFacadeLocal usuarioRespuestaFacade;

    // /////////////////////////////////////////////////////////////////////////
    // Atributos de la clase
    // /////////////////////////////////////////////////////////////////////////
    private List<Grupo> lstGrupos;
    private List<UsuarioRespuesta> listaUsuarioRespuesta;
    private boolean presentarVerTestResultados;

    //////////////////////////////////////
    ////Métodos de la clase
    //////////////////////////////////////
    /**
     * Metodo que inicializa el backing bean
     */
    @PostConstruct
    public void init() {
        listarTestEstudiante();
    }

    /**
     * Obtiene la lista de test (test por grupo) disponibles para el estudiante
     */
    private void listarTestEstudiante() {
        try {
            Integer idUsuario = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
            lstGrupos = grupoFacade.findGrupoByIdUsuario(idUsuario);
            listaUsuarioRespuesta = usuarioRespuestaFacade.obtenerRespuestaUsuario(idUsuario);
            if (listaUsuarioRespuesta != null && listaUsuarioRespuesta.size() > 0) {
                presentarVerTestResultados = true;
            }
        } catch (Exception e) {
            _logger.error("Error consultando lista: " + e.getMessage());
        }
    }

    public String presentarTest(Integer idPeriodo) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idPeriodo", idPeriodo);
        return "presentaTest";
    }

    public List<Grupo> getLstGrupos() {
        return lstGrupos;
    }

    public void setLstGrupos(List<Grupo> lstGrupos) {
        this.lstGrupos = lstGrupos;
    }

    public boolean isPresentarVerTestResultados() {
        return presentarVerTestResultados;
    }

    public void setPresentarVerTestResultados(boolean presentarVerTestResultados) {
        this.presentarVerTestResultados = presentarVerTestResultados;
    }

}
