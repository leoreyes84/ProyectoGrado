/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.session;

import co.edu.udistrital.prototipovak.entity.UsuarioRespuesta;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author lreyes
 */
@Stateless
public class UsuarioRespuestaFacade extends AbstractFacade<UsuarioRespuesta> implements UsuarioRespuestaFacadeLocal {
    @PersistenceContext(unitName = "PrototipoVAK-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioRespuestaFacade() {
        super(UsuarioRespuesta.class);
    }
    
    @Override
    public List<UsuarioRespuesta> obtenerRespuestaUsuario(Integer idUsuario) {
        try {
            Query consulta;
            String cadena_consulta = "select * from usuario_respuesta where usr_id = " + idUsuario;
            
            // Asigna crea el query de cadena_consulta
            consulta = getEntityManager().createNativeQuery(cadena_consulta, UsuarioRespuesta.class);
            List<UsuarioRespuesta> consultada = (List<UsuarioRespuesta>) consulta.getResultList();
            return consultada;
        } catch (Exception e) {
            e.getCause();
            return null;
        }
    }
    
}
