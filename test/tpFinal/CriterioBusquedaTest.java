package tpFinal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class CriterioBusquedaTest {

    @Test
    public void testFiltrarDevuelveSoloMuestrasQueCumplen() {
        CriterioBusqueda criterioMock = mock(CriterioBusqueda.class);

        Muestra muestra1 = mock(Muestra.class);
        Muestra muestra2 = mock(Muestra.class);
        Muestra muestra3 = mock(Muestra.class);

        List<Muestra> muestras = List.of(muestra1, muestra2, muestra3);

        when(criterioMock.cumple(muestra1)).thenReturn(true);
        when(criterioMock.cumple(muestra2)).thenReturn(false);
        when(criterioMock.cumple(muestra3)).thenReturn(true);

        // Indicamos que al llamar filtrar use el realMethod
        when(criterioMock.filtrar(anyList())).thenCallRealMethod();

        List<Muestra> resultado = criterioMock.filtrar(muestras);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(muestra1));
        assertTrue(resultado.contains(muestra3));
        assertFalse(resultado.contains(muestra2));
    }

    @Test
    public void testFiltrarConListaVaciaDevuelveListaVacia() {
        CriterioBusqueda criterioMock = mock(CriterioBusqueda.class);

        List<Muestra> muestras = new ArrayList<>();

        List<Muestra> resultado = criterioMock.filtrar(muestras);

        assertTrue(resultado.isEmpty());
    }

}
