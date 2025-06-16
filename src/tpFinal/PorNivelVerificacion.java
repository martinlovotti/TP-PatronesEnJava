package tpFinal;

public class PorNivelVerificacion implements CriterioBusqueda {
    private boolean verificada;      // true = verificada, false = solo votada

    public PorNivelVerificacion(boolean verificada) { this.verificada = verificada; }

    @Override
    public boolean cumple(Muestra m) {
        return verificada
               ? m.getEstadoActual() instanceof EstadoMuestraProcesoVerificado
               : m.getEstadoActual() instanceof EstadoMuestraProceso;
    }
}