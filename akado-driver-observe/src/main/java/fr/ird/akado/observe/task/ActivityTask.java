package fr.ird.akado.observe.task;

import fr.ird.akado.core.Inspector;
import fr.ird.akado.observe.inspector.activity.ObserveActivityInspector;
import fr.ird.akado.observe.result.Results;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.data.ps.logbook.Route;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ActivityTask extends ObserveDataBaseInspectorTask<Activity> {

    public ActivityTask(String exportDirectoryPath, List<Trip> tripList, List<Inspector<?>> inspectors, Results r) {
        super(exportDirectoryPath, tripList, r, ObserveActivityInspector.filterInspectors(inspectors), null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ObserveActivityInspector> getInspectors() {
        return (List<ObserveActivityInspector>) super.getInspectors();
    }

    @Override
    protected void inspect() throws Exception {
        for (Trip trip : getTripList()) {
            getInspectors().forEach(i -> i.setTrip(trip));
            for (Route route : trip.getLogbookRoute()) {
                if (rejectDate(route.getDate())) {
                    continue;
                }
                getInspectors().forEach(i -> i.setRoute(route));
                Set<Activity> toValidate = route.getActivity();
                onData(toValidate);
            }
        }
    }

    @Override
    protected void writeResults(String directoryPath, Results results) throws IOException {
        results.writeInActivitySheet(directoryPath);
        results.writeLogs(directoryPath);
    }
}
