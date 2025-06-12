package tpFinal;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.HashMap;

class EstadoUsuarioBasicoTest {

    private EstadoUsuarioBasico estado;
    private Usuario usuario;
    private Muestra muestra;
    private Vinchuca vinchuca;

    @BeforeEach
    public void setup() {
        estado = new EstadoUsuarioBasico();

        usuario = mock(Usuario.class);
        muestra = mock(Muestra.class);
        vinchuca = Vinchuca.Infestans;

        // Para simplificar, usamos mapas reales para opiniones y envios
        when(usuario.getEnvios()).thenReturn(new HashMap<>());
        when(usuario.getOpiniones()).thenReturn(new HashMap<>());
    }

    @Test
    public void testSubirMuestra_AgregaEnvioConFechaActual() {
        estado.SubirMuestra(muestra, usuario);

        HashMap<Muestra, LocalDate> envios = usuario.getEnvios();
        assertTrue(envios.containsKey(muestra));
        assertEquals(LocalDate.now(), envios.get(muestra));
    }

    @Test
    public void testOpinar_PuedeOpinar_AgregaOpinionYLlamaAgregarOpinion() {
        when(usuario.puedeOpinarSobre(muestra)).thenReturn(true);

        // Para capturar la fecha puesta en opiniones
        HashMap<Muestra, LocalDate> opinionesReal = new HashMap<>();
        when(usuario.getOpiniones()).thenReturn(opinionesReal);

        estado.opinar(muestra, vinchuca, usuario);

        // Se agregó la opinión
        assertTrue(opinionesReal.containsKey(muestra));
        assertEquals(LocalDate.now(), opinionesReal.get(muestra));

        // Se llamó al método agregarOpinion de muestra con los argumentos correctos
        verify(muestra).agregarOpinion(vinchuca, usuario, muestra);
    }

    @Test
    public void testOpinar_NoPuedeOpinar_NoAgregaOpinionYLlamaAgregarOpinion() {
        when(usuario.puedeOpinarSobre(muestra)).thenReturn(false);

        HashMap<Muestra, LocalDate> opinionesReal = new HashMap<>();
        when(usuario.getOpiniones()).thenReturn(opinionesReal);

        estado.opinar(muestra, vinchuca, usuario);

        // No se agregó la opinión
        assertFalse(opinionesReal.containsKey(muestra));

        // No se llamó a agregarOpinion de muestra
        verify(muestra, never()).agregarOpinion(any(), any(), any());
    }

}
