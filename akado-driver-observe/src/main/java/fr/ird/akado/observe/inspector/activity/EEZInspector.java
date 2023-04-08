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
@AutoService(ObserveActivityInspector.class)
public class EEZInspector extends ObserveActivityInspector {

    public EEZInspector() {
        this.description = "Check if the EEZ reported is consistent with operation.";
    }

    @Override
    public Results execute() throws Exception {
        if (!AAProperties.isWarningInspectorEnabled()) {
            return null;
        }
        Activity activity = get();
        if (activity.getVesselActivity().isFishing() && activity.withoutFpaZone()) {
            ActivityResult r = createResult(MessageDescriptions.W_1232_ACTIVITY_OPERATION_EEZ_INCONSISTENCY, activity,
                                            activity.getID(getTrip(), getRoute()),
                                            activity.getVesselActivity().getCode());
            return Results.of(r);
        }
        return null;
    }
}
