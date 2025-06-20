package tpFinal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdministradorDeZonas {
	private List<zonaDeCobertura> zonas;
	
	//Constructor
    public AdministradorDeZonas() {
        this.zonas = new ArrayList<>();
    }
    
    //Add zona
    public void registrarZona(zonaDeCobertura zona) {
        zonas.add(zona);
    }

    
    //Desde el sitioWeb le llega este msj y se queda con las zona y le avisa de la verificacion
    public void notificarValidacion(Muestra muestra) {
        for (zonaDeCobertura zona : zonas) {
            if (zona.contieneMuestra(muestra)) {
                zona.muestraValidada(muestra);  
            }
        }
    }
    
    //Métodos de zonas
    public List<zonaDeCobertura> getZonasQueContienen(Muestra muestra) {
        return zonas.stream()
                .filter(z -> z.contieneMuestra(muestra))
                .collect(Collectors.toList());
    }

    public List<zonaDeCobertura> zonasSolapadasCon(zonaDeCobertura zc) {
        return zonas.stream()
                .filter(z -> !z.equals(zc) && z.seSolapaCon(zc))
                .collect(Collectors.toList());
    }
    
    public void nuevaMuestra(Muestra muestra) {
        for (zonaDeCobertura zona : zonas) {
            if (zona.contieneMuestra(muestra)) {
                zona.agregarMuestra(muestra);
            }
        }
    }
}
