package fr.ird.akado.observe.inspector.ps.logbook.sample;

import fr.ird.akado.core.Inspector;
import fr.ird.akado.observe.inspector.ObserveInspector;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;

import java.util.List;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class ObserveSampleInspector extends ObserveInspector<Sample> {
    private Trip trip;

    public static List<ObserveSampleInspector> loadInspectors() {
        return loadInspectors(ObserveSampleInspector.class);
    }

    public static List<ObserveSampleInspector> filterInspectors(List<Inspector<?>> inspectors) {
        return filterInspectors(ObserveSampleInspector.class, inspectors);
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
