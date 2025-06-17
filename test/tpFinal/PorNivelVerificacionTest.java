package tpFinal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PorNivelVerificacionTest {

    private Muestra muestraMock;
    private EstadoMuestra estadoVerificadoMock;
    private EstadoMuestra estadoProcesoMock;

    @BeforeEach
    public void setUp() {
        muestraMock = mock(Muestra.class);
        estadoVerificadoMock = mock(EstadoMuestra.class);
        estadoProcesoMock = mock(EstadoMuestra.class);

        when(estadoVerificadoMock.esVerificada()).thenReturn(true);
        when(estadoProcesoMock.esVerificada()).thenReturn(false);
    }

    @Test
    public void testCumpleConMuestraVerificadaCuandoBuscaVerificadas() {
        PorNivelVerificacion criterio = new PorNivelVerificacion(true);

        when(muestraMock.getEstadoActual()).thenReturn(estadoVerificadoMock);

        assertTrue(criterio.cumple(muestraMock));
    }

    @Test
    public void testNoCumpleConMuestraNoVerificadaCuandoBuscaVerificadas() {
        PorNivelVerificacion criterio = new PorNivelVerificacion(true);

        when(muestraMock.getEstadoActual()).thenReturn(estadoProcesoMock);

        assertFalse(criterio.cumple(muestraMock));
    }

    @Test
    public void testCumpleConMuestraNoVerificadaCuandoBuscaNoVerificadas() {
        PorNivelVerificacion criterio = new PorNivelVerificacion(false);

        when(muestraMock.getEstadoActual()).thenReturn(estadoProcesoMock);

        assertTrue(criterio.cumple(muestraMock));
    }

    @Test
    public void testNoCumpleConMuestraVerificadaCuandoBuscaNoVerificadas() {
        PorNivelVerificacion criterio = new PorNivelVerificacion(false);

        when(muestraMock.getEstadoActual()).thenReturn(estadoVerificadoMock);

        assertFalse(criterio.cumple(muestraMock));
    }

}
