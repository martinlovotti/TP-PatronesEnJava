package tpFinal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdministradorDeZonasTest {

	AdministradorDeZonas admin;
    zonaDeCobertura zona1;
    zonaDeCobertura zona2;
    Muestra muestra;

    @BeforeEach
    public void setUp() {
        admin = new AdministradorDeZonas();
        zona1 = mock(zonaDeCobertura.class);
        zona2 = mock(zonaDeCobertura.class);
        muestra = mock(Muestra.class);
    }

    @Test
    public void testRegistrarZona() {
        admin.registrarZona(zona1);
        admin.registrarZona(zona2);

        // Verificamos que al registrar no ocurra nada inesperado
        List<zonaDeCobertura> zonas = admin.getZonasQueContienen(muestra); // simulamos que ninguna la contiene
        assertTrue(zonas.isEmpty());
    }

    @Test
    public void testNuevaMuestraEsAgregadaSiLaZonaLaContiene() {
        when(zona1.contieneMuestra(muestra)).thenReturn(true);
        when(zona2.contieneMuestra(muestra)).thenReturn(false);

        admin.registrarZona(zona1);
        admin.registrarZona(zona2);
        admin.nuevaMuestra(muestra);

        verify(zona1).agregarMuestra(muestra);
        verify(zona2, never()).agregarMuestra(muestra);
    }

    @Test
    public void testNotificarValidacionSoloALasZonasQueContienenLaMuestra() {
        when(zona1.contieneMuestra(muestra)).thenReturn(false);
        when(zona2.contieneMuestra(muestra)).thenReturn(true);

        admin.registrarZona(zona1);
        admin.registrarZona(zona2);
        admin.notificarValidacion(muestra);

        verify(zona1, never()).muestraValidada(muestra);
        verify(zona2).muestraValidada(muestra);
    }

    @Test
    public void testGetZonasQueContienen() {
        when(zona1.contieneMuestra(muestra)).thenReturn(true);
        when(zona2.contieneMuestra(muestra)).thenReturn(false);

        admin.registrarZona(zona1);
        admin.registrarZona(zona2);

        List<zonaDeCobertura> resultado = admin.getZonasQueContienen(muestra);
        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(zona1));
    }

    @Test
    public void testZonasSolapadasCon() {
        zonaDeCobertura zona3 = mock(zonaDeCobertura.class);

        // zona1 será el parámetro (zc), y zona2 y zona3 están en la lista
        when(zona2.seSolapaCon(zona1)).thenReturn(true);   // Esta debe devolver true
        when(zona3.seSolapaCon(zona1)).thenReturn(false);  // Esta debe devolver false

        admin.registrarZona(zona1);
        admin.registrarZona(zona2);
        admin.registrarZona(zona3);

        List<zonaDeCobertura> solapadas = admin.zonasSolapadasCon(zona1);

        assertEquals(1, solapadas.size());
        assertTrue(solapadas.contains(zona2));
        assertFalse(solapadas.contains(zona1)); // Confirmamos que no se incluye a sí misma
    }

}
