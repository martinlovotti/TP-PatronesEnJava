package tpFinal;

import java.time.LocalDate;

public class EstadoUsuarioBasico extends EstadoUsuario{
//Implementaciones propias de usuarioBasico
	@Override
	public void SubirMuestra(Muestra m, Usuario u) {
		u.getEnvios().put(m, LocalDate.now());
		u.evaluarEstado(LocalDate.now());
	}

	@Override
	public void opinar(Muestra m, Vinchuca v, Usuario u) {
        if (u.puedeOpinarSobre(m)) {
            u.getOpiniones().put(m, LocalDate.now());
            m.agregarOpinion(v, u, m);
            u.evaluarEstado(LocalDate.now());
        } else {
        	System.out.println("El usuario no puede opinar sobre esta muestra.");
        }
	}

}
