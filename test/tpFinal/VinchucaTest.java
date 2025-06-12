package tpFinal;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class VinchucaTest {
	
	Vinchuca vinchuca;

	@Before
	public void setUp() {
		vinchuca = Vinchuca.Ninguna;	
	}
	
	@Test 
	public void imprimir(){
		assertEquals("Ninguna", vinchuca.imprimir());
	}
}
