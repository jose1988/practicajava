package com.pangea.practica.control.controller;

import com.pangea.practica.modelo.entidades.Empleado;
import com.pangea.practica.control.controller.util.JsfUtil;
import com.pangea.practica.control.controller.util.PaginationHelper;
import com.pangea.practica.control.servicios.Pruebaservicio_Service;
import com.pangea.practica.modelo.bean.CargoFacade;
import com.pangea.practica.modelo.bean.EmpleadoFacade;
import com.pangea.practica.modelo.entidades.Cargo;
import com.pangea.practica.modelo.entidades.Departamento;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.xml.ws.WebServiceRef;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;



@ManagedBean(name = "empleadoController")
@SessionScoped
public class EmpleadoController implements Serializable {
   @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/192.168.1.102_15429/prueba/pruebaservicio.wsdl")
   private Pruebaservicio_Service service;

    private Empleado current;

   
    private DataModel items = null;
    @EJB
    private com.pangea.practica.modelo.bean.EmpleadoFacade ejbFacade;
    @EJB
    private com.pangea.practica.modelo.bean.CargoFacade cargoFacade;
     @EJB
    private com.pangea.practica.modelo.bean.DepartamentoFacade departamentoFacade;

   
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List<Cargo> cargos;
    private Cargo cargoselecionado;
    private List<Departamento> departamentos;

    private Cargo aux;

    public Cargo getAux() {
        return aux;
    }

    public void setAux(Cargo aux) {
        this.aux = aux;
    }
    private Departamento departamentoseleccionado;
    private String Buscar;
 private Empleado descripcion;
  /** LAZYYY **/
    private LazyDataModel<Empleado> lazyModel;
    private int pagIndex = 0;
    private Map<String, String> fields = new HashMap<String, String>();
    private String sortF = null;
    private boolean sortB = false;
    private int paginacion = 10;
    /** FIN LAZY **/
    
    
    public EmpleadoController() {
    }

    @PostConstruct 
    public void iniciar(){
        cargos =cargoFacade.findAll();
         departamentos =departamentoFacade.findAll();
    
    }
    
      public void busqueda() {  

        descripcion = getFacade().descripcion(Buscar);
 
    } 
    
    public Empleado getSelected() {
        if (current == null) {
            current = new Empleado();
            selectedItemIndex = -1;
        }
        return current;
    }

    private EmpleadoFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }
    public Cargo getCargoselecionado() {
        return cargoselecionado;
    }

    public void setCargoselecionado(Cargo cargoselecionado) {
        this.cargoselecionado = cargoselecionado;
    }
     public Empleado getCurrent() {
        return current;
    }

    public void setCurrent(Empleado current) {
        this.current = current;
    }
    
