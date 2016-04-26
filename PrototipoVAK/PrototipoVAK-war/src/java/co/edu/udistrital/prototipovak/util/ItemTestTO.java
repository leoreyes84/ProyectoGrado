/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.util;

import javax.faces.model.SelectItem;

/**
 *
 * @author Leonardo
 */
public class ItemTestTO {
    
    private Integer idPregunta;
    private String pregunta;
    private Integer idRadioRespuesta;
    private SelectItem[] itemsRespuestas;

    public Integer getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Integer idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public Integer getIdRadioRespuesta() {
        return idRadioRespuesta;
    }

    public void setIdRadioRespuesta(Integer idRadioRespuesta) {
        this.idRadioRespuesta = idRadioRespuesta;
    }

    public SelectItem[] getItemsRespuestas() {
        return itemsRespuestas;
    }

    public void setItemsRespuestas(SelectItem[] itemsRespuestas) {
        this.itemsRespuestas = itemsRespuestas;
    }
    
        
    
}
