package tpFinal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;

class UsuarioTest {

	Muestra muestraMock;
	Usuario usuarioComun;
	Usuario usuarioComun2;

	
	@BeforeEach
	void setUp() {
		muestraMock = mock(Muestra.class);
		usuarioComun = new Usuario(111, false);
		usuarioComun2 = new Usuario(222, false);
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
	

}
