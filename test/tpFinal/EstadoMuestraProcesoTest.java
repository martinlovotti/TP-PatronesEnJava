package tpFinal;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoMuestraProcesoTest {

    EstadoMuestraProceso estado;
    Muestra muestraMock;
    Usuario usuarioMock;
    Vinchuca vinchuca;

    @BeforeEach
    void setUp() {
        estado = new EstadoMuestraProceso();
        muestraMock = mock(Muestra.class);
        usuarioMock = mock(Usuario.class);
        vinchuca = Vinchuca.Infestans;
    }

    @Test
    void testEsVerificadaDevuelveFalse() {
        assertFalse(estado.esVerificada());
    }

    @Test
    void testAgregarOpinionUsuarioExpertoActualizaEstado() {
        when(usuarioMock.isEsExperto()).thenReturn(true);

        estado.agregarOpinion(vinchuca, usuarioMock, muestraMock);

        verify(muestraMock).realizarOpinion(vinchuca);
        verify(muestraMock).setEstadoMuestra(any(EstadoMuestraProcesoExperto.class));
    }

    @Test
    void testAgregarOpinionUsuarioNoExpertoNoCambiaEstado() {
        when(usuarioMock.isEsExperto()).thenReturn(false);

        estado.agregarOpinion(vinchuca, usuarioMock, muestraMock);

        verify(muestraMock).realizarOpinion(vinchuca);
        verify(muestraMock, never()).setEstadoMuestra(any());
    }

    @Test
    void testCalcularResultadoDelegadoAMuestra() {
        Vinchuca esperado = Vinchuca.Sordida;
        when(muestraMock.obtenerVinchucaConMasVotos()).thenReturn(esperado);

        Vinchuca resultado = estado.calcularResultado(muestraMock);

        assertEquals(esperado, resultado);
    }
}
