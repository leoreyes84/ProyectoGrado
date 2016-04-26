/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.mb;

import co.edu.udistrital.prototipovak.entity.Pregunta;
import co.edu.udistrital.prototipovak.session.PreguntaFacadeLocal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
    private String[] idsRespuesta;
    private String idprueba;
    
    @PostConstruct
    public void inti(){
        obtenerPreguntasTest();
    }
    
    /**
     * Obtiene la lista de preguntas
     */
    public void obtenerPreguntasTest(){
        lstPreguntas = preguntaFacade.findAll();
    }
    
    public void guardarRespuestas(){
        String a="";
        System.out.println("idprueba: "+idprueba);
    }

    public List<Pregunta> getLstPreguntas() {
        return lstPreguntas;
    }

    public void setLstPreguntas(List<Pregunta> lstPreguntas) {
        this.lstPreguntas = lstPreguntas;
    }

    public String[] getIdsRespuesta() {
        return idsRespuesta;
    }

    public void setIdsRespuesta(String[] idsRespuesta) {
        this.idsRespuesta = idsRespuesta;
    }

    public String getIdprueba() {
        return idprueba;
    }

    public void setIdprueba(String idprueba) {
        this.idprueba = idprueba;
    }


}
