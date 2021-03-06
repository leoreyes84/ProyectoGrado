/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.mb;

import co.edu.udistrital.prototipovak.entity.Respuesta;
import co.edu.udistrital.prototipovak.entity.Usuario;
import co.edu.udistrital.prototipovak.entity.UsuarioRespuesta;
import co.edu.udistrital.prototipovak.session.RespuestaFacadeLocal;
import co.edu.udistrital.prototipovak.session.UsuarioFacadeLocal;
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
public class ProfVeDetalleResultadosMB {
    
    // /////////////////////////////////////////////////////////////////////////
    // EJB de la clase
    // /////////////////////////////////////////////////////////////////////////
    @EJB
    private UsuarioRespuestaFacadeLocal usuarioRespuestaFacade;
    @EJB
    private UsuarioFacadeLocal usuarioFacade;
    @EJB
    private RespuestaFacadeLocal respuestaFacade;
    
    // /////////////////////////////////////////////////////////////////////////
    // Logger de la clase
    // /////////////////////////////////////////////////////////////////////////
    private static Logger _logger = Logger.getLogger(ProfVeDetalleResultadosMB.class);
    
    // /////////////////////////////////////////////////////////////////////////
    // Atributos de la clase
    // /////////////////////////////////////////////////////////////////////////
    private List<UsuarioRespuesta> listaUsuRespuesta;
    private PieChartModel resultadosTest;

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

    }

    public PieChartModel getResultadosTest() {
        return resultadosTest;
    }

    public void setResultadosTest(PieChartModel resultadosTest) {
        this.resultadosTest = resultadosTest;
    }
    
}
