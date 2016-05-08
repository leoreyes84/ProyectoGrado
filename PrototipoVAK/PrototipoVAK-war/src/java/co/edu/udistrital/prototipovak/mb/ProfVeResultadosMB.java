/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.mb;

import co.edu.udistrital.prototipovak.entity.Grupo;
import co.edu.udistrital.prototipovak.entity.Respuesta;
import co.edu.udistrital.prototipovak.entity.SugerenciaResultado;
import co.edu.udistrital.prototipovak.entity.Usuario;
import co.edu.udistrital.prototipovak.entity.UsuarioRespuesta;
import co.edu.udistrital.prototipovak.session.GrupoFacadeLocal;
import co.edu.udistrital.prototipovak.session.RespuestaFacadeLocal;
import co.edu.udistrital.prototipovak.session.SugerenciaResultadoFacadeLocal;
import co.edu.udistrital.prototipovak.session.UsuarioRespuestaFacadeLocal;
import co.edu.udistrital.prototipovak.util.Constantes;
import java.util.List;
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
    // EJB de la clase
    // /////////////////////////////////////////////////////////////////////////
    @EJB
    private GrupoFacadeLocal grupoFacade;
    @EJB
    private RespuestaFacadeLocal respuestaFacade;
    @EJB
    private SugerenciaResultadoFacadeLocal sugerenciaResultadoFacade;
    @EJB
    private UsuarioRespuestaFacadeLocal usuarioRespuestaFacade;

    // /////////////////////////////////////////////////////////////////////////
    // Logger de la clase
    // /////////////////////////////////////////////////////////////////////////
    private static Logger _logger = Logger.getLogger(ProfVeResultadosMB.class);

    // /////////////////////////////////////////////////////////////////////////
    // Atributos de la clase
    // /////////////////////////////////////////////////////////////////////////
    private Integer idGrupo;
    private PieChartModel resultadoNumRespuestas;
    private PieChartModel resultadoEstudiantes;
    private Grupo grupo;
    private int numeroEstudiatesGrupo;
    private int numeroEstudiantesConRespuesta;
    private List<SugerenciaResultado> listaSugerenciaVisual;
    private List<SugerenciaResultado> listaSugerenciaAuditivo;
    private List<SugerenciaResultado> listaSugerenciaKinestesico;

    // /////////////////////////////////////////////////////////////////////////
    // Metodos de la clase
    // /////////////////////////////////////////////////////////////////////////
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
            grupo = grupoFacade.findGrupoByID(idGrupo);
            //Obtiene los usuarios (Estudiantes) del grupo
            List<Usuario> lstUsuario = grupo != null ? grupo.getUsuarioList() : null;
            if (lstUsuario != null && lstUsuario.size() > 0) {
                numeroEstudiatesGrupo = lstUsuario.size();
                //Instanciar charts
                resultadoNumRespuestas = new PieChartModel();
                resultadoEstudiantes = new PieChartModel();
                //Contador estudiantes
                int contEstudianteVisual = 0;
                int contEstudianteAuditivo = 0;
                int contEstudianteKines = 0;
                for (Usuario estudiante : lstUsuario) {
                    //Contador respuestas
                    int contRespuestaVisual = 0;
                    int contRespuestaAuditivo = 0;
                    int contRespuestaKines = 0;
                    //Obtiene las respuestas del estudiante
                    List<UsuarioRespuesta> lstRespuestas = usuarioRespuestaFacade.obtenerRespuestaUsuario(estudiante.getUsrId());
                    if (lstRespuestas != null && lstRespuestas.size() > 0) {
                        ++numeroEstudiantesConRespuesta;
                        for (UsuarioRespuesta usuarioRespuestaTemp : lstRespuestas) {
                            Respuesta respuesta = respuestaFacade.findRespuestaByID(usuarioRespuestaTemp.getUsuarioRespuestaPK().getRtaId());
                            //Numero de respuestas por tipo V o A o K
                            if (respuesta.getRtaTipoRespuesta().equals(Constantes.COD_TIPO_APRENDIZAJE_VISUAL)) {
                                resultadoNumRespuestas.set(Constantes.TIPO_APRENDIZAJE_VISUAL, ++contRespuestaVisual);
                            } else if (respuesta.getRtaTipoRespuesta().equals(Constantes.COD_TIPO_APRENDIZAJE_AUDITIVO)) {
                                resultadoNumRespuestas.set(Constantes.TIPO_APRENDIZAJE_AUDITIVO, ++contRespuestaAuditivo);
                            } else if (respuesta.getRtaTipoRespuesta().equals(Constantes.COD_TIPO_APRENDIZAJE_KINESTESICO)) {
                                resultadoNumRespuestas.set(Constantes.TIPO_APRENDIZAJE_KINESTESICO, ++contRespuestaKines);
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
                //PieChart de numero de estudiantes
                resultadoEstudiantes.set(Constantes.TIPO_APRENDIZAJE_VISUAL, contEstudianteVisual);
                resultadoEstudiantes.set(Constantes.TIPO_APRENDIZAJE_AUDITIVO, contEstudianteAuditivo);
                resultadoEstudiantes.set(Constantes.TIPO_APRENDIZAJE_KINESTESICO, contEstudianteKines);

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
            try {
                //Consulta de sugerencias/caracteristicas de las respuestas
                listaSugerenciaVisual = sugerenciaResultadoFacade.findSugerenciasProfesorVisual();
                listaSugerenciaAuditivo = sugerenciaResultadoFacade.findSugerenciasProfesorAuditivo();
                listaSugerenciaKinestesico = sugerenciaResultadoFacade.findSugerenciasProfesorKinestesico();
            } catch (Exception ex) {
                _logger.error("Error consultando sugerencias " + ex.getMessage());
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

    public int getNumeroEstudiatesGrupo() {
        return numeroEstudiatesGrupo;
    }

    public void setNumeroEstudiatesGrupo(int numeroEstudiatesGrupo) {
        this.numeroEstudiatesGrupo = numeroEstudiatesGrupo;
    }

    public int getNumeroEstudiantesConRespuesta() {
        return numeroEstudiantesConRespuesta;
    }

    public void setNumeroEstudiantesConRespuesta(int numeroEstudiantesConRespuesta) {
        this.numeroEstudiantesConRespuesta = numeroEstudiantesConRespuesta;
    }

    public List<SugerenciaResultado> getListaSugerenciaVisual() {
        return listaSugerenciaVisual;
    }

    public void setListaSugerenciaVisual(List<SugerenciaResultado> listaSugerenciaVisual) {
        this.listaSugerenciaVisual = listaSugerenciaVisual;
    }

    public List<SugerenciaResultado> getListaSugerenciaAuditivo() {
        return listaSugerenciaAuditivo;
    }

    public void setListaSugerenciaAuditivo(List<SugerenciaResultado> listaSugerenciaAuditivo) {
        this.listaSugerenciaAuditivo = listaSugerenciaAuditivo;
    }

    public List<SugerenciaResultado> getListaSugerenciaKinestesico() {
        return listaSugerenciaKinestesico;
    }

    public void setListaSugerenciaKinestesico(List<SugerenciaResultado> listaSugerenciaKinestesico) {
        this.listaSugerenciaKinestesico = listaSugerenciaKinestesico;
    }

}
