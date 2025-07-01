package tpFinal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoMuestraProcesoExpertoTest {

    EstadoMuestraProcesoExperto estado;
    Muestra muestraMock;
    Usuario usuarioMock;
    Vinchuca vinchuca;

    @BeforeEach
    void setUp() {
        estado = new EstadoMuestraProcesoExperto();
        muestraMock = mock(Muestra.class);
        usuarioMock = mock(Usuario.class);
        vinchuca = Vinchuca.Infestans;
    }

    @Test
    void testEsVerificadaDevuelveFalse() {
        assertFalse(estado.esVerificada());
    }

    @Test
    void testAgregarOpinionExpertoYVotoPrevioCambiaEstado() {
        when(usuarioMock.isEsExperto()).thenReturn(true);
        when(muestraMock.fueVotado(vinchuca)).thenReturn(true);

        estado.agregarOpinion(vinchuca, usuarioMock, muestraMock);

        verify(muestraMock).realizarOpinion(vinchuca);
        verify(muestraMock).setEstadoMuestra(any(EstadoMuestraProcesoVerificado.class));
    }

    @Test
    void testAgregarOpinionExpertoSinVotoPrevioNoCambiaEstado() {
        when(usuarioMock.isEsExperto()).thenReturn(true);
        when(muestraMock.fueVotado(vinchuca)).thenReturn(false);

        estado.agregarOpinion(vinchuca, usuarioMock, muestraMock);

        verify(muestraMock).realizarOpinion(vinchuca);
        verify(muestraMock, never()).setEstadoMuestra(any());
    }

    @Test
    void testAgregarOpinionNoExpertoNoHaceNada() {
        when(usuarioMock.isEsExperto()).thenReturn(false);

        estado.agregarOpinion(vinchuca, usuarioMock, muestraMock);

        verify(muestraMock, never()).realizarOpinion(any());
        verify(muestraMock, never()).setEstadoMuestra(any());
    }

    @Test
    void testCalcularResultadoDevuelveTipoMasFrecuente() {
        // Opiniones: Infestans x3, Sordida x2
        estado.agregarOpinion(Vinchuca.Infestans, usuarioExpertoMock(true, true), muestraMock);
        estado.agregarOpinion(Vinchuca.Sordida, usuarioExpertoMock(true, false), muestraMock);
        estado.agregarOpinion(Vinchuca.Infestans, usuarioExpertoMock(true, false), muestraMock);
        estado.agregarOpinion(Vinchuca.Infestans, usuarioExpertoMock(true, false), muestraMock);
        estado.agregarOpinion(Vinchuca.Sordida, usuarioExpertoMock(true, false), muestraMock);

        Vinchuca resultado = estado.calcularResultado(muestraMock);
        assertEquals(Vinchuca.Infestans, resultado);
    }

    // Método de ayuda para simular múltiples usuarios expertos (sin usar mocks internos compartidos)
    private Usuario usuarioExpertoMock(boolean esExperto, boolean fueVotado) {
        Usuario u = mock(Usuario.class);
        when(u.isEsExperto()).thenReturn(esExperto);
        when(muestraMock.fueVotado(any())).thenReturn(fueVotado);
        return u;
    }
}
