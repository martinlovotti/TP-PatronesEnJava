package tpFinal;

public class Organizacion implements Observador{
	 private String nombre;
	 private Ubicacion ubicacion;
	 private TipoOrganizacion tipo;
	 private int cantidadDeEmpleados;
	 //Se consideró que haya una funcionalidad distinta si hay una nueva muestra o validacion
	 private FuncionalidadExterna funcionalidadNuevaMuestra;
	 private FuncionalidadExterna funcionalidadNuevaValidacion;
	 
	 //Constructor
	 public Organizacion(String nombre, Ubicacion ubicacion, TipoOrganizacion tipo, int empleados) {
	        this.nombre = nombre;
	        this.ubicacion = ubicacion;
	        this.tipo = tipo;
	        this.cantidadDeEmpleados = empleados;
	  }
	  
	 //Getters y Setters
	 public void setFuncionalidadNuevaMuestra(FuncionalidadExterna funcionalidad) {
	     this.funcionalidadNuevaMuestra = funcionalidad;
	 }

	 public void setFuncionalidadNuevaValidacion(FuncionalidadExterna funcionalidad) {
	     this.funcionalidadNuevaValidacion = funcionalidad;
	 }

	 public String getNombre() {
	     return nombre;
	 }

	 public Ubicacion getUbicacion() {
	     return ubicacion;
	 }

     public TipoOrganizacion getTipo() {
         return tipo;
     }

     public int getCantidadDeEmpleados() {
         return cantidadDeEmpleados;
     }
	    
	 //Métodos de Organización
	 
     //Los metodos de realizarFuncionalidad se invocan cada vez que haya una notificación
     public void realizarFuncionalidadNuevaMuestra(zonaDeCobertura z, Muestra m) {
	  	funcionalidadNuevaMuestra.nuevoEvento(this,  z,  m);
	  }
	    
	  public void realizarFuncionalidadNuevaValidacion(zonaDeCobertura z, Muestra m) {
	  	funcionalidadNuevaValidacion.nuevoEvento(this, z, m);
	  }
	    
	  @Override
	  public void notificarSubida(Muestra muestra, zonaDeCobertura zona) {
	      System.out.println("[" + nombre + "] recibió notificación de muestra en " + zona.getNombre());
	      this.realizarFuncionalidadNuevaMuestra(zona, muestra); 
	  }

	  @Override
	  public void notificarValidacion(Muestra muestra, zonaDeCobertura zona) {
	      System.out.println("[" + nombre + "] recibió notificación de validación en " + zona.getNombre());
	      this.realizarFuncionalidadNuevaValidacion(zona, muestra);
	  }
}
