/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quejasYreclamos.modelo.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cvaldes
 */
@Entity
@Table(name = "respuestas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Respuestas.findAll", query = "SELECT r FROM Respuestas r"),
    @NamedQuery(name = "Respuestas.findByRespuestasId", query = "SELECT r FROM Respuestas r WHERE r.respuestasId = :respuestasId"),
    @NamedQuery(name = "Respuestas.findByFecha", query = "SELECT r FROM Respuestas r WHERE r.fecha = :fecha")})
public class Respuestas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "respuestas_id")
    private Integer respuestasId;
    @Basic(optional = false)
    @Lob
    @Column(name = "mensaje")
    private String mensaje;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "Usuarios_id", referencedColumnName = "usuarios_id")
    @ManyToOne(optional = false)
    private Usuarios usuariosid;
    @JoinColumn(name = "Peticiones_id", referencedColumnName = "peticiones_id")
    @ManyToOne(optional = false)
    private Peticiones peticionesid;

    public Respuestas() {
    }

    public Respuestas(Integer respuestasId) {
        this.respuestasId = respuestasId;
    }

    public Respuestas(Integer respuestasId, String mensaje) {
        this.respuestasId = respuestasId;
        this.mensaje = mensaje;
    }

    public Integer getRespuestasId() {
        return respuestasId;
    }

    public void setRespuestasId(Integer respuestasId) {
        this.respuestasId = respuestasId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuarios getUsuariosid() {
        return usuariosid;
    }

    public void setUsuariosid(Usuarios usuariosid) {
        this.usuariosid = usuariosid;
    }

    public Peticiones getPeticionesid() {
        return peticionesid;
    }

    public void setPeticionesid(Peticiones peticionesid) {
        this.peticionesid = peticionesid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (respuestasId != null ? respuestasId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Respuestas)) {
            return false;
        }
        Respuestas other = (Respuestas) object;
        if ((this.respuestasId == null && other.respuestasId != null) || (this.respuestasId != null && !this.respuestasId.equals(other.respuestasId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "quejasYreclamos.modelo.entidades.Respuestas[ respuestasId=" + respuestasId + " ]";
    }
    
}
