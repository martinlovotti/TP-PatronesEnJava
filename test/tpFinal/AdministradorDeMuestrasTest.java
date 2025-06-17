package tpFinal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdministradorDeMuestrasTest {

    AdministradorDeMuestras admin;
    Muestra muestra1;
    Muestra muestra2;
    Ubicacion ubicacionReferencia;

    @BeforeEach
    public void setUp() {
        admin = new AdministradorDeMuestras();

        // Creamos mocks
        muestra1 = mock(Muestra.class);
        muestra2 = mock(Muestra.class);
        ubicacionReferencia = mock(Ubicacion.class);
    }

    @Test
    public void testRegistrarMuestra_laAgregaALaLista() {
        admin.registrarMuestra(muestra1);
        assertEquals(1, admin.getTodas().size());
        assertTrue(admin.getTodas().contains(muestra1));
    }

    @Test
    public void testMuestrasPorUsuario_filtraCorrectamente() {
        when(muestra1.identificacion()).thenReturn(1);
        when(muestra2.identificacion()).thenReturn(2);

        admin.registrarMuestra(muestra1);
        admin.registrarMuestra(muestra2);

        List<Muestra> resultado = admin.muestrasPorUsuario(1);
        assertEquals(1, resultado.size());
        assertTrue(resultado.contains(muestra1));
        assertFalse(resultado.contains(muestra2));
    }

    @Test
    public void testMuestrasCercanas_devuelveSoloLasCercanas() {
        Ubicacion ubi1 = mock(Ubicacion.class);
        Ubicacion ubi2 = mock(Ubicacion.class);

        when(muestra1.getUbicacion()).thenReturn(ubi1);
        when(muestra2.getUbicacion()).thenReturn(ubi2);

        // Muestra1 está a 3km, muestra2 está a 10km
        when(ubi1.distanciaA(ubicacionReferencia)).thenReturn(3.0);
        when(ubi2.distanciaA(ubicacionReferencia)).thenReturn(10.0);

        admin.registrarMuestra(muestra1);
        admin.registrarMuestra(muestra2);

        List<Muestra> cercanas = admin.muestrasCercanas(ubicacionReferencia, 5.0);

        assertEquals(1, cercanas.size());
        assertTrue(cercanas.contains(muestra1));
        assertFalse(cercanas.contains(muestra2));
    }
    
    @Test
    public void testBuscarDelegacionAFiltrar() {
        // Arrange
        CriterioBusqueda criterioMock = mock(CriterioBusqueda.class);
        List<Muestra> todasLasMuestras = List.of(muestra1, muestra2);
        List<Muestra> resultadoEsperado = List.of(muestra1);

        // Registramos las muestras
        admin.registrarMuestra(muestra1);
        admin.registrarMuestra(muestra2);

        // Simulamos que el criterio devuelve solo muestra1
        when(criterioMock.filtrar(todasLasMuestras)).thenReturn(resultadoEsperado);

        // Act
        List<Muestra> resultado = admin.buscar(criterioMock);

        // Assert
        assertEquals(resultadoEsperado, resultado);
        verify(criterioMock).filtrar(todasLasMuestras);
    }

}
