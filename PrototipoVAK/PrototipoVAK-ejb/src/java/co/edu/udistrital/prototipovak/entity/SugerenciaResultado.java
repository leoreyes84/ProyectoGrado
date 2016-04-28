/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gerson Cespedes
 */
@Entity
@Table(name = "sugerencia_resultado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SugerenciaResultado.findAll", query = "SELECT s FROM SugerenciaResultado s"),
    @NamedQuery(name = "SugerenciaResultado.findBySugId", query = "SELECT s FROM SugerenciaResultado s WHERE s.sugId = :sugId"),
    @NamedQuery(name = "SugerenciaResultado.findBySugTipoVak", query = "SELECT s FROM SugerenciaResultado s WHERE s.sugTipoVak = :sugTipoVak"),
    @NamedQuery(name = "SugerenciaResultado.findBySugParrafo", query = "SELECT s FROM SugerenciaResultado s WHERE s.sugParrafo = :sugParrafo")})
public class SugerenciaResultado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "sug_id")
    private Integer sugId;
    @Size(max = 1)
    @Column(name = "sug_tipo_vak")
    private String sugTipoVak;
    @Size(max = 1000)
    @Column(name = "sug_parrafo")
    private String sugParrafo;

    public SugerenciaResultado() {
    }

    public SugerenciaResultado(Integer sugId) {
        this.sugId = sugId;
    }

    public Integer getSugId() {
        return sugId;
    }

    public void setSugId(Integer sugId) {
        this.sugId = sugId;
    }

    public String getSugTipoVak() {
        return sugTipoVak;
    }

    public void setSugTipoVak(String sugTipoVak) {
        this.sugTipoVak = sugTipoVak;
    }

    public String getSugParrafo() {
        return sugParrafo;
    }

    public void setSugParrafo(String sugParrafo) {
        this.sugParrafo = sugParrafo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sugId != null ? sugId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SugerenciaResultado)) {
            return false;
        }
        SugerenciaResultado other = (SugerenciaResultado) object;
        if ((this.sugId == null && other.sugId != null) || (this.sugId != null && !this.sugId.equals(other.sugId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.udistrital.prototipovak.entity.SugerenciaResultado[ sugId=" + sugId + " ]";
    }
    
}
