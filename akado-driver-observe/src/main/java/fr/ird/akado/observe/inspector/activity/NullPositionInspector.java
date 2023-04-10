package fr.ird.akado.observe.inspector.activity;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;

/**
 * Created on 06/04/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 * See <a href="https://gitlab.com/ultreiaio/ird-observe/-/issues/2663">ObServe issue 2663</a>
 */
@AutoService(ObserveActivityInspector.class)
public class NullPositionInspector extends ObserveActivityInspector {
    public NullPositionInspector() {
        this.description = "Check when activity has no coordinate that we are on a FOB operation with lost or end of transmission.";
    }

    @Override
    public Results execute() throws Exception {
        Activity activity = get();
        boolean activityRequiresCoordinate = activity.requiresCoordinate();
        if (activityRequiresCoordinate) {
            ActivityResult r = createResult(MessageDescriptions.E1231_ACTIVITY_POSITION_INCONSISTENCY, activity,
                                            activity.getID(getTrip(), getRoute()),
                                            activity.getVesselActivity().getLabel2());
            return Results.of(r);
        }
        return null;
    }
}
