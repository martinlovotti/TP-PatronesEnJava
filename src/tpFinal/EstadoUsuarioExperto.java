package tpFinal;

import java.time.LocalDate;

public class EstadoUsuarioExperto extends EstadoUsuario {
//Implementaciones propias de UsuarioExperto
	@Override
	public void SubirMuestra(Muestra m, Usuario u) {
		u.getEnvios().put(m, LocalDate.now());
	}

	@Override
	public void opinar(Muestra m, Vinchuca v, Usuario u) {
        if (u.puedeOpinarSobre(m)) {
            u.getOpiniones().put(m, LocalDate.now());
            m.agregarOpinion(v, u, m);
        } else {
        	System.out.println("El usuario no puede opinar sobre esta muestra.");
        }	
	}
}
