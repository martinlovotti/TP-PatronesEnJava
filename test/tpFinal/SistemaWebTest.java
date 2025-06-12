package tpFinal;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class SistemaWebTest {
	SistemaWeb sistema;
    AdministradorDeZonas adminZonasMock;
    AdministradorDeMuestras adminMuestrasMock;
    Muestra muestraMock;
    Ubicacion ubicacionMock;
    zonaDeCobertura zonaMock;
    
    @BeforeEach
    public void setUp() {
        adminZonasMock = mock(AdministradorDeZonas.class);
        adminMuestrasMock = mock(AdministradorDeMuestras.class);
        muestraMock = mock(Muestra.class);
        ubicacionMock = mock(Ubicacion.class);
        zonaMock = mock(zonaDeCobertura.class);

        sistema = new SistemaWeb(adminZonasMock, adminMuestrasMock);
    }
    
    @Test
    public void testRecibirMuestraRegistraEnMuestrasYZonas() {
        sistema.recibirMuestra(muestraMock);

        verify(adminMuestrasMock).registrarMuestra(muestraMock);
        verify(adminZonasMock).nuevaMuestra(muestraMock);
    }
    
    @Test
    void testRecibirVerificacionNotificaValidacion() {
        sistema.recibirVerificacion(muestraMock);
        verify(adminZonasMock).notificarValidacion(muestraMock);
    }

    @Test
    void testMuestrasCercanasDevuelveListaEsperada() {
        List<Muestra> listaEsperada = new ArrayList<>();
        listaEsperada.add(muestraMock);
        when(adminMuestrasMock.muestrasCercanas(ubicacionMock, 10.0)).thenReturn(listaEsperada);

        List<Muestra> resultado = sistema.muestrasCercanas(ubicacionMock, 10.0);

        assertEquals(listaEsperada, resultado);
        verify(adminMuestrasMock).muestrasCercanas(ubicacionMock, 10.0);
    }

    @Test
    void testZonasQueContienenDevuelveZonasEsperadas() {
        List<zonaDeCobertura> zonasEsperadas = List.of(zonaMock);
        when(adminZonasMock.getZonasQueContienen(muestraMock)).thenReturn(zonasEsperadas);

        List<zonaDeCobertura> resultado = sistema.zonasQueContienen(muestraMock);

        assertEquals(zonasEsperadas, resultado);
        verify(adminZonasMock).getZonasQueContienen(muestraMock);
    }

    @Test
    void testRegistrarZonaLlamaAlAdministradorDeZonas() {
        sistema.registrarZona(zonaMock);
        verify(adminZonasMock).registrarZona(zonaMock);
    }

}
