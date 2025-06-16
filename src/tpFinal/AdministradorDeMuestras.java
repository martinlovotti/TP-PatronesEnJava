package tpFinal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdministradorDeMuestras {
	private List<Muestra> muestras;

    public AdministradorDeMuestras() {
        this.muestras = new ArrayList<>();
    }

    public void registrarMuestra(Muestra muestra) {
        this.muestras.add(muestra);
    }

    public List<Muestra> getTodas() {
        return new ArrayList<>(muestras); // Para evitar modificación externa
    }

    public List<Muestra> muestrasCercanas(Ubicacion ubicacion, double distanciaKm) {
        return muestras.stream()
                .filter(m -> m.getUbicacion().distanciaA(ubicacion) <= distanciaKm)
                .collect(Collectors.toList());
    }

    public List<Muestra> muestrasPorUsuario(int idUsuario) {
        return muestras.stream()
                .filter(m -> m.identificacion() == idUsuario)
                .collect(Collectors.toList());
    }

	public List<Muestra> buscar(CriterioBusqueda c) {
		// TODO Auto-generated method stub
		return c.filtrar(this.getTodas());
	}

}
