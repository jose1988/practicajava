/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pangea.practica.modelo.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Pangea
 */
@Entity
@Table(name = "DEPARTAMENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Departamento.findAll", query = "SELECT d FROM Departamento d"),
    @NamedQuery(name = "Departamento.findByIdDepartamento", query = "SELECT d FROM Departamento d WHERE d.idDepartamento = :idDepartamento"),
    @NamedQuery(name = "Departamento.findByNombre", query = "SELECT d FROM Departamento d WHERE d.nombre = :nombre"),
    @NamedQuery(name = "Departamento.findByCantEmpleados", query = "SELECT d FROM Departamento d WHERE d.cantEmpleados = :cantEmpleados"),
    @NamedQuery(name = "Departamento.findBylong", query = "SELECT d FROM Departamento d WHERE length(d.nombre)>6"),
    @NamedQuery(name = "Departamento.findByDescripcion", query = "SELECT d FROM Departamento d WHERE d.descripcion = :descripcion"),
   })
public class Departamento implements Serializable {
    private static final long serialVersionUID = 1L;
     @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEPARTAMENTOSEQ")
    @SequenceGenerator(name = "DEPARTAMENTOSEQ", sequenceName = "DEPARTAMENTO_SE", allocationSize = 1)
  
    @Basic(optional = false)
    @Column(name = "ID_DEPARTAMENTO")
    private BigDecimal idDepartamento;
    @Size(max = 30)
    @Column(name = "NOMBRE")
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CANT_EMPLEADOS")
    private BigDecimal cantEmpleados;
    @Size(max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
  
    @OneToMany(mappedBy = "idDepartamento", fetch = FetchType.LAZY)
    private List<Empleado> empleadoList;

    public Departamento() {
    }

    public Departamento(BigDecimal idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Number getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(BigDecimal idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getCantEmpleados() {
        return cantEmpleados;
    }

    public void setCantEmpleados(BigDecimal cantEmpleados) {
        this.cantEmpleados = cantEmpleados;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

   

    

    

    @XmlTransient
    public List<Empleado> getEmpleadoList() {
        return empleadoList;
    }

    public void setEmpleadoList(List<Empleado> empleadoList) {
        this.empleadoList = empleadoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDepartamento != null ? idDepartamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departamento)) {
            return false;
        }
        Departamento other = (Departamento) object;
        if ((this.idDepartamento == null && other.idDepartamento != null) || (this.idDepartamento != null && !this.idDepartamento.equals(other.idDepartamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pangea.practica.modelo.entidades.Departamento[ idDepartamento=" + idDepartamento + " ]";
    }
    
}
