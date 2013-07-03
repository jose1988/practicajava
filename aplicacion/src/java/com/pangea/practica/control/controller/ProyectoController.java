package com.pangea.practica.control.controller;

import com.pangea.practica.modelo.entidades.Proyecto;
import com.pangea.practica.control.controller.util.JsfUtil;
import com.pangea.practica.control.controller.util.PaginationHelper;
import com.pangea.practica.control.servicios.Pruebaservicio_Service;
import com.pangea.practica.modelo.bean.ProyectoFacade;
import com.pangea.practica.modelo.entidades.Cargo;
import com.pangea.practica.modelo.entidades.Empleado;

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

@ManagedBean(name = "proyectoController")
@SessionScoped
public class ProyectoController implements Serializable {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/192.168.1.102_15429/prueba/pruebaservicio.wsdl")
private Pruebaservicio_Service service;
    
    private Proyecto current;
    private DataModel items = null;
    @EJB
    private com.pangea.practica.modelo.bean.ProyectoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    /**
     * LAZYYY *
     */
    private LazyDataModel<Proyecto> lazyModel;
    private int pagIndex = 0;
    private Map<String, String> fields = new HashMap<String, String>();
    private String sortF = null;
    private boolean sortB = false;
    private int paginacion = 10;
    /**
     * FIN LAZY *
     */
    @EJB
    private com.pangea.practica.modelo.bean.EmpleadoFacade empleadoFacade;
    private List<Empleado> empleados;
    private Empleado empleadoselecionado;
    private String buscar;
    private Proyecto descripcion;
    
    public ProyectoController() {
    }
    
    public Proyecto getSelected() {
        if (current == null) {
            current = new Proyecto();
            selectedItemIndex = -1;
        }
        return current;
    }
    
    @PostConstruct    
    public void iniciar() {
        empleados = empleadoFacade.findAll();
        
        
    }
    
    private ProyectoFacade getFacade() {
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
    
    public String getBuscar() {
        return buscar;
    }
    
    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }
    
    public void busqueda() {        
        
        descripcion = getFacade().descripcion(buscar);
        
    }    

    public String prepareList() {
        recreateModel();
        return "List";
    }
    
    public String prepareView() {
        current = (Proyecto) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }
    
    public String prepareCreate() {
        current = new Proyecto();
        selectedItemIndex = -1;
        return "Create";
    }
    
    public String create() {
        try {
            current.setEmpleadoid(empleadoselecionado);
            
        com.pangea.practica.control.servicios.Proyecto insert=new com.pangea.practica.control.servicios.Proyecto();
              com.pangea.practica.control.servicios.Empleado insert2=new com.pangea.practica.control.servicios.Empleado();
              insert.setProyectoid(current.getProyectoid());
             insert.setNombre(current.getNombre());
             insert.setDescripcion(current.getDescripcion());
             insert2.setEmpleadoid(current.getEmpleadoid().getEmpleadoid());
            insert.setEmpleadoid(insert2);
           
            this.crearProyecto(insert);
            return prepareCreate();
        } catch (Exception e) {
           
            return null;
        }
    }
    
    public String prepareEdit() {
        current = (Proyecto) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }
    
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProyectoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public String destroy() {
        current = (Proyecto) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProyectoDeleted"));
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

    public ProyectoFacade getEjbFacade() {
        return ejbFacade;
    }

    public Proyecto getCurrent() {
        return current;
    }
    
    public void setCurrent(Proyecto current) {
        this.current = current;
    }
    
    public void setEjbFacade(ProyectoFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    private void recreateModel() {
        items = null;
    }
    
    private void recreatePagination() {
        pagination = null;
    }

    public Proyecto getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(Proyecto descripcion) {
        this.descripcion = descripcion;
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

    public List<Empleado> getEmpleados() {
        return empleados;
    }
    
    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }
    
    public Empleado getEmpleadoselecionado() {
        return empleadoselecionado;
    }
    
    public void setEmpleadoselecionado(Empleado empleadoselecionado) {
        this.empleadoselecionado = empleadoselecionado;
    }
    
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }
    
    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }
    
    @FacesConverter(forClass = Proyecto.class)
    public static class ProyectoControllerConverter implements Converter {
        
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProyectoController controller = (ProyectoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "proyectoController");
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
            if (object instanceof Proyecto) {
                Proyecto o = (Proyecto) object;
                return getStringKey(o.getProyectoid());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Proyecto.class.getName());
            }
        }
    }

    /**
     * INICIO *
     */
    public void inicializarLazy() {
        lazyModel = new LazyDataModel<Proyecto>() {
            public List<Proyecto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
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
    
    public LazyDataModel<Proyecto> getLazyModel() {
        if (lazyModel == null) {
            inicializarLazy();
        }
        return lazyModel;
    }
    
    public void setLazyModel(LazyDataModel<Proyecto> lazyModel) {
        this.lazyModel = lazyModel;
    }
//fin lazy
    
    @FacesConverter(forClass = Empleado.class)
    public static class EmpleadoControllerConverter implements Converter {
        
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProyectoController controller = (ProyectoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "proyectoController");
            return controller.empleadoFacade.find(getKey(value));
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
    
    public void onEdit(RowEditEvent event) {        
        System.out.println("entro a edit");
        Proyecto c = (Proyecto) event.getObject();
        getFacade().edit(c);
        
    }    
    
    public void onCancel(RowEditEvent event) {        
        System.out.println("entro a cancel");
    }    
    
    public void eliminar() {        
        System.out.println("entro a eliminar");        
        System.out.println(current.getNombre());
        Proyecto c = getFacade().find(current.getProyectoid());
        
        
        getFacade().remove(c);
        inicializarLazy();
        mostrarMensaje(1, "Mensaje", "ha sido eliminado");
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

    private void crearProyecto(com.pangea.practica.control.servicios.Proyecto registroProyecto) {
        com.pangea.practica.control.servicios.Pruebaservicio port = service.getPruebaservicioPort();
        port.crearProyecto(registroProyecto);
    }
}
