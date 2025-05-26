package tpFinal;

public class EstadoMuestraProcesoVerificado extends EstadoMuestra {

	@Override
	public void agregarOpinion(Vinchuca v, Usuario u, Muestra m) {
		System.out.print("No se puede votar, muestra verificada");
	}

}
