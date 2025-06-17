package tpFinal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PorFechaUltimaVotacionPosteriorTest {

    private PorFechaUltimaVotacionPosterior criterio;
    private Muestra muestra;
    private LocalDate fechaReferencia;

    @BeforeEach
    public void setup() {
        fechaReferencia = LocalDate.of(2023, 1, 1);
        criterio = new PorFechaUltimaVotacionPosterior(fechaReferencia);
        muestra = mock(Muestra.class);
    }

    @Test
    public void testCumple_FechaPosterior() {
        when(muestra.getFechaUltimaVotacion()).thenReturn(LocalDate.of(2023, 1, 2));

        assertTrue(criterio.cumple(muestra));

        verify(muestra).getFechaUltimaVotacion();
    }

    @Test
    public void testCumple_FechaIgual() {
        when(muestra.getFechaUltimaVotacion()).thenReturn(LocalDate.of(2023, 1, 1));

        assertFalse(criterio.cumple(muestra));

        verify(muestra).getFechaUltimaVotacion();
    }

    @Test
    public void testCumple_FechaAnterior() {
        when(muestra.getFechaUltimaVotacion()).thenReturn(LocalDate.of(2022, 12, 31));

        assertFalse(criterio.cumple(muestra));

        verify(muestra).getFechaUltimaVotacion();
    }
}
