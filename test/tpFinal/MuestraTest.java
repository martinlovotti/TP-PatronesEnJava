package tpFinal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// prueva push
class MuestraTest {
	
	Muestra muestraReal;
	Usuario usuarioMock;
	Usuario usuarioMock2;
	Usuario usuarioMock3;
	Usuario usuarioMock4;
	Ubicacion ubiMock;
	EstadoMuestraProceso estadoMock;
	@BeforeEach
	void setUp() {
		usuarioMock = mock(Usuario.class);
		usuarioMock2 = mock(Usuario.class);
		usuarioMock3 = mock(Usuario.class);
		usuarioMock4 = mock(Usuario.class);
		ubiMock = mock(Ubicacion.class);
		muestraReal = new Muestra(Vinchuca.Ninguna, ubiMock, usuarioMock4);
		
	}
	
	@Test
	void getIdentificacion() {
		
		assertEquals(muestraReal.identificacion(), 0);
	}
	
	@Test
	void getPropietarioID() {
		
		muestraReal.getPropietarioId();
		verify(usuarioMock4, times(2)).getId(); //2 veces porque se llama cuando se crea la muestra y en la linea de arriba
	}
	
	@Test
	void getUbicacionMuestra() {
		
		assertEquals(muestraReal.getUbicacion(), ubiMock);
	}
	
	@Test
	void SeCompruebaCreacionConValorDelConstructor() {
		//muestraReal.agregarOpinion(Vinchuca.ImagenPocoClara, usuarioMock, muestraReal);
		assertEquals(1, muestraReal.obtenerVotosDe(Vinchuca.Ninguna));
	}
	
	@Test
	void SeAgregaOpinion() {
		when(usuarioMock.isEsExperto()).thenReturn(true);
		when(usuarioMock.getId()).thenReturn(22);
		
		muestraReal.agregarOpinion(Vinchuca.ImagenPocoClara, usuarioMock, muestraReal);
		assertEquals(1, muestraReal.obtenerVotosDe(Vinchuca.ImagenPocoClara));
	}
	
	@Test
	void usuarioNoExpertoNoSumaAlHistorial() {
		when(usuarioMock.isEsExperto()).thenReturn(true);
		when(usuarioMock2.isEsExperto()).thenReturn(false);
		when(usuarioMock.getId()).thenReturn(22);
		when(usuarioMock2.getId()).thenReturn(11);
		
		muestraReal.agregarOpinion(Vinchuca.ImagenPocoClara, usuarioMock, muestraReal);
		muestraReal.agregarOpinion(Vinchuca.ImagenPocoClara, usuarioMock2, muestraReal);
		assertEquals(1, muestraReal.obtenerVotosDe(Vinchuca.ImagenPocoClara));
		
	}
	
	@Test
	void usuariosExpertosOpinanYLuegoQuedaVerificadaNoSePuedeVolverAVotar() {
		when(usuarioMock.isEsExperto()).thenReturn(true);
		when(usuarioMock2.isEsExperto()).thenReturn(true);
		when(usuarioMock3.isEsExperto()).thenReturn(false);
		when(usuarioMock.getId()).thenReturn(22);
		when(usuarioMock2.getId()).thenReturn(11);
		when(usuarioMock3.getId()).thenReturn(33);
		
		muestraReal.agregarOpinion(Vinchuca.ImagenPocoClara, usuarioMock, muestraReal);
		muestraReal.agregarOpinion(Vinchuca.ImagenPocoClara, usuarioMock2, muestraReal);
		muestraReal.agregarOpinion(Vinchuca.ImagenPocoClara, usuarioMock3, muestraReal);
		
		assertEquals(2, muestraReal.obtenerVotosDe(Vinchuca.ImagenPocoClara));
	}

	@Test
	void usuariosNoExpertosOpinan() {
		when(usuarioMock.isEsExperto()).thenReturn(false);
		when(usuarioMock2.isEsExperto()).thenReturn(false);
		when(usuarioMock.getId()).thenReturn(22);
		when(usuarioMock2.getId()).thenReturn(11);
		
		muestraReal.agregarOpinion(Vinchuca.ImagenPocoClara, usuarioMock, muestraReal);
		muestraReal.agregarOpinion(Vinchuca.ImagenPocoClara, usuarioMock2, muestraReal);
		muestraReal.agregarOpinion(Vinchuca.ImagenPocoClara, usuarioMock3, muestraReal);
		
		assertEquals(3, muestraReal.obtenerVotosDe(Vinchuca.ImagenPocoClara));
	}
	
	@Test
	void usuariosNoExpertosOpinanYCambiaEstadoActual(){
		when(usuarioMock.isEsExperto()).thenReturn(false);
		when(usuarioMock2.isEsExperto()).thenReturn(false);
		when(usuarioMock3.isEsExperto()).thenReturn(false);
		when(usuarioMock.getId()).thenReturn(22);
		when(usuarioMock2.getId()).thenReturn(11);
		when(usuarioMock3.getId()).thenReturn(33);
		
		muestraReal.agregarOpinion(Vinchuca.ImagenPocoClara, usuarioMock, muestraReal);
		muestraReal.agregarOpinion(Vinchuca.ImagenPocoClara, usuarioMock2, muestraReal);
		muestraReal.agregarOpinion(Vinchuca.ChincheFoliada, usuarioMock3, muestraReal);
		
		assertEquals(Vinchuca.ImagenPocoClara, muestraReal.getResultadoActual());
		
	}
}

