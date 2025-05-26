package tpFinal;

import java.time.LocalDate;
import java.util.HashMap;

public abstract class Usuario {
	private int id;
	private boolean esExperto;
	private EstadoUsuario estado;
	HashMap<Muestra, LocalDate> envios;
	HashMap<Muestra, LocalDate> opiniones;
	
	public void SubirMuestra(Muestra m) {
		estado.SubirMuestra(m,this);
	}
	
	public void opinar(Muestra m, Vinchuca v) {
		estado.opinar(m,v,this);
	}
	
	public int cantidadDeOpiniones(){
		return 0; //Implementar
	};
	
	public int cantidadDeEnvios() {
		return 0; //Implementar
	}
	
	public boolean isEsExperto() {
		return esExperto;
	}
	
	public void setEsExperto(boolean esExperto) {
		this.esExperto = esExperto;
	}
	
	public int getId() {
		return id;
	}
	
}
