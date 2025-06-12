package tpFinal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class EstadoUsuarioExpertoValidadoTest {
	Muestra muestraMock;
	Usuario usuarioMock;
	EstadoUsuarioExpertoValidado estadoReal;
	Vinchuca vinchuca;
	
	@BeforeEach
	void setUp() {
		muestraMock = mock(Muestra.class);
		usuarioMock = mock(Usuario.class);
		vinchuca = vinchuca.ImagenPocoClara;
		estadoReal = new EstadoUsuarioExpertoValidado();
	}
	
	@Test
	void subirMuestra() {
		estadoReal.SubirMuestra(muestraMock, usuarioMock);
		verify(usuarioMock).getEnvios();
	}
	
	@Test
	void usuarioPuedeOpinarSeRegistraYAgregaOpinion() {
	    when(usuarioMock.puedeOpinarSobre(muestraMock)).thenReturn(true);

	    // Opción segura: usar un HashMap real
	    HashMap<Muestra, LocalDate> opinionesMap = spy(new HashMap<>());
	    when(usuarioMock.getOpiniones()).thenReturn(opinionesMap);

	    estadoReal.opinar(muestraMock, vinchuca, usuarioMock);

	    verify(usuarioMock).puedeOpinarSobre(muestraMock);
	    verify(usuarioMock).getOpiniones();
	    verify(opinionesMap).put(eq(muestraMock), any(LocalDate.class));
	    verify(muestraMock).agregarOpinion(vinchuca, usuarioMock, muestraMock);
	}
	@Test
	void usuarioNoPuedeOpinarNoSeRegistraNiAgregaOpinion() {
	    // Preparación: el usuario NO puede opinar sobre la muestra
	    when(usuarioMock.puedeOpinarSobre(muestraMock)).thenReturn(false);

	    // Ejecución
	    estadoReal.opinar(muestraMock, vinchuca, usuarioMock);

	    // Verificaciones:
	    verify(usuarioMock).puedeOpinarSobre(muestraMock); // sí se llama
	    verify(usuarioMock, never()).getOpiniones(); // no se llama
	    verify(muestraMock, never()).agregarOpinion(any(), any(), any()); // no se llama
	}
}
