/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.session;

import co.edu.udistrital.prototipovak.entity.Periodo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author lreyes
 */
@Local
public interface PeriodoFacadeLocal {

    void create(Periodo periodo);

    void edit(Periodo periodo);

    void remove(Periodo periodo);

    Periodo find(Object id);

    List<Periodo> findAll();

    List<Periodo> findRange(int[] range);

    int count();
    
}
