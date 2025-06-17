package tpFinal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CriterioORTest {

    private CriterioOR criterioOR;
    private CriterioBusqueda criterioTrue;
    private CriterioBusqueda criterioFalse;
    private Muestra muestra;

    @BeforeEach
    public void setup() {
        criterioOR = new CriterioOR();

        criterioTrue = mock(CriterioBusqueda.class);
        criterioFalse = mock(CriterioBusqueda.class);

        muestra = mock(Muestra.class);
    }

    @Test
    public void testCumple_AlMenosUnCriterioTrue() {
        when(criterioTrue.cumple(muestra)).thenReturn(true);
        when(criterioFalse.cumple(muestra)).thenReturn(false);

        criterioOR.agregar(criterioFalse).agregar(criterioTrue);

        boolean resultado = criterioOR.cumple(muestra);

        assertTrue(resultado);

        verify(criterioFalse).cumple(muestra);
        verify(criterioTrue).cumple(muestra);
    }

    @Test
    public void testCumple_TodosCriteriosFalse() {
        when(criterioFalse.cumple(muestra)).thenReturn(false);

        criterioOR.agregar(criterioFalse).agregar(criterioFalse);

        boolean resultado = criterioOR.cumple(muestra);

        assertFalse(resultado);

        verify(criterioFalse, times(2)).cumple(muestra);
    }

    @Test
    public void testCumple_SinSubcriteriosDevuelveFalsePorDefecto() {
        boolean resultado = criterioOR.cumple(muestra);

        // anyMatch de un stream vacío devuelve false
        assertFalse(resultado);
    }
}
