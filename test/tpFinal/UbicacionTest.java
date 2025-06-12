package tpFinal;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class UbicacionTest {
	Ubicacion referenciaUbicacion;
    Ubicacion ubicacionCerca;
    Ubicacion ubicacionLejos;

    Muestra muestraReferencia;
    Muestra muestraCerca;
    Muestra muestraLejos;

    List<Ubicacion> listaUbicaciones;
    List<Muestra> listaMuestras;
    
    @BeforeEach
    void setUp() {
        // Ubicaciones reales (usadas en múltiples tests)
        referenciaUbicacion = new Ubicacion(-34.6037, -58.3816); // CABA
        ubicacionCerca = new Ubicacion(-34.61, -58.38);           // Cerca (menos de 10km)
        ubicacionLejos = new Ubicacion(-34.9214, -57.9544);       // Lejos (La Plata)

        listaUbicaciones = Arrays.asList(ubicacionCerca, ubicacionLejos);

        // Mocks de Muestra con ubicaciones simuladas
        muestraReferencia = mock(Muestra.class);
        muestraReferencia.ubicacion = referenciaUbicacion;

        muestraCerca = mock(Muestra.class);
        muestraCerca.ubicacion = ubicacionCerca;

        muestraLejos = mock(Muestra.class);
        muestraLejos.ubicacion = ubicacionLejos;

        listaMuestras = Arrays.asList(muestraCerca, muestraLejos);
    }
    
    @Test
    public void testGetLatitudYLongitud() {
        
        assertEquals(-34.6037, referenciaUbicacion.getLatitud(), 0.0001);
        assertEquals(-58.3816, referenciaUbicacion.getLongitud(), 0.0001);
    }
    
    @Test
    public void testDistanciaAdevuelveDistanciaCorrecta() {
        double distancia = referenciaUbicacion.distanciaA(ubicacionLejos);
        assertTrue(distancia > 50 && distancia < 70); // Aproximadamente 58 km
    }
    
    @Test
    public void testUbicacionesCercanasDevuelveSoloLasQueEstanEnRadio() {
        Ubicacion referencia = new Ubicacion(-34.6037, -58.3816); // CABA
        Ubicacion cerca = new Ubicacion(-34.61, -58.38);           // Cerca
        Ubicacion lejos = new Ubicacion(-34.9214, -57.9544);       // La Plata

        List<Ubicacion> lista = Arrays.asList(cerca, lejos);
        List<Ubicacion> resultado = referencia.ubicacionesCercanas(referencia, lista, 10); // 10 km

        assertTrue(resultado.contains(cerca));
        assertFalse(resultado.contains(lejos));
    }

    @Test
    public void testCercanasALaMuestraDevuelveSoloMuestrasCercanas() {
        List<Muestra> muestras = Arrays.asList(muestraCerca, muestraLejos);
        List<Muestra> resultado = referenciaUbicacion.cercanasALaMuestra(muestraReferencia, muestras, 10);

        assertTrue(resultado.contains(muestraCerca));
        assertFalse(resultado.contains(muestraLejos));
    }
}
