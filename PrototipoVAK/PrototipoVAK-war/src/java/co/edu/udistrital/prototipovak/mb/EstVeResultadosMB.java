/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.mb;

import co.edu.udistrital.prototipovak.entity.SugerenciaResultado;
import co.edu.udistrital.prototipovak.entity.UsuarioRespuesta;
import co.edu.udistrital.prototipovak.session.SugerenciaResultadoFacadeLocal;
import co.edu.udistrital.prototipovak.session.UsuarioRespuestaFacadeLocal;
import co.edu.udistrital.prototipovak.util.Constantes;
import java.util.ArrayList;
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
public class EstVeResultadosMB {
    // /////////////////////////////////////////////////////////////////////////
    // Logger de la clase
    // /////////////////////////////////////////////////////////////////////////
    private static Logger _logger = Logger.getLogger(EstVeResultadosMB.class);

    private List<UsuarioRespuesta> listaUsuRespuesta;
    private List<SugerenciaResultado> listaSugerenciaResultado;
    private PieChartModel resultadosTest;

    @EJB
    private UsuarioRespuestaFacadeLocal usuarioRespuestaFacade;
    @EJB
    private SugerenciaResultadoFacadeLocal suegerenciaResultadoFacade;

    /**
     * Creates a new instance of EstVeResultadosMB
     */
    public EstVeResultadosMB() {
    }

    /**
     * Metodo que inicializa el backing bean
     */
    @PostConstruct
    public void init() {
        listarRespuestasUsu();
    }

    private void listarRespuestasUsu() {
        resultadosTest = new PieChartModel();
        Integer idUsuario = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("idUsuario");
        int contVisual = 0;
        int contAuditivo = 0;
        int contKines = 0;
        try {
            listaUsuRespuesta = usuarioRespuestaFacade.obtenerRespuestaUsuario(idUsuario);
        } catch (Exception ex) {
            _logger.error("Error listarRespuestasUsu: " + ex.getMessage());;
        }

        for (UsuarioRespuesta usuarioRespuestaTemp : listaUsuRespuesta) {
            System.out.println(usuarioRespuestaTemp.getRespuesta().getRtaRespuesta());
            if (usuarioRespuestaTemp.getRespuesta().getRtaTipoRespuesta().equals(Constantes.COD_TIPO_APRENDIZAJE_VISUAL)) {
                resultadosTest.set(Constantes.TIPO_APRENDIZAJE_VISUAL, contVisual++);
            } else if (usuarioRespuestaTemp.getRespuesta().getRtaTipoRespuesta().equals(Constantes.COD_TIPO_APRENDIZAJE_AUDITIVO)) {
                resultadosTest.set(Constantes.TIPO_APRENDIZAJE_AUDITIVO, contAuditivo++);
            } else if (usuarioRespuestaTemp.getRespuesta().getRtaTipoRespuesta().equals(Constantes.COD_TIPO_APRENDIZAJE_KINESTESICO)) {
                resultadosTest.set(Constantes.TIPO_APRENDIZAJE_KINESTESICO, contKines++);
            }
        }
        System.out.println("visual: " + contVisual + " auditivo: " + contAuditivo + " kinestesico: " + contKines);
        //contVisual = 7;
        //contAuditivo = 7;
        //contKines = 2;
        resultadosTest.setTitle(Constantes.TITULO_RESULTADO_TEST);
        resultadosTest.setLegendPosition(Constantes.ORIENTACION_W_RESULTADOS_TEST);
        resultadosTest.setShowDataLabels(true);
        resultadosTest.setShadow(true);
        resultadosTest.setSeriesColors(Constantes.COLORES_RESULTADOS_TEST);

        listaSugerenciaResultado = suegerenciaResultadoFacade.findAll();
        List<SugerenciaResultado> listaSugerenciaTemp = new ArrayList<SugerenciaResultado>();

        for (SugerenciaResultado sugerenciaResultadoTemp : listaSugerenciaResultado) {

            if ((contVisual > contAuditivo) && (contVisual > contKines)) {

                if (!sugerenciaResultadoTemp.getSugTipoVak().toString().equals(Constantes.COD_TIPO_APRENDIZAJE_VISUAL.toString())) {
                    listaSugerenciaTemp.add(sugerenciaResultadoTemp);
                }

            }
            if ((contAuditivo > contVisual) && (contAuditivo > contKines)) {
                if (!sugerenciaResultadoTemp.getSugTipoVak().toString().equals(Constantes.COD_TIPO_APRENDIZAJE_AUDITIVO.toString())) {
                    listaSugerenciaTemp.add(sugerenciaResultadoTemp);
                }

            }
            if ((contKines > contAuditivo) && (contKines > contVisual)) {
                if (!sugerenciaResultadoTemp.getSugTipoVak().toString().equals(Constantes.COD_TIPO_APRENDIZAJE_KINESTESICO.toString())) {
                    listaSugerenciaTemp.add(sugerenciaResultadoTemp);
                }

            }
            if ((contVisual == contAuditivo) && (contVisual == contKines) && (contAuditivo == contKines)) {
                break;
            }
            if ((contVisual == contAuditivo)) {
                if (sugerenciaResultadoTemp.getSugTipoVak().toString().equals(Constantes.COD_TIPO_APRENDIZAJE_KINESTESICO.toString())) {
                    listaSugerenciaTemp.add(sugerenciaResultadoTemp);
                    break;
                }

            }
            if ((contVisual == contKines)) {
                if (sugerenciaResultadoTemp.getSugTipoVak().toString().equals(Constantes.COD_TIPO_APRENDIZAJE_AUDITIVO.toString())) {
                    listaSugerenciaTemp.add(sugerenciaResultadoTemp);
                    break;
                }

            }
            if ((contAuditivo == contKines)) {
                if (sugerenciaResultadoTemp.getSugTipoVak().toString().equals(Constantes.COD_TIPO_APRENDIZAJE_VISUAL.toString())) {
                    listaSugerenciaTemp.add(sugerenciaResultadoTemp);
                    break;
                }

            }

        }

        listaSugerenciaResultado.removeAll(listaSugerenciaTemp);

    }

    public PieChartModel getResultadosTest() {
        return resultadosTest;
    }

    public void setResultadosTest(PieChartModel resultadosTest) {
        this.resultadosTest = resultadosTest;
    }

    public List<SugerenciaResultado> getListaSugerenciaResultado() {
        return listaSugerenciaResultado;
    }

    public void setListaSugerenciaResultado(List<SugerenciaResultado> listaSugerenciaResultado) {
        this.listaSugerenciaResultado = listaSugerenciaResultado;
    }

}
