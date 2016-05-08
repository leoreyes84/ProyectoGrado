/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.session;

import co.edu.udistrital.prototipovak.entity.SugerenciaResultado;
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
public class SugerenciaResultadoFacade extends AbstractFacade<SugerenciaResultado> implements SugerenciaResultadoFacadeLocal {
    @PersistenceContext(unitName = "PrototipoVAK-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SugerenciaResultadoFacade() {
        super(SugerenciaResultado.class);
    }

    @Override
    public List<SugerenciaResultado> findSugerenciasEstutianteVisual() throws Exception {
         try {
            Query consulta;
            StringBuilder cadena_consulta = new StringBuilder();
            cadena_consulta.append("SELECT * FROM db_vak.sugerencia_resultado WHERE sug_tipo_usr = 'EST' AND sug_tipo_vak = 'V'");
            // Crea el query de cadena_consulta
            consulta = getEntityManager().createNativeQuery(cadena_consulta.toString(), SugerenciaResultado.class);
            List<SugerenciaResultado> consultada = (List<SugerenciaResultado>) consulta.getResultList();
            return consultada;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public List<SugerenciaResultado> findSugerenciasEstutianteAuditivo() throws Exception {
        try {
            Query consulta;
            StringBuilder cadena_consulta = new StringBuilder();
            cadena_consulta.append("SELECT * FROM db_vak.sugerencia_resultado WHERE sug_tipo_usr = 'EST' AND sug_tipo_vak = 'A'");
            // Crea el query de cadena_consulta
            consulta = getEntityManager().createNativeQuery(cadena_consulta.toString(), SugerenciaResultado.class);
            List<SugerenciaResultado> consultada = (List<SugerenciaResultado>) consulta.getResultList();
            return consultada;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public List<SugerenciaResultado> findSugerenciasEstutianteKinestesico() throws Exception {
        try {
            Query consulta;
            StringBuilder cadena_consulta = new StringBuilder();
            cadena_consulta.append("SELECT * FROM db_vak.sugerencia_resultado WHERE sug_tipo_usr = 'EST' AND sug_tipo_vak = 'K'");
            // Crea el query de cadena_consulta
            consulta = getEntityManager().createNativeQuery(cadena_consulta.toString(), SugerenciaResultado.class);
            List<SugerenciaResultado> consultada = (List<SugerenciaResultado>) consulta.getResultList();
            return consultada;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public List<SugerenciaResultado> findSugerenciasProfesorVisual() throws Exception {
        try {
            Query consulta;
            StringBuilder cadena_consulta = new StringBuilder();
            cadena_consulta.append("SELECT * FROM db_vak.sugerencia_resultado WHERE sug_tipo_usr = 'PRF' AND sug_tipo_vak = 'V'");
            // Crea el query de cadena_consulta
            consulta = getEntityManager().createNativeQuery(cadena_consulta.toString(), SugerenciaResultado.class);
            List<SugerenciaResultado> consultada = (List<SugerenciaResultado>) consulta.getResultList();
            return consultada;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public List<SugerenciaResultado> findSugerenciasProfesorAuditivo() throws Exception {
        try {
            Query consulta;
            StringBuilder cadena_consulta = new StringBuilder();
            cadena_consulta.append("SELECT * FROM db_vak.sugerencia_resultado WHERE sug_tipo_usr = 'PRF' AND sug_tipo_vak = 'A'");
            // Crea el query de cadena_consulta
            consulta = getEntityManager().createNativeQuery(cadena_consulta.toString(), SugerenciaResultado.class);
            List<SugerenciaResultado> consultada = (List<SugerenciaResultado>) consulta.getResultList();
            return consultada;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public List<SugerenciaResultado> findSugerenciasProfesorKinestesico() throws Exception {
        try {
            Query consulta;
            StringBuilder cadena_consulta = new StringBuilder();
            cadena_consulta.append("SELECT * FROM db_vak.sugerencia_resultado WHERE sug_tipo_usr = 'PRF' AND sug_tipo_vak = 'K'");
            // Crea el query de cadena_consulta
            consulta = getEntityManager().createNativeQuery(cadena_consulta.toString(), SugerenciaResultado.class);
            List<SugerenciaResultado> consultada = (List<SugerenciaResultado>) consulta.getResultList();
            return consultada;
        } catch (Exception e) {
            throw new Exception();
        }
    }
    
}
