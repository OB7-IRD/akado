package fr.ird.akado.observe.inspector.activity;

import com.google.auto.service.AutoService;
import fr.ird.akado.core.spatial.GISHandler;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.common.OTUtils;
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
        Activity activity = get();
        Double longitude = activity.getLongitude() == null ? null : Double.valueOf(activity.getLongitude());
        Double latitude = activity.getLatitude() == null ? null : Double.valueOf(activity.getLatitude());
        if (longitude == null || latitude == null) {
            //FIXME tester ce cas là https://gitlab.com/ultreiaio/ird-observe/-/issues/2663
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
                ActivityResult r = createResult(MessageDescriptions.E_1214_ACTIVITY_POSITION_NOT_IN_OCEAN, activity,
                                                activity.getID(getTrip(), getRoute()),
                                                "(" + OTUtils.degreesDecimalToStringDegreesMinutes(latitude, true) + "/" + OTUtils.degreesDecimalToStringDegreesMinutes(longitude, false) + ")",
                                                country);
                results.add(r);
            } else {
                ActivityResult r = createResult(MessageDescriptions.W_1217_ACTIVITY_POSITION_WEIRD, activity,
                                                activity.getID(getTrip(), getRoute()),
                                                "(" + OTUtils.degreesDecimalToStringDegreesMinutes(latitude, true) + "/" + OTUtils.degreesDecimalToStringDegreesMinutes(longitude, false) + ")");
                results.add(r);
            }
        } else if ((Objects.equals(getTrip().getOcean().getCode(), Ocean.INDIEN) && !inIO) || (Objects.equals(getTrip().getOcean().getCode(), Ocean.ATLANTIQUE) && !inAO)) {
            ActivityResult r = createResult(MessageDescriptions.E_1212_ACTIVITY_OCEAN_INCONSISTENCY, activity,
                                            activity.getID(getTrip(), getRoute()),
                                            getTrip().getOcean().getCode(),
                                            Ocean.getOcean(longitude, latitude));

            r.setValueObtained(getTrip().getOcean().getCode());
            r.setValueExpected(Ocean.getOcean(longitude, latitude));
            results.add(r);
        }
        return results;
    }
}
