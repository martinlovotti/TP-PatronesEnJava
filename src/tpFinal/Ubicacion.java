package tpFinal;

import java.util.List;
import java.util.stream.Collectors;

public class Ubicacion {
	private double latitud;
    private double longitud;

    public Ubicacion(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public double distanciaA(Ubicacion otra) {
        // fórmula Haversine para calcular distancia en km
        double R = 6371; // Radio de la Tierra en km
        double dLat = Math.toRadians(otra.latitud - this.latitud);
        double dLon = Math.toRadians(otra.longitud - this.longitud);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(this.latitud)) * Math.cos(Math.toRadians(otra.latitud)) *
                   Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }
    
    public List<Ubicacion> ubicacionesCercanas(Ubicacion referencia, List<Ubicacion> lista, double radioKm) {
        return lista.stream()
            .filter(u -> referencia.distanciaA(u) <= radioKm)
            .collect(Collectors.toList());
    }
    
    public List<Muestra> cercanasALaMuestra(Muestra referencia, List<Muestra> muestras, double radioKm) {
        return muestras.stream()
            .filter(m -> referencia.ubicacion.distanciaA(m.ubicacion) <= radioKm)
            .collect(Collectors.toList());
    }
}
