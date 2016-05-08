/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.session;

import co.edu.udistrital.prototipovak.entity.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Gerson Cespedes
 */
@Stateless
public class UsuarioFacade extends AbstractFacade<Usuario> implements UsuarioFacadeLocal {

    @PersistenceContext(unitName = "PrototipoVAK-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioFacade() {
        super(Usuario.class);
    }

    @Override
    public Usuario usuarioByEmailYPass(String codigoUsuario, String password){
        try {
            Query consulta;
            String cadena_consulta = "select * from usuario where usr_codigo= '" + codigoUsuario + "'and usr_contrasenia = '" + password + "'";
            // Asigna crea el query de cadena_consulta
            consulta = getEntityManager().createNativeQuery(cadena_consulta, Usuario.class);
            Usuario consultada = (Usuario) consulta.getSingleResult();
            return consultada;
        } catch (Exception e) {
            return null;
//            throw new Exception();
        }
    }

    @Override
    public List<Usuario> findUsuarioByCodigo(String codigoUsuario) throws Exception{
        try {
            List<Usuario> consultada = getEntityManager().createNamedQuery("Usuario.findByUsrCodigo")
                    .setParameter("usrCodigo", codigoUsuario)
                    .getResultList();
            return consultada;
        } catch (Exception e) {
           throw new Exception();
        }
    }

    @Override
    public Usuario findUsuarioById(Integer idUsuario) throws Exception{
        try {
            Usuario consultada = (Usuario) getEntityManager().createNamedQuery("Usuario.findByUsrId")
                    .setParameter("usrId", idUsuario)
                    .getSingleResult();
            return consultada;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public List<Usuario> findEstudiantesByIdGrupo(Integer idGrupo) throws Exception {
        try {
            Query consulta;
            StringBuilder cadena_consulta = new StringBuilder();
            cadena_consulta.append("select distinct usr.* from db_vak.usuario usr  ");
            cadena_consulta.append("join db_vak.usuario_grupo usrgr  on usrgr.usr_id = usr.usr_id ");
            cadena_consulta.append("join db_vak.grupo gr on gr.grup_id = usrgr.grup_id ");
//            cadena_consulta.append("join db_vak.usuario_respuesta usrrt on usrrt.usr_id = usr.usr_id ");
            cadena_consulta.append("where gr.grup_id = ").append(idGrupo);
            cadena_consulta.append(" order by usr.usr_apellidos");
            
            // Asigna crea el query de cadena_consulta
            consulta = getEntityManager().createNativeQuery(cadena_consulta.toString(), Usuario.class);
            List<Usuario> consultada = (List<Usuario>) consulta.getResultList();
            return consultada;
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
