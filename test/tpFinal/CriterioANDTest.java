package tpFinal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CriterioANDTest {

    private CriterioAND criterioAND;
    private CriterioBusqueda criterioTrue;
    private CriterioBusqueda criterioFalse;
    private Muestra muestra;

    @BeforeEach
    public void setup() {
        criterioAND = new CriterioAND();

        // Mock de subcriterios
        criterioTrue = mock(CriterioBusqueda.class);
        criterioFalse = mock(CriterioBusqueda.class);

        // Mock de muestra
        muestra = mock(Muestra.class);
    }

    @Test
    public void testCumple_ConTodosCriteriosTrue() {
        when(criterioTrue.cumple(muestra)).thenReturn(true);
        
        criterioAND.agregar(criterioTrue).agregar(criterioTrue);

        boolean resultado = criterioAND.cumple(muestra);

        assertTrue(resultado);
        verify(criterioTrue, times(2)).cumple(muestra);
    }

    @Test
    public void testCumple_ConUnCriterioFalse() {
        when(criterioTrue.cumple(muestra)).thenReturn(true);
        when(criterioFalse.cumple(muestra)).thenReturn(false);

        criterioAND.agregar(criterioTrue).agregar(criterioFalse);

        boolean resultado = criterioAND.cumple(muestra);

        assertFalse(resultado);
        // Verificamos que se evaluaron ambos (o al menos hasta el que dio false)
        verify(criterioTrue).cumple(muestra);
        verify(criterioFalse).cumple(muestra);
    }

    @Test
    public void testCumple_SinSubcriteriosDevuelveTruePorDefecto() {
        boolean resultado = criterioAND.cumple(muestra);

        // Cuando no hay subcriterios, allMatch de un stream vacío devuelve true
        assertTrue(resultado);
    }

}
