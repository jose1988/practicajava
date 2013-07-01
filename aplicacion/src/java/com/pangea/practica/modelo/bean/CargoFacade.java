/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pangea.practica.modelo.bean;

import com.pangea.practica.modelo.entidades.Cargo;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author Pangea
 */
@Stateless
public class CargoFacade extends AbstractFacade<Cargo> {
    @PersistenceContext(unitName = "aplicacionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CargoFacade() {
        super(Cargo.class);
    }
    
    public List<Cargo> retornacargos(){
    List<Cargo> Lista=null;
    
    Lista=findAll();
    return Lista;
} 
    
   public Cargo descripcion(String nombre){
      Cargo c=null;
      
     c=(Cargo) em.createNamedQuery("Cargo.findByNombre").setParameter("nombre",nombre).getSingleResult();
      return c;
   } 
    
    
}
