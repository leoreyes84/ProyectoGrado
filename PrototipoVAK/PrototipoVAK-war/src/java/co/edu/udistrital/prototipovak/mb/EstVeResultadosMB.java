/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.mb;

import co.edu.udistrital.prototipovak.entity.Respuesta;
import co.edu.udistrital.prototipovak.entity.SugerenciaResultado;
import co.edu.udistrital.prototipovak.entity.Usuario;
import co.edu.udistrital.prototipovak.entity.UsuarioRespuesta;
import co.edu.udistrital.prototipovak.session.RespuestaFacadeLocal;
import co.edu.udistrital.prototipovak.session.SugerenciaResultadoFacadeLocal;
import co.edu.udistrital.prototipovak.session.UsuarioFacadeLocal;
import co.edu.udistrital.prototipovak.session.UsuarioRespuestaFacadeLocal;
import co.edu.udistrital.prototipovak.util.Constantes;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.jboss.logging.Logger;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Leonardo
 */
@ManagedBean
@ViewScoped
public class EstVeResultadosMB {

    // /////////////////////////////////////////////////////////////////////////
    // EJB de la clase
    // /////////////////////////////////////////////////////////////////////////
    @EJB
    private UsuarioFacadeLocal usuarioFacade;
    @EJB
    private UsuarioRespuestaFacadeLocal usuarioRespuestaFacade;
    @EJB
    private SugerenciaResultadoFacadeLocal sugerenciaResultadoFacade;
    @EJB
    private RespuestaFacadeLocal respuestaFacade;

    // /////////////////////////////////////////////////////////////////////////
    // Logger de la clase
    // /////////////////////////////////////////////////////////////////////////
    private static Logger _logger = Logger.getLogger(EstVeResultadosMB.class);

    // /////////////////////////////////////////////////////////////////////////
    // Atributos de la clase
    // /////////////////////////////////////////////////////////////////////////
    private List<UsuarioRespuesta> listaUsuRespuesta;
    private List<SugerenciaResultado> listaSugerenciaVisual;
    private List<SugerenciaResultado> listaSugerenciaAuditivo;
    private List<SugerenciaResultado> listaSugerenciaKinestesico;
    private PieChartModel resultadosTest;
    private boolean visual;
    private boolean auditivo;
    private boolean kinestesico;

    // /////////////////////////////////////////////////////////////////////////
    // Metodos de la clase
    // /////////////////////////////////////////////////////////////////////////
    /**
     * Metodo que inicializa el backing bean
     */
    @PostConstruct
    public void init() {
        listarRespuestasUsu();
    }

