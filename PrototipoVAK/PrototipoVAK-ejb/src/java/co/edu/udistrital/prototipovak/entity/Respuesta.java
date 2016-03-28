/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lreyes
 */
@Entity
@Table(name = "respuesta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Respuesta.findAll", query = "SELECT r FROM Respuesta r"),
    @NamedQuery(name = "Respuesta.findByRtaId", query = "SELECT r FROM Respuesta r WHERE r.rtaId = :rtaId"),
    @NamedQuery(name = "Respuesta.findByRtaRespuesta", query = "SELECT r FROM Respuesta r WHERE r.rtaRespuesta = :rtaRespuesta"),
    @NamedQuery(name = "Respuesta.findByRtaTipoRespuesta", query = "SELECT r FROM Respuesta r WHERE r.rtaTipoRespuesta = :rtaTipoRespuesta")})
public class Respuesta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rta_id")
    private Integer rtaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "rta_respuesta")
    private String rtaRespuesta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rta_tipo_respuesta")
    private Character rtaTipoRespuesta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "respuesta")
    private List<UsuarioRespuesta> usuarioRespuestaList;
    @JoinColumn(name = "preg_id", referencedColumnName = "preg_id")
    @ManyToOne(optional = false)
    private Pregunta pregId;

    public Respuesta() {
    }

    public Respuesta(Integer rtaId) {
        this.rtaId = rtaId;
    }

    public Respuesta(Integer rtaId, String rtaRespuesta, Character rtaTipoRespuesta) {
        this.rtaId = rtaId;
        this.rtaRespuesta = rtaRespuesta;
        this.rtaTipoRespuesta = rtaTipoRespuesta;
    }

    public Integer getRtaId() {
        return rtaId;
    }

    public void setRtaId(Integer rtaId) {
        this.rtaId = rtaId;
    }

    public String getRtaRespuesta() {
        return rtaRespuesta;
    }

    public void setRtaRespuesta(String rtaRespuesta) {
        this.rtaRespuesta = rtaRespuesta;
    }

    public Character getRtaTipoRespuesta() {
        return rtaTipoRespuesta;
    }

    public void setRtaTipoRespuesta(Character rtaTipoRespuesta) {
        this.rtaTipoRespuesta = rtaTipoRespuesta;
    }

    @XmlTransient
    public List<UsuarioRespuesta> getUsuarioRespuestaList() {
        return usuarioRespuestaList;
    }

    public void setUsuarioRespuestaList(List<UsuarioRespuesta> usuarioRespuestaList) {
        this.usuarioRespuestaList = usuarioRespuestaList;
    }

    public Pregunta getPregId() {
        return pregId;
    }

    public void setPregId(Pregunta pregId) {
        this.pregId = pregId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rtaId != null ? rtaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Respuesta)) {
            return false;
        }
        Respuesta other = (Respuesta) object;
        if ((this.rtaId == null && other.rtaId != null) || (this.rtaId != null && !this.rtaId.equals(other.rtaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.udistrital.prototipovak.entity.Respuesta[ rtaId=" + rtaId + " ]";
    }
    
}
