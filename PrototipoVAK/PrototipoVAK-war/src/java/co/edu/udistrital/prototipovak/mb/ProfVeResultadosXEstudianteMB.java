/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.mb;

import co.edu.udistrital.prototipovak.entity.Grupo;
import co.edu.udistrital.prototipovak.entity.Usuario;
import co.edu.udistrital.prototipovak.session.GrupoFacadeLocal;
import co.edu.udistrital.prototipovak.session.UsuarioFacadeLocal;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.jboss.logging.Logger;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@RequestScoped
public class ProfVeResultadosXEstudianteMB {
    
    // /////////////////////////////////////////////////////////////////////////
    // EJB de la clase
    // /////////////////////////////////////////////////////////////////////////
    @EJB
    private UsuarioFacadeLocal usuarioFacade;
    @EJB
    private GrupoFacadeLocal grupoFacade;
    
    
    // /////////////////////////////////////////////////////////////////////////
    // Logger de la clase
    // /////////////////////////////////////////////////////////////////////////
    private static Logger _logger = Logger.getLogger(ProfVeResultadosXEstudianteMB.class);

    // /////////////////////////////////////////////////////////////////////////
    // Atributos de la clase
    // /////////////////////////////////////////////////////////////////////////
    private List<Usuario> lstEstudiantes;
     private Grupo grupo;
    
    // /////////////////////////////////////////////////////////////////////////
    // Metodos de la clase
    // /////////////////////////////////////////////////////////////////////////
    @PostConstruct
    public void init() {
        //Obtiene de sesion el grupo para obtener resultados
        Integer idGrupo = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idGrupo");
        try {
            grupo = grupoFacade.findGrupoByID(idGrupo);
            //Lista de estudiantes que han respondido
            lstEstudiantes = usuarioFacade.findEstudiantesByIdGrupo(idGrupo);
        } catch (Exception ex) {
            _logger.error("Error cargar lista " + ex.getMessage(), ex);
        }
    }
    
    public String mostrarResultadoEstudiante(Integer idUsuario){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idUsuario",idUsuario);
        return "verResultadosIndividuales";
    }

    public List<Usuario> getLstEstudiantes() {
        return lstEstudiantes;
    }

    public void setLstEstudiantes(List<Usuario> lstEstudiantes) {
        this.lstEstudiantes = lstEstudiantes;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }
    
}
