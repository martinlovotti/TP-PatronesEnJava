package tpFinal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoMuestraProcesoVerificadoTest {


    private EstadoMuestraProcesoVerificado estado;
    private Muestra muestraMock;
    private Usuario usuarioMock;
    private Vinchuca vinchuca;

    @BeforeEach
    void setUp() {
        estado = new EstadoMuestraProcesoVerificado();
        muestraMock = mock(Muestra.class);
        usuarioMock = mock(Usuario.class);
        vinchuca = Vinchuca.Infestans;
    }

    @Test
    void testEsVerificadaDevuelveTrue() {
        assertTrue(estado.esVerificada());
    }

    @Test
    void testAgregarOpinionNoHaceNada() {
        // No hay efecto observable directo, solo se imprime algo (que no se testea normalmente)
        estado.agregarOpinion(vinchuca, usuarioMock, muestraMock);

        // Verificamos que la muestra no fue modificada
        verifyNoInteractions(muestraMock);
    }

    @Test
    void testCalcularResultadoDevuelveOpinionActual() {
        when(muestraMock.getOpinion()).thenReturn(Vinchuca.Sordida);

        Vinchuca resultado = estado.calcularResultado(muestraMock);
        assertEquals(Vinchuca.Sordida, resultado);
    }
}
