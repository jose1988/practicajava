/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pangea.practica.modelo.bean;

import com.pangea.practica.modelo.entidades.Cargo;
import com.pangea.practica.modelo.entidades.Departamento;
import com.pangea.practica.modelo.entidades.Empleado;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Pangea
 */
@Stateless
public class DepartamentoFacade extends AbstractFacade<Departamento> {
    @PersistenceContext(unitName = "aplicacionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DepartamentoFacade() {
        super(Departamento.class);
    }
    
     public List<Departamento> retornadepartamentos(){
    List<Departamento> Lista=null;
    Lista=findAll();
    return Lista;
} 
    public  List<Departamento> largo(){
       List<Departamento> c=null;
      
     c=( List<Departamento>) em.createNamedQuery("Departamento.findBylong").getResultList();
      return c;
   }  
    
 public  List<String[]> psd1(){
     
     List<Object[]> listac;
    Query q=em.createNativeQuery("SELECT departamento.nombre , AVG(empleado.sueldo) FROM departamento, empleado  WHERE departamento.ID_DEPARTAMENTO= empleado.ID_DEPARTAMENTO GROUP By departamento.nombre");
    listac= q.getResultList();
    
    List<String[]> lista=new ArrayList<String[]>();
     
  
    for(int i=0; i<listac.size();i++){
        String[] vect=new String[2];
    Object obj=listac.get(i)[0];
    vect[0]=new String((String) obj);
    Object obj2=listac.get(i)[1];
    BigDecimal BigD = (BigDecimal) obj2;
    vect[1]=BigD.toString();
    lista.add(vect);
   
    }
   
      
      return lista;
     
   }   
 
  public  List<String[]> psd2(){
       List<Departamento> c=null;
      
     c=( List<Departamento>) em.createNamedQuery("Departamento.findAll").getResultList();
      List<String[]> lista=new ArrayList<String[]>();
     for(int i=0; i<c.size(); i++){
         String[] vect=new String[2];
         List<Empleado> le=null;
         double m = 0;
         double acum=0;
         String prom;
         le=c.get(i).getEmpleadoList();
         for(int j=0; j<le.size(); j++){
            m=c.get(i).getEmpleadoList().get(j).getSueldo();
            acum=acum+m;
         }
         acum=acum/le.size();
         prom=String.valueOf(m);
          
          vect[0]=new String(c.get(i).getNombre());
          vect[1]=new String(prom);
           lista.add(vect);
     }
    return lista;
   }  
 
 public  List<String[]> cep1(){
     
     List<Object[]> listac;
    Query q=em.createNativeQuery("SELECT departamento.nombre, count (*) FROM departamento, empleado WHERE departamento.ID_DEPARTAMENTO= empleado.ID_DEPARTAMENTO GROUP By departamento.nombre");
    listac= q.getResultList();
    
    List<String[]> lista=new ArrayList<String[]>();
  
    for(int i=0; i<listac.size();i++){
        String[] vect=new String[2];
    Object obj=listac.get(i)[0];
    vect[0]=new String((String) obj);
    Object obj2=listac.get(i)[1];
    BigDecimal BigD = (BigDecimal) obj2;
    vect[1]=BigD.toString();
    lista.add(vect);
   
    }
      return lista;
   }   
 
  public  List<String[]> cep2(){
       List<Departamento> c=null;
      
     c=( List<Departamento>) em.createNamedQuery("Departamento.findAll").getResultList();
      List<String[]> lista=new ArrayList<String[]>();
     for(int i=0; i<c.size(); i++){
         String[] vect=new String[2];
         List<Empleado> le=null;
         double m = 0;
         double acum=0;
         String prom;
         le=c.get(i).getEmpleadoList();
         
            
         
         acum=le.size();
         prom=String.valueOf(acum);
          
          vect[0]=new String(c.get(i).getNombre());
          vect[1]=new String(prom);
           lista.add(vect);
     }
    return lista;
   }  
 
  public Departamento descripcion(String nombre){
      Departamento c=null;
      
     c=(Departamento) em.createNamedQuery("Departamento.findByNombre").setParameter("nombre",nombre).getSingleResult();
      return c;
   } 
 
}
