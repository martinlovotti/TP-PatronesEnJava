package tpFinal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
		if(u.isEsExperto() && m.fueVotado(v)) {
			//m.historial.put(v, (m.obtenerVotosDe(v))+1);
			addOpinion(v);
			m.realizarOpinion(v);
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
	public Vinchuca calcularResultado(Muestra m) {
		// Aca se debe calcular con la lista de opinionesExpertos y hacerle el setOpinion a esa muestra
		return this.opinionesExpertos.stream()
		        .collect(Collectors.groupingBy(
		            Function.identity(),
		            Collectors.counting()
		        ))
		        .entrySet().stream()
		        .max(Map.Entry.comparingByValue())
		        .map(Map.Entry::getKey)
		        .orElse(null);
	}
}
