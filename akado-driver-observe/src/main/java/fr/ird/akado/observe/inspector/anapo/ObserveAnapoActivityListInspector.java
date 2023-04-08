package fr.ird.akado.observe.inspector.anapo;

import fr.ird.akado.core.Inspector;
import fr.ird.akado.core.common.MessageDescription;
import fr.ird.akado.observe.inspector.ObserveInspector;
import fr.ird.akado.observe.result.AnapoResult;
import fr.ird.akado.observe.result.object.Anapo;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;

import java.util.List;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public abstract class ObserveAnapoActivityListInspector extends ObserveInspector<List<Activity>> {

    public static List<ObserveAnapoActivityListInspector> loadInspectors() {
        return loadInspectors(ObserveAnapoActivityListInspector.class);
    }

    public static List<ObserveAnapoActivityListInspector> filterInspectors(List<Inspector<?>> inspectors) {
        return filterInspectors(ObserveAnapoActivityListInspector.class, inspectors);
    }

    protected AnapoResult createResult(MessageDescription messageDescription, Anapo datum, Object... parameters) {
        AnapoResult r = createResult(datum, messageDescription);
        createResult(r, parameters);
        return r;
    }

    private AnapoResult createResult(Anapo datum, MessageDescription messageDescription) {
        return new AnapoResult(datum, messageDescription);
    }
}
