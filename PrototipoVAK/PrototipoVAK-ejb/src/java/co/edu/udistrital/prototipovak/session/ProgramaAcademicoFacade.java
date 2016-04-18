/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.session;

import co.edu.udistrital.prototipovak.entity.ProgramaAcademico;
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
public class ProgramaAcademicoFacade extends AbstractFacade<ProgramaAcademico> implements ProgramaAcademicoFacadeLocal {

    @PersistenceContext(unitName = "PrototipoVAK-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProgramaAcademicoFacade() {
        super(ProgramaAcademico.class);
    }

    @Override
    public List<ProgramaAcademico> programaAcadPorEstudiante(String codigoEstudiante) {
        try {
            Query consulta;
            String cadena_consulta = "SELECT * FROM programa_academico progaca, grupo grup, usuario_grupo usugrupo, usuario usu "
                    + "WHERE progaca.prog_id=grup.prog_id "
                    + "AND usugrupo.usr_id= usu.usr_id "
                    + "AND usu.usr_codigo= " + codigoEstudiante;                   

            // Asigna crea el query de cadena_consulta
            consulta = getEntityManager().createNativeQuery(cadena_consulta, ProgramaAcademico.class);
            List<ProgramaAcademico> consultada = (List<ProgramaAcademico>) consulta.getResultList();
            return consultada;
        } catch (Exception e) {
            e.getCause();
            return null;
        }
    }

}
