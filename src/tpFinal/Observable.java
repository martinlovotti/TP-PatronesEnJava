package tpFinal;
//Interfaz que defina protocolo de Observables
public interface Observable {
	void suscribir(Observador observador);
    void desuscribir(Observador observador);
    void notificarSubida(Muestra muestra);
    void notificarValidacion(Muestra muestra);
}
