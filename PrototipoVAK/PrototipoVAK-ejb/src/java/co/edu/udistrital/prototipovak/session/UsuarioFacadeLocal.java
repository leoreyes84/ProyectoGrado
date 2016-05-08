/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.session;

import co.edu.udistrital.prototipovak.entity.Usuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Gerson Cespedes
 */
@Local
public interface UsuarioFacadeLocal {

    void create(Usuario usuario);

    void edit(Usuario usuario);

    void remove(Usuario usuario);

    Usuario find(Object id);

    List<Usuario> findAll();

    List<Usuario> findRange(int[] range);

    int count();
    
    Usuario usuarioByEmailYPass(String codigoUsuario, String password) throws Exception;
    
    List<Usuario> findUsuarioByCodigo(String codigoUsuario) throws Exception;
    
    Usuario findUsuarioById(Integer idUsuario) throws Exception;
    
    List<Usuario> findEstudiantesByIdGrupo(Integer idGrupo) throws Exception;
    
}
