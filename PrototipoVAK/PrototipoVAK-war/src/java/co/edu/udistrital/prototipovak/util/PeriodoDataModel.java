/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.util;

import co.edu.udistrital.prototipovak.entity.Periodo;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Leonardo
 */
public class PeriodoDataModel extends ListDataModel<Periodo> implements SelectableDataModel<Periodo> {
    
    public PeriodoDataModel(List<Periodo> data) {  
        super(data);  
    } 

    @Override
    public Object getRowKey(Periodo periodo) {
        return periodo.getPeriId();
    }

    @Override
    public Periodo getRowData(String rowKey) {
        List<Periodo> periodos = (List<Periodo>) getWrappedData();  
          
        for(Periodo periodo : periodos) {  
            if(periodo.getPeriId().toString().equals(rowKey))  
                return periodo;  
        }  
        return null; 
    }
    
}
