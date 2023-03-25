package fr.ird.akado.observe.inspector.ps.logbook.activities;

import fr.ird.akado.core.Inspector;
import fr.ird.akado.observe.inspector.ObserveInspector;
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
public abstract class ObserveActivityListInspector extends ObserveInspector<List<Activity>> {
    private Trip trip;
    private Route route;

    public static List<ObserveActivityListInspector> loadInspectors() {
        return loadInspectors(ObserveActivityListInspector.class);
    }

    public static List<ObserveActivityListInspector> filterInspectors(List<Inspector<?>> inspectors) {
        return filterInspectors(ObserveActivityListInspector.class, inspectors);
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
}
