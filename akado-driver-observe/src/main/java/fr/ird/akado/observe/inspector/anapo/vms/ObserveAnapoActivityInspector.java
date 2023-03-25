package fr.ird.akado.observe.inspector.anapo.vms;

import fr.ird.akado.core.Inspector;
import fr.ird.akado.observe.inspector.ObserveInspector;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.data.ps.logbook.Route;

import java.util.List;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class ObserveAnapoActivityInspector extends ObserveInspector<Activity> {
    private Trip trip;
    private Route route;

    public static List<ObserveAnapoActivityInspector> loadInspectors() {
        return loadInspectors(ObserveAnapoActivityInspector.class);
    }

    public static List<ObserveAnapoActivityInspector> filterInspectors(List<Inspector<?>> inspectors) {
        return filterInspectors(ObserveAnapoActivityInspector.class, inspectors);
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
