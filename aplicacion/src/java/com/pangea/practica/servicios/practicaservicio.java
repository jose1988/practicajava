/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pangea.practica.servicios;

import com.pangea.practica.modelo.bean.CargoFacade;
import com.pangea.practica.modelo.bean.DepartamentoFacade;
import com.pangea.practica.modelo.bean.EmpleadoFacade;
import com.pangea.practica.modelo.bean.ProyectoFacade;
import com.pangea.practica.modelo.entidades.Cargo;
import com.pangea.practica.modelo.entidades.Departamento;
import com.pangea.practica.modelo.entidades.Empleado;
import com.pangea.practica.modelo.entidades.Proyecto;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Pangea
 */
@WebService(serviceName = "practicaservicio")
public class practicaservicio {
@EJB
DepartamentoFacade departamentoFacade;
@EJB
CargoFacade cargoFacade;
@EJB
EmpleadoFacade empleadoFacade;
@EJB
ProyectoFacade proyectoFacade;

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " ,bienvenido!";
    }


@WebMethod(operationName = "listar")
    public List<Departamento> listar() {
        return departamentoFacade.retornadepartamentos();
    }
 @WebMethod(operationName = "descripcion")
    public Cargo descripcion(@WebParam(name = "name") String txt) {
        return cargoFacade.descripcion(txt);
    }
@WebMethod(operationName = "largo")
    public  List<Departamento> largo() {
        return departamentoFacade.largo();
    }


@WebMethod(operationName = "psd1")
    public List<String[]> psd1() {
        return departamentoFacade.psd1();
    }

@WebMethod(operationName = "insertarCargo")
    public void insertarCargo(@WebParam(name = "registroCargo") Cargo registro) {
 
      cargoFacade.insertar(registro);
    }

@WebMethod(operationName = "insertarDepartamento")
    public void insertarDepartamento(@WebParam(name = "registroDepartamento") Departamento registro) {
 
      departamentoFacade.insertar(registro);
    }

@WebMethod(operationName = "insertarEmpleado")
    public void insertarEmpleado(@WebParam(name = "registroEmpleado") Empleado registro) {
 
      empleadoFacade.insertar(registro);
    }

@WebMethod(operationName = "insertarProyecto")
    public void insertarProyecto(@WebParam(name = "registroProyecto") Proyecto registro) {
 
      proyectoFacade.insertar(registro);
    }




@WebMethod(operationName = "cep1")
    public List<String[]> cep1() {
        return departamentoFacade.cep1();
    }


@WebMethod(operationName = "cep2")
    public List<String[]> cep2() {
        return departamentoFacade.cep2();
    }
@WebMethod(operationName = "psg1")
    public BigDecimal psg1() {
        return empleadoFacade.psg1();
    }


@WebMethod(operationName = "edc1")
    public List<String[]> edc1() {
        return empleadoFacade.edc1();
    }

@WebMethod(operationName = "edc2")
    public List<String[]> edc2() {
        return empleadoFacade.edc1();
    }
}
