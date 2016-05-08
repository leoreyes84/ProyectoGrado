/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.session;

import co.edu.udistrital.prototipovak.entity.Pregunta;
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
public class PreguntaFacade extends AbstractFacade<Pregunta> implements PreguntaFacadeLocal {

    @PersistenceContext(unitName = "PrototipoVAK-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PreguntaFacade() {
        super(Pregunta.class);
    }

    @Override
    public List<Pregunta> findPreguntaRandom() throws Exception {
        try {
            Query consulta;
            StringBuilder cadena_consulta = new StringBuilder();
            cadena_consulta.append("SELECT * FROM db_vak.pregunta ORDER BY RAND() LIMIT 5");

            // Crea el query de cadena_consulta
            consulta = getEntityManager().createNativeQuery(cadena_consulta.toString(), Pregunta.class);
            List<Pregunta> consultada = (List<Pregunta>) consulta.getResultList();
            return consultada;
        } catch (Exception e) {
            throw new Exception();
        }
    }

}
