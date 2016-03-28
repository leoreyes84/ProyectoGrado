/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.session;

import co.edu.udistrital.prototipovak.entity.ProgramaAcademico;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author lreyes
 */
@Local
public interface ProgramaAcademicoFacadeLocal {

    void create(ProgramaAcademico programaAcademico);

    void edit(ProgramaAcademico programaAcademico);

    void remove(ProgramaAcademico programaAcademico);

    ProgramaAcademico find(Object id);

    List<ProgramaAcademico> findAll();

    List<ProgramaAcademico> findRange(int[] range);

    int count();
    
}
