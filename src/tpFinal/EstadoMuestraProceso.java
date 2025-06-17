package tpFinal;

public class EstadoMuestraProceso extends EstadoMuestra {

    @Override
    public boolean esVerificada() {
        return false;
    }
    
	@Override
	public void agregarOpinion(Vinchuca v, Usuario u, Muestra m) {
		
		if(u.isEsExperto()) {
			m.estadoActual = new EstadoMuestraProcesoExperto();
			m.ponerA();
			m.historial.put(v, 1);
			m.actualizarUltimaVotacion();
		}else {
			m.historial.put(v, (m.obtenerVotosDe(v))+1);
			m.opinion = m.obtenerVinchucaConMasVotos();
			m.actualizarUltimaVotacion();
		}
		
	}

}