    /**
     * Consulta las respuestas dadas por el estudiante
     */
    private void listarRespuestasUsu() {
        String nombreEstudiante = " ";
        resultadosTest = new PieChartModel();
        Integer idUsuario = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
        int contVisual = 0;
        int contAuditivo = 0;
        int contKines = 0;
        try {
            listaUsuRespuesta = usuarioRespuestaFacade.obtenerRespuestaUsuario(idUsuario);
            Usuario estudiante = usuarioFacade.findUsuarioById(idUsuario);
            nombreEstudiante += estudiante.getUsrNombres().concat(" ").concat(estudiante.getUsrApellidos());
        } catch (Exception ex) {
            _logger.error("Error listarRespuestasUsu: " + ex.getMessage());
        }

        //Recorrer respuestas y asociar al graficador (PieChart)
        for (UsuarioRespuesta usuarioRespuestaTemp : listaUsuRespuesta) {
            try {
                Respuesta respuesta = respuestaFacade.findRespuestaByID(usuarioRespuestaTemp.getUsuarioRespuestaPK().getRtaId());
                if (respuesta.getRtaTipoRespuesta().equals(Constantes.COD_TIPO_APRENDIZAJE_VISUAL)) {
                    resultadosTest.set(Constantes.TIPO_APRENDIZAJE_VISUAL, ++contVisual);
                } else if (respuesta.getRtaTipoRespuesta().equals(Constantes.COD_TIPO_APRENDIZAJE_AUDITIVO)) {
                    resultadosTest.set(Constantes.TIPO_APRENDIZAJE_AUDITIVO, ++contAuditivo);
                } else if (respuesta.getRtaTipoRespuesta().equals(Constantes.COD_TIPO_APRENDIZAJE_KINESTESICO)) {
                    resultadosTest.set(Constantes.TIPO_APRENDIZAJE_KINESTESICO, ++contKines);
                }
            } catch (Exception ex) {
                _logger.error("Error consultando respuesta " + ex.getMessage());
            }
        }

        //Caracteristicas del PieChart
        resultadosTest.setTitle(Constantes.TITULO_RESULTADO_TEST.concat(nombreEstudiante));
        resultadosTest.setLegendPosition(Constantes.ORIENTACION_W_RESULTADOS_TEST);
        resultadosTest.setShowDataLabels(true);
        resultadosTest.setShadow(true);
        resultadosTest.setSeriesColors(Constantes.COLORES_RESULTADOS_TEST);

        try {
            //Consulta de sugerencias/caracteristicas de las respuestas
            listaSugerenciaVisual = sugerenciaResultadoFacade.findSugerenciasEstutianteVisual();
            listaSugerenciaAuditivo = sugerenciaResultadoFacade.findSugerenciasEstutianteAuditivo();
            listaSugerenciaKinestesico = sugerenciaResultadoFacade.findSugerenciasEstutianteKinestesico();
        } catch (Exception ex) {
            _logger.error("Error consultando sugerencias " + ex.getMessage());
        }

        if ((contVisual > contAuditivo) && (contVisual > contKines)) {
            visual = true;
            auditivo = false;
            kinestesico = false;

        } else if ((contAuditivo > contVisual) && (contAuditivo > contKines)) {
            visual = false;
            auditivo = true;
            kinestesico = false;

        } else if ((contKines > contAuditivo) && (contKines > contVisual)) {
            visual = false;
            auditivo = false;
            kinestesico = true;

        } else if ((contVisual == contAuditivo) && (contVisual == contKines) && (contAuditivo == contKines)) {
            visual = true;
            auditivo = true;
            kinestesico = true;

        } else if ((contVisual == contAuditivo)) {
            visual = true;
            auditivo = true;
            kinestesico = false;

        } else if ((contVisual == contKines)) {
            visual = true;
            auditivo = false;
            kinestesico = true;

        } else if ((contAuditivo == contKines)) {
            visual = false;
            auditivo = true;
            kinestesico = true;

        }

//        List<SugerenciaResultado> listaSugerenciaTemp = new ArrayList<>();
//        for (SugerenciaResultado sugerenciaResultadoTemp : listaSugerenciaResultado) {
//
//            if ((contVisual > contAuditivo) && (contVisual > contKines)) {
//
//                if (!sugerenciaResultadoTemp.getSugTipoVak().equals(Constantes.COD_TIPO_APRENDIZAJE_VISUAL.toString())) {
//                    listaSugerenciaTemp.add(sugerenciaResultadoTemp);
//                }
//            }
//            if ((contAuditivo > contVisual) && (contAuditivo > contKines)) {
//                if (!sugerenciaResultadoTemp.getSugTipoVak().equals(Constantes.COD_TIPO_APRENDIZAJE_AUDITIVO.toString())) {
//                    listaSugerenciaTemp.add(sugerenciaResultadoTemp);
//                }
//            }
//            if ((contKines > contAuditivo) && (contKines > contVisual)) {
//                if (!sugerenciaResultadoTemp.getSugTipoVak().equals(Constantes.COD_TIPO_APRENDIZAJE_KINESTESICO.toString())) {
//                    listaSugerenciaTemp.add(sugerenciaResultadoTemp);
//                }
//            }
//            if ((contVisual == contAuditivo) && (contVisual == contKines) && (contAuditivo == contKines)) {
//                break;
//            }
//            if ((contVisual == contAuditivo)) {
//                if (sugerenciaResultadoTemp.getSugTipoVak().equals(Constantes.COD_TIPO_APRENDIZAJE_KINESTESICO.toString())) {
//                    listaSugerenciaTemp.add(sugerenciaResultadoTemp);
//                    break;
//                }
//            }
//            if ((contVisual == contKines)) {
//                if (sugerenciaResultadoTemp.getSugTipoVak().equals(Constantes.COD_TIPO_APRENDIZAJE_AUDITIVO.toString())) {
//                    listaSugerenciaTemp.add(sugerenciaResultadoTemp);
//                    break;
//                }
//            }
//            if ((contAuditivo == contKines)) {
//                if (sugerenciaResultadoTemp.getSugTipoVak().equals(Constantes.COD_TIPO_APRENDIZAJE_VISUAL.toString())) {
//                    listaSugerenciaTemp.add(sugerenciaResultadoTemp);
//                    break;
//                }
//            }
//        }
//        listaSugerenciaResultado.removeAll(listaSugerenciaTemp);
    }

    public PieChartModel getResultadosTest() {
        return resultadosTest;
    }

    public void setResultadosTest(PieChartModel resultadosTest) {
        this.resultadosTest = resultadosTest;
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

    public boolean isVisual() {
        return visual;
    }

    public void setVisual(boolean visual) {
        this.visual = visual;
    }

    public boolean isAuditivo() {
        return auditivo;
    }

    public void setAuditivo(boolean auditivo) {
        this.auditivo = auditivo;
    }

    public boolean isKinestesico() {
        return kinestesico;
    }

    public void setKinestesico(boolean kinestesico) {
        this.kinestesico = kinestesico;
    }

}
