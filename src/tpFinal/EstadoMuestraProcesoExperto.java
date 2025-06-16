package tpFinal;

public class EstadoMuestraProcesoExperto extends EstadoMuestra {

	@Override
	public void agregarOpinion(Vinchuca v, Usuario u, Muestra m) {
		// TODO Auto-generated method stub
		if(u.isEsExperto() && m.fueVotado(v)) {
			m.historial.put(v, (m.obtenerVotosDe(v))+1);
			m.estadoActual = new EstadoMuestraProcesoVerificado();
			m.opinion = m.obtenerVinchucaConMasVotos();
			m.actualizarUltimaVotacion();
		}else{
			if(u.isEsExperto()) {
				m.historial.put(v, (m.obtenerVotosDe(v))+1);
				m.actualizarUltimaVotacion();
			}else {
			System.out.print("No se puede votar"); 
			}
		}
	}
}
