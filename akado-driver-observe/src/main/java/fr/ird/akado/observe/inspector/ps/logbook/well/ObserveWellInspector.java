package fr.ird.akado.observe.inspector.ps.logbook.well;

import fr.ird.akado.core.Inspector;
import fr.ird.akado.observe.inspector.ObserveInspector;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Well;

import java.util.List;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class ObserveWellInspector extends ObserveInspector<Well> {
    private Trip trip;

    public static List<ObserveWellInspector> loadInspectors() {
        return loadInspectors(ObserveWellInspector.class);
    }

    public static List<ObserveWellInspector> filterInspectors(List<Inspector<?>> inspectors) {
        return filterInspectors(ObserveWellInspector.class, inspectors);
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
