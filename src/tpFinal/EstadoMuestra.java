package tpFinal;

public abstract class EstadoMuestra {
	public abstract void agregarOpinion(Vinchuca v, Usuario u, Muestra m);
	public abstract boolean esVerificada();
	public abstract Vinchuca calcularResultado(Muestra m);
}
