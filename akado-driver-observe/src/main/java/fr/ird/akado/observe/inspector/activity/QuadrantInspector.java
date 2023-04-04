package fr.ird.akado.observe.inspector.activity;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.Constant;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.common.message.Message;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.referential.common.Ocean;

import java.util.Objects;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveActivityInspector.class)
public class QuadrantInspector extends ObserveActivityInspector {

    public QuadrantInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the quadrant and the position activity are consistency.";
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();
        Activity activity = get();
        double longitude = activity.getLongitude() == null ? 0 : Double.valueOf(activity.getLongitude());
        double latitude = activity.getLatitude() == null ? 0 : Double.valueOf(activity.getLatitude());
        int quadrant = activity.getQuadrant();
        String activityId = activity.getTopiaId();

        if (latitude == 0 && !(quadrant == 1 || quadrant == 4)) {

            ActivityResult r = createResult(activity, Message.ERROR, Constant.CODE_ACTIVITY_QUADRANT_LAT_INCONSISTENCY, Constant.LABEL_ACTIVITY_QUADRANT_LAT_INCONSISTENCY, true,
                                            activityId,
                                            quadrant);
            results.add(r);
        }
        if (longitude == 0 && !(quadrant == 2 || quadrant == 1)) {
            ActivityResult r = createResult(activity, Message.ERROR, Constant.CODE_ACTIVITY_QUADRANT_LON_INCONSISTENCY, Constant.LABEL_ACTIVITY_QUADRANT_LON_INCONSISTENCY, true,
                                            activityId,
                                            quadrant);
            results.add(r);
        }

        if ((quadrant == 3 || quadrant == 4)
                && Objects.equals(getTrip().getOcean().getCode(), Ocean.INDIEN)) {
            ActivityResult r = createResult(activity, Message.ERROR, Constant.CODE_ACTIVITY_QUADRANT_INCONSISTENCY, Constant.LABEL_ACTIVITY_QUADRANT_INCONSISTENCY, true,
                                            activityId,
                                            quadrant);
            results.add(r);

        }

        if ((quadrant == 3 || quadrant == 4)
                && Objects.equals(Ocean.getOcean(longitude, latitude), Ocean.INDIEN)) {
            ActivityResult r = createResult(activity, Message.ERROR, Constant.CODE_ACTIVITY_QUADRANT_INCONSISTENCY_POSITION, Constant.LABEL_ACTIVITY_QUADRANT_INCONSISTENCY_POSITION, true,
                                            activityId,
                                            quadrant);
            results.add(r);
        }
        return results;
    }
}
