/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.util;

import co.edu.udistrital.prototipovak.entity.ProgramaAcademico;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Leonardo
 */
public class ProgramaDataModel extends ListDataModel<ProgramaAcademico> implements SelectableDataModel<ProgramaAcademico>{
    
    public ProgramaDataModel(List<ProgramaAcademico> data) {  
        super(data);  
    } 

    @Override
    public Object getRowKey(ProgramaAcademico programa) {
        return programa.getProgId();
    }

    @Override
    public ProgramaAcademico getRowData(String rowKey) {
        List<ProgramaAcademico> programas = (List<ProgramaAcademico>) getWrappedData();  
          
        for(ProgramaAcademico programa : programas) {  
            if(programa.getProgId().toString().equals(rowKey))  
                return programa;  
        }  
        return null; 
    }
    
}
