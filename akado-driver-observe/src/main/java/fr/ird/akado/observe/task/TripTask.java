package fr.ird.akado.observe.task;

import fr.ird.akado.core.Inspector;
import fr.ird.akado.observe.inspector.metatrip.ObserveTripListInspector;
import fr.ird.akado.observe.inspector.trip.ObserveTripInspector;
import fr.ird.akado.observe.result.Results;
import fr.ird.driver.observe.business.data.ps.common.Trip;

import java.io.IOException;
import java.util.List;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class TripTask extends ObserveDataBaseInspectorTask<Trip> {

    public TripTask(String exportDirectoryPath, List<Trip> tripList, List<Inspector<?>> inspectors, Results r) {
        super(exportDirectoryPath, tripList, r, ObserveTripInspector.filterInspectors(inspectors), ObserveTripListInspector.filterInspectors(inspectors));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ObserveTripInspector> getInspectors() {
        return (List<ObserveTripInspector>) super.getInspectors();
    }

    @Override
    protected void inspect() throws Exception {
        List<Trip> toValidate = getTripList();
        onData(toValidate);
    }

    @Override
    protected void writeResults(String directoryPath, Results results) throws IOException {
        results.writeInTripSheet(directoryPath);
        results.writeInMetaTripSheet(directoryPath);
        results.writeLogs(directoryPath);
    }
}
