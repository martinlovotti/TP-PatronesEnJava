package tpFinal;

import java.time.LocalDate;

public class PorFechaUltimaVotacionPosterior implements CriterioBusqueda{
	private LocalDate fecha;

    public PorFechaUltimaVotacionPosterior(LocalDate fecha) { this.fecha = fecha; }

    @Override
    public boolean cumple(Muestra m) {
        return m.getFechaUltimaVotacion().isAfter(fecha);
    }
}
