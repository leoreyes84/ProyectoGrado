/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.delegate;

import co.edu.udistrital.prototipovak.entity.Pregunta;
import co.edu.udistrital.prototipovak.entity.Respuesta;
import co.edu.udistrital.prototipovak.to.PreguntaTO;
import co.edu.udistrital.prototipovak.util.Constantes;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de transformar el entity de Preguntas en TO y viceversa 
 * @author Leonardo
 */
public class PreguntaDelegate {

    /**
     * Transforma el Entity en TO
     *
     * @param pregunta el entity a transformar
     * @return el TO transformado
     */
    public PreguntaTO toPreguntaTO(Pregunta pregunta) {
        PreguntaTO preguntaTO = new PreguntaTO();
        preguntaTO.setPregId(pregunta.getPregId());
        preguntaTO.setPregunta(pregunta.getPregPregunta());
        List<Respuesta> lstRespuestas = pregunta.getRespuestaList();
        if (lstRespuestas != null && lstRespuestas.size() > 0) {
            for (Respuesta respuesta : lstRespuestas) {
                if (respuesta.getRtaTipoRespuesta().equals(Constantes.COD_TIPO_APRENDIZAJE_VISUAL)) {
                    preguntaTO.setIdRtaV(respuesta.getRtaId());
                    preguntaTO.setRespuestaV(respuesta.getRtaRespuesta());
                    preguntaTO.setTipoRtaV(respuesta.getRtaTipoRespuesta());
                } else if (respuesta.getRtaTipoRespuesta().equals(Constantes.COD_TIPO_APRENDIZAJE_AUDITIVO)) {
                    preguntaTO.setIdRtaA(respuesta.getRtaId());
                    preguntaTO.setRespuestaA(respuesta.getRtaRespuesta());
                    preguntaTO.setTipoRtaA(respuesta.getRtaTipoRespuesta());
                } else if (respuesta.getRtaTipoRespuesta().equals(Constantes.COD_TIPO_APRENDIZAJE_KINESTESICO)) {
                    preguntaTO.setIdRtaK(respuesta.getRtaId());
                    preguntaTO.setRespuestaK(respuesta.getRtaRespuesta());
                    preguntaTO.setTipoRtaK(respuesta.getRtaTipoRespuesta());
                }
            }
        }
        return preguntaTO;
    }

    /**
     * Transforma el TO en Entity
     *
     * @param preguntaTo el to a transformar
     * @return el entity transformado
     */
    public Pregunta toPreguntaEntity(PreguntaTO preguntaTo) {
        Pregunta preg = new Pregunta();
        preg.setPregId(preguntaTo.getPregId());
        preg.setPregPregunta(preguntaTo.getPregunta());

        //Asociar respuestas
        Respuesta rtaV = new Respuesta();
        rtaV.setPregId(preg);
        rtaV.setRtaId(preguntaTo.getIdRtaV());
        rtaV.setRtaRespuesta(preguntaTo.getRespuestaV());
        rtaV.setRtaTipoRespuesta(preguntaTo.getTipoRtaV());

        Respuesta rtaA = new Respuesta();
        rtaA.setPregId(preg);
        rtaA.setRtaId(preguntaTo.getIdRtaA());
        rtaA.setRtaRespuesta(preguntaTo.getRespuestaA());
        rtaA.setRtaTipoRespuesta(preguntaTo.getTipoRtaA());

        Respuesta rtaK = new Respuesta();
        rtaK.setPregId(preg);
        rtaK.setRtaId(preguntaTo.getIdRtaK());
        rtaK.setRtaRespuesta(preguntaTo.getRespuestaK());
        rtaK.setRtaTipoRespuesta(preguntaTo.getTipoRtaK());

        List<Respuesta> lstRespuestas = new ArrayList<>();
        lstRespuestas.add(rtaV);
        lstRespuestas.add(rtaA);
        lstRespuestas.add(rtaK);
        preg.setRespuestaList(lstRespuestas);
        return preg;
    }

}
