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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author Leonardo
 */
@Entity
@Table(name = "pregunta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pregunta.findAll", query = "SELECT p FROM Pregunta p"),
    @NamedQuery(name = "Pregunta.findByPregId", query = "SELECT p FROM Pregunta p WHERE p.pregId = :pregId"),
    @NamedQuery(name = "Pregunta.findByPregPregunta", query = "SELECT p FROM Pregunta p WHERE p.pregPregunta = :pregPregunta")})
public class Pregunta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "preg_id")
    private Integer pregId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "preg_pregunta")
    private String pregPregunta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pregId", fetch=FetchType.EAGER)
    private List<Respuesta> respuestaList;

    public Pregunta() {
    }

    public Pregunta(Integer pregId) {
        this.pregId = pregId;
    }

    public Pregunta(Integer pregId, String pregPregunta) {
        this.pregId = pregId;
        this.pregPregunta = pregPregunta;
    }

    public Integer getPregId() {
        return pregId;
    }

    public void setPregId(Integer pregId) {
        this.pregId = pregId;
    }

    public String getPregPregunta() {
        return pregPregunta;
    }

    public void setPregPregunta(String pregPregunta) {
        this.pregPregunta = pregPregunta;
    }

    @XmlTransient
    public List<Respuesta> getRespuestaList() {
        return respuestaList;
    }

    public void setRespuestaList(List<Respuesta> respuestaList) {
        this.respuestaList = respuestaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pregId != null ? pregId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pregunta)) {
            return false;
        }
        Pregunta other = (Pregunta) object;
        if ((this.pregId == null && other.pregId != null) || (this.pregId != null && !this.pregId.equals(other.pregId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.udistrital.prototipovak.entity.Pregunta[ pregId=" + pregId + " ]";
    }
    
}
