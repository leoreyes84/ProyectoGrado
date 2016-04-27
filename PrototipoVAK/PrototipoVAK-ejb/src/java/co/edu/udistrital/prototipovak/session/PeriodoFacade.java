/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.session;

import co.edu.udistrital.prototipovak.entity.Periodo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author lreyes
 */
@Stateless
public class PeriodoFacade extends AbstractFacade<Periodo> implements PeriodoFacadeLocal {
    @PersistenceContext(unitName = "PrototipoVAK-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PeriodoFacade() {
        super(Periodo.class);
    }

    @Override
    public Periodo findPeriodoById(Integer idPeriodo) throws Exception {
        try {
            Periodo consultada = (Periodo) getEntityManager().createNamedQuery("Periodo.findByPeriId")
                    .setParameter("periId", idPeriodo)
                    .getSingleResult();
            return consultada;
        } catch (Exception e) {
            throw new Exception();
        }
    }
    
}
