package fr.ird.akado.observe.inspector.activity;

import fr.ird.akado.core.Inspector;
import fr.ird.akado.observe.WithRoute;
import fr.ird.akado.observe.inspector.ObserveInspector;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.data.ps.logbook.Route;

import java.util.List;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class ObserveActivityInspector extends ObserveInspector<Activity> implements WithRoute {
    private Trip trip;
    private Route route;

    public static List<ObserveActivityInspector> loadInspectors() {
        return loadInspectors(ObserveActivityInspector.class);
    }

    public static List<ObserveActivityInspector> filterInspectors(List<Inspector<?>> inspectors) {
        return filterInspectors(ObserveActivityInspector.class, inspectors);
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    protected ActivityResult createResult(Activity datum, String messageLevel, String messageCode, String messageLabel, boolean inconsistent, Object... parameters) {
        ActivityResult r = createResult(datum);
        createResult(r, messageLevel, messageCode, messageLabel, inconsistent, parameters);
        return r;
    }

    private ActivityResult createResult(Activity datum) {
        return WithRoute.copy(new ActivityResult(datum), this);
    }
}
