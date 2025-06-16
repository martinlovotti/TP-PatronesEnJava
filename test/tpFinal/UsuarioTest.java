package tpFinal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;

class UsuarioTest {

	Muestra muestraMock;
	Usuario usuarioComun;
	Usuario usuarioComun2;
	SistemaWeb sitio;
	
	@BeforeEach
	void setUp() {
		muestraMock = mock(Muestra.class);
		sitio = mock(SistemaWeb.class);
		usuarioComun = new Usuario(111, false,sitio);
		usuarioComun2 = new Usuario(222, false,sitio);
	}
	
	
	@Test
	void seCompruebaCreacionConValorDelConstructor() {
		assertEquals(111, usuarioComun.getId());
		assertEquals(false, usuarioComun.isEsExperto());
	}
	
	@Test
	void seSubeUnaMuestraConUsuarioComun() {
		
		usuarioComun.SubirMuestra(muestraMock);
        HashMap<Muestra, LocalDate> envios = usuarioComun.getEnvios();
        assertTrue(envios.containsKey(muestraMock));
        assertEquals(LocalDate.now(), envios.get(muestraMock));
	}
	
	@Test
	void opinaSobreUnaMuestraUnUsuarioComun() {
		when(muestraMock.getPropietarioId()).thenReturn(99);
		
        // Estado inicial: sin opiniones
        assertFalse(usuarioComun.getOpiniones().containsKey(muestraMock));

        // Acción
        usuarioComun.opinar(muestraMock, Vinchuca.Infestans); // Se supone que este enum existe

        // Verifica que la opinión fue registrada
        assertTrue(usuarioComun.getOpiniones().containsKey(muestraMock));
        
        // Verifica que la fecha registrada es hoy
        assertEquals(LocalDate.now(), usuarioComun.getOpiniones().get(muestraMock));
	}
	
	@Test
	void testUsuarioNoPuedeOpinarSobreSuPropiaMuestra() {
        // Simular que la muestra pertenece al usuario (muestra.getPropietarioId() == usuario.getId())
        when(muestraMock.getPropietarioId()).thenReturn(usuarioComun.getId());

        // El usuario intenta opinar sobre su propia muestra
        boolean puedeOpinar = usuarioComun.puedeOpinarSobre(muestraMock);

        // Assert: no debería poder opinar
        assertFalse(puedeOpinar);

        // Intentamos ejecutar el método opinar y verificar que no se agrega opinión
        usuarioComun.opinar(muestraMock, Vinchuca.Infestans);
	}
	@Test
	void evaluarEstadoNoCambiaSiEsExpertoValidado() {
	    Usuario usuarioExpertoValidado = new Usuario(333, true, sitio); // true = experto validado

	    // Intenta cambiar estado
	    usuarioExpertoValidado.evaluarEstado(LocalDate.now());

	    // Su estado no debe cambiar (es experto validado)
	    assertTrue(usuarioExpertoValidado.isesExpertoValidado(),
	        "El estado de un experto validado no debe cambiar nunca");
	}
	
	@Test
	void opinarDisparaNotificacionAlSistemaSiLaMuestraEstaVerificada() {
	    Muestra muestra = mock(Muestra.class);
	    EstadoMuestra estadoVerificado = mock(EstadoMuestraProcesoVerificado.class);

	    // Configura la muestra con un estado de tipo EstadoMuestraProcesoVerificado
	    when(muestra.getEstadoActual()).thenReturn(estadoVerificado);
	    when(muestra.getPropietarioId()).thenReturn(999); // un id que no coincida con el usuario

	    Usuario usuario = new Usuario(123, false, sitio);

	    usuario.opinar(muestra, Vinchuca.Infestans);

	    // Verifica que se notificó al sistema
	    verify(sitio).recibirVerificacion(muestra);
	}
	@Test
	void evaluarEstadoPromocionaAExpertoConActividadSuficiente() {
	    // Cargar 11 envíos y 21 opiniones en los últimos 10 días
	    for (int i = 0; i < 11; i++) {
	        usuarioComun.getEnvios().put(mock(Muestra.class), LocalDate.now().minusDays(5));
	    }
	    for (int i = 0; i < 21; i++) {
	        usuarioComun.getOpiniones().put(mock(Muestra.class), LocalDate.now().minusDays(3));
	    }

	    usuarioComun.evaluarEstado(LocalDate.now());

	    assertTrue(usuarioComun.getEstado() instanceof EstadoUsuarioExperto,
	        "Debe pasar a experto por actividad reciente");
	}
	
	@Test
	void evaluarEstadoPermaneceOBajaABasicoSiActividadInsuficiente() {
	    // Cargar pocos registros que no cumplen los requisitos
	    usuarioComun.getEnvios().put(mock(Muestra.class), LocalDate.now().minusDays(10));
	    usuarioComun.getOpiniones().put(mock(Muestra.class), LocalDate.now().minusDays(3));

	    usuarioComun.evaluarEstado(LocalDate.now());

	    assertTrue(usuarioComun.getEstado() instanceof EstadoUsuarioBasico,
	        "Debe quedar o volver a básico si no alcanza la actividad mínima");
	}

}
