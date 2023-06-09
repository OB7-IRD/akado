package fr.ird.akado.observe.task;

import fr.ird.akado.core.Inspector;
import fr.ird.akado.observe.inspector.sample.ObserveSampleInspector;
import fr.ird.akado.observe.result.Results;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class SampleTask extends ObserveDataBaseInspectorTask<Sample> {

    public SampleTask(String exportDirectoryPath, List<Trip> tripList, List<Inspector<?>> inspectors, Results r) {
        super(exportDirectoryPath, tripList, r, ObserveSampleInspector.filterInspectors(inspectors), null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ObserveSampleInspector> getInspectors() {
        return (List<ObserveSampleInspector>) super.getInspectors();
    }

    @Override
    protected void inspect() throws Exception {
        for (Trip trip : getTripList()) {
            getInspectors().forEach(i -> i.setTrip(trip));
            Set<Sample> toValidate = trip.getLogbookSample();
            onData(toValidate);
        }
    }

    @Override
    protected void writeResults(String directoryPath, Results results) throws IOException {
        results.writeInSampleSheet(directoryPath);
        results.writeLogs(directoryPath);
    }
}
