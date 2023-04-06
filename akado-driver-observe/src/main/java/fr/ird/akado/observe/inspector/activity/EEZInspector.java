package fr.ird.akado.observe.inspector.activity;

import com.google.auto.service.AutoService;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
//FIXME voir avec Pascal si on garde, mais attention le message n'est pas bon du tout
@AutoService(ObserveActivityInspector.class)
public class EEZInspector extends ObserveActivityInspector {
    public static boolean operationAndEEZInconsistent(Activity a) {
        return a.getFpaZone() == null && a.getVesselActivity().getCode().equals("6");
    }

    public EEZInspector() {
        this.description = "Check if the EEZ reported is consistent with operation.";
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();
        if (!AAProperties.isWarningInspectorEnabled()) {
            return results;
        }
        Activity activity = get();
        if (operationAndEEZInconsistent(activity)) {
            ActivityResult r = createResult(MessageDescriptions.W_1232_ACTIVITY_OPERATION_EEZ_INCONSISTENCY, activity,
                                            activity.getID(getTrip(), getRoute()),
                                            activity.getVesselActivity().getCode(),
                                            activity.getSetCount());
            results.add(r);
        }
        return results;
    }
}
