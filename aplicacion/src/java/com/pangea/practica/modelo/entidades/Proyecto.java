/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pangea.practica.modelo.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Pangea
 */
@Entity
@Table(name = "PROYECTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proyecto.findAll", query = "SELECT p FROM Proyecto p"),
    @NamedQuery(name = "Proyecto.findByProyectoid", query = "SELECT p FROM Proyecto p WHERE p.proyectoid = :proyectoid"),
    @NamedQuery(name = "Proyecto.findByNombre", query = "SELECT p FROM Proyecto p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Proyecto.findByDescripcion", query = "SELECT p FROM Proyecto p WHERE p.descripcion = :descripcion")})
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "proyectoSEQ")
    @SequenceGenerator(name = "proyectoSEQ", sequenceName = "PROYECTO_SEQ", allocationSize = 1)
    @Column(name = "PROYECTOID")
    private BigDecimal proyectoid;
    @Size(max = 30)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 30)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @JoinColumn(name = "EMPLEADOID", referencedColumnName = "EMPLEADOID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Empleado empleadoid;

    public Proyecto() {
    }

    public Proyecto(BigDecimal proyectoid) {
        this.proyectoid = proyectoid;
    }

    public BigDecimal getProyectoid() {
        return proyectoid;
    }

    public void setProyectoid(BigDecimal proyectoid) {
        this.proyectoid = proyectoid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Empleado getEmpleadoid() {
        return empleadoid;
    }

    public void setEmpleadoid(Empleado empleadoid) {
        this.empleadoid = empleadoid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proyectoid != null ? proyectoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proyecto)) {
            return false;
        }
        Proyecto other = (Proyecto) object;
        if ((this.proyectoid == null && other.proyectoid != null) || (this.proyectoid != null && !this.proyectoid.equals(other.proyectoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pangea.practica.modelo.entidades.Proyecto[ proyectoid=" + proyectoid + " ]";
    }
}
