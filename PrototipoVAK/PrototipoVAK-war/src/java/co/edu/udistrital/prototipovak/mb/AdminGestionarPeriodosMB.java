/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.mb;

import co.edu.udistrital.prototipovak.entity.Periodo;
import co.edu.udistrital.prototipovak.session.PeriodoFacadeLocal;
import co.edu.udistrital.prototipovak.util.PeriodoDataModel;
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
public class AdminGestionarPeriodosMB {

    // /////////////////////////////////////////////////////////////////////////
    // Logger de la clase
    // /////////////////////////////////////////////////////////////////////////
    private static Logger _logger = Logger.getLogger(AdminGestionarPeriodosMB.class);

    // /////////////////////////////////////////////////////////////////////////
    // EJB de la clase
    // /////////////////////////////////////////////////////////////////////////
    @EJB
    private PeriodoFacadeLocal periodoFacade;

    // /////////////////////////////////////////////////////////////////////////
    // Atributos de la clase
    // /////////////////////////////////////////////////////////////////////////
    private String nombrePeriodo;
    private String descripcion;
    private List<Periodo> periodos;
    private Periodo selectedPeriodo;
    private PeriodoDataModel mediumPeriodoModel;

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
            Periodo periodo = new Periodo();
            periodo.setPeriNombre(nombrePeriodo);
            periodo.setPeriDescripcion(descripcion);
            periodoFacade.create(periodo);
            this.cargarTabla();
            this.limpiarValores();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro creado.", ""));
            _logger.info("Periodo creado");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en el sistema!", ""));
            _logger.error("Error guardarPeriodo() "+ex.getMessage(), ex);
        }
    }
    
    /**
     * Modifica el programa
     */
    public void modificarItem() {
        try {
            selectedPeriodo = (Periodo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedPeriodo");
            if (selectedPeriodo != null) {
                //Setear valores
                selectedPeriodo.setPeriNombre(nombrePeriodo);
                selectedPeriodo.setPeriDescripcion(descripcion);
                //Modificar
                periodoFacade.edit(selectedPeriodo);
                this.cargarTabla();
                this.limpiarValores();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro modificado", ""));
                _logger.info("Periodo modificado");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en el sistema!", ""));
            _logger.error("Error modificarPeriodo() "+ex.getMessage(), ex);
        }
    }
    
    /**
     * Elimina el programa
     */
    public void eliminarItem() {
        try {
            selectedPeriodo = (Periodo) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedPeriodo");
            if (selectedPeriodo != null) {
                //Eliminar
                periodoFacade.remove(selectedPeriodo);
                this.cargarTabla();
                this.limpiarValores();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro eliminado", ""));
                _logger.info("Periodo eliminado");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en el sistema!", ""));
            _logger.error("Error eliminarPeriodo() "+ex.getMessage(), ex);
        }
    }
    
    /**
     * Carga la tabla de programas
     */
    private void cargarTabla() {
        periodos = new ArrayList<Periodo>();
        // Consulta todos los programas
        periodos = periodoFacade.findAll();
        // Adiciona al data model para selección por pantalla
        mediumPeriodoModel = new PeriodoDataModel(periodos);
    }
    
    /**
     * Limpia valores de ventana emergente
     */
    public void limpiarValores() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("selectedPeriodo");
        this.nombrePeriodo = null;
        this.descripcion = null;
    }
    
    /**
     * Carga objeto a modificar
     */
    public void cargarValoresModificar() {
        if (null == selectedPeriodo) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR, no fue posible recuperar los datos del registro", ""));
        } else {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedPeriodo", selectedPeriodo);
            nombrePeriodo = selectedPeriodo.getPeriNombre();
            descripcion = selectedPeriodo.getPeriDescripcion();
        }
    }

    /**
     * Carga el objeto a eliminar en sesión
     */
    public void cargarObjetoSeleccionado() {
        if (null == selectedPeriodo) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR, no pudo ser eliminado el registro", ""));
        } else {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedPeriodo", selectedPeriodo);
        }
    }

    public String getNombrePeriodo() {
        return nombrePeriodo;
    }

    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Periodo getSelectedPeriodo() {
        return selectedPeriodo;
    }

    public void setSelectedPeriodo(Periodo selectedPeriodo) {
        this.selectedPeriodo = selectedPeriodo;
    }

    public PeriodoDataModel getMediumPeriodoModel() {
        return mediumPeriodoModel;
    }

    public void setMediumPeriodoModel(PeriodoDataModel mediumPeriodoModel) {
        this.mediumPeriodoModel = mediumPeriodoModel;
    }

    
}
