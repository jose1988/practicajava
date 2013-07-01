/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pangea.practica.modelo.bean;

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
public class EmpleadoFacade extends AbstractFacade<Empleado> {
    @PersistenceContext(unitName = "aplicacionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpleadoFacade() {
        super(Empleado.class);
    }
    
     public  BigDecimal psg1(){
     
     Object listac;
    Query q=em.createNativeQuery("SELECT  AVG(empleado.sueldo) FROM  empleado ");
    listac= q.getSingleResult();
    BigDecimal BigD=BigDecimal.ZERO;
    BigD = (BigDecimal) listac;
    return BigD;
     
   }   
    
     public  String psg2(){
     
     List<Empleado> q=null;
     double m;
       
      double acum = 0 ;
      String prom;
     q=em.createNamedQuery("Empleado.findAll").getResultList();
     
     for(int i=0;i<q.size();i++){
          m = q.get(i).getSueldo();
         acum=acum+m;
     }
     acum=acum/q.size();
     prom=String.valueOf(acum);
     
    return prom;
     
   } 
      public Empleado descripcion(String nombre){
      Empleado c=null;
      
     c=(Empleado) em.createNamedQuery("Empleado.findByNombre").setParameter("nombre",nombre).getSingleResult();
      return c;
   } 
     
     public  List<String[]> edc1(){
     
     List<Object[]> listac;
    Query q=em.createNativeQuery("SELECT empleado. nombre , departamento.nombre, cargo.nombre FROM departamento, empleado, cargo  WHERE departamento.ID_DEPARTAMENTO= empleado.ID_DEPARTAMENTO and cargo.cargoId=empleado.cargoId");
    listac= q.getResultList();
    
    List<String[]> lista=new ArrayList<String[]>();
     
  
    for(int i=0; i<listac.size();i++){
        String[] vect=new String[3];
    Object obj=listac.get(i)[0];
    vect[0]=new String((String) obj);
    Object obj2=listac.get(i)[1];
    vect[1]=new String((String) obj2);
    Object obj3=listac.get(i)[2];
    vect[2]=new String((String) obj3);
    lista.add(vect);
   
    }
   
      
      return lista;
     
   }  
     
      public  List<String[]> edc2(){
       List<Departamento> c=null;
      
     c=( List<Departamento>) em.createNamedQuery("empleado.findAll").getResultList();
      List<String[]> lista=new ArrayList<String[]>();
     for(int i=0; i<c.size(); i++){
         String[] vect=new String[2];
         List<Empleado> le=null;
         String nom = null,car = null,departa;
         double acum=0;
         String prom;
         le=c.get(i).getEmpleadoList();
         departa=le.get(i).getNombre();
         for(int j=0; j<le.size(); j++){
            car=le.get(j).getCargoid().getNombre();
            nom=le.get(j).getNombre();
         }
         
          
          vect[0]=new String(departa);
          vect[1]=new String(nom);
          vect[2]=new String(car);
           lista.add(vect);
     }
    return lista;
   }  
     
}
