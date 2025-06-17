package tpFinal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class UsuarioTest {

    Usuario usuario;
    SistemaWeb sistemaMock;

    @BeforeEach
    public void setUp() {
        sistemaMock = mock(SistemaWeb.class);
        // Por defecto, un usuario NO experto validado
        usuario = new Usuario(1, false, sistemaMock);
    }

    // --- Constructor y getters básicos ---

    @Test
    public void testConstructorInicializaEstadoBasico() {
        assertFalse(usuario.isesExpertoValidado());
        assertTrue(usuario.getEstado() instanceof EstadoUsuarioBasico);
        assertEquals(1, usuario.getId());
    }

    @Test
    public void testConstructorExpertoValidadoInicializaCorrectamente() {
        Usuario experto = new Usuario(2, true, sistemaMock);
        assertTrue(experto.isesExpertoValidado());
        assertTrue(experto.getEstado() instanceof EstadoUsuarioExpertoValidado);
    }

    // --- Método puedeOpinarSobre ---

    @Test
    public void testPuedeOpinarSobreOtraPersonaYNoHaOpinando() {
        Muestra muestraMock = mock(Muestra.class);
        when(muestraMock.getPropietarioId()).thenReturn(99);
        assertTrue(usuario.puedeOpinarSobre(muestraMock));

        usuario.getOpiniones().put(muestraMock, LocalDate.now());
        assertFalse(usuario.puedeOpinarSobre(muestraMock));
    }

    @Test
    public void testNoPuedeOpinarSobreSiEsMismaPersona() {
        Muestra muestraMock = mock(Muestra.class);
        when(muestraMock.getPropietarioId()).thenReturn(usuario.getId());
        assertFalse(usuario.puedeOpinarSobre(muestraMock));
    }

    // --- Método evaluarEstado: cobertura completa ---

    @Test
    void testEvaluarEstadoNoHaceNadaSiEsExpertoValidado() {
        Usuario usuarioValidado = new Usuario(2, true, sistemaMock);
        usuarioValidado.evaluarEstado(LocalDate.now());
        assertTrue(usuarioValidado.getEstado() instanceof EstadoUsuarioExpertoValidado);
    }

    @Test
    void testEvaluarEstadoSeVuelveExpertoCuandoSuperaCondiciones() {
        for (int i = 0; i < 11; i++) {
            usuario.getEnvios().put(mock(Muestra.class), LocalDate.now());
        }
        for (int i = 0; i < 21; i++) {
            usuario.getOpiniones().put(mock(Muestra.class), LocalDate.now());
        }
        usuario.evaluarEstado(LocalDate.now());
        assertTrue(usuario.getEstado() instanceof EstadoUsuarioExperto);
    }

    @Test
    void testEvaluarEstadoSeVuelveBasicoSiNoCumpleCondiciones() {
        for (int i = 0; i < 5; i++) {
            usuario.getEnvios().put(mock(Muestra.class), LocalDate.now());
        }
        for (int i = 0; i < 10; i++) {
            usuario.getOpiniones().put(mock(Muestra.class), LocalDate.now());
        }
        usuario.evaluarEstado(LocalDate.now());
        assertTrue(usuario.getEstado() instanceof EstadoUsuarioBasico);
    }

    @Test
    void testEvaluarEstadoBordeExactoNoEsExperto() {
        for (int i = 0; i < 10; i++) {
            usuario.getEnvios().put(mock(Muestra.class), LocalDate.now());
        }
        for (int i = 0; i < 20; i++) {
            usuario.getOpiniones().put(mock(Muestra.class), LocalDate.now());
        }
        usuario.evaluarEstado(LocalDate.now());
        assertTrue(usuario.getEstado() instanceof EstadoUsuarioBasico);
    }

    @Test
    void testEvaluarEstadoSoloEnviosCumplePeroNoOpiniones() {
        for (int i = 0; i < 11; i++) {
            usuario.getEnvios().put(mock(Muestra.class), LocalDate.now());
        }
        for (int i = 0; i < 10; i++) {
            usuario.getOpiniones().put(mock(Muestra.class), LocalDate.now());
        }
        usuario.evaluarEstado(LocalDate.now());
        assertTrue(usuario.getEstado() instanceof EstadoUsuarioBasico);
    }

    @Test
    void testEvaluarEstadoSoloOpinionesCumplePeroNoEnvios() {
        for (int i = 0; i < 5; i++) {
            usuario.getEnvios().put(mock(Muestra.class), LocalDate.now());
        }
        for (int i = 0; i < 21; i++) {
            usuario.getOpiniones().put(mock(Muestra.class), LocalDate.now());
        }
        usuario.evaluarEstado(LocalDate.now());
        assertTrue(usuario.getEstado() instanceof EstadoUsuarioBasico);
    }

    @Test
    void testEvaluarEstadoConDatosViejosNoCuenta() {
        LocalDate fechaVieja = LocalDate.now().minusDays(31);
        for (int i = 0; i < 15; i++) {
            usuario.getEnvios().put(mock(Muestra.class), fechaVieja);
        }
        for (int i = 0; i < 25; i++) {
            usuario.getOpiniones().put(mock(Muestra.class), fechaVieja);
        }
        usuario.evaluarEstado(LocalDate.now());
        assertTrue(usuario.getEstado() instanceof EstadoUsuarioBasico);
    }

    // --- Métodos que delegan a estado ---

    @Test
    void testSubirMuestraDelegadoAlEstado() {
        Muestra muestraMock = mock(Muestra.class);
        EstadoUsuario estadoMock = mock(EstadoUsuario.class);
        usuario.setEstado(estadoMock);

        usuario.SubirMuestra(muestraMock);

        verify(estadoMock).SubirMuestra(muestraMock, usuario);
    }

    @Test
    void testOpinarDelegadoAlEstadoYRecibeVerificacion() {
        Muestra muestraMock = mock(Muestra.class);
        EstadoUsuario estadoMock = mock(EstadoUsuario.class);
        usuario.setEstado(estadoMock);

        // Configuramos para que getEstadoActual() devuelva EstadoMuestraProcesoVerificado
        when(muestraMock.getEstadoActual()).thenReturn(new EstadoMuestraProcesoVerificado());

        usuario.opinar(muestraMock, Vinchuca.Ninguna);

        verify(estadoMock).opinar(muestraMock, Vinchuca.Ninguna, usuario);
        verify(sistemaMock).recibirVerificacion(muestraMock);
    }

    @Test
    void testOpinarDelegadoAlEstadoSinVerificacion() {
        Muestra muestraMock = mock(Muestra.class);
        EstadoUsuario estadoMock = mock(EstadoUsuario.class);
        usuario.setEstado(estadoMock);

        // Estado diferente para no llamar a recibirVerificacion
        when(muestraMock.getEstadoActual()).thenReturn(new EstadoMuestraProceso());

        usuario.opinar(muestraMock, Vinchuca.Ninguna);

        verify(estadoMock).opinar(muestraMock, Vinchuca.Ninguna, usuario);
        verify(sistemaMock, never()).recibirVerificacion(any());
    }
    
}
