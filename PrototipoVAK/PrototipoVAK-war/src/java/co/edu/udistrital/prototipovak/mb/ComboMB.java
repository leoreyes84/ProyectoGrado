/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.mb;

import co.edu.udistrital.prototipovak.entity.Grupo;
import co.edu.udistrital.prototipovak.entity.Periodo;
import co.edu.udistrital.prototipovak.entity.ProgramaAcademico;
import co.edu.udistrital.prototipovak.session.GrupoFacadeLocal;
import co.edu.udistrital.prototipovak.session.PeriodoFacadeLocal;
import co.edu.udistrital.prototipovak.session.ProgramaAcademicoFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author lreyes
 */
@ManagedBean
@ApplicationScoped
public class ComboMB {

    @EJB
    private GrupoFacadeLocal grupoFacade;
    @EJB
    private PeriodoFacadeLocal periodoFacade;
    @EJB
    private ProgramaAcademicoFacadeLocal programaAcademicoFacade;

    /**
     * Consulta los programas académicos
     * @return Lista tipo selectItem con los programas académicos
     */
    public List<SelectItem> getComboProgramaAcademico() {
        //Consutar Programas académicos
        List<ProgramaAcademico> list = programaAcademicoFacade.findAll();
        List<SelectItem> res = new ArrayList<>();
        if (list != null) {
            for (ProgramaAcademico progAcad : list) {
                res.add(new SelectItem(progAcad.getProgId(), progAcad.getProgNombre()));
            }
        }
        return res;
    }

    /**
     * Consulta los períodos
     * @return Lista tipo selectItem con los períodos
     */
    public List<SelectItem> getComboPeriodo() {
        //Consultar períodos
        List<Periodo> list = periodoFacade.findAll();
        List<SelectItem> res = new ArrayList<>();
        if (list != null) {
            for (Periodo periodo : list) {
                res.add(new SelectItem(periodo.getPeriId(), periodo.getPeriNombre()));
            }
        }
        return res;
    }

    /**
     * Consulta los grupos
     * @return Lista tipo selectItem con los grupos
     */
    public List<SelectItem> getComboGrupo() {
        //Consultar períodos
        List<Grupo> list = grupoFacade.findAll();
        List<SelectItem> res = new ArrayList<>();
        if (list != null) {
            for (Grupo grupo : list) {
                res.add(new SelectItem(grupo.getGrupId(), grupo.getGrupNombre()));
            }
        }
        return res;
    }
    

}
