package tpFinal;

import java.time.LocalDate;

public class PorFechaCreacionPosterior implements CriterioBusqueda {
	private LocalDate fecha;

    public PorFechaCreacionPosterior(LocalDate fecha) { this.fecha = fecha; }

    @Override
    public boolean cumple(Muestra m) {
        return m.getFechaCreacion().isAfter(fecha);
    }
}
