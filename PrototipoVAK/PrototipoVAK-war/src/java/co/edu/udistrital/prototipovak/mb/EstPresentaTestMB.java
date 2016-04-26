/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.mb;

import co.edu.udistrital.prototipovak.entity.Pregunta;
import co.edu.udistrital.prototipovak.entity.Respuesta;
import co.edu.udistrital.prototipovak.session.PreguntaFacadeLocal;
import co.edu.udistrital.prototipovak.util.ItemTestTO;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import org.jboss.logging.Logger;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@RequestScoped
public class EstPresentaTestMB {

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

    //////////////////////////////////////
    ////Métodos de la clase
    //////////////////////////////////////
    @PostConstruct
    public void inti() {
        obtenerPreguntasTest();
    }

    /**
     * Obtiene la lista de preguntas
     */
    public void obtenerPreguntasTest() {
        //Busca las preguntas junto con sus respuestas
        lstPreguntas = preguntaFacade.findAll();
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
    }

    public void guardarRespuestas() {
        String a = "";
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
