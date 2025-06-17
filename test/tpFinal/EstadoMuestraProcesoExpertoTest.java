package tpFinal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;


import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoMuestraProcesoExpertoTest {

    private EstadoMuestraProcesoExperto estado;
    private Muestra muestra;
    private Usuario usuarioExperto;
    private Vinchuca vinchuca;

    @BeforeEach
    public void setup() {
        estado = new EstadoMuestraProcesoExperto();

        usuarioExperto = mock(Usuario.class);
        when(usuarioExperto.isEsExperto()).thenReturn(true);
        when(usuarioExperto.getId()).thenReturn(1);

        muestra = spy(new Muestra(Vinchuca.Ninguna, null, usuarioExperto));

        HashMap<Vinchuca, Integer> historialLimpio = new HashMap<>();
        for (Vinchuca v : Vinchuca.values()) {
            historialLimpio.put(v, 0);
        }

        // Asignar el campo directamente, sin usar doReturn()
        muestra.historial = historialLimpio;

        muestra.estadoActual = estado;

        vinchuca = Vinchuca.Infestans;
    }

    @Test
    public void testAgregarOpinionExpertoNoVotoPrevio() {
        // No hay votos previos para vinchuca, historial.get(vinchuca) == 0

        estado.agregarOpinion(vinchuca, usuarioExperto, muestra);

        // El voto para vinchuca debe pasar a 1
        assertEquals(1, muestra.historial.get(vinchuca));

        // Estado no cambia porque no había voto previo (según tu lógica)
        assertSame(estado, muestra.estadoActual);

        // La opinion puede no cambiar aún o ser la vinchuca (depende de la lógica)
    }

    @Test
    public void testAgregarOpinionExpertoYaVoto() {
        // Simulamos voto previo para vinchuca
        muestra.historial.put(vinchuca, 1);

        estado.agregarOpinion(vinchuca, usuarioExperto, muestra);

        // El voto para vinchuca debe pasar a 2
        assertEquals(2, muestra.historial.get(vinchuca));

        // El estado debe cambiar a EstadoMuestraProcesoVerificado
        assertTrue(muestra.estadoActual instanceof EstadoMuestraProcesoVerificado);

        // La opinion debe actualizarse al vinchuca con más votos
        assertEquals(vinchuca, muestra.opinion);
    }
    
    @Test
    public void testEsVerificadaDevuelveFalse() {
        EstadoMuestraProcesoExperto estado = new EstadoMuestraProcesoExperto();

        assertFalse(estado.esVerificada(), "EstadoMuestraProcesoExperto debe devolver false en esVerificada");
    }
}
