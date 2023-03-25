package fr.ird.akado.observe.task;

import fr.ird.akado.core.Inspector;
import fr.ird.akado.observe.ObserveDataBaseInspector;
import fr.ird.akado.observe.inspector.ps.logbook.well.ObserveWellInspector;
import fr.ird.akado.observe.result.Results;
import fr.ird.common.log.LogService;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Well;
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
public class WellTask extends ObserveDataBaseInspectorTask<Well> {
    private static final Logger log = LogManager.getLogger(WellTask.class);

    public WellTask(String exportDirectoryPath, List<Trip> tripList, List<Inspector<?>> inspectors, Results r) {
        super(exportDirectoryPath, tripList, r, ObserveWellInspector.filterInspectors(inspectors), null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ObserveWellInspector> getInspectors() {
        return (List<ObserveWellInspector>) super.getInspectors();
    }

    @Override
    public void run() {
        try {
            for (Trip trip : getTripList()) {
                getInspectors().forEach(i -> i.setTrip(trip));
                Set<Well> toValidate = trip.getWell();
                onData(toValidate);
            }
            writeResultsInFile();
        } catch (Exception ex) {
            log.error("Error in database inspector task: {}", this, ex);
            LogService.getService(ObserveDataBaseInspector.class).logApplicationError(ex.getMessage());
        }
    }
}
