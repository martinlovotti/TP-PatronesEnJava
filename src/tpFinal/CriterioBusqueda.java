package tpFinal;

import java.util.List;
import java.util.stream.Collectors;

public interface CriterioBusqueda {
	boolean cumple(Muestra m);
	
	default List<Muestra> filtrar(List<Muestra> muestras){
		return muestras.stream()
                .filter(this::cumple)
                .collect(Collectors.toList());
	}
}
