package fr.ird.akado.observe.task;

import fr.ird.akado.core.DataBaseInspectorTask;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.observe.inspector.ObserveInspector;
import fr.ird.akado.observe.result.Results;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import io.ultreia.java4all.util.TimeLog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class ObserveDataBaseInspectorTask<T> extends DataBaseInspectorTask {
    private static final Logger log = LogManager.getLogger(ObserveDataBaseInspectorTask.class);
    private static final TimeLog TIMELOG = new TimeLog(ObserveDataBaseInspectorTask.class, 500, 1000);

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

    protected abstract void inspect() throws Exception;

    protected abstract void writeResults(String directoryPath, Results results) throws IOException;

    public List<? extends ObserveInspector<T>> getInspectors() {
        return inspectors;
    }

    public List<? extends ObserveInspector<List<T>>> getListInspectors() {
        return listInspectors;
    }

    public List<Trip> getTripList() {
        return tripList;
    }

    @Override
    public final void run() {
        String name = getClass().getSimpleName();
        log.info("Running task: {}", name);
        long t0 = TimeLog.getTime();
        try {
            inspect();
            TIMELOG.log(t0, "inspect", name);
        } catch (Exception e) {
            log.error("Error while inspecting data in database inspector task: {}", name, e);
        }
        if (!AAProperties.isResultsEnabled()) {
            return;
        }
        t0 = TimeLog.getTime();
        Results results = getResults();
        if (!results.isEmpty()) {
            try {
                writeResults(exportDirectoryPath, results);
            } catch (IOException e) {
                log.error("Error while writing results in database inspector task: {}", name, e);
            } finally {
                results.clear();
                TIMELOG.log(t0, "write results", name);
            }
        }
    }

    public void onData(Collection<T> toValidate) throws Exception {
        if (toValidate == null || toValidate.isEmpty()) {
            return;
        }
        for (T datum : toValidate) {
            onDatum(datum);
        }
        if (listInspectors != null) {
            onDataList(toValidate);
        }
    }

    protected void onDatum(T datum) throws Exception {
        for (ObserveInspector<T> inspector : getInspectors()) {
            inspector.set(datum);
            Results results = inspector.execute();
            if (results != null && !results.isEmpty()) {
                getResults().addAll(results);
            }
        }
    }

    protected void onDataList(Collection<T> toValidate) throws Exception {
        List<T> list = List.copyOf(toValidate);
        for (ObserveInspector<List<T>> inspector : getListInspectors()) {
            inspector.set(list);
            Results results = inspector.execute();
            if (results != null && !results.isEmpty()) {
                getResults().addAll(results);
            }
        }
    }

    @Override
    public Results getResults() {
        return (Results) super.getResults();
    }
}
