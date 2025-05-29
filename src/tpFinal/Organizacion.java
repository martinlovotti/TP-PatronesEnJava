package tpFinal;

public class Organizacion {
	 private String nombre;
	    private Ubicacion ubicacion;
	    private TipoOrganizacion tipo;
	    private int cantidadDeEmpleados;

	    public Organizacion(String nombre, Ubicacion ubicacion, TipoOrganizacion tipo, int empleados) {
	        this.nombre = nombre;
	        this.ubicacion = ubicacion;
	        this.tipo = tipo;
	        this.cantidadDeEmpleados = empleados;
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
}
