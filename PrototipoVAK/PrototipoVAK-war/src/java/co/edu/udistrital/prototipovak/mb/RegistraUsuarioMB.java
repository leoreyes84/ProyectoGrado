
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.mb;

import co.edu.udistrital.prototipovak.entity.Grupo;
import co.edu.udistrital.prototipovak.entity.ProgramaAcademico;
import co.edu.udistrital.prototipovak.entity.Rol;
import co.edu.udistrital.prototipovak.entity.Usuario;
import co.edu.udistrital.prototipovak.session.GrupoFacadeLocal;
import co.edu.udistrital.prototipovak.session.ProgramaAcademicoFacadeLocal;
import co.edu.udistrital.prototipovak.session.RolFacadeLocal;
import co.edu.udistrital.prototipovak.session.UsuarioFacadeLocal;
import co.edu.udistrital.prototipovak.util.Constantes;
import co.edu.udistrital.prototipovak.util.Seguridad;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.hibernate.validator.constraints.Email;
import org.jboss.logging.Logger;

/**
 *
 * @author lreyes
 */
@ManagedBean
@ViewScoped
public class RegistraUsuarioMB {
    @EJB
    private ProgramaAcademicoFacadeLocal programaAcademicoFacade;

    // /////////////////////////////////////////////////////////////////////////
    // Logger de la clase
    // /////////////////////////////////////////////////////////////////////////
    private static Logger _logger = Logger.getLogger(RegistraUsuarioMB.class);

    // /////////////////////////////////////////////////////////////////////////
    // EJB de la clase
    // /////////////////////////////////////////////////////////////////////////
    @EJB
    private GrupoFacadeLocal grupoFacade;
    @EJB
    private RolFacadeLocal rolFacade;
    @EJB
    private UsuarioFacadeLocal usuarioFacade;

    // /////////////////////////////////////////////////////////////////////////
    // Atributos de la clase
    // /////////////////////////////////////////////////////////////////////////
    private String codigo;
    private String nombres;
    private String apellidos;
    @Email(message = "No es un correo válido")
    private String email;
    private String contrasenia;
    private Integer idPrograma;
    private Integer idGrupo;
    private List<SelectItem> listGrupos;

    // /////////////////////////////////////////////////////////////////////////
    // Metodos de la clase
    // /////////////////////////////////////////////////////////////////////////
    public String guardarUsuario() {
        try {
            if (validar()) {
                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setUsrCodigo(codigo);
                nuevoUsuario.setUsrNombres(nombres);
                nuevoUsuario.setUsrApellidos(apellidos);
                nuevoUsuario.setUsrEmail(email);
                nuevoUsuario.setUsrContrasenia(Seguridad.Sha(contrasenia));
                //Asociar rol
                Rol rol = rolFacade.findRolByCodigo(Constantes.ROL_CODIGO_ESTUDIANTE);
                List<Rol> listRol = new ArrayList<>();
                listRol.add(rol);
                nuevoUsuario.setRolList(listRol);
                //Asociar grupo (programa)
                Grupo grupo = grupoFacade.findGrupoByID(idGrupo);
                List<Grupo> listGrupo = new ArrayList<>();
                listGrupo.add(grupo);
                nuevoUsuario.setGrupoList(listGrupo);
                //Crear registro
                usuarioFacade.create(nuevoUsuario);
                _logger.info("Usuario creado");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro exitoso", ""));
                return "registroExitoso";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El código ya exixte", ""));
                return null;
            }
        } catch (Exception ex) {
            _logger.error(ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERROR, no pudo ser guardado el registro", ""));
            return null;
        }
    }

    /**
     * Valida antes de guardar
     *
     * @return true si es valido guardar
     */
    private boolean validar() {
        try {
            //Validar que no exista el código
            List<Usuario> lstUsuario = usuarioFacade.findUsuarioByCodigo(codigo);
            return !(lstUsuario != null && lstUsuario.size() > 0);
        } catch (Exception ex) {
            _logger.error("Error validando "+Arrays.toString(ex.getStackTrace()));
            return false;
        }
    }
    
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
     * Consulta los grupos pertenecientes a un programa
     *
     * @return lista de
     */
    public List<SelectItem> obtenerComboGrupo() {
        try {
            listGrupos = new ArrayList<>();
            if (idPrograma != null) {
                //Consultar períodos
                List<Grupo> list = grupoFacade.filtrarBusqueda(idPrograma, null, null);
                if (list != null) {
                    for (Grupo grupo : list) {
                        listGrupos.add(new SelectItem(grupo.getGrupId(), grupo.getPeriId().getPeriNombre() + "/" + grupo.getGrupNombre()));
                    }
                }
            }
            return listGrupos;
        } catch (Exception e) {
            _logger.error("Error al botener combo: " + e.getMessage());
            return null;
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Integer getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Integer idPrograma) {
        this.idPrograma = idPrograma;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public List<SelectItem> getListGrupos() {
        return listGrupos;
    }

    public void setListGrupos(List<SelectItem> listGrupos) {
        this.listGrupos = listGrupos;
    }

}
