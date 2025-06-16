package tpFinal;

public class PorTipoInsecto implements CriterioBusqueda {
    private Vinchuca tipo;

    public PorTipoInsecto(Vinchuca tipo) { this.tipo = tipo; }

    @Override
    public boolean cumple(Muestra m) {
        return m.getResultadoActual() == tipo;
    }
    
}

