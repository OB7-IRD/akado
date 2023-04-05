package fr.ird.akado.observe.inspector.trip;

import fr.ird.akado.core.Inspector;
import fr.ird.akado.core.common.MessageDescription;
import fr.ird.akado.observe.inspector.ObserveInspector;
import fr.ird.akado.observe.result.TripResult;
import fr.ird.driver.observe.business.data.ps.common.Trip;

import java.util.List;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class ObserveTripInspector extends ObserveInspector<Trip> {
    public static List<ObserveTripInspector> loadInspectors() {
        return loadInspectors(ObserveTripInspector.class);
    }

    public static List<ObserveTripInspector> filterInspectors(List<Inspector<?>> inspectors) {
        return filterInspectors(ObserveTripInspector.class, inspectors);
    }

    protected TripResult createResult(MessageDescription messageDescription, Trip datum, Object... parameters) {
        TripResult r = createResult(datum, messageDescription);
        createResult(r, parameters);
        return r;
    }

    private TripResult createResult(Trip datum, MessageDescription messageDescription) {
        return new TripResult(datum, messageDescription);
    }
}
