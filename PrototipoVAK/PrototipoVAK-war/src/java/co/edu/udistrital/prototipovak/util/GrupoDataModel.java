/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.util;

import co.edu.udistrital.prototipovak.entity.Grupo;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Leonardo
 */
public class GrupoDataModel extends ListDataModel<Grupo> implements SelectableDataModel<Grupo> {
    
    public GrupoDataModel(List<Grupo> data) {  
        super(data);  
    } 

    @Override
    public Object getRowKey(Grupo grupo) {
        return grupo.getGrupId();
    }

    @Override
    public Grupo getRowData(String rowKey) {
        List<Grupo> grupos = (List<Grupo>) getWrappedData();  
          
        for(Grupo grupo : grupos) {  
            if(grupo.getGrupId().toString().equals(rowKey))  
                return grupo;  
        }  
        return null; 
    }
       
}
