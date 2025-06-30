package tpFinal;

import java.util.List;

public class EstadoMuestraProcesoExperto extends EstadoMuestra {
	private List<Vinchuca> opinionesExpertos;
	
    @Override
    public boolean esVerificada() {
        return false;
    }
    
	@Override
	public void agregarOpinion(Vinchuca v, Usuario u, Muestra m) {
		// TODO Auto-generated method stub
		if(u.isEsExperto() && m.fueVotado(v)) {
			//m.historial.put(v, (m.obtenerVotosDe(v))+1);
			m.setEstadoMuestra(new EstadoMuestraProcesoVerificado());
			m.realizarOpinion(v);
			calcularResultado(m);
			//m.opinion = m.obtenerVinchucaConMasVotos();
			//m.actualizarUltimaVotacion();
		}else{
			if(u.isEsExperto()) {
				m.realizarOpinion(v);
				//m.historial.put(v, (m.obtenerVotosDe(v))+1);
				//m.actualizarUltimaVotacion();
			}else {
			System.out.print("No se puede votar"); 
			}
		}
	}

	@Override
	public void calcularResultado(Muestra m) {
	// Aca se debe calcular con la lista de opinionesExpertos y hacerle el setOpinion a esa muestra
	}
}
