/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.udistrital.prototipovak.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author lreyes
 */
@Embeddable
public class UsuarioRespuestaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "usr_id")
    private int usrId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rta_id")
    private int rtaId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "peri_id")
    private int periId;

    public UsuarioRespuestaPK() {
    }

    public UsuarioRespuestaPK(int usrId, int rtaId, int periId) {
        this.usrId = usrId;
        this.rtaId = rtaId;
        this.periId = periId;
    }

    public int getUsrId() {
        return usrId;
    }

    public void setUsrId(int usrId) {
        this.usrId = usrId;
    }

    public int getRtaId() {
        return rtaId;
    }

    public void setRtaId(int rtaId) {
        this.rtaId = rtaId;
    }

    public int getPeriId() {
        return periId;
    }

    public void setPeriId(int periId) {
        this.periId = periId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) usrId;
        hash += (int) rtaId;
        hash += (int) periId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioRespuestaPK)) {
            return false;
        }
        UsuarioRespuestaPK other = (UsuarioRespuestaPK) object;
        if (this.usrId != other.usrId) {
            return false;
        }
        if (this.rtaId != other.rtaId) {
            return false;
        }
        if (this.periId != other.periId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.udistrital.prototipovak.entity.UsuarioRespuestaPK[ usrId=" + usrId + ", rtaId=" + rtaId + ", periId=" + periId + " ]";
    }
    
}
