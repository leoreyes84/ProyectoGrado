/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.session;

import co.edu.udistrital.prototipovak.entity.SugerenciaResultado;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gerson Cespedes
 */
@Local
public interface SugerenciaResultadoFacadeLocal {

    void create(SugerenciaResultado sugerenciaResultado);

    void edit(SugerenciaResultado sugerenciaResultado);

    void remove(SugerenciaResultado sugerenciaResultado);

    SugerenciaResultado find(Object id);

    List<SugerenciaResultado> findAll();

    List<SugerenciaResultado> findRange(int[] range);

    int count();
    
}
