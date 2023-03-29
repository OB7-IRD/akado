package fr.ird.akado.observe.task;

import fr.ird.akado.core.Inspector;
import fr.ird.akado.observe.inspector.activities.ObserveActivityListInspector;
import fr.ird.akado.observe.inspector.activity.ObserveActivityInspector;
import fr.ird.akado.observe.result.Results;
import fr.ird.common.log.LogService;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.data.ps.logbook.Route;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Set;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ActivityTask extends ObserveDataBaseInspectorTask<Activity> {
    private static final Logger log = LogManager.getLogger(ActivityTask.class);

    public ActivityTask(String exportDirectoryPath, List<Trip> tripList, List<Inspector<?>> inspectors, Results r) {
        super(exportDirectoryPath, tripList, r, ObserveActivityInspector.filterInspectors(inspectors), ObserveActivityListInspector.filterInspectors(inspectors));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ObserveActivityInspector> getInspectors() {
        return (List<ObserveActivityInspector>) super.getInspectors();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ObserveActivityListInspector> getListInspectors() {
        return (List<ObserveActivityListInspector>) super.getListInspectors();
    }

    @Override
    public void run() {
        try {
            LogService.getService(this.getClass()).logApplicationInfo("Activity processing...");
            for (Trip trip : getTripList()) {
                getInspectors().forEach(i -> i.setTrip(trip));
                getListInspectors().forEach(i -> i.setTrip(trip));
                for (Route route : trip.getLogbookRoute()) {
                    getInspectors().forEach(i -> i.setRoute(route));
                    getListInspectors().forEach(i -> i.setRoute(route));

                    Set<Activity> toValidate = route.getActivity();
                    onData(toValidate);
                }
            }
            writeResultsInFile();
        } catch (Exception ex) {
            log.error("Error in database inspector task: {}", this, ex);
        }
    }
}
