/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.session;

import co.edu.udistrital.prototipovak.entity.UsuarioRespuesta;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
}
