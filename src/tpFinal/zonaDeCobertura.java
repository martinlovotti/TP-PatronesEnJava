package tpFinal;

import java.util.List;
import java.util.stream.Collectors;

public class zonaDeCobertura {
	private String nombre;
    private Ubicacion epicentro;
    private double radioKm;
    private List<Muestra> muestras;

    public zonaDeCobertura(String nombre, Ubicacion epicentro, double radioKm, List<Muestra> muestras) {
        this.nombre = nombre;
        this.epicentro = epicentro;
        this.radioKm = radioKm;
        this.muestras = muestras;
    }

    public String getNombre() {
        return nombre;
    }

    public Ubicacion getEpicentro() {
        return epicentro;
    }

    public double getRadioKm() {
        return radioKm;
    }
    
    public void agregarMuestra(Muestra m) {
        this.muestras.add(m);
    }

    public List<Muestra> getMuestrasDentro() {
        return muestras.stream()
            .filter(m -> epicentro.distanciaA(m.getUbicacion()) <= radioKm)
            .collect(Collectors.toList());
    }

    public boolean contieneMuestra(Muestra m) {
        return epicentro.distanciaA(m.getUbicacion()) <= radioKm;
    }

    public boolean seSolapaCon(zonaDeCobertura otra) {
        double distanciaEntreCentros = this.epicentro.distanciaA(otra.epicentro);
        return distanciaEntreCentros <= (this.radioKm + otra.radioKm);
    }
}
