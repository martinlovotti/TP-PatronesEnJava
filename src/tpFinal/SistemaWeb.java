package tpFinal;

import java.util.List;

public class SistemaWeb {
	private AdministradorDeZonas adminZonas;
    private AdministradorDeMuestras adminMuestras;

    public SistemaWeb() {
        this.adminZonas = new AdministradorDeZonas();
        this.adminMuestras = new AdministradorDeMuestras();
    }

    public void recibirMuestra(Muestra muestra) {
        adminMuestras.registrarMuestra(muestra);
        adminZonas.nuevaMuestra(muestra);
    }
    
    
    //Se invoca cuando un usuario opina y la muestra fue verificada
    public void recibirVerificacion(Muestra m) {
    	this.adminZonas.notificarValidacion(m);//Avisa al admin de zonas
    }

    public List<Muestra> muestrasCercanas(Ubicacion ubicacion, double distancia) {
        return adminMuestras.muestrasCercanas(ubicacion, distancia);
    }

    public List<zonaDeCobertura> zonasQueContienen(Muestra muestra) {
        return adminZonas.getZonasQueContienen(muestra);
    }

    public void registrarZona(zonaDeCobertura zona) {
        adminZonas.registrarZona(zona);
    }

}
