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
@Table(name = "programa_academico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProgramaAcademico.findAll", query = "SELECT p FROM ProgramaAcademico p order by p.progNombre"),
    @NamedQuery(name = "ProgramaAcademico.findByProgId", query = "SELECT p FROM ProgramaAcademico p WHERE p.progId = :progId"),
    @NamedQuery(name = "ProgramaAcademico.findByProgCodigo", query = "SELECT p FROM ProgramaAcademico p WHERE p.progCodigo = :progCodigo"),
    @NamedQuery(name = "ProgramaAcademico.findByProgNombre", query = "SELECT p FROM ProgramaAcademico p WHERE p.progNombre = :progNombre")})
public class ProgramaAcademico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "prog_id")
    private Integer progId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "prog_codigo")
    private String progCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "prog_nombre")
    private String progNombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "progId")
    private List<Grupo> grupoList;

    public ProgramaAcademico() {
    }

    public ProgramaAcademico(Integer progId) {
        this.progId = progId;
    }

    public ProgramaAcademico(Integer progId, String progCodigo, String progNombre) {
        this.progId = progId;
        this.progCodigo = progCodigo;
        this.progNombre = progNombre;
    }

    public Integer getProgId() {
        return progId;
    }

    public void setProgId(Integer progId) {
        this.progId = progId;
    }

    public String getProgCodigo() {
        return progCodigo;
    }

    public void setProgCodigo(String progCodigo) {
        this.progCodigo = progCodigo;
    }

    public String getProgNombre() {
        return progNombre;
    }

    public void setProgNombre(String progNombre) {
        this.progNombre = progNombre;
    }

    @XmlTransient
    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (progId != null ? progId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProgramaAcademico)) {
            return false;
        }
        ProgramaAcademico other = (ProgramaAcademico) object;
        if ((this.progId == null && other.progId != null) || (this.progId != null && !this.progId.equals(other.progId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.udistrital.prototipovak.entity.ProgramaAcademico[ progId=" + progId + " ]";
    }
    
}
