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

class EstadoMuestraProcesoVerificadoTest {

    private EstadoMuestraProcesoVerificado estado;
    private Muestra muestra;
    private Usuario usuario;
    private Vinchuca vinchuca;

    @BeforeEach
    public void setup() {
        estado = new EstadoMuestraProcesoVerificado();

        usuario = mock(Usuario.class);
        when(usuario.isEsExperto()).thenReturn(true);
        when(usuario.getId()).thenReturn(1);

        muestra = spy(new Muestra(Vinchuca.Ninguna, null, usuario));

        HashMap<Vinchuca, Integer> historialLimpio = new HashMap<>();
        for (Vinchuca v : Vinchuca.values()) {
            historialLimpio.put(v, 0);
        }
        muestra.historial = historialLimpio;

        muestra.estadoActual = estado;

        vinchuca = Vinchuca.Infestans;
    }

    @Test
    public void testAgregarOpinion_NoModificaMuestra() {
        // Act
        estado.agregarOpinion(vinchuca, usuario, muestra);

        // Assert
        // Verificamos que no se haya modificado historial ni opinion ni estadoActual
        verify(muestra, never()).ponerA();
        verify(muestra, never()).obtenerVotosDe(any());
        verify(muestra, never()).obtenerVinchucaConMasVotos();

        // El estado debe seguir siendo el mismo
        assertSame(estado, muestra.estadoActual);

        // La opinión no cambia
        assertEquals(Vinchuca.Ninguna, muestra.opinion);
        
        assertEquals(true, muestra.estadoActual.esVerificada());
    }
}
