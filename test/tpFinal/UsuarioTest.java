package tpFinal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class UsuarioTest {

    private Usuario usuario;
    private Usuario usuarioValido;
    private SistemaWeb sitioMock;
    private Muestra muestraMock;
    private EstadoMuestra estadoMock;
    private Vinchuca vinchuca;

    @BeforeEach
    void setUp() {
        sitioMock = mock(SistemaWeb.class);
        muestraMock = mock(Muestra.class);
        estadoMock = mock(EstadoMuestra.class);
        vinchuca = Vinchuca.Infestans;

        usuario = new Usuario(1, false, sitioMock); // No experto validado
        usuarioValido = new Usuario(2, true, sitioMock); // Experto validado

        // Por default: el estado de la muestra no está verificado
        when(muestraMock.getEstadoActual()).thenReturn(estadoMock);
        when(estadoMock.esVerificada()).thenReturn(false);
        when(muestraMock.getPropietarioId()).thenReturn(999); // distinto al ID del usuario
    }

    @Test
    void testSubirMuestraLaAgregaAEnvios() {
        assertTrue(usuario.getEnvios().isEmpty());

        usuario.SubirMuestra(muestraMock);

        assertEquals(1, usuario.getEnvios().size());
        assertTrue(usuario.getEnvios().containsKey(muestraMock));
    }

    @Test
    void testOpinarAgregaOpinionSiPuedeYNoVerifica() {
        EstadoMuestra estadoMuestraMock = mock(EstadoMuestra.class);

        when(muestraMock.getEstadoActual()).thenReturn(estadoMuestraMock);
        when(estadoMuestraMock.esVerificada()).thenReturn(false);
        when(muestraMock.getPropietarioId()).thenReturn(999); // distinto al ID del usuario

        // Simulamos comportamiento de muestraMock.agregarOpinion(...)
        doAnswer(invoc -> {
            estadoMuestraMock.agregarOpinion(vinchuca, usuario, muestraMock);
            return null;
        }).when(muestraMock).agregarOpinion(any(), any(), any());

        usuario.opinar(muestraMock, vinchuca);

        // Verificamos que se agregó la opinión
        assertEquals(1, usuario.getOpiniones().size());
        assertTrue(usuario.getOpiniones().containsKey(muestraMock));

        // Verificamos que se llamó a agregarOpinion del estado
        verify(estadoMuestraMock).agregarOpinion(vinchuca, usuario, muestraMock);
        verify(sitioMock, never()).recibirVerificacion(muestraMock);
    }

    @Test
    void testOpinarConEstadoVerificadoNotificaSitio() {
        when(estadoMock.esVerificada()).thenReturn(true);

        usuario.opinar(muestraMock, vinchuca);

        verify(sitioMock).recibirVerificacion(muestraMock);
    }

    @Test
    void testNoPuedeOpinarSobreSuPropiaMuestra() {
        when(muestraMock.getPropietarioId()).thenReturn(1); // mismo ID

        assertFalse(usuario.puedeOpinarSobre(muestraMock));
    }

    @Test
    void testNoPuedeOpinarSiYaOpino() {
        usuario.getOpiniones().put(muestraMock, LocalDate.now());

        assertFalse(usuario.puedeOpinarSobre(muestraMock));
    }

    @Test
    void testPuedeOpinarSiEsOtraMuestraYNoOpino() {
        assertTrue(usuario.puedeOpinarSobre(muestraMock));
    }

    @Test
    void testEvaluarEstado_CambiaAExpertoSiCumpleCondiciones() {
        // Simula 11 envíos y 21 opiniones dentro de los últimos 30 días
        for (int i = 0; i < 11; i++)
            usuario.getEnvios().put(mock(Muestra.class), LocalDate.now());

        for (int i = 0; i < 21; i++)
            usuario.getOpiniones().put(mock(Muestra.class), LocalDate.now());

        usuario.evaluarEstado(LocalDate.now());

        assertTrue(usuario.getEstado() instanceof EstadoUsuarioExperto);
    }

    @Test
    void testEvaluarEstado_CambiaABasicoSiNoCumpleCondiciones() {
        // Menos de 10 envíos
        for (int i = 0; i < 5; i++)
            usuario.getEnvios().put(mock(Muestra.class), LocalDate.now());

        for (int i = 0; i < 10; i++)
            usuario.getOpiniones().put(mock(Muestra.class), LocalDate.now());

        usuario.evaluarEstado(LocalDate.now());

        assertTrue(usuario.getEstado() instanceof EstadoUsuarioBasico);
    }

    @Test
    void testEvaluarEstado_NoCambiaSiEsExpertoValidado() {
        // Le metemos datos de todas formas
        for (int i = 0; i < 20; i++) {
            usuarioValido.getEnvios().put(mock(Muestra.class), LocalDate.now());
            usuarioValido.getOpiniones().put(mock(Muestra.class), LocalDate.now());
        }

        EstadoUsuario estadoAnterior = usuarioValido.getEstado();

        usuarioValido.evaluarEstado(LocalDate.now());

        assertSame(estadoAnterior, usuarioValido.getEstado());
    }

    @Test
    void testGettersBasicos() {
        assertEquals(1, usuario.getId());
        assertFalse(usuario.isesExpertoValidado());
        assertFalse(usuario.isEsExperto());
    }
}