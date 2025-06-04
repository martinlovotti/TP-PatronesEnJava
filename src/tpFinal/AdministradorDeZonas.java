package tpFinal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdministradorDeZonas {
	private List<zonaDeCobertura> zonas;

    public AdministradorDeZonas() {
        this.zonas = new ArrayList<>();
    }

    public void registrarZona(zonaDeCobertura zona) {
        zonas.add(zona);
    }

    public void nuevaMuestra(Muestra muestra) {
        for (zonaDeCobertura zona : zonas) {
            if (zona.contieneMuestra(muestra)) {
                zona.agregarMuestra(muestra);
            }
        }
    }
    
    
    //Desde el sitioWeb le llega este msj y se queda con las zona y le avisa de la verificacion
    public void notificarValidacion(Muestra muestra) {
        for (zonaDeCobertura zona : zonas) {
            if (zona.contieneMuestra(muestra)) {
                zona.muestraValidada(muestra);  
            }
        }
    }

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
}
