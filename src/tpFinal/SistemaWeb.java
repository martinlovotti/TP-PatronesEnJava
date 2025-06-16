package tpFinal;

import java.util.ArrayList;
import java.util.List;

public class SistemaWeb {
	private AdministradorDeZonas adminZonas;
    private AdministradorDeMuestras adminMuestras;
    private List<Organizacion> organizaciones;
    
    public SistemaWeb(AdministradorDeZonas adminZonas, AdministradorDeMuestras adminMuestras) {
        this.adminZonas = adminZonas;
        this.adminMuestras = adminMuestras;
        this.organizaciones = new ArrayList<>();
    }
    
    public void suscribeOrganizacionAZona(Organizacion o, zonaDeCobertura z) {
    	z.suscribir(o); //Delega a zonaDeCobertura
    }
    
    public void registrarOrganizacion(Organizacion org) {
        if (!organizaciones.contains(org)) {
            organizaciones.add(org);
        }
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
    
    public List<Muestra> buscar(CriterioBusqueda c){
    	return adminMuestras.buscar(c);
    }

}
