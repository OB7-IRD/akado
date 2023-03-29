package fr.ird.akado.observe.inspector.metatrip;

import fr.ird.akado.core.Inspector;
import fr.ird.akado.observe.inspector.ObserveInspector;
import fr.ird.akado.observe.result.MetaTripResult;
import fr.ird.akado.observe.result.TripResult;
import fr.ird.driver.observe.business.data.ps.common.Trip;

import java.util.List;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class ObserveTripListInspector extends ObserveInspector<List<Trip>> {
    public static List<ObserveTripListInspector> loadInspectors() {
        return loadInspectors(ObserveTripListInspector.class);
    }

    public static List<ObserveTripListInspector> filterInspectors(List<Inspector<?>> inspectors) {
        return filterInspectors(ObserveTripListInspector.class, inspectors);
    }

    protected MetaTripResult createResult(List<Trip> datum, String messageLevel, String messageCode, String messageLabel, boolean inconsistent, Object... parameters) {
        MetaTripResult r = createResult(datum);
        createResult(r, messageLevel, messageCode, messageLabel, inconsistent, parameters);
        return r;
    }

    private MetaTripResult createResult(List<Trip> datum) {
        return new MetaTripResult(datum);
    }


    protected TripResult createResult(Trip datum, String messageLevel, String messageCode, String messageLabel, boolean inconsistent, Object... parameters) {
        TripResult r = createResult(datum);
        createResult(r, messageLevel, messageCode, messageLabel, inconsistent, parameters);
        return r;
    }

    private TripResult createResult(Trip datum) {
        return new TripResult(datum);
    }
}
