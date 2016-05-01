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
}
