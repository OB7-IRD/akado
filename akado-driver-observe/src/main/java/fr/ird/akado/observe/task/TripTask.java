package fr.ird.akado.observe.task;

import fr.ird.akado.core.Inspector;
import fr.ird.akado.observe.inspector.ps.common.ObserveTripInspector;
import fr.ird.akado.observe.inspector.ps.common.ObserveTripListInspector;
import fr.ird.akado.observe.result.Results;
import fr.ird.common.log.LogService;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class TripTask extends ObserveDataBaseInspectorTask<Trip> {
    private static final Logger log = LogManager.getLogger(TripTask.class);

    public TripTask(String exportDirectoryPath, List<Trip> tripList, List<Inspector<?>> inspectors, Results r) {
        super(exportDirectoryPath, tripList, r, ObserveTripInspector.filterInspectors(inspectors), ObserveTripListInspector.filterInspectors(inspectors));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ObserveTripInspector> getInspectors() {
        return (List<ObserveTripInspector>) super.getInspectors();
    }

    @Override
    public void run() {
        try {
            LogService.getService(this.getClass()).logApplicationInfo("Trip processing...");
            List<Trip> toValidate = getTripList();
            onData(toValidate);
            writeResultsInFile();
        } catch (Exception ex) {
            log.error("Error in database inspector task: {}", this, ex);
        }
    }
}
