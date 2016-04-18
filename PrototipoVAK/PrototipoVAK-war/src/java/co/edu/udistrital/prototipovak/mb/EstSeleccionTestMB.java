/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.mb;

import co.edu.udistrital.prototipovak.entity.ProgramaAcademico;
import co.edu.udistrital.prototipovak.entity.UsuarioRespuesta;
import co.edu.udistrital.prototipovak.session.ProgramaAcademicoFacadeLocal;
import co.edu.udistrital.prototipovak.session.UsuarioRespuestaFacadeLocal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Gerson Cespedes
 */
@ManagedBean
@RequestScoped
public class EstSeleccionTestMB {

    private List<ProgramaAcademico> listaPrograma;
    private List<UsuarioRespuesta> listaUsuarioRespuesta;

    private boolean presentarVerTestResultados;

    @EJB
    private ProgramaAcademicoFacadeLocal programaAcademicoFacade;
    @EJB
    private UsuarioRespuestaFacadeLocal usuarioRespuestaFacade;

    /**
     * Creates a new instance of EstSeleccionTestMB
     */
    public EstSeleccionTestMB() {
    }

    /**
     * Metodo que inicializa el backing bean
     */
    @PostConstruct
    public void init() {
        listarProgramaEstudiante();
    }

    private void listarProgramaEstudiante() {

        //Integer idUsuario = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
        //Cambiar el codigo por el id del estudiante, y cambiar la consulta.
        String codigoEst = "20152099036";
        presentarVerTestResultados = false;

        listaPrograma = programaAcademicoFacade.programaAcadPorEstudiante(codigoEst);
        listaUsuarioRespuesta = usuarioRespuestaFacade.obtenerRespuestaUsuario(1);
        if (listaUsuarioRespuesta != null && listaUsuarioRespuesta.size() > 0) {
            presentarVerTestResultados = true;
        }

        if (listaPrograma != null && listaPrograma.size() > 0) {

            for (ProgramaAcademico listaPrograma1 : listaPrograma) {

                System.out.println("**********Nombre del programa academico: " + listaPrograma1.getProgNombre());

            }

        } else {

            System.out.println("**********La lista está vacía************");
        }

    }

    public List<ProgramaAcademico> getListaPrograma() {
        return listaPrograma;
    }

    public void setListaPrograma(List<ProgramaAcademico> listaPrograma) {
        this.listaPrograma = listaPrograma;
    }

    public boolean isPresentarVerTestResultados() {
        return presentarVerTestResultados;
    }

    public void setPresentarVerTestResultados(boolean presentarVerTestResultados) {
        this.presentarVerTestResultados = presentarVerTestResultados;
    }

}
