package tpFinal;

public interface Observador {
	void notificarSubida(Muestra m, zonaDeCobertura z);
	void notificarValidacion(Muestra m, zonaDeCobertura z);
}
