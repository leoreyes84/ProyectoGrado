/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.mb;

import co.edu.udistrital.prototipovak.delegate.PreguntaDelegate;
import co.edu.udistrital.prototipovak.entity.Pregunta;
import co.edu.udistrital.prototipovak.entity.Respuesta;
import co.edu.udistrital.prototipovak.session.PreguntaFacadeLocal;
import co.edu.udistrital.prototipovak.session.RespuestaFacadeLocal;
import co.edu.udistrital.prototipovak.to.PreguntaTO;
import co.edu.udistrital.prototipovak.util.Constantes;
import co.edu.udistrital.prototipovak.util.PreguntaDataModel;
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
public class AdminGestionarPreguntasMB {

    // /////////////////////////////////////////////////////////////////////////
    // Logger de la clase
    // /////////////////////////////////////////////////////////////////////////
    private static Logger _logger = Logger.getLogger(AdminGestionarPreguntasMB.class);

    // /////////////////////////////////////////////////////////////////////////
    // EJB de la clase
    // /////////////////////////////////////////////////////////////////////////
    @EJB
    private PreguntaFacadeLocal preguntaFacade;
    @EJB
    private RespuestaFacadeLocal respuestaFacade;

    // /////////////////////////////////////////////////////////////////////////
    // Atributos de la clase
    // /////////////////////////////////////////////////////////////////////////
    private String pregunta;
    private String respuestaV;
    private String respuestaA;
    private String respuestaK;
    private Character tipoRtaV;
    private Character tipoRtaA;
    private Character tipoRtaK;
    private List<PreguntaTO> preguntas;
    private PreguntaTO selectedPregunta;
    private PreguntaDataModel mediumPreguntaModel;
    private PreguntaDelegate preguntaDelegate;

    //////////////////////////////////////
    ////Métodos de la clase
    //////////////////////////////////////
    /**
     * Metodo que inicializa el bean
     */
    @PostConstruct
    public void init() {
        //Delegate para operaciones
        preguntaDelegate = new PreguntaDelegate();
        //Cargar tabla
        cargarTabla();
    }

    /**
     * Guarda el programa académico
     */
    public void guardarItem() {
        try {
            PreguntaTO preguntaTO = new PreguntaTO();
            preguntaTO.setPregunta(pregunta);
            preguntaTO.setRespuestaV(respuestaV);
            preguntaTO.setRespuestaA(respuestaA);
            preguntaTO.setRespuestaK(respuestaK);
            preguntaTO.setTipoRtaV(Constantes.COD_TIPO_APRENDIZAJE_VISUAL);
            preguntaTO.setTipoRtaA(Constantes.COD_TIPO_APRENDIZAJE_AUDITIVO);
            preguntaTO.setTipoRtaK(Constantes.COD_TIPO_APRENDIZAJE_KINESTESICO);
            //Entity pregunta
            Pregunta preguntaEntity = preguntaEntity = preguntaDelegate.toPreguntaEntity(preguntaTO);
            preguntaFacade.create(preguntaEntity);
            this.cargarTabla();
            this.limpiarValores();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro creado.", ""));
            _logger.info("Pregunta creada");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en el sistema!", ""));
            _logger.error("Error guardar pregunta " + ex.getMessage(), ex);
        }
    }

