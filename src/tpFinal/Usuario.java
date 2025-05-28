package tpFinal;

import java.time.LocalDate;
import java.util.HashMap;

public abstract class Usuario {
	private int id;
	private boolean esExpertoValidado;
	private boolean esExperto;
	private EstadoUsuario estado;
	HashMap<Muestra, LocalDate> envios;
	HashMap<Muestra, LocalDate> opiniones;
	
    public Usuario(int id, boolean esExpertoValidado) {
        this.id = id;
        this.opiniones = new HashMap<>();
        this.envios = new HashMap<>();
        this.esExpertoValidado = esExpertoValidado;
        if (esExpertoValidado) {
            this.estado = new EstadoUsuarioExpertoValidado();
        } else {
            this.estado = new EstadoUsuarioBasico();
        }
    }
    
	
	public void SubirMuestra(Muestra m) {
		estado.SubirMuestra(m,this);
	}
	
	public void opinar(Muestra m, Vinchuca v) {
		estado.opinar(m,v,this);
	}
	
	
	public void evaluarEstado(LocalDate fechaActual) {
		if (esExpertoValidado) return; // si es experto validado el usuario nunca va a cambiar
		
	    long enviosRecientes = envios.values().stream() // cuenta la cantidad de envios 
	            .filter(fecha -> !fecha.isBefore(fechaActual.minusDays(30)))
	            .count();

	    long opinionesRecientes = opiniones.values().stream() // cuenta la cantidad de opiniones
	            .filter(fecha -> !fecha.isBefore(fechaActual.minusDays(30)))
	            .count();
	    
        if (enviosRecientes > 10 && opinionesRecientes > 20) { // cambia el estado dependiendo su actividad
            this.estado = new EstadoUsuarioExperto();
        } else {
            this.estado = new EstadoUsuarioBasico();
        }
	}
	
	
    public HashMap<Muestra, LocalDate> getOpiniones() { 
    	return opiniones; 
    }
    
    public HashMap<Muestra, LocalDate> getEnvios() { 
    	return envios; 
    }
    
    
    public boolean puedeOpinarSobre(Muestra muestra) {
        boolean esOtraPersona = muestra.getPropietarioId() != this.id;
        boolean noHaOpinando = opiniones.containsKey(muestra) == false;
        return esOtraPersona && noHaOpinando;
    }
	
    
    
    public boolean isEsExperto() {
		return esExperto;
	}
    
    
	public boolean isesExpertoValidado() {
		return esExpertoValidado;
	}
	

	
	public int getId() {
		return id;
	}
	
}
