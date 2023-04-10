package fr.ird.akado.observe.inspector.activity;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.referential.common.Ocean;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveActivityInspector.class)
public class QuadrantInspector extends ObserveActivityInspector {

    public QuadrantInspector() {
        this.description = "Check if the quadrant and the position activity are consistency.";
    }

    @Override
    public Results execute() throws Exception {
        Activity activity = get();
        if (activity.withoutCoordinate()) {
            return null;
        }
        Ocean ocean = getTrip().getOcean();
        if (ocean.isMultiple() || ocean.isAtlantic()) {
            // on multiple or atlantic ocean no check to do (all quadrant are authorized)
            return null;
        }
        int quadrant = activity.getQuadrant();
        if (!ocean.isIndian() || quadrant == 1 || quadrant == 2) {
            return null;
        }
        // on indian ocean, must be on quadrant 1 or 2
        ActivityResult r = createResult(MessageDescriptions.E1213_ACTIVITY_QUADRANT_INCONSISTENCY, activity,
                                        activity.getID(getTrip(), getRoute()),
                                        quadrant);
        return Results.of(r);
    }
}
