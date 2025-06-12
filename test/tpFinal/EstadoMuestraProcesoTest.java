package tpFinal;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoMuestraProcesoTest {

    private EstadoMuestraProceso estado;
    private Muestra muestra;
    private Usuario usuarioExperto;
    private Usuario usuarioNoExperto;
    private Vinchuca vinchuca;

    @BeforeEach
    public void setup() {
        estado = new EstadoMuestraProceso();

        // Mock de propietario (necesario para Muestra)
        Usuario propietario = mock(Usuario.class);
        when(propietario.getId()).thenReturn(1);

        // Instancia real de Muestra con historial inicializado
        muestra = new Muestra(Vinchuca.Ninguna, new Ubicacion(1, 2), propietario);

        usuarioExperto = mock(Usuario.class);
        when(usuarioExperto.isEsExperto()).thenReturn(true);
        when(usuarioExperto.getId()).thenReturn(2);

        usuarioNoExperto = mock(Usuario.class);
        when(usuarioNoExperto.isEsExperto()).thenReturn(false);
        when(usuarioNoExperto.getId()).thenReturn(3);

        vinchuca = Vinchuca.Infestans;
    }

    @Test
    public void testAgregarOpinionConUsuarioExperto_CambiaEstadoYPoneA() {
        // Poner valores diferentes en historial antes de la opinión experta
        muestra.historial.put(Vinchuca.Sordida, 5);
        muestra.historial.put(Vinchuca.Infestans, 2);

        estado.agregarOpinion(vinchuca, usuarioExperto, muestra);

        // Verificamos que el estado cambió
        assertTrue(muestra.getEstadoActual() instanceof EstadoMuestraProcesoExperto);

        // Verificamos que se reinició el historial
        for (Vinchuca v : Vinchuca.values()) {
            int votos = muestra.historial.get(v);
            if (v == vinchuca) {
                assertEquals(1, votos); // El experto votó esta
            } else {
                assertEquals(0, votos);
            }
        }
    }

    @Test
    public void testAgregarOpinionConUsuarioNoExperto_SumaVotoYActualizaOpinion() {
        // Dejar historial limpio
        muestra.historial.put(vinchuca, 0);
        muestra.opinion = Vinchuca.Ninguna;

        estado.agregarOpinion(vinchuca, usuarioNoExperto, muestra);

        // Verifica que el voto se incrementa
        assertEquals(1, muestra.obtenerVotosDe(vinchuca));
    }


}
