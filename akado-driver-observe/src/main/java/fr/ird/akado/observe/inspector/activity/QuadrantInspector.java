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
        Activity activite = get();
        Double longitude = activite.getLongitude() == null ? null : Double.valueOf(activite.getLongitude());
        Double latitude = activite.getLatitude() == null ? null : Double.valueOf(activite.getLatitude());
        if (longitude == null || latitude == null) {
            return results;
        }
        if (latitude == 0 && !(activite.getQuadrant() == 1 || activite.getQuadrant() == 4)) {

            ActivityResult r = createResult(activite, Message.ERROR, Constant.CODE_ACTIVITY_QUADRANT_LAT_INCONSISTENCY, Constant.LABEL_ACTIVITY_QUADRANT_LAT_INCONSISTENCY, true,
                                            activite.getTopiaId(),
                                            activite.getQuadrant());
            results.add(r);
        }
        if (longitude == 0 && !(activite.getQuadrant() == 2 || activite.getQuadrant() == 1)) {
            ActivityResult r = createResult(activite, Message.ERROR, Constant.CODE_ACTIVITY_QUADRANT_LON_INCONSISTENCY, Constant.LABEL_ACTIVITY_QUADRANT_LON_INCONSISTENCY, true,
                                            activite.getTopiaId(),
                                            activite.getQuadrant());
            results.add(r);
        }

        if ((activite.getQuadrant() == 3 || activite.getQuadrant() == 4)
                && Objects.equals(getTrip().getOcean().getCode(), Ocean.INDIEN)) {
            ActivityResult r = createResult(activite, Message.ERROR, Constant.CODE_ACTIVITY_QUADRANT_INCONSISTENCY, Constant.LABEL_ACTIVITY_QUADRANT_INCONSISTENCY, true,
                                            activite.getTopiaId(),
                                            activite.getQuadrant());
            results.add(r);

        }

        if ((activite.getQuadrant() == 3 || activite.getQuadrant() == 4)
                && Objects.equals(Ocean.getOcean(longitude, latitude), Ocean.INDIEN)) {
            ActivityResult r = createResult(activite, Message.ERROR, Constant.CODE_ACTIVITY_QUADRANT_INCONSISTENCY_POSITION, Constant.LABEL_ACTIVITY_QUADRANT_INCONSISTENCY_POSITION, true,
                                            activite.getTopiaId(),
                                            activite.getQuadrant());
            results.add(r);
        }
        return results;
    }
}
