/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quejasYreclamos.modelo.entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author cvaldes
 */
@Entity
@Table(name = "peticiones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Peticiones.findAll", query = "SELECT p FROM Peticiones p"),
    @NamedQuery(name = "Peticiones.findByPeticionesId", query = "SELECT p FROM Peticiones p WHERE p.peticionesId = :peticionesId"),
    @NamedQuery(name = "Peticiones.findByAsunto", query = "SELECT p FROM Peticiones p WHERE p.asunto = :asunto"),
    @NamedQuery(name = "Peticiones.findByFecha", query = "SELECT p FROM Peticiones p WHERE p.fecha = :fecha"),
    @NamedQuery(name = "Peticiones.findByEstado", query = "SELECT p FROM Peticiones p WHERE p.estado = :estado"),
    @NamedQuery(name = "Peticiones.findByFechaAtencion", query = "SELECT p FROM Peticiones p WHERE p.fechaAtencion = :fechaAtencion"),
    @NamedQuery(name = "Peticiones.findByPrioridad", query = "SELECT p FROM Peticiones p WHERE p.prioridad = :prioridad")})
public class Peticiones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "peticiones_id")
    private Integer peticionesId;
    @Basic(optional = false)
    @Column(name = "asunto")
    private String asunto;
    @Basic(optional = false)
    @Lob
    @Column(name = "mensaje")
    private String mensaje;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "estado")
    private String estado;
    @Column(name = "fecha_atencion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAtencion;
    @Column(name = "prioridad")
    private String prioridad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "peticionesid")
    private Collection<Respuestas> respuestasCollection;
    @JoinColumn(name = "Usuarios_id", referencedColumnName = "usuarios_id")
    @ManyToOne(optional = false)
    private Usuarios usuariosid;

    public Peticiones() {
    }

    public Peticiones(Integer peticionesId) {
        this.peticionesId = peticionesId;
    }

    public Peticiones(Integer peticionesId, String asunto, String mensaje) {
        this.peticionesId = peticionesId;
        this.asunto = asunto;
        this.mensaje = mensaje;
    }

    public Integer getPeticionesId() {
        return peticionesId;
    }

    public void setPeticionesId(Integer peticionesId) {
        this.peticionesId = peticionesId;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(Date fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    @XmlTransient
    public Collection<Respuestas> getRespuestasCollection() {
        return respuestasCollection;
    }

    public void setRespuestasCollection(Collection<Respuestas> respuestasCollection) {
        this.respuestasCollection = respuestasCollection;
    }

    public Usuarios getUsuariosid() {
        return usuariosid;
    }

    public void setUsuariosid(Usuarios usuariosid) {
        this.usuariosid = usuariosid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (peticionesId != null ? peticionesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Peticiones)) {
            return false;
        }
        Peticiones other = (Peticiones) object;
        if ((this.peticionesId == null && other.peticionesId != null) || (this.peticionesId != null && !this.peticionesId.equals(other.peticionesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "quejasYreclamos.modelo.entidades.Peticiones[ peticionesId=" + peticionesId + " ]";
    }
    
}
