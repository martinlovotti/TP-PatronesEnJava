package tpFinal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class zonaDeCoberturaTest {
	zonaDeCobertura zona;
    Ubicacion epicentroMock;
    Muestra muestraMock;
    Muestra muestraCercanaMock;
    Observador observadorMock;

    @BeforeEach
    void setUp() {
        epicentroMock = mock(Ubicacion.class);
        muestraMock = mock(Muestra.class);
        muestraCercanaMock = mock(Muestra.class);
        observadorMock = mock(Observador.class);

        zona = new zonaDeCobertura("Zona Test", epicentroMock, 10.0, new ArrayList<>());
        zona.suscribir(observadorMock);
    }
    
    
    //Test de getters
    @Test
    void testGetNombreDevuelveNombreCorrecto() {
        assertEquals("Zona Test", zona.getNombre());
    }

    @Test
    void testGetEpicentroDevuelveUbicacionCorrecta() {
        assertEquals(epicentroMock, zona.getEpicentro());
    }

    @Test
    void testGetRadioKmDevuelveRadioCorrecto() {
        assertEquals(10.0, zona.getRadioKm());
    }
    
    
    @Test
    void testAgregarMuestraAgregaYNotifica() {
        zona.agregarMuestra(muestraMock);
        assertTrue(zona.getMuestrasDentro().contains(muestraMock));
        verify(observadorMock).notificarSubida(muestraMock, zona);
    }
    
    @Test
    void testMuestraValidadaNotificaSiEstaDentro() {
        zona.agregarMuestra(muestraMock);
        zona.muestraValidada(muestraMock);
        verify(observadorMock).notificarValidacion(muestraMock, zona);
    }

    @Test
    void testMuestraValidadaNoNotificaSiNoEstaDentro() {
        zona.muestraValidada(muestraMock);
        verify(observadorMock, never()).notificarValidacion(any(), any());
    }
    
    
    @Test
    void testGetMuestrasDentroFiltraPorDistancia() {
        Ubicacion ubicacionLejana = mock(Ubicacion.class);
        
        when(muestraCercanaMock.getUbicacion()).thenReturn(epicentroMock);
        when(muestraMock.getUbicacion()).thenReturn(ubicacionLejana);
        when(epicentroMock.distanciaA(ubicacionLejana)).thenReturn(20.0);
        
        List<Muestra> muestras = Arrays.asList(muestraCercanaMock, muestraMock);
        zona = new zonaDeCobertura("Zona Test", epicentroMock, 10.0, muestras);
        
        List<Muestra> dentro = zona.getMuestrasDentro();
        
        assertTrue(dentro.contains(muestraCercanaMock));
        assertFalse(dentro.contains(muestraMock)); // adicional opcional
    }

    @Test
    void testContieneMuestraTrueSiEstaDentroDelRadio() {
        when(muestraMock.getUbicacion()).thenReturn(epicentroMock);
        when(epicentroMock.distanciaA(epicentroMock)).thenReturn(5.0);

        assertTrue(zona.contieneMuestra(muestraMock));
    }

    @Test
    void testContieneMuestraFalseSiEstaFueraDelRadio() {
        when(muestraMock.getUbicacion()).thenReturn(epicentroMock);
        when(epicentroMock.distanciaA(epicentroMock)).thenReturn(15.0);

        assertFalse(zona.contieneMuestra(muestraMock));
    }
    
    
    @Test
    void testSeSolapaConDevuelveTrueSiHaySuperposicion() {
        zonaDeCobertura otraZona = new zonaDeCobertura("Zona 2", epicentroMock, 5.0, new ArrayList<>());
        when(epicentroMock.distanciaA(epicentroMock)).thenReturn(5.0);

        assertTrue(zona.seSolapaCon(otraZona));
    }

    @Test
    void testSeSolapaConDevuelveFalseSiNoHaySuperposicion() {
        zonaDeCobertura otraZona = new zonaDeCobertura("Zona 2", epicentroMock, 2.0, new ArrayList<>());
        when(epicentroMock.distanciaA(epicentroMock)).thenReturn(20.0);

        assertFalse(zona.seSolapaCon(otraZona));
    }
    
    @Test
    void testSuscribirYDesuscribirObservador() {
        Observador otroObservador = mock(Observador.class);
        zona.suscribir(otroObservador);
        zona.desuscribir(observadorMock);

        zona.agregarMuestra(muestraMock);
        verify(otroObservador).notificarSubida(muestraMock, zona);
        verify(observadorMock, never()).notificarSubida(any(), any());
    }    
}
