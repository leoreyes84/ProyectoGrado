/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.session;

import co.edu.udistrital.prototipovak.entity.Grupo;
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
public class GrupoFacade extends AbstractFacade<Grupo> implements GrupoFacadeLocal {

    @PersistenceContext(unitName = "PrototipoVAK-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GrupoFacade() {
        super(Grupo.class);
    }

    @Override
    public List<Grupo> filtrarBusqueda(Integer idPrograma, Integer idPeriodo, Integer idGrupo) throws Exception {
        try {
            Query consulta;
            StringBuilder cadena_consulta = new StringBuilder();
            cadena_consulta.append("SELECT * FROM grupo gr JOIN programa_academico pa ON pa.prog_id = gr.prog_id JOIN periodo p ON p.peri_id = gr.peri_id ");
            cadena_consulta.append(" WHERE 1 = 1");

            if (idPrograma != null && idPrograma > 0) {
                cadena_consulta.append(" AND pa.prog_id=").append(idPrograma);
            }
            if (idPeriodo != null && idPeriodo > 0) {
                cadena_consulta.append(" AND p.peri_id=").append(idPeriodo);
            }
            if (idGrupo != null && idGrupo > 0) {
                cadena_consulta.append(" AND gr.grup_id=").append(idGrupo);
            }
            // Asigna crea el query de cadena_consulta
            consulta = getEntityManager().createNativeQuery(cadena_consulta.toString(), Grupo.class);
            List<Grupo> consultada = (List<Grupo>) consulta.getResultList();
            return consultada;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public Grupo findGrupoByID(Integer idGrupo) throws Exception{
        try {
            Grupo consultada = (Grupo) getEntityManager().createNamedQuery("Grupo.findByGrupId")
                    .setParameter("grupId", idGrupo)
                    .getSingleResult();
            return consultada;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @Override
    public List<Grupo> findGrupoByIdUsuario(Integer idUsuario) throws Exception {
        try {
            Query consulta;
            StringBuilder cadena_consulta = new StringBuilder();
            cadena_consulta.append("SELECT * FROM grupo gru ");
            cadena_consulta.append("JOIN programa_academico prog ON prog.prog_id = gru.prog_id ");
            cadena_consulta.append("JOIN periodo per ON per.peri_id = gru.peri_id ");
            cadena_consulta.append("JOIN usuario_grupo usrg on usrg.grup_id = gru.grup_id ");
            cadena_consulta.append("JOIN usuario usu on usu.usr_id = usrg.usr_id ");
            cadena_consulta.append("WHERE usu.usr_id =").append(idUsuario);
            // Asigna crea el query de cadena_consulta
            consulta = getEntityManager().createNativeQuery(cadena_consulta.toString(), Grupo.class);
            List<Grupo> consultada = (List<Grupo>) consulta.getResultList();
            return consultada;
        } catch (Exception e) {
            throw new Exception();
        }
    }

}
