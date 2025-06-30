package tpFinal;

import java.util.ArrayList;
import java.util.List;

public class EstadoMuestraProcesoExperto extends EstadoMuestra {
	private List<Vinchuca> opinionesExpertos; 
	//Para contabilizar las opiniones de expertos
	
	public EstadoMuestraProcesoExperto() {
		this.opinionesExpertos = new ArrayList<>();
	}
	
	private void addOpinion(Vinchuca v) {
		this.opinionesExpertos.add(v);
	}
	
    @Override
    public boolean esVerificada() {
        return false;
    }
    
	@Override
	public void agregarOpinion(Vinchuca v, Usuario u, Muestra m) {
		// TODO Auto-generated method stub
		if(u.isEsExperto() && m.fueVotado(v)) {
			//m.historial.put(v, (m.obtenerVotosDe(v))+1);
			m.realizarOpinion(v);
			addOpinion(v);
			calcularResultado(m);
			m.setEstadoMuestra(new EstadoMuestraProcesoVerificado());
			//m.opinion = m.obtenerVinchucaConMasVotos();
			//m.actualizarUltimaVotacion();
		}else{
			if(u.isEsExperto()) {
				addOpinion(v);
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