    /**
     * Modifica el programa
     */
    public void modificarItem() {
        try {
            selectedPregunta = (PreguntaTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedPregunta");
            if (selectedPregunta != null) {
                //Setear valores
                selectedPregunta.setPregunta(pregunta);
                selectedPregunta.setRespuestaV(respuestaV);
                selectedPregunta.setRespuestaA(respuestaA);
                selectedPregunta.setRespuestaK(respuestaK);
                selectedPregunta.setTipoRtaV(Constantes.COD_TIPO_APRENDIZAJE_VISUAL);
                selectedPregunta.setTipoRtaA(Constantes.COD_TIPO_APRENDIZAJE_AUDITIVO);
                selectedPregunta.setTipoRtaK(Constantes.COD_TIPO_APRENDIZAJE_KINESTESICO);
                //Entity
                Pregunta preguntaEntity = preguntaEntity = preguntaDelegate.toPreguntaEntity(selectedPregunta);
                preguntaFacade.edit(preguntaEntity);
                this.cargarTabla();
                this.limpiarValores();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro modificado", ""));
                _logger.info("Pregunta modificada");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en el sistema!", ""));
            _logger.error("Error modificar Pregunta " + ex.getMessage(), ex);
        }
    }

    /**
     * Elimina el programa
     */
    public void eliminarItem() {
        try {
            selectedPregunta = (PreguntaTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedPregunta");
            if (selectedPregunta != null) {
                //Eliminar Entity
                Pregunta preguntaEntity = preguntaEntity = preguntaDelegate.toPreguntaEntity(selectedPregunta);
                //Remover respuestas (hijas)
                for (Respuesta rta : preguntaEntity.getRespuestaList()) {
                    respuestaFacade.remove(rta);
                }
                //Remover pregunta (padre)
                preguntaFacade.remove(preguntaEntity);
                this.cargarTabla();
                this.limpiarValores();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro eliminado", ""));
                _logger.info("Pregunta eliminada");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error en el sistema!", ""));
            _logger.error("Error eliminar Pregunta" + ex.getMessage(), ex);
        }
    }

    /**
     * Carga la tabla de programas
     */
    private void cargarTabla() {
        try {
            preguntas = new ArrayList<>();
            // Consulta todos los programas
            preguntas = this.findAllPreguntas();
            // Adiciona al data model para selección por pantalla
            mediumPreguntaModel = new PreguntaDataModel(preguntas);
        } catch (Exception ex) {
            _logger.error("Error cargando tabla" + ex.getMessage(), ex);
        }
    }

    /**
     * Limpia valores de ventana emergente
     */
    public void limpiarValores() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("selectedPregunta");
        this.pregunta = null;
        this.respuestaV = null;
        this.respuestaA = null;
        this.respuestaK = null;
    }

    /**
     * Carga objeto a modificar
     */
    public void cargarValoresModificar() {
        if (null == selectedPregunta) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR, no fue posible recuperar los datos del registro", ""));
        } else {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedPregunta", selectedPregunta);
            pregunta = selectedPregunta.getPregunta();
            respuestaV = selectedPregunta.getRespuestaV();
            respuestaA = selectedPregunta.getRespuestaA();
            respuestaK = selectedPregunta.getRespuestaK();
        }
    }

    /**
     * Carga el objeto a eliminar en sesión
     */
    public void cargarObjetoSeleccionado() {
        if (null == selectedPregunta) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR, no pudo ser eliminado el registro", ""));
        } else {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedPregunta", selectedPregunta);
        }
    }

    /**
     * Busca todas las preguntas y sus respuestas
     *
     * @return
     * @throws java.lang.Exception
     */
    private List<PreguntaTO> findAllPreguntas() throws Exception {
        try {
            //Busca las preguntas
            List<Pregunta> lstPreguntas = preguntaFacade.findAll();
            if (lstPreguntas != null && lstPreguntas.size() > 0) {
                List<PreguntaTO> lstPregTO = new ArrayList<>();
                for (Pregunta preEntity : lstPreguntas) {
                    //Convierte pregunta entity en TO
                    lstPregTO.add(preguntaDelegate.toPreguntaTO(preEntity));
                }
                return lstPregTO;
            }
            return null;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuestaV() {
        return respuestaV;
    }

    public void setRespuestaV(String respuestaV) {
        this.respuestaV = respuestaV;
    }

    public String getRespuestaA() {
        return respuestaA;
    }

    public void setRespuestaA(String respuestaA) {
        this.respuestaA = respuestaA;
    }

    public String getRespuestaK() {
        return respuestaK;
    }

    public void setRespuestaK(String respuestaK) {
        this.respuestaK = respuestaK;
    }

    public PreguntaTO getSelectedPregunta() {
        return selectedPregunta;
    }

    public void setSelectedPregunta(PreguntaTO selectedPregunta) {
        this.selectedPregunta = selectedPregunta;
    }

    public PreguntaDataModel getMediumPreguntaModel() {
        return mediumPreguntaModel;
    }

    public void setMediumPreguntaModel(PreguntaDataModel mediumPreguntaModel) {
        this.mediumPreguntaModel = mediumPreguntaModel;
    }

    public Character getTipoRta1() {
        return tipoRtaV;
    }

    public void setTipoRta1(Character tipoRta1) {
        this.tipoRtaV = tipoRta1;
    }

    public Character getTipoRta2() {
        return tipoRtaA;
    }

    public void setTipoRta2(Character tipoRta2) {
        this.tipoRtaA = tipoRta2;
    }

    public Character getTipoRta3() {
        return tipoRtaK;
    }

    public void setTipoRta3(Character tipoRta3) {
        this.tipoRtaK = tipoRta3;
    }

}
