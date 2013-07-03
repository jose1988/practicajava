/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pangea.practica.modelo.bean;

import com.pangea.practica.modelo.entidades.Proyecto;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Pangea
 */
@Stateless
public class ProyectoFacade extends AbstractFacade<Proyecto> {
    @PersistenceContext(unitName = "aplicacionPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProyectoFacade() {
        super(Proyecto.class);
    }
    
    public void insertar(Proyecto registro){
        
    this.create(registro);
    } 
    
     public Proyecto descripcion(String nombre){
      Proyecto c=null;
      
     c=(Proyecto) em.createNamedQuery("Proyecto.findByNombre").setParameter("nombre",nombre).getSingleResult();
      return c;
   } 
    
    
}
