package tpFinal;

public class CriterioOR extends CriterioCompuesto {
    @Override
    public boolean cumple(Muestra m) {
        return subCriterios.stream().anyMatch(c -> c.cumple(m));
    }
}

