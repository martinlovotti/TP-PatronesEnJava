package tpFinal;

import java.util.HashMap;
import java.util.Map;



public class Muestra {
    Vinchuca opinion;
    Ubicacion ubicacion;
    private int usuario;
    private Usuario propietario;
    HashMap<Vinchuca, Integer> historial;
    EstadoMuestra estadoActual;




    public int identificacion() {
        return usuario;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }


    public void agregarOpinion(Vinchuca v, Usuario u, Muestra m) {
        estadoActual.agregarOpinion(v,u,m);
    }

    public Vinchuca getResultadoActual() {
        return opinion;
    }

    public boolean fueVotado(Vinchuca v) {
        return (historial.get(v) > 0);
    }

    public int obtenerVotosDe(Vinchuca v) {
        return historial.get(v);
    }

    public void ponerA(){
        for (Vinchuca v : Vinchuca.values()) {
            historial.put(v, 0);
        }
    }

    public Vinchuca obtenerVinchucaConMasVotos() {
        Vinchuca resultado = null;
        int maxVotos = -1;

        for (Map.Entry<Vinchuca, Integer> entrada : historial.entrySet()) {
            if (entrada.getValue() > maxVotos) {
                maxVotos = entrada.getValue();
                resultado = entrada.getKey();
            }
        }

        return resultado;
    }

    public Muestra(Vinchuca opinion, Ubicacion ubicacion, Usuario propietario) {
        this.opinion = opinion;
        this.ubicacion = ubicacion;
        this.usuario = propietario.getId();
        this.setPropietario(propietario);
        this.historial = new HashMap<>();
        this.ponerA();
        this.estadoActual = new EstadoMuestraProceso();
        this.agregarOpinion(opinion, propietario, this);
    }



    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }



    public int getPropietarioId() {
        return this.propietario.getId();
    }

}