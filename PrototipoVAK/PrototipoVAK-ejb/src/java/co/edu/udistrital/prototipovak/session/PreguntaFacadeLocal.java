/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.session;

import co.edu.udistrital.prototipovak.entity.Pregunta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author lreyes
 */
@Local
public interface PreguntaFacadeLocal {

    void create(Pregunta pregunta);

    void edit(Pregunta pregunta);

    void remove(Pregunta pregunta);

    Pregunta find(Object id);

    List<Pregunta> findAll();

    List<Pregunta> findRange(int[] range);

    int count();
    
    List<Pregunta> findPreguntaRandom() throws Exception;
    
}
