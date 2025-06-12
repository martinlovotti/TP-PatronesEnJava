package tpFinal;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class OrganizacionTest {
	Organizacion organizacionReal;
	Ubicacion ubicacionMock;
	TipoOrganizacion tipoOrg;
	zonaDeCobertura zonaMock;
	Muestra muestraMock;
	FuncionalidadExterna funcionalidadMuestraMock;
	FuncionalidadExterna funcionalidadValidacionMock;
	
	@BeforeEach
	void setUp() {
		muestraMock = mock(Muestra.class);
		ubicacionMock = mock(Ubicacion.class);
		zonaMock = mock(zonaDeCobertura.class);
		tipoOrg = TipoOrganizacion.SALUD;
		organizacionReal = new Organizacion("ONG", ubicacionMock,tipoOrg,2);
		funcionalidadMuestraMock = mock(FuncionalidadExterna.class);
	    funcionalidadValidacionMock = mock(FuncionalidadExterna.class);
		// Inyectamos los mocks
	    organizacionReal.setFuncionalidadNuevaMuestra(funcionalidadMuestraMock);
	    organizacionReal.setFuncionalidadNuevaValidacion(funcionalidadValidacionMock);
	}
	
	
	@Test
	void testNotificarSubidaEjecutaFuncionalidadNuevaMuestra() {
	    when(zonaMock.getNombre()).thenReturn("Zona Norte");
	    organizacionReal.notificarSubida(muestraMock, zonaMock);
	    verify(funcionalidadMuestraMock).nuevoEvento(organizacionReal, zonaMock, muestraMock);
	}
	@Test
	void testNotificarValidacionEjecutaFuncionalidadNuevaValidacion() {
	    when(zonaMock.getNombre()).thenReturn("Zona Sur");
	    organizacionReal.notificarValidacion(muestraMock, zonaMock);
	    verify(funcionalidadValidacionMock).nuevoEvento(organizacionReal, zonaMock, muestraMock);
	}
	
	@Test
	void testGetNombreDevuelveElNombreCorrecto() {
	    assertEquals("ONG", organizacionReal.getNombre());
	}

	@Test
	void testGetUbicacionDevuelveLaUbicacionCorrecta() {
	    assertEquals(ubicacionMock, organizacionReal.getUbicacion());
	}

	@Test
	void testGetTipoDevuelveElTipoCorrecto() {
	    assertEquals(TipoOrganizacion.SALUD, organizacionReal.getTipo());
	}

	@Test
	void testGetCantidadDeEmpleadosDevuelveElValorCorrecto() {
	    assertEquals(2, organizacionReal.getCantidadDeEmpleados());
	}
	
}
