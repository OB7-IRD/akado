package fr.ird.akado.core;

import fr.ird.akado.core.common.AbstractResults;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class DataBaseInspectorTask implements Runnable {

    protected AbstractResults<?> results;

    public DataBaseInspectorTask(AbstractResults<?> r) {
        this.results = r;
    }

    public AbstractResults<?> getResults() {
        return results;
    }
}
