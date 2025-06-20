package tpFinal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdministradorDeMuestras {
	private List<Muestra> muestras;
	
	//Constructor
    public AdministradorDeMuestras() {
        this.muestras = new ArrayList<>();
    }
    
    //Getters
    public List<Muestra> getTodas() {
        return new ArrayList<>(muestras); // Para evitar modificación externa
    }
    
    
    //Add Muestras
    public void registrarMuestra(Muestra muestra) {
        this.muestras.add(muestra);
    }

    
    //Métodos de filtrado en la Lista
    
    public List<Muestra> muestrasCercanas(Ubicacion ubicacion, double distanciaKm) {
        return muestras.stream()
                .filter(m -> m.getUbicacion().distanciaA(ubicacion) <= distanciaKm)
                .toList();
    }

    public List<Muestra> muestrasPorUsuario(int idUsuario) {
        return muestras.stream()
                .filter(m -> m.identificacion() == idUsuario)
                .toList();
    }
    
    //Busqueda Composite
	public List<Muestra> buscar(CriterioBusqueda c) {
		// TODO Auto-generated method stub
		return c.filtrar(this.getTodas());
	}

}
