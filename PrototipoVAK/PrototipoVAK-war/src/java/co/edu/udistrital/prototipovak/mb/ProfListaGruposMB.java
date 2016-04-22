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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Gerson Cespedes
 */
@ManagedBean
@ViewScoped
public class ProfListaGruposMB {

    private List<ProgramaAcademico> listaProgramasAcademicos;
    private List<Periodo> listaPeriodos;
    private List<Grupo> listaGrupos;
    private List<Grupo> listaFiltrada;
   
    private Map<String, Integer> listNombreProg = new HashMap<String, Integer>();
    private Map<String, Integer> listNombrePeriodo = new HashMap<String, Integer>();
    private Map<String, Integer> listNombreGrupo = new HashMap<String, Integer>();

    private Integer idPrograma;
    private Integer idPeriodo;
    private Integer idGrupo;

    @EJB
    private ProgramaAcademicoFacadeLocal programaAcademicoFacade;
    @EJB
    private PeriodoFacadeLocal periodoFacade;
    @EJB
    private GrupoFacadeLocal grupoFacade;

    /**
     * Creates a new instance of ProfListaGruposMB
     */
    public ProfListaGruposMB() {
    }

    /**
     * Metodo que inicializa el backing bean
     */
    @PostConstruct
    public void init() {
        listarProgramasAcademicos();
        listarPeriodos();
        listarGrupos();
    }

    private void listarProgramasAcademicos() {

        listaProgramasAcademicos = programaAcademicoFacade.findAll();
        if (listNombreProg == null) {
            listNombreProg = new HashMap<>();
        }

        for (ProgramaAcademico progAcadTemp : listaProgramasAcademicos) {

            listNombreProg.put(progAcadTemp.getProgNombre(), progAcadTemp.getProgId());
            System.out.println(progAcadTemp.getProgNombre());
        }

    }

    private void listarPeriodos() {

        listaPeriodos = periodoFacade.findAll();
        if (listNombrePeriodo == null) {
            listNombrePeriodo = new HashMap<>();
        }

        for (Periodo periodoTemp : listaPeriodos) {

            listNombrePeriodo.put(periodoTemp.getPeriNombre(), periodoTemp.getPeriId());
            System.out.println(periodoTemp.getPeriNombre());
        }

    }

    private void listarGrupos() {

        listaGrupos = grupoFacade.findAll();
        if (listNombreGrupo == null) {
            listNombreGrupo = new HashMap<>();
        }

        for (Grupo grupoTemp : listaGrupos) {

            listNombreGrupo.put(grupoTemp.getGrupNombre(), grupoTemp.getGrupId());
            System.out.println(grupoTemp.getGrupNombre());
        }

    }

    public void obtenerResultados() {

        listaFiltrada = grupoFacade.filtrarBusqueda(idPrograma, idPeriodo, idGrupo);
        
          for (Grupo grupoTemp : listaFiltrada) {

            
            System.out.println(grupoTemp.getProgId().getProgNombre());
        }

    }

    public Map<String, Integer> getListNombreProg() {
        return listNombreProg;
    }

    public void setListNombreProg(Map<String, Integer> listNombreProg) {
        this.listNombreProg = listNombreProg;
    }

    public Integer getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Integer idPrograma) {
        this.idPrograma = idPrograma;
    }

    public Map<String, Integer> getListNombrePeriodo() {
        return listNombrePeriodo;
    }

    public void setListNombrePeriodo(Map<String, Integer> listNombrePeriodo) {
        this.listNombrePeriodo = listNombrePeriodo;
    }

    public Map<String, Integer> getListNombreGrupo() {
        return listNombreGrupo;
    }

    public void setListNombreGrupo(Map<String, Integer> listNombreGrupo) {
        this.listNombreGrupo = listNombreGrupo;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public List<Grupo> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<Grupo> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }


}
