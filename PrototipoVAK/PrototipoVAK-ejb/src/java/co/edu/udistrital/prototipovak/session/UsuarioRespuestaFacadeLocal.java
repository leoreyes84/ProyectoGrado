/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.session;

import co.edu.udistrital.prototipovak.entity.UsuarioRespuesta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author lreyes
 */
@Local
public interface UsuarioRespuestaFacadeLocal {

    void create(UsuarioRespuesta usuarioRespuesta);

    void edit(UsuarioRespuesta usuarioRespuesta);

    void remove(UsuarioRespuesta usuarioRespuesta);

    UsuarioRespuesta find(Object id);

    List<UsuarioRespuesta> findAll();

    List<UsuarioRespuesta> findRange(int[] range);

    int count();
    
}
