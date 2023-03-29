package fr.ird.akado.observe.inspector.anapo;

import fr.ird.akado.core.Inspector;
import fr.ird.akado.observe.WithRoute;
import fr.ird.akado.observe.inspector.ObserveInspector;
import fr.ird.akado.observe.result.AnapoResult;
import fr.ird.akado.observe.result.object.Anapo;
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
public abstract class ObserveAnapoActivityInspector extends ObserveInspector<Activity> implements WithRoute {
    private Trip trip;
    private Route route;

    public static List<ObserveAnapoActivityInspector> loadInspectors() {
        return loadInspectors(ObserveAnapoActivityInspector.class);
    }

    public static List<ObserveAnapoActivityInspector> filterInspectors(List<Inspector<?>> inspectors) {
        return filterInspectors(ObserveAnapoActivityInspector.class, inspectors);
    }

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

    protected AnapoResult createResult(Anapo datum, String messageLevel, String messageCode, String messageLabel, boolean inconsistent, Object... parameters) {
        AnapoResult r = createResult(datum);
        createResult(r, messageLevel, messageCode, messageLabel, inconsistent, parameters);
        return r;
    }

    private AnapoResult createResult(Anapo datum) {
        return WithRoute.copy(new AnapoResult(datum), this);
    }
}
