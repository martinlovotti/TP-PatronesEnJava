package tpFinal;

public class EstadoMuestraProceso extends EstadoMuestra {

    @Override
    public boolean esVerificada() {
        return false;
    }
    
	@Override
	public void agregarOpinion(Vinchuca v, Usuario u, Muestra m) {
		
		if(u.isEsExperto()) {
			m.realizarOpinion(v);
			m.setEstadoMuestra(new EstadoMuestraProcesoExperto());
			//m.estadoActual = new EstadoMuestraProcesoExperto();
			//m.historial.put(v, 1);
			//m.actualizarUltimaVotacion();
			
		}else {
			m.realizarOpinion(v);
			//m.historial.put(v, (m.obtenerVotosDe(v))+1);
			//m.setOpinion(m.obtenerVinchucaConMasVotos());
			//m.actualizarUltimaVotacion();
		}
		
	}

	@Override
	public Vinchuca calcularResultado(Muestra m) {
		return m.obtenerVinchucaConMasVotos();
	}

}
