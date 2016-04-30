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
    public List<UsuarioRespuesta> obtenerRespuestaUsuario(Integer idUsuario) throws Exception{
        try {
            Query consulta;
            StringBuilder cadena_consulta = new StringBuilder();
            cadena_consulta.append("select * from usuario_respuesta where usr_id = ").append(idUsuario);
            
            // Asigna crea el query de cadena_consulta
            consulta = getEntityManager().createNativeQuery(cadena_consulta.toString(), UsuarioRespuesta.class);
            List<UsuarioRespuesta> consultada = (List<UsuarioRespuesta>) consulta.getResultList();
            return consultada;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public List<UsuarioRespuesta> obtenerRespuestasGrupo(Integer idGrupo) throws Exception {
        try {
            Query consulta;
            StringBuilder cadena_consulta = new StringBuilder();
            cadena_consulta.append("select * from usuario_respuesta urta ");
            cadena_consulta.append("join usuario_grupo ugrupo on ugrupo.usr_id = urta.usr_id ");
            cadena_consulta.append("where ugrupo.grup_id = ").append(idGrupo);
            
            // Asigna crea el query de cadena_consulta
            consulta = getEntityManager().createNativeQuery(cadena_consulta.toString(), UsuarioRespuesta.class);
            List<UsuarioRespuesta> consultada = (List<UsuarioRespuesta>) consulta.getResultList();
            return consultada;
        } catch (Exception e) {
            throw new Exception();
        }
    }
    
}
