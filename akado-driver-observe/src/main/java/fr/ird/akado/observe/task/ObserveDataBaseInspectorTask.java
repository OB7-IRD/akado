package fr.ird.akado.observe.task;

import fr.ird.akado.core.DataBaseInspectorTask;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.observe.inspector.ObserveInspector;
import fr.ird.akado.observe.result.Results;
import fr.ird.driver.observe.business.data.ps.common.Trip;

import java.util.Collection;
import java.util.List;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class ObserveDataBaseInspectorTask<T> extends DataBaseInspectorTask {

    private final String exportDirectoryPath;
    private final List<Trip> tripList;
    private final List<? extends ObserveInspector<T>> inspectors;
    private final List<? extends ObserveInspector<List<T>>> listInspectors;

    protected ObserveDataBaseInspectorTask(String exportDirectoryPath,
                                           List<Trip> tripList,
                                           Results r,
                                           List<? extends ObserveInspector<T>> inspectors,
                                           List<? extends ObserveInspector<List<T>>> listInspectors) {
        super(r);
        this.exportDirectoryPath = exportDirectoryPath;
        this.tripList = tripList;
        this.inspectors = inspectors;
        this.listInspectors = listInspectors == null ? List.of() : listInspectors;
    }

    public List<? extends ObserveInspector<T>> getInspectors() {
        return inspectors;
    }

    public List<? extends ObserveInspector<List<T>>> getListInspectors() {
        return listInspectors;
    }

    public List<Trip> getTripList() {
        return tripList;
    }

    public void onData(Collection<T> toValidate) throws Exception {
        if (toValidate == null || toValidate.isEmpty()) {
            return;
        }
        for (T datum : toValidate) {
            for (ObserveInspector<T> inspector : getInspectors()) {
                inspector.set(datum);
                Results results = inspector.execute();
                if (results != null && !results.isEmpty()) {
                    getResults().addAll(results);
                }
            }
        }
        if (listInspectors != null) {
            List<T> list = List.copyOf(toValidate);
            for (ObserveInspector<List<T>> inspector : getListInspectors()) {
                inspector.set(list);
                Results results = inspector.execute();
                if (results != null && !results.isEmpty()) {
                    getResults().addAll(results);
                }
            }
        }
    }

    /**
     * Write all results in the excel file.
     */
    public void writeResultsInFile() {
        if (!AAProperties.isResultsEnabled()) {
            return;
        }
        if (!getResults().isEmpty()) {
            getResults().exportToXLS(exportDirectoryPath);
            getResults().clear();
        }
    }

    @Override
    public Results getResults() {
        return (Results) super.getResults();
    }
}
