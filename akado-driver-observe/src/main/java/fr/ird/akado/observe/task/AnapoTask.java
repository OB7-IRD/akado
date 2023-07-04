package fr.ird.akado.observe.task;

import fr.ird.akado.core.Inspector;
import fr.ird.akado.observe.inspector.anapo.ObserveAnapoActivityInspector;
import fr.ird.akado.observe.inspector.anapo.ObserveAnapoActivityListInspector;
import fr.ird.akado.observe.result.Results;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.data.ps.logbook.Route;

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class AnapoTask extends ObserveDataBaseInspectorTask<Activity> {

    public AnapoTask(String exportDirectoryPath, List<Trip> tripList, List<Inspector<?>> inspectors, Results r) {
        super(exportDirectoryPath, tripList, r, ObserveAnapoActivityInspector.filterInspectors(inspectors), ObserveAnapoActivityListInspector.filterInspectors(inspectors));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ObserveAnapoActivityInspector> getInspectors() {
        return (List<ObserveAnapoActivityInspector>) super.getInspectors();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ObserveAnapoActivityListInspector> getListInspectors() {
        return (List<ObserveAnapoActivityListInspector>) super.getListInspectors();
    }

    @Override
    protected void inspect() throws Exception {
        List<Activity> allActivities = new LinkedList<>();
        for (Trip trip : getTripList()) {
            for (Route route : trip.getLogbookRoute()) {
                if (rejectDate(route.getDate())) {
                    continue;
                }
                Set<Activity> toValidate = route.getActivity();
                allActivities.addAll(toValidate);
                for (Activity activity : toValidate) {
                    activity.setLandingDate(trip.getEndDate());
                    activity.setVessel(trip.getVessel());
                    activity.setDate(route.getDate());
                    onDatum(activity);
                }
            }
        }
        // Sort activities by vessel code, then by activity full date
        allActivities.sort(Comparator.comparing(a -> ((Activity) a).getVessel().getCode()).thenComparing(a -> ((Activity) a).getFullDate()));
        onDataList(allActivities);
    }

    @Override
    protected void writeResults(String directoryPath, Results results) throws IOException {
        results.writeInAnapoSheet(directoryPath);
        results.writeLogs(directoryPath);
    }
}
