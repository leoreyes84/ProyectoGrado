/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.to;

/**
 *
 * @author lreyes
 */
public class PreguntaTO {
    
    private Integer pregId;
    private String pregunta;
    private Integer idRtaV;
    private Integer idRtaA;
    private Integer idRtaK;
    private String respuestaV;
    private String respuestaA;
    private String respuestaK;
    private Character tipoRtaV;
    private Character tipoRtaA;
    private Character tipoRtaK;

    public Integer getPregId() {
        return pregId;
    }

    public void setPregId(Integer pregId) {
        this.pregId = pregId;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public Integer getIdRtaV() {
        return idRtaV;
    }

    public void setIdRtaV(Integer idRtaV) {
        this.idRtaV = idRtaV;
    }

    public Integer getIdRtaA() {
        return idRtaA;
    }

    public void setIdRtaA(Integer idRtaA) {
        this.idRtaA = idRtaA;
    }

    public Integer getIdRtaK() {
        return idRtaK;
    }

    public void setIdRtaK(Integer idRtaK) {
        this.idRtaK = idRtaK;
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

    public Character getTipoRtaV() {
        return tipoRtaV;
    }

    public void setTipoRtaV(Character tipoRtaV) {
        this.tipoRtaV = tipoRtaV;
    }

    public Character getTipoRtaA() {
        return tipoRtaA;
    }

    public void setTipoRtaA(Character tipoRtaA) {
        this.tipoRtaA = tipoRtaA;
    }

    public Character getTipoRtaK() {
        return tipoRtaK;
    }

    public void setTipoRtaK(Character tipoRtaK) {
        this.tipoRtaK = tipoRtaK;
    }
    
}
