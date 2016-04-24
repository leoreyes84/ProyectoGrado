/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.util;

import co.edu.udistrital.prototipovak.entity.Pregunta;
import co.edu.udistrital.prototipovak.to.PreguntaTO;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Leonardo
 */
public class PreguntaDataModel extends ListDataModel<PreguntaTO> implements SelectableDataModel<PreguntaTO>{
    
    public PreguntaDataModel(List<PreguntaTO> data) {  
        super(data);  
    } 

    @Override
    public Object getRowKey(PreguntaTO pregunta) {
        return pregunta.getPregId();
    }

    @Override
    public PreguntaTO getRowData(String rowKey) {
        List<PreguntaTO> preguntas = (List<PreguntaTO>) getWrappedData();  
          
        for(PreguntaTO pregunta : preguntas) {  
            if(pregunta.getPregId().toString().equals(rowKey))  
                return pregunta;  
        }  
        return null; 
    }
    
}
