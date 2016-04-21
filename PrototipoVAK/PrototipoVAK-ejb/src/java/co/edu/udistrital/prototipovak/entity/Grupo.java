/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g order by g.grupNombre"),
    @NamedQuery(name = "Grupo.findByGrupId", query = "SELECT g FROM Grupo g WHERE g.grupId = :grupId"),
    @NamedQuery(name = "Grupo.findByGrupNombre", query = "SELECT g FROM Grupo g WHERE g.grupNombre = :grupNombre")})
public class Grupo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "grup_id")
    private Integer grupId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "grup_nombre")
    private String grupNombre;
    @JoinTable(name = "usuario_grupo", joinColumns = {
        @JoinColumn(name = "grup_id", referencedColumnName = "grup_id")}, inverseJoinColumns = {
        @JoinColumn(name = "usr_id", referencedColumnName = "usr_id")})
    @ManyToMany
    private List<Usuario> usuarioList;
    @JoinColumn(name = "peri_id", referencedColumnName = "peri_id")
    @ManyToOne(optional = false)
    private Periodo periId;
    @JoinColumn(name = "prog_id", referencedColumnName = "prog_id")
    @ManyToOne(optional = false)
    private ProgramaAcademico progId;

    public Grupo() {
    }

    public Grupo(Integer grupId) {
        this.grupId = grupId;
    }

    public Grupo(Integer grupId, String grupNombre) {
        this.grupId = grupId;
        this.grupNombre = grupNombre;
    }

    public Integer getGrupId() {
        return grupId;
    }

    public void setGrupId(Integer grupId) {
        this.grupId = grupId;
    }

    public String getGrupNombre() {
        return grupNombre;
    }

    public void setGrupNombre(String grupNombre) {
        this.grupNombre = grupNombre;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public Periodo getPeriId() {
        return periId;
    }

    public void setPeriId(Periodo periId) {
        this.periId = periId;
    }

    public ProgramaAcademico getProgId() {
        return progId;
    }

    public void setProgId(ProgramaAcademico progId) {
        this.progId = progId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grupId != null ? grupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.grupId == null && other.grupId != null) || (this.grupId != null && !this.grupId.equals(other.grupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.udistrital.prototipovak.entity.Grupo[ grupId=" + grupId + " ]";
    }
    
}
