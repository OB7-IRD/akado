package fr.ird.akado.observe.inspector.activity;

import com.google.auto.service.AutoService;
import fr.ird.akado.core.spatial.GISHandler;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.common.OTUtils;
import fr.ird.common.message.Message;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.referential.common.Ocean;

import java.util.Objects;

import static fr.ird.akado.observe.Constant.CODE_ACTIVITY_OCEAN_INCONSISTENCY;
import static fr.ird.akado.observe.Constant.CODE_ACTIVITY_POSITION_NOT_IN_OCEAN;
import static fr.ird.akado.observe.Constant.CODE_ACTIVITY_POSITION_WEIRD;
import static fr.ird.akado.observe.Constant.LABEL_ACTIVITY_OCEAN_INCONSISTENCY;
import static fr.ird.akado.observe.Constant.LABEL_ACTIVITY_POSITION_NOT_IN_OCEAN;
import static fr.ird.akado.observe.Constant.LABEL_ACTIVITY_POSITION_WEIRD;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveActivityInspector.class)
public class PositionInspector extends ObserveActivityInspector {

    public static boolean inIndianOcean(Activity activity) {
        return GISHandler.getService().inIndianOcean(
                activity.getLongitude(),
                activity.getLatitude());
    }

    public static boolean inAtlanticOcean(Activity activity) {
        return GISHandler.getService().inAtlanticOcean(
                activity.getLongitude(),
                activity.getLatitude());
    }

    public static Boolean onCoastLine(Activity activity) {
        return GISHandler.getService().onCoastLine(
                activity.getLongitude(),
                activity.getLatitude());
    }

    public static String inLand(Activity activity) {
        return GISHandler.getService().inLand(
                activity.getLongitude(),
                activity.getLatitude());
    }

    public PositionInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the position activity are in ocean or inland, "
                + "and if the position activity and ocean are consistent.";
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
        boolean inIO = GISHandler.getService().inIndianOcean(
                longitude,
                latitude);
        boolean inAO = GISHandler.getService().inAtlanticOcean(
                longitude,
                latitude);
        boolean inHarbour = GISHandler.getService().inHarbour(
                longitude,
                latitude);
        if (inHarbour) {
            return results;
        }

        if (!inIO && !inAO) {
            String country = GISHandler.getService().inLand(longitude, latitude);

            if (country != null) {
                ActivityResult r = createResult(activite, Message.ERROR, CODE_ACTIVITY_POSITION_NOT_IN_OCEAN, LABEL_ACTIVITY_POSITION_NOT_IN_OCEAN, false,
                                                activite.getTopiaId(),
                                                "(" + OTUtils.degreesDecimalToStringDegreesMinutes(latitude, true) + "/" + OTUtils.degreesDecimalToStringDegreesMinutes(longitude, false) + ")",
                                                country);
                results.add(r);
            } else {
                ActivityResult r = createResult(activite, Message.WARNING, CODE_ACTIVITY_POSITION_WEIRD, LABEL_ACTIVITY_POSITION_WEIRD, false,
                                                activite.getTopiaId(),
                                                "(" + OTUtils.degreesDecimalToStringDegreesMinutes(latitude, true) + "/" + OTUtils.degreesDecimalToStringDegreesMinutes(longitude, false) + ")");
                results.add(r);
            }
        } else if ((Objects.equals(getTrip().getOcean().getCode(), Ocean.INDIEN) && !inIO) || (Objects.equals(getTrip().getOcean().getCode(), Ocean.ATLANTIQUE) && !inAO)) {
            ActivityResult r = createResult(activite, Message.ERROR, CODE_ACTIVITY_OCEAN_INCONSISTENCY, LABEL_ACTIVITY_OCEAN_INCONSISTENCY, false,
                                            activite.getTopiaId(),
                                            getTrip().getOcean().getCode(),
                                            Ocean.getOcean(longitude, latitude));

            r.setValueObtained(getTrip().getOcean().getCode());
            r.setValueExpected(Ocean.getOcean(longitude, latitude));
            results.add(r);
        }
        return results;
    }
}
