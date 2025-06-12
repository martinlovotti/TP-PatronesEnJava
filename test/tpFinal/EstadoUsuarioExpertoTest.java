package tpFinal;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import java.time.LocalDate;
import java.util.HashMap;

public class EstadoUsuarioExpertoTest {

    private EstadoUsuarioExperto estado;
    private Usuario usuario;
    private Muestra muestra;
    private Vinchuca vinchuca;

    @BeforeEach
    public void setup() {
        estado = new EstadoUsuarioExperto();

        usuario = mock(Usuario.class);
        muestra = mock(Muestra.class);
        vinchuca = Vinchuca.Infestans;

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

        HashMap<Muestra, LocalDate> opinionesReal = new HashMap<>();
        when(usuario.getOpiniones()).thenReturn(opinionesReal);

        estado.opinar(muestra, vinchuca, usuario);

        assertTrue(opinionesReal.containsKey(muestra));
        assertEquals(LocalDate.now(), opinionesReal.get(muestra));

        verify(muestra).agregarOpinion(vinchuca, usuario, muestra);
    }

    @Test
    public void testOpinar_NoPuedeOpinar_NoAgregaOpinionYLlamaAgregarOpinion() {
        when(usuario.puedeOpinarSobre(muestra)).thenReturn(false);

        HashMap<Muestra, LocalDate> opinionesReal = new HashMap<>();
        when(usuario.getOpiniones()).thenReturn(opinionesReal);

        estado.opinar(muestra, vinchuca, usuario);

        assertFalse(opinionesReal.containsKey(muestra));

        verify(muestra, never()).agregarOpinion(any(), any(), any());
    }
}
