package tpFinal;

import java.util.ArrayList;
import java.util.List;

public abstract class CriterioCompuesto implements CriterioBusqueda {
    protected List<CriterioBusqueda> subCriterios = new ArrayList<>();

    public CriterioCompuesto agregar(CriterioBusqueda c) {
        subCriterios.add(c);
        return this;
    }
}