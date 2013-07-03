package com.pangea.practica.control.controller;

import com.pangea.practica.modelo.entidades.Cargo;
import com.pangea.practica.control.controller.util.JsfUtil;
import com.pangea.practica.control.controller.util.PaginationHelper;
import com.pangea.practica.control.servicios.Pruebaservicio_Service;

import com.pangea.practica.modelo.bean.CargoFacade;
import java.awt.event.ActionEvent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.xml.ws.WebServiceRef;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.event.TransferEvent;

import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.chart.BubbleChartModel;
import org.primefaces.model.chart.BubbleChartSeries;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.LatLngBounds;
import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;
import org.primefaces.model.chart.CartesianChartModel;

@ManagedBean(name = "cargoController")

@SessionScoped

public class CargoController implements Serializable {
  @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/192.168.1.102_15429/prueba/pruebaservicio.wsdl")
    private Pruebaservicio_Service service;
    
    private Cargo current;

    

   
    private DataModel items = null;
    @EJB
    private com.pangea.practica.modelo.bean.CargoFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String nombre;
    private String apellido;
     private Date date1; 
       private String text = "PrimeFaces"; 
 private String value1; 
private Integer rating1;
private Cargo descripcion;

   

      
    private DualListModel<String> cities;  
 private MindmapNode root;  

  private int count;
private BubbleChartModel bubbleModel; 
 
    private MindmapNode selectedNode;

   private String date;  
      
    private String phone;  
      
    private String phoneExt;  
      
    private String taxId;  
      
    private String ssn;  
      
    private String productKey;
    
    private String value;
private String password1;  

  
    private String password2;  
    private String password3;  
    private String password4;  
    private String password5; 
    private String buscar;

    
    private int number1;  
      
    private double number2;  
      
    private int number3;  
      
    private int number4;  
      
    private int number5;  
      
    private int number6;  
  
    private int number7;  
  private DashboardModel model; 
  
  private List<String> images;
  private List<Cargo> cargos;

    /** LAZYYY **/
    private LazyDataModel<Cargo> lazyModel;
    private int pagIndex = 0;
    private Map<String, String> fields = new HashMap<String, String>();
    private String sortF = null;
    private boolean sortB = false;
    private int paginacion = 10;
    /** FIN LAZY **/
    

    public CargoController() {
        
        nombre="jose";
        apellido="fuentes";
        createBubbleModel(); 
          root = new DefaultMindmapNode("google.com", "Google WebSite", "FFCC00", false);  
          
        MindmapNode ips = new DefaultMindmapNode("IPs", "IP Numbers", "6e9ebf", true);  
        MindmapNode ns = new DefaultMindmapNode("NS(s)", "Namespaces", "6e9ebf", true);  
        MindmapNode malware = new DefaultMindmapNode("Malware", "Malicious Software", "6e9ebf", true);  
          
        root.addNode(ips);  
        root.addNode(ns);  
        root.addNode(malware); 
        model = new DefaultDashboardModel();  
        DashboardColumn column1 = new DefaultDashboardColumn();  
        DashboardColumn column2 = new DefaultDashboardColumn();  
        DashboardColumn column3 = new DefaultDashboardColumn();  
          
        column1.addWidget("sports");  
        column1.addWidget("finance");  
          
        column2.addWidget("lifestyle");  
        column2.addWidget("weather");  
          
        column3.addWidget("politics");  
  
        model.addColumn(column1);  
        model.addColumn(column2);  
        model.addColumn(column3);
        
         
        images = new ArrayList<String>();  
        images.add("nature1.jpg");  
        images.add("nature2.jpg");  
        images.add("nature3.jpg");  
        images.add("nature4.jpg");  
     
  
        
    }

    public Cargo getSelected() {
        if (current == null) {
            current = new Cargo();
            selectedItemIndex = -1;
        }
        return current;
    }
    
    public Cargo getCurrent() {
        return current;
    }
    public void setCurrent(Cargo current) {
        this.current = current;
    }

       public MindmapNode getRoot() {
        return root;
    }

