/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.mb;

import co.edu.udistrital.prototipovak.entity.Pregunta;
import co.edu.udistrital.prototipovak.entity.Respuesta;
import co.edu.udistrital.prototipovak.entity.UsuarioRespuesta;
import co.edu.udistrital.prototipovak.entity.UsuarioRespuestaPK;
import co.edu.udistrital.prototipovak.session.PreguntaFacadeLocal;
import co.edu.udistrital.prototipovak.session.UsuarioRespuestaFacadeLocal;
import co.edu.udistrital.prototipovak.util.ItemTestTO;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.jboss.logging.Logger;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@ViewScoped
public class EstPresentaTestMB {

    // /////////////////////////////////////////////////////////////////////////
    // EJB de la clase
    // /////////////////////////////////////////////////////////////////////////
    @EJB
    private UsuarioRespuestaFacadeLocal usuarioRespuestaFacade;
    @EJB
    private PreguntaFacadeLocal preguntaFacade;

    // /////////////////////////////////////////////////////////////////////////
    // Logger de la clase
    // /////////////////////////////////////////////////////////////////////////
    private static Logger _logger = Logger.getLogger(EstPresentaTestMB.class);

    // /////////////////////////////////////////////////////////////////////////
    // Atributos de la clase
    // /////////////////////////////////////////////////////////////////////////
    private List<Pregunta> lstPreguntas;
    private ItemTestTO[] itemTestTO;
    private Integer idUsuario;
    private Integer idPeriodo;

    //////////////////////////////////////
    ////Métodos de la clase
    //////////////////////////////////////
    @PostConstruct
    public void inti() {
        idUsuario = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
        idPeriodo = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idPeriodo");
        obtenerPreguntasTest();
    }

    /**
     * Obtiene la lista de preguntas
     */
    public void obtenerPreguntasTest() {
        try {
            //Busca las preguntas junto con sus respuestas
            lstPreguntas = preguntaFacade.findPreguntaRandom();
            if (lstPreguntas != null && lstPreguntas.size() > 0) {
                //Tamaño del arreglo de preguntas
                this.itemTestTO = new ItemTestTO[lstPreguntas.size()];
                int i = 0;
                for (Pregunta pregunta : lstPreguntas) {
                    itemTestTO[i] = new ItemTestTO();
                    itemTestTO[i].setIdPregunta(pregunta.getPregId());
                    itemTestTO[i].setPregunta(pregunta.getPregPregunta());
                    //Tamaño del arreglo de las respuestas
                    SelectItem[] itemsRespuestas = new SelectItem[pregunta.getRespuestaList().size()];
                    int j = 0;
                    for (Respuesta respuesta : pregunta.getRespuestaList()) {
                        SelectItem item = new SelectItem(respuesta.getRtaId(), respuesta.getRtaRespuesta());
                        itemsRespuestas[j] = item;
                        j++;
                    }
                    this.itemTestTO[i].setItemsRespuestas(itemsRespuestas);
                    i++;
                }
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "No fue posible cargar el test.", ""));
            _logger.error("Error obteniendo test " + Arrays.toString(ex.getStackTrace()));
        }
    }

    /**
     * Guarda en BD las respuestas seleccionadas por el usuario
     *
     * @return regla de navegación
     */
    public String guardarRespuestas() {
        try {
            //Recorrer el arreglo para guardar las respuestas
            for (ItemTestTO itemRespuesta : itemTestTO) {
                UsuarioRespuesta respuesta = new UsuarioRespuesta();
                UsuarioRespuestaPK respuestaPK = new UsuarioRespuestaPK();
                respuestaPK.setPeriId(idPeriodo);
                respuestaPK.setUsrId(idUsuario);
                respuestaPK.setRtaId(itemRespuesta.getIdRadioRespuesta());
                respuesta.setUsuarioRespuestaPK(respuestaPK);
                usuarioRespuestaFacade.create(respuesta);
            }
            _logger.info("Test guardado");
            return "verResultados";
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error guardando los datos.", ""));
            _logger.error("Error guardando respuestas " + Arrays.toString(ex.getStackTrace()));
            return null;
        }
    }

    public List<Pregunta> getLstPreguntas() {
        return lstPreguntas;
    }

    public void setLstPreguntas(List<Pregunta> lstPreguntas) {
        this.lstPreguntas = lstPreguntas;
    }

    public ItemTestTO[] getItemTestTO() {
        return itemTestTO;
    }

    public void setItemTestTO(ItemTestTO[] itemTestTO) {
        this.itemTestTO = itemTestTO;
    }

}
