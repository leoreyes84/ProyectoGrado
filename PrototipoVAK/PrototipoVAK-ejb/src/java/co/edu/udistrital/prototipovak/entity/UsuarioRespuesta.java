/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Leonardo
 */
@Entity
@Table(name = "usuario_respuesta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioRespuesta.findAll", query = "SELECT u FROM UsuarioRespuesta u"),
    @NamedQuery(name = "UsuarioRespuesta.findByUsrId", query = "SELECT u FROM UsuarioRespuesta u WHERE u.usuarioRespuestaPK.usrId = :usrId"),
    @NamedQuery(name = "UsuarioRespuesta.findByRtaId", query = "SELECT u FROM UsuarioRespuesta u WHERE u.usuarioRespuestaPK.rtaId = :rtaId"),
    @NamedQuery(name = "UsuarioRespuesta.findByPeriId", query = "SELECT u FROM UsuarioRespuesta u WHERE u.usuarioRespuestaPK.periId = :periId")})
public class UsuarioRespuesta implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsuarioRespuestaPK usuarioRespuestaPK;
    @JoinColumn(name = "peri_id", referencedColumnName = "peri_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Periodo periodo;
    @JoinColumn(name = "rta_id", referencedColumnName = "rta_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Respuesta respuesta;
    @JoinColumn(name = "usr_id", referencedColumnName = "usr_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario usuario;

    public UsuarioRespuesta() {
    }

    public UsuarioRespuesta(UsuarioRespuestaPK usuarioRespuestaPK) {
        this.usuarioRespuestaPK = usuarioRespuestaPK;
    }

    public UsuarioRespuesta(int usrId, int rtaId, int periId) {
        this.usuarioRespuestaPK = new UsuarioRespuestaPK(usrId, rtaId, periId);
    }

    public UsuarioRespuestaPK getUsuarioRespuestaPK() {
        return usuarioRespuestaPK;
    }

    public void setUsuarioRespuestaPK(UsuarioRespuestaPK usuarioRespuestaPK) {
        this.usuarioRespuestaPK = usuarioRespuestaPK;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Respuesta getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Respuesta respuesta) {
        this.respuesta = respuesta;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioRespuestaPK != null ? usuarioRespuestaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioRespuesta)) {
            return false;
        }
        UsuarioRespuesta other = (UsuarioRespuesta) object;
        if ((this.usuarioRespuestaPK == null && other.usuarioRespuestaPK != null) || (this.usuarioRespuestaPK != null && !this.usuarioRespuestaPK.equals(other.usuarioRespuestaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.udistrital.prototipovak.entity.UsuarioRespuesta[ usuarioRespuestaPK=" + usuarioRespuestaPK + " ]";
    }
    
}
