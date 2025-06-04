package tpFinal;

public interface Observable {
	void suscribir(Observador observador);
    void desuscribir(Observador observador);
    void notificarSubida(Muestra muestra);
    void notificarValidacion(Muestra muestra);
}