public String getBuscar() {
        return Buscar;
    }

    public void setBuscar(String Buscar) {
        this.Buscar = Buscar;
    }

    public Empleado getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Empleado descripcion) {
        this.descripcion = descripcion;
    }
     public CargoFacade getCargoFacade() {
        return cargoFacade;
    }

    public void setCargoFacade(CargoFacade cargoFacade) {
        this.cargoFacade = cargoFacade;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }
    
    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Empleado) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Empleado();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        
        try {
            current.setCargoid(cargoselecionado);
            current.setDepartamentoid(departamentoseleccionado);
            
             com.pangea.practica.control.servicios.Empleado insert=new com.pangea.practica.control.servicios.Empleado();
              com.pangea.practica.control.servicios.Cargo insert2=new com.pangea.practica.control.servicios.Cargo();
              com.pangea.practica.control.servicios.Departamento insert3=new com.pangea.practica.control.servicios.Departamento();
             insert.setEmpleadoid(current.getEmpleadoid());
             insert.setNombre(current.getNombre());
             insert.setApellido(current.getApellido());
             insert.setDireccion(current.getDireccion());
             insert.setSueldo(current.getSueldo().toBigInteger());
             insert2.setCargoid(current.getCargoid().getCargoid());
            insert.setCargoid(insert2);
            insert3.setDepartamentoid(current.getDepartamentoid().getDepartamentoid());
            insert.setDepartamentoid(insert3);
            this.crearEmpleado(insert);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EmpleadoCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Empleado) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EmpleadoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Empleado) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("EmpleadoDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }
    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }

    public Departamento getDepartamentoseleccionado() {
        return departamentoseleccionado;
    }

    public void setDepartamentoseleccionado(Departamento departamentoseleccionado) {
        this.departamentoseleccionado = departamentoseleccionado;
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

   @FacesConverter(forClass = Cargo.class)
    public static class CargoControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EmpleadoController controller = (EmpleadoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "empleadoController");
            return controller.cargoFacade.find(getKey(value));
        }

        java.math.BigDecimal getKey(String value) {
            java.math.BigDecimal key;
            key = new java.math.BigDecimal(value);
            return key;
        }

        String getStringKey(java.math.BigDecimal value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Cargo) {
                Cargo o = (Cargo) object;
                return getStringKey(o.getCargoid());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Cargo.class.getName());
            }
        }
    }
    
    @FacesConverter(forClass = Departamento.class)
    public static class DepartamentoControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EmpleadoController controller = (EmpleadoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "empleadoController");
            return controller.departamentoFacade.find(getKey(value));
        }

        java.math.BigDecimal getKey(String value) {
            java.math.BigDecimal key;
            key = new java.math.BigDecimal(value);
            return key;
        }

        String getStringKey(java.math.BigDecimal value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Departamento) {
                Departamento o = (Departamento) object;
                return getStringKey(o.getDepartamentoid());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Departamento.class.getName());
            }
        }
    }


  @FacesConverter(forClass = Empleado.class)
    public static class EmpleadoControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EmpleadoController controller = (EmpleadoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "empleadoController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.math.BigDecimal getKey(String value) {
            java.math.BigDecimal key;
            key = new java.math.BigDecimal(value);
            return key;
        }

        String getStringKey(java.math.BigDecimal value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Empleado) {
                Empleado o = (Empleado) object;
                return getStringKey(o.getEmpleadoid());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Empleado.class.getName());
            }
        }
    }
      
      /** INICIO **/
    public void inicializarLazy() {
        lazyModel = new LazyDataModel<Empleado>() {

       
            public List<Empleado> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
                paginacion = pageSize;
                int cantidad = getFacade().count();
                if (cantidad > 0) {
                    pagIndex = first;
                    fields = filters;
                    sortF = sortField;
                    sortB = true;
                    String cadena = "";
                    lazyModel.setWrappedData(null);
                    cantidad = getFacade().count();
                    lazyModel.setRowCount(cantidad);
                    return getFacade().findRange(sortF, sortB, new int[]{first, first + pageSize}, filters, cadena);

                }
                return null;
                // return lazymv;
            }
        };

        lazyModel.setRowCount((int) getFacade().count());
        //lazyModel.setRowCount(10);

        if (lazyModel.getPageSize() == 0) {
            lazyModel.setPageSize(1);
        }
        if (lazyModel.getRowCount() == 0) {
            lazyModel.setRowCount(10);
        }

    }

    public LazyDataModel<Empleado> getLazyModel() {
        if (lazyModel == null) {
            inicializarLazy();
        }
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Empleado> lazyModel) {
        this.lazyModel = lazyModel;
    }
//fin lazy
     public void onEdit(RowEditEvent event) {  
         System.out.println("entro a edit");
         Empleado c=(Empleado) event.getObject();
         getFacade().edit(c);
          
    }  
      
    public void onCancel(RowEditEvent event) {  
        System.out.println("entro a cancel");
    }  
    
    public void eliminar() {  
       System.out.println("entro a eliminar");  
       System.out.println(current.getNombre());
       Empleado c=getFacade().find(current.getEmpleadoid());
    
       getFacade().remove(c);
       inicializarLazy();
       mostrarMensaje( 1,"Mensaje","ha sido eliminado");
      
    } 
    
     public void mostrarMensaje(int _opcMensaje, String _cabeceraMensaje, String _cuerpomensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        switch (_opcMensaje) {
            case 0: {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, _cabeceraMensaje, _cuerpomensaje));
                break;
            }
            case 1: {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, _cabeceraMensaje, _cuerpomensaje));
                break;
            }
            case 2: {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, _cabeceraMensaje, _cuerpomensaje));
                break;
            }
            case 3: {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, _cabeceraMensaje, _cuerpomensaje));
                break;
            }
        }
    }

    private void crearEmpleado(com.pangea.practica.control.servicios.Empleado registroEmpleado) {
        com.pangea.practica.control.servicios.Pruebaservicio port = service.getPruebaservicioPort();
        port.crearEmpleado(registroEmpleado);
    }


}
