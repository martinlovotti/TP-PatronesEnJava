package tpFinal;

public class EstadoMuestraProcesoVerificado extends EstadoMuestra {

    @Override
    public boolean esVerificada() {
        return true;
    }
    
	@Override
	public void agregarOpinion(Vinchuca v, Usuario u, Muestra m) {
		System.out.print("No se puede votar, muestra verificada");
	}

	@Override
	public Vinchuca calcularResultado(Muestra m) {
		return m.getOpinion();
	}

}
