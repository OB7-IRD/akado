package fr.ird.akado.observe.inspector.well;

import fr.ird.akado.core.Inspector;
import fr.ird.akado.observe.WithTrip;
import fr.ird.akado.observe.inspector.ObserveInspector;
import fr.ird.akado.observe.result.WellResult;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Well;

import java.util.List;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class ObserveWellInspector extends ObserveInspector<Well> implements WithTrip {
    public static List<ObserveWellInspector> loadInspectors() {
        return loadInspectors(ObserveWellInspector.class);
    }

    public static List<ObserveWellInspector> filterInspectors(List<Inspector<?>> inspectors) {
        return filterInspectors(ObserveWellInspector.class, inspectors);
    }

    private Trip trip;

    @Override
    public Trip getTrip() {
        return trip;
    }

    @Override
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    protected WellResult createResult(Well datum, String messageLevel, String messageCode, String messageLabel, boolean inconsistent, Object... parameters) {
        WellResult r = createResult(datum);
        createResult(r, messageLevel, messageCode, messageLabel, inconsistent, parameters);
        return r;
    }

    private WellResult createResult(Well datum) {
        return WithTrip.copy(new WellResult(datum), this);
    }
}
