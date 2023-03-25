package fr.ird.akado.observe.inspector.ps.common;

import fr.ird.akado.core.Inspector;
import fr.ird.akado.observe.inspector.ObserveInspector;
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
}