    public void setRoot(MindmapNode root) {
        this.root = root;
    }
    private CargoFacade getFacade() {
        return ejbFacade;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

     public MindmapNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(MindmapNode selectedNode) {
        this.selectedNode = selectedNode;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }
    
     public Integer getRating1() {
        return rating1;
    }

    public void setRating1(Integer rating1) {
        this.rating1 = rating1;
    }
    
     public DualListModel<String> getCities() {
        return cities;
    }
     
      public Cargo getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(Cargo descripcion) {
        this.descripcion = descripcion;
    }
         public void save() {  
        addMessage("Data saved");  
    }  
      
    public void update2() {  
        addMessage("Data updated");  
    }  
      
    public void delete() {  
        addMessage("Data deleted");  
    }  
      public BubbleChartModel getBubbleModel() {
        return bubbleModel;
    }
       public void cargar()
    {
        descripcion=getFacade().descripcion(buscar);
        
    }
      
      public void busqueda() {  

        descripcion = getFacade().descripcion(buscar);
 
    } 

    public void setBubbleModel(BubbleChartModel bubbleModel) {
        this.bubbleModel = bubbleModel;
    }
       public void handleToggle(ToggleEvent event) {  
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Fieldset Toggled", "Visibility:" + event.getVisibility());  
  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
    public void addMessage(String summary) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }

    public void setCities(DualListModel<String> cities) {
        this.cities = cities;
    }
     public void handleFileUpload(FileUploadEvent event) {  
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    } 
 public void PickListBean() {  
        
          
        //Cities  
        List<String> citiesSource = new ArrayList<String>();  
        List<String> citiesTarget = new ArrayList<String>();  
          
        citiesSource.add("Istanbul");  
        citiesSource.add("Ankara");  
        citiesSource.add("Izmir");  
        citiesSource.add("Antalya");  
        citiesSource.add("Bursa");  
          
        cities = new DualListModel<String>(citiesSource, citiesTarget);  
    }  
 
  public void onTransfer(TransferEvent event) {  
        StringBuilder builder = new StringBuilder();  
        
          
        FacesMessage msg = new FacesMessage();  
        msg.setSeverity(FacesMessage.SEVERITY_INFO);  
        msg.setSummary("Items Transferred");  
        msg.setDetail(builder.toString());  
          
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
    
     public void onStateChange(StateChangeEvent event) {  
        LatLngBounds bounds = event.getBounds();  
        int zoomLevel = event.getZoomLevel();  
          
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Zoom Level", String.valueOf(zoomLevel)));  
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Center", event.getCenter().toString()));  
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "NorthEast", bounds.getNorthEast().toString()));  
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "SouthWest", bounds.getSouthWest().toString()));  
    }  
    public void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
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

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Cargo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Cargo();
        selectedItemIndex = -1;
        return "Create";
    }

     public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
       public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneExt() {
        return phoneExt;
    }

    public void setPhoneExt(String phoneExt) {
        this.phoneExt = phoneExt;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

      public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getPassword3() {
        return password3;
    }

    public void setPassword3(String password3) {
        this.password3 = password3;
    }

    public String getPassword4() {
        return password4;
    }

    public void setPassword4(String password4) {
        this.password4 = password4;
    }

    public String getPassword5() {
        return password5;
    }

    public void setPassword5(String password5) {
        this.password5 = password5;
    }
    
    public String getBuscar() {
        return buscar;
    }

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }
    
     public int getNumber1() {
        return number1;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }
    public DashboardModel getModel() {
        return model;
    }

    public void setModel(DashboardModel model) {
        this.model = model;
    }
    

    public double getNumber2() {
        return number2;
    }

    public void setNumber2(double number2) {
        this.number2 = number2;
    }

    public int getNumber3() {
        return number3;
    }

    public void setNumber3(int number3) {
        this.number3 = number3;
    }

    public int getNumber4() {
        return number4;
    }

    public void setNumber4(int number4) {
        this.number4 = number4;
    }

    public int getNumber5() {
        return number5;
    }

    public void setNumber5(int number5) {
        this.number5 = number5;
    }

    public int getNumber6() {
        return number6;
    }

    public void setNumber6(int number6) {
        this.number6 = number6;
    }

    public int getNumber7() {
        return number7;
    }

    public void setNumber7(int number7) {
        this.number7 = number7;
    }
    
     public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    
     private void createBubbleModel() {  
        bubbleModel = new BubbleChartModel();  
  
        bubbleModel.add(new BubbleChartSeries("Acura", 70, 183,55));  
        bubbleModel.add(new BubbleChartSeries("Alfa Romeo", 45, 92, 36));  
        bubbleModel.add(new BubbleChartSeries("AM General", 24, 104, 40));  
        bubbleModel.add(new BubbleChartSeries("Bugatti", 50, 123, 60));  
        bubbleModel.add(new BubbleChartSeries("BMW", 15, 89, 25));  
        bubbleModel.add(new BubbleChartSeries("Audi", 40, 180, 80));  
        bubbleModel.add(new BubbleChartSeries("Aston Martin", 70, 70, 48));  
    }  
    public String create() {
      
        try {
           com.pangea.practica.control.servicios.Cargo insert=new com.pangea.practica.control.servicios.Cargo();
           insert.setCargoid(current.getCargoid());
           insert.setDescripcion(current.getDescripcion());
           insert.setNombre(current.getNombre());
           
            this.crearCargo(insert);
             return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("Error al crear Cargo"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Cargo) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CargoUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Cargo) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CargoDeleted"));
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

    public void increment() {  
        count++;  
    } 
      public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
       
    private void addMessage2(FacesMessage message) {  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    } 
    
     public void handleReorder(DashboardReorderEvent event) {  
        FacesMessage message2 = new FacesMessage();  
        message2.setSeverity(FacesMessage.SEVERITY_INFO);  
        message2.setSummary("Reordered: " + event.getWidgetId());  
        message2.setDetail("Item index: " + event.getItemIndex() + ", Column index: " + event.getColumnIndex() + ", Sender index: " + event.getSenderColumnIndex());  
          
        addMessage2(message2);  
    }  
      
      

     
     
         public void onNodeSelect(SelectEvent event) {  
        MindmapNode node = (MindmapNode) event.getObject();  
          
        //populate if not already loaded  
        if(node.getChildren().isEmpty()) {  
            Object label = node.getLabel();  
  
            if(label.equals("NS(s)")) {  
                for(int i = 0; i < 25; i++) {  
                    node.addNode(new DefaultMindmapNode("ns" + i + ".google.com", "Namespace " + i + " of Google", "82c542"));  
                }  
            }  
            else if(label.equals("IPs")) {  
                for(int i = 0; i < 18; i++) {  
                    node.addNode(new DefaultMindmapNode("1.1.1."  + i, "IP Number: 1.1.1." + i, "fce24f"));  
                }   
  
            }  
            else if(label.equals("Malware")) {  
                for(int i = 0; i < 18; i++) {  
                    String random = UUID.randomUUID().toString();  
                    node.addNode(new DefaultMindmapNode("Malware-"  + random, "Malicious Software: " + random, "3399ff", false));  
                }  
            }  
        }  
          
    }  
      
    public void onNodeDblselect(SelectEvent event) {  
        this.selectedNode = (MindmapNode) event.getObject();          
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
            CargoController controller = (CargoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cargoController");
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
            if (object instanceof Cargo) {
                Cargo o = (Cargo) object;
                return getStringKey(o.getCargoid());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Cargo.class.getName());
            }
        }
    }
    
    /** INICIO **/
    public void inicializarLazy() {
        lazyModel = new LazyDataModel<Cargo>() {

       
            public List<Cargo> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,String> filters) {
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

    public LazyDataModel<Cargo> getLazyModel() {
        if (lazyModel == null) {
            inicializarLazy();
        }
        return lazyModel;
    }

    public void setLazyModel(LazyDataModel<Cargo> lazyModel) {
        this.lazyModel = lazyModel;
    }
//fin lazy
    
    public void onEdit(RowEditEvent event) {  
         System.out.println("entro a edit");
         Cargo c=(Cargo) event.getObject();
         getFacade().edit(c);
          
    }  
      
    public void onCancel(RowEditEvent event) {  
        System.out.println("entro a cancel");
    }  
    
    public void eliminar() {  
       System.out.println("entro a eliminar");  
       System.out.println(current.getNombre());
       Cargo c=getFacade().find(current.getCargoid());
       
      if(c.getEmpleadoList().size()<1) {
         c.setEmpleadoList(null);
       getFacade().remove(c);
       inicializarLazy();
       mostrarMensaje( 1,"Mensaje","ha sido eliminado");
      }
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

    private void crearCargo(com.pangea.practica.control.servicios.Cargo registroCargo) {
        com.pangea.practica.control.servicios.Pruebaservicio port = service.getPruebaservicioPort();
        port.crearCargo(registroCargo);
    }

 
}
