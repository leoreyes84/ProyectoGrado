/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.mb;

import co.edu.udistrital.prototipovak.entity.ProgramaAcademico;
import co.edu.udistrital.prototipovak.session.ProgramaAcademicoFacadeLocal;
import co.edu.udistrital.prototipovak.util.ProgramaDataModel;
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
public class AdminGestionarProgramasMB {

    // /////////////////////////////////////////////////////////////////////////
    // Logger de la clase
    // /////////////////////////////////////////////////////////////////////////
    private static Logger _logger = Logger.getLogger(AdminGestionarProgramasMB.class);

    @EJB
    private ProgramaAcademicoFacadeLocal programaAcademicoFacade;

    private String codigoPrograma;
    private String nombrePrograma;
    private List<ProgramaAcademico> programas;
    private ProgramaAcademico selectedPrograma;
    private ProgramaDataModel mediumProgramaModel;

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
            ProgramaAcademico programaAcademico = new ProgramaAcademico();
            programaAcademico.setProgCodigo(codigoPrograma);
            programaAcademico.setProgNombre(this.nombrePrograma);
            programaAcademicoFacade.create(programaAcademico);
            this.cargarTabla();
            this.limpiarValores();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro creado.", ""));
            _logger.info("Programa creado");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en el sistema!", ""));
            _logger.error("Error guardarItem() "+ex.getMessage(), ex);
        }
    }
    
    /**
     * Modifica el programa
     */
    public void modificarItem() {
        try {
            selectedPrograma = (ProgramaAcademico) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedPrograma");
            if (selectedPrograma != null) {
                //Setear valores
                selectedPrograma.setProgCodigo(this.codigoPrograma);
                selectedPrograma.setProgNombre(this.nombrePrograma);
                //Modificar
                programaAcademicoFacade.edit(selectedPrograma);
                this.cargarTabla();
                this.limpiarValores();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro modificado", ""));
                _logger.info("Programa modificado");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en el sistema!", ""));
            _logger.error("Error modificarPrograma() "+ex.getMessage(), ex);
        }
    }
    
    /**
     * Elimina el programa
     */
    public void eliminarItem() {
        try {
            selectedPrograma = (ProgramaAcademico) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedPrograma");
            if (selectedPrograma != null) {
                //Eliminar
                programaAcademicoFacade.remove(selectedPrograma);
                this.cargarTabla();
                this.limpiarValores();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro eliminado", ""));
                _logger.info("Programa eliminado");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en el sistema!", ""));
            _logger.error("Error eliminarPrograma() "+ex.getMessage(), ex);
        }
    }
    
    /**
     * Carga la tabla de programas
     */
    private void cargarTabla() {
        programas = new ArrayList<ProgramaAcademico>();
        // Consulta todos los programas
        programas = programaAcademicoFacade.findAll();
        // Adiciona al data model para selección por pantalla
        mediumProgramaModel = new ProgramaDataModel(programas);
    }
    
    /**
     * Limpia valores de ventana emergente
     */
    public void limpiarValores() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("selectedPrograma");
        this.codigoPrograma = null;
        this.nombrePrograma = null;
    }
    
    /**
     * Carga objeto a modificar
     */
    public void cargarValoresModificar() {
        if (null == selectedPrograma) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR, no fue posible recuperar los datos del registro", ""));
        } else {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedPrograma", selectedPrograma);
            codigoPrograma = selectedPrograma.getProgCodigo();
            nombrePrograma = selectedPrograma.getProgNombre();
        }
    }

    /**
     * Carga el objeto a eliminar en sesión
     */
    public void cargarObjetoSeleccionado() {
        if (null == selectedPrograma) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR, no pudo ser eliminado el registro", ""));
        } else {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedPrograma", selectedPrograma);
        }
    }

    public String getCodigoPrograma() {
        return codigoPrograma;
    }

    public void setCodigoPrograma(String codigoPrograma) {
        this.codigoPrograma = codigoPrograma;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    public ProgramaDataModel getMediumProgramaModel() {
        return mediumProgramaModel;
    }

    public void setMediumProgramaModel(ProgramaDataModel mediumProgramaModel) {
        this.mediumProgramaModel = mediumProgramaModel;
    }

    public ProgramaAcademico getSelectedPrograma() {
        return selectedPrograma;
    }

    public void setSelectedPrograma(ProgramaAcademico selectedPrograma) {
        this.selectedPrograma = selectedPrograma;
    }

    
}
