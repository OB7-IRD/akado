package fr.ird.akado.observe.inspector.activity;

import com.google.auto.service.AutoService;
import fr.ird.akado.core.spatial.GISHandler;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.common.OTUtils;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.referential.common.Ocean;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveActivityInspector.class)
public class PositionInspector extends ObserveActivityInspector {

    public static boolean inIndianOcean(Activity activity) {
        if (activity.getPositionInIndianOcean() != null) {
            return activity.getPositionInIndianOcean().get();
        }
        if (activity.withoutCoordinate()) {
            return false;
        }
        return GISHandler.getService().inIndianOcean(activity.getLongitude(), activity.getLatitude());
    }

    public static boolean inAtlanticOcean(Activity activity) {
        if (activity.getPositionInAtlanticOcean() != null) {
            return activity.getPositionInAtlanticOcean().get();
        }
        if (activity.withoutCoordinate()) {
            return false;
        }
        return GISHandler.getService().inAtlanticOcean(activity.getLongitude(), activity.getLatitude());
    }

    public static String inLand(Activity activity) {
        if (activity.getPositionInLand() != null) {
            return activity.getPositionInLand().get();
        }
        if (activity.withoutCoordinate()) {
            return null;
        }
        return GISHandler.getService().inLand(activity.getLongitude(), activity.getLatitude());
    }

    public PositionInspector() {
        this.description = "Check if the position activity are in ocean or inland, "
                + "and if the position activity and ocean are consistent.";
    }

    @Override
    public Results execute() throws Exception {
        Activity activity = get();
        if (activity.withoutCoordinate()) {
            activity.setPositionInAtlanticOcean(() -> false);
            activity.setPositionInIndianOcean(() -> false);
            activity.setPositionInLand(() -> null);
            return null;
        }
        double longitude = Double.valueOf(activity.getLongitude());
        double latitude = Double.valueOf(activity.getLatitude());
        GISHandler service = GISHandler.getService();
        boolean inHarbour = service.inHarbour(longitude, latitude);
        if (inHarbour) {
            return null;
        }
        boolean inIO = service.inIndianOcean(longitude, latitude);
        if (inIO) {
            activity.setPositionInAtlanticOcean(() -> false);
            activity.setPositionInIndianOcean(() -> true);
            activity.setPositionInLand(() -> null);
        }
        boolean inAO = !inIO && service.inAtlanticOcean(longitude, latitude);
        if (inAO) {
            activity.setPositionInAtlanticOcean(() -> true);
            activity.setPositionInIndianOcean(() -> false);
            activity.setPositionInLand(() -> null);
        }
        if (!inIO && !inAO) {
            String country = service.inLand(longitude, latitude);
            if (country != null) {

                activity.setPositionInAtlanticOcean(() -> false);
                activity.setPositionInIndianOcean(() -> false);
                activity.setPositionInLand(() -> country);

                // not on a ocean
                ActivityResult r = createResult(MessageDescriptions.E_1214_ACTIVITY_POSITION_NOT_IN_OCEAN, activity,
                                                activity.getID(getTrip(), getRoute()),
                                                "(" + OTUtils.degreesDecimalToStringDegreesMinutes(latitude, true) + "/" + OTUtils.degreesDecimalToStringDegreesMinutes(longitude, false) + ")",
                                                country);
                return Results.of(r);
            }
            activity.setPositionInAtlanticOcean(() -> false);
            activity.setPositionInIndianOcean(() -> false);
            activity.setPositionInLand(() -> null);
            // not in IO or AO
            ActivityResult r = createResult(MessageDescriptions.W_1217_ACTIVITY_POSITION_WEIRD, activity,
                                            activity.getID(getTrip(), getRoute()),
                                            "(" + OTUtils.degreesDecimalToStringDegreesMinutes(latitude, true) + "/" + OTUtils.degreesDecimalToStringDegreesMinutes(longitude, false) + ")");
            return Results.of(r);
        }
        if ((getTrip().getOcean().isIndian() && !inIO) || (getTrip().getOcean().isAtlantic() && !inAO)) {
            String oceanCode = Ocean.getOcean(longitude, latitude);
            ActivityResult r = createResult(MessageDescriptions.E_1212_ACTIVITY_OCEAN_INCONSISTENCY, activity,
                                            activity.getID(getTrip(), getRoute()),
                                            getTrip().getOcean().getCode(),
                                            oceanCode);
            r.setValueObtained(getTrip().getOcean().getCode());
            r.setValueExpected(oceanCode);
            return Results.of(r);
        }
        return null;
    }
}
