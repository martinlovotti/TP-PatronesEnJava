package tpFinal;

public class CriterioAND extends CriterioCompuesto {

	@Override
    public boolean cumple(Muestra m) {
        return subCriterios.stream().allMatch(c -> c.cumple(m));
    }
}


