/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.session;

import co.edu.udistrital.prototipovak.entity.Grupo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author lreyes
 */
@Local
public interface GrupoFacadeLocal {

    void create(Grupo grupo);

    void edit(Grupo grupo);

    void remove(Grupo grupo);

    Grupo find(Object id);

    List<Grupo> findAll();

    List<Grupo> findRange(int[] range);

    int count();
    
    List<Grupo> filtrarBusqueda(Integer idPrograma, Integer idPeriodo, Integer idGrupo) throws Exception;
    
    Grupo findGrupoByID(Integer idGrupo) throws Exception;
    
    List<Grupo> findGrupoByIdUsuario(Integer idUsuario) throws Exception;
    
}
