/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.mb;

import co.edu.udistrital.prototipovak.entity.Grupo;
import co.edu.udistrital.prototipovak.entity.Periodo;
import co.edu.udistrital.prototipovak.entity.ProgramaAcademico;
import co.edu.udistrital.prototipovak.session.GrupoFacadeLocal;
import co.edu.udistrital.prototipovak.util.GrupoDataModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
public class AdminGestionarGruposMB {
    
    // /////////////////////////////////////////////////////////////////////////
    // EJB de la clase
    // /////////////////////////////////////////////////////////////////////////
    @EJB
    private GrupoFacadeLocal grupoFacade;
    
    // /////////////////////////////////////////////////////////////////////////
    // Logger de la clase
    // /////////////////////////////////////////////////////////////////////////
    private static Logger _logger = Logger.getLogger(AdminGestionarGruposMB.class);
    
    // /////////////////////////////////////////////////////////////////////////
    // Atributos de la clase
    // /////////////////////////////////////////////////////////////////////////
    private String nombreGrupo;
    private Integer idPrograma;
    private Integer idPeriodo;
    private List<Grupo> grupos;
    private Grupo selectedGrupo;
    private GrupoDataModel mediumGrupoModel;
    
    //////////////////////////////////////
    ////Métodos de la clase
    //////////////////////////////////////
    /**
     * Metodo que inicializa el bean
     */
    @PostConstruct
    public void init() {
        //Cargar tabla
        cargarTabla();
    }
    
    /**
     * Guarda el programa académico
     */
    public void guardarItem() {
        try {
            Grupo grupo = new Grupo();
            grupo.setGrupNombre(nombreGrupo);
            //Asociación programa
            grupo.setProgId(new ProgramaAcademico(idPrograma));
            //Asociación perido
            grupo.setPeriId(new Periodo(idPeriodo));
            grupoFacade.create(grupo);
            this.cargarTabla();
            this.limpiarValores();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro creado.", ""));
            _logger.info("Grupo creado");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en el sistema!", ""));
            _logger.error("Error guardar Grupo "+ex.getMessage(), ex);
        }
    }
    
    /**
     * Modifica el programa
     */
    public void modificarItem() {
        try {
            selectedGrupo = (Grupo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedGrupo");
            if (selectedGrupo != null) {
                //Setear valores
                selectedGrupo.setGrupNombre(nombreGrupo);
                //Asociación programa
                selectedGrupo.setProgId(new ProgramaAcademico(idPrograma));
                //Asociación periodo
                selectedGrupo.setPeriId(new Periodo(idPeriodo));
                //Modificar
                grupoFacade.edit(selectedGrupo);
                this.cargarTabla();
                this.limpiarValores();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro modificado", ""));
                _logger.info("Grupo modificado");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en el sistema!", ""));
            _logger.error("Error modificar Grupo "+ex.getMessage(), ex);
        }
    }
    
    /**
     * Elimina el programa
     */
    public void eliminarItem() {
        try {
            selectedGrupo = (Grupo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedGrupo");
            if (selectedGrupo != null) {
                //Eliminar
                grupoFacade.remove(selectedGrupo);
                this.cargarTabla();
                this.limpiarValores();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro eliminado", ""));
                _logger.info("Grupo eliminado");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en el sistema!", ""));
            _logger.error("Error eliminar Grupo "+ex.getMessage(), ex);
        }
    }
    
    /**
     * Carga la tabla de programas
     */
    private void cargarTabla() {
        grupos = new ArrayList<>();
        // Consulta todos los programas
        grupos = grupoFacade.findAll();
        // Adiciona al data model para selección por pantalla
        mediumGrupoModel = new GrupoDataModel(grupos);
    }
    
    /**
     * Limpia valores de ventana emergente
     */
    public void limpiarValores() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("selectedGrupo");
        this.nombreGrupo = null;
        this.idPrograma = null;
        this.idPeriodo = null;
    }
    
    /**
     * Carga objeto a modificar
     */
    public void cargarValoresModificar() {
        if (null == selectedGrupo) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR, no fue posible recuperar los datos del registro", ""));
        } else {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedGrupo", selectedGrupo);
            nombreGrupo = selectedGrupo.getGrupNombre();
            idPrograma = selectedGrupo.getProgId().getProgId();
            idPeriodo = selectedGrupo.getPeriId().getPeriId();
        }
    }

    /**
     * Carga el objeto a eliminar en sesión
     */
    public void cargarObjetoSeleccionado() {
        if (null == selectedGrupo) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR, no pudo ser eliminado el registro", ""));
        } else {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedGrupo", selectedGrupo);
        }
    }
    

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public Integer getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Integer idPrograma) {
        this.idPrograma = idPrograma;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Grupo getSelectedGrupo() {
        return selectedGrupo;
    }

    public void setSelectedGrupo(Grupo selectedGrupo) {
        this.selectedGrupo = selectedGrupo;
    }

    public GrupoDataModel getMediumGrupoModel() {
        return mediumGrupoModel;
    }

    public void setMediumGrupoModel(GrupoDataModel mediumGrupoModel) {
        this.mediumGrupoModel = mediumGrupoModel;
    }
    
}
