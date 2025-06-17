package tpFinal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PorTipoInsectoTest {

    private PorTipoInsecto criterioInfestans;
    private Muestra muestra;

    @BeforeEach
    public void setup() {
        criterioInfestans = new PorTipoInsecto(Vinchuca.Infestans);
        muestra = mock(Muestra.class);
    }

    @Test
    public void testCumple_CoincideConTipo() {
        when(muestra.getResultadoActual()).thenReturn(Vinchuca.Infestans);

        assertTrue(criterioInfestans.cumple(muestra));
        verify(muestra).getResultadoActual();
    }

    @Test
    public void testCumple_NoCoincideConTipo() {
        when(muestra.getResultadoActual()).thenReturn(Vinchuca.Sordida);

        assertFalse(criterioInfestans.cumple(muestra));
        verify(muestra).getResultadoActual();
    }

}
