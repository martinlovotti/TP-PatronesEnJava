package tpFinal;

//Clase abstracta del estado usuario que define el protocolo
public abstract class EstadoUsuario {
	public abstract void SubirMuestra(Muestra m, Usuario u);
	public abstract void opinar(Muestra m, Vinchuca v, Usuario u);
	
}
