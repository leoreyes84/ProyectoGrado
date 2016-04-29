/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.mb;

import co.edu.udistrital.prototipovak.entity.Grupo;
import co.edu.udistrital.prototipovak.entity.Usuario;
import co.edu.udistrital.prototipovak.entity.UsuarioRespuesta;
import co.edu.udistrital.prototipovak.session.GrupoFacadeLocal;
import co.edu.udistrital.prototipovak.util.Constantes;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.jboss.logging.Logger;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@RequestScoped
public class ProfVeResultadosMB {

    // /////////////////////////////////////////////////////////////////////////
    // Logger de la clase
    // /////////////////////////////////////////////////////////////////////////
    private static Logger _logger = Logger.getLogger(ProfVeResultadosMB.class);

    // /////////////////////////////////////////////////////////////////////////
    // EJB de la clase
    // /////////////////////////////////////////////////////////////////////////
    @EJB
    private GrupoFacadeLocal grupoFacade;

    // /////////////////////////////////////////////////////////////////////////
    // Atributos de la clase
    // /////////////////////////////////////////////////////////////////////////
    private Integer idGrupo;
    private PieChartModel resultadoNumRespuestas;
    private PieChartModel resultadoEstudiantes;
    private Grupo grupo;

    @PostConstruct
    public void init() {
        //Obtiene de sesion el grupo para obtener resultados
        idGrupo = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idGrupo");
        this.cargarResulados();
    }

    /**
     * Obtiene los resultados del grupo
     */
    private void cargarResulados() {
        //Consultar los estudiantes del grupo
        try {
            //TODO: se puede mejorar la consulta, trayendo unicamente los usuarios con rol estudiante
            grupo = grupoFacade.findGrupoByID(idGrupo);
            //Obtiene los usuarios (Estudiantes) del grupo
            List<Usuario> lstUsuario = grupo != null ? grupo.getUsuarioList() : null;
            if (lstUsuario != null && lstUsuario.size() > 0) {
                //Instanciar charts
                resultadoNumRespuestas = new PieChartModel();
                resultadoEstudiantes = new PieChartModel();
                //Contador respuestas
                int contRespuestaVisual = 0;
                int contRespuestaAuditivo = 0;
                int contRespuestaKines = 0;
                //Contador estudiantes
                int contEstudianteVisual = 0;
                int contEstudianteAuditivo = 0;
                int contEstudianteKines = 0;
                for (Usuario estudiante : lstUsuario) {
                    //Obtiene las respuestas del estudiante
                    List<UsuarioRespuesta> lstRespuestas = estudiante.getUsuarioRespuestaList();
                    if (lstRespuestas != null && lstRespuestas.size() > 0) {
                        for (UsuarioRespuesta respuesta : lstRespuestas) {
                            //Numero de respuestas por tipo V o A o K
                            if (respuesta.getRespuesta().getRtaTipoRespuesta().equals(Constantes.COD_TIPO_APRENDIZAJE_VISUAL)) {
                                resultadoNumRespuestas.set(Constantes.TIPO_APRENDIZAJE_VISUAL, contRespuestaVisual++);
                            } else if (respuesta.getRespuesta().getRtaTipoRespuesta().equals(Constantes.COD_TIPO_APRENDIZAJE_AUDITIVO)) {
                                resultadoNumRespuestas.set(Constantes.TIPO_APRENDIZAJE_AUDITIVO, contRespuestaAuditivo++);
                            } else if (respuesta.getRespuesta().getRtaTipoRespuesta().equals(Constantes.COD_TIPO_APRENDIZAJE_KINESTESICO)) {
                                resultadoNumRespuestas.set(Constantes.TIPO_APRENDIZAJE_KINESTESICO, contRespuestaKines++);
                            }
                        }
                        //Numero de estudiantes por tipo V o A o K
                        if (contRespuestaVisual > contRespuestaAuditivo && contRespuestaVisual > contRespuestaKines) {
                            contEstudianteVisual++;
                        } else if (contRespuestaAuditivo > contRespuestaVisual && contRespuestaAuditivo > contRespuestaKines) {
                            contEstudianteAuditivo++;
                        } else if (contRespuestaKines > contRespuestaVisual && contRespuestaKines > contRespuestaAuditivo) {
                            contEstudianteKines++;
                        }
                    }
                }
                resultadoEstudiantes.set(Constantes.TIPO_APRENDIZAJE_VISUAL, contEstudianteVisual);
                resultadoEstudiantes.set(Constantes.TIPO_APRENDIZAJE_AUDITIVO, contEstudianteAuditivo);
                resultadoEstudiantes.set(Constantes.TIPO_APRENDIZAJE_KINESTESICO, contEstudianteKines);
                _logger.info("visual " + contRespuestaVisual);
                _logger.info("auditivo " + contRespuestaAuditivo);
                _logger.info("kinestesico " + contRespuestaKines);

                _logger.info("--------------");

                _logger.info("visual " + contEstudianteVisual);
                _logger.info("auditivo " + contEstudianteAuditivo);
                _logger.info("kinestesico " + contEstudianteKines);
                //Caracteristicas chart respuestas
                resultadoNumRespuestas.setTitle(Constantes.TITULO_RESULTADO_PREGUNTAS_GRUPO);
                resultadoNumRespuestas.setLegendPosition(Constantes.ORIENTACION_W_RESULTADOS_TEST);
                resultadoNumRespuestas.setShowDataLabels(true);
                resultadoNumRespuestas.setShadow(true);
                resultadoNumRespuestas.setSeriesColors(Constantes.COLORES_RESULTADOS_TEST);
                //Caracteristicas chart estudiantes
                resultadoEstudiantes.setTitle(Constantes.TITULO_RESULTADO_ESTUDIANTES_GRUPO);
                resultadoEstudiantes.setLegendPosition(Constantes.ORIENTACION_W_RESULTADOS_TEST);
                resultadoEstudiantes.setShowDataLabels(true);
                resultadoEstudiantes.setShadow(true);
                resultadoEstudiantes.setSeriesColors(Constantes.COLORES_RESULTADOS_TEST);
            }
        } catch (Exception ex) {
            _logger.error("Error cargarResulados " + ex.getMessage(), ex);
        }
    }

    public PieChartModel getResultadosNumRespuestasGrupo() {
        return resultadoNumRespuestas;
    }

    public void setResultadosNumRespuestasGrupo(PieChartModel resultadosNumRespuestasGrupo) {
        this.resultadoNumRespuestas = resultadosNumRespuestasGrupo;
    }

    public PieChartModel getResultadosPorcentajes() {
        return resultadoEstudiantes;
    }

    public void setResultadosPorcentajes(PieChartModel resultadosPorcentajes) {
        this.resultadoEstudiantes = resultadosPorcentajes;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

}
