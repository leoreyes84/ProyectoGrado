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
@Table(name = "periodo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Periodo.findAll", query = "SELECT p FROM Periodo p"),
    @NamedQuery(name = "Periodo.findByPeriId", query = "SELECT p FROM Periodo p WHERE p.periId = :periId"),
    @NamedQuery(name = "Periodo.findByPeriNombre", query = "SELECT p FROM Periodo p WHERE p.periNombre = :periNombre"),
    @NamedQuery(name = "Periodo.findByPeriDescripcion", query = "SELECT p FROM Periodo p WHERE p.periDescripcion = :periDescripcion")})
public class Periodo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "peri_id")
    private Integer periId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "peri_nombre")
    private String periNombre;
    @Size(max = 45)
    @Column(name = "peri_descripcion")
    private String periDescripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periId", fetch = FetchType.LAZY)
    private List<Grupo> grupoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo", fetch = FetchType.LAZY)
    private List<UsuarioRespuesta> usuarioRespuestaList;

    public Periodo() {
    }

    public Periodo(Integer periId) {
        this.periId = periId;
    }

    public Periodo(Integer periId, String periNombre) {
        this.periId = periId;
        this.periNombre = periNombre;
    }

    public Integer getPeriId() {
        return periId;
    }

    public void setPeriId(Integer periId) {
        this.periId = periId;
    }

    public String getPeriNombre() {
        return periNombre;
    }

    public void setPeriNombre(String periNombre) {
        this.periNombre = periNombre;
    }

    public String getPeriDescripcion() {
        return periDescripcion;
    }

    public void setPeriDescripcion(String periDescripcion) {
        this.periDescripcion = periDescripcion;
    }

    @XmlTransient
    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }

    @XmlTransient
    public List<UsuarioRespuesta> getUsuarioRespuestaList() {
        return usuarioRespuestaList;
    }

    public void setUsuarioRespuestaList(List<UsuarioRespuesta> usuarioRespuestaList) {
        this.usuarioRespuestaList = usuarioRespuestaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (periId != null ? periId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Periodo)) {
            return false;
        }
        Periodo other = (Periodo) object;
        if ((this.periId == null && other.periId != null) || (this.periId != null && !this.periId.equals(other.periId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.udistrital.prototipovak.entity.Periodo[ periId=" + periId + " ]";
    }
    
}
