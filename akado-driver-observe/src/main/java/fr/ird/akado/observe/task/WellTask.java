package fr.ird.akado.observe.task;

import fr.ird.akado.core.Inspector;
import fr.ird.akado.observe.inspector.well.ObserveWellInspector;
import fr.ird.akado.observe.result.Results;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Well;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class WellTask extends ObserveDataBaseInspectorTask<Well> {

    public WellTask(String exportDirectoryPath, List<Trip> tripList, List<Inspector<?>> inspectors, Results r) {
        super(exportDirectoryPath, tripList, r, ObserveWellInspector.filterInspectors(inspectors), null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ObserveWellInspector> getInspectors() {
        return (List<ObserveWellInspector>) super.getInspectors();
    }

    @Override
    protected void inspect() throws Exception {
        for (Trip trip : getTripList()) {
            getInspectors().forEach(i -> i.setTrip(trip));
            Set<Well> toValidate = trip.getWell();
            onData(toValidate);
        }
    }

    @Override
    protected void writeResults(String directoryPath, Results results) throws IOException {
        results.writeInWellSheet(directoryPath);
        results.writeLogs(directoryPath);
    }
}
