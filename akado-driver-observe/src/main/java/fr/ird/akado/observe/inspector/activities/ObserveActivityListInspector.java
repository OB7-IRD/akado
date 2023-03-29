package fr.ird.akado.observe.inspector.activities;

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
public abstract class ObserveActivityListInspector extends ObserveInspector<List<Activity>> implements WithRoute {


    public static List<ObserveActivityListInspector> loadInspectors() {
        return loadInspectors(ObserveActivityListInspector.class);
    }

    public static List<ObserveActivityListInspector> filterInspectors(List<Inspector<?>> inspectors) {
        return filterInspectors(ObserveActivityListInspector.class, inspectors);
    }
    private Trip trip;
    private Route route;

    @Override
    public Trip getTrip() {
        return trip;
    }

    @Override
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @Override
    public Route getRoute() {
        return route;
    }

    @Override
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
