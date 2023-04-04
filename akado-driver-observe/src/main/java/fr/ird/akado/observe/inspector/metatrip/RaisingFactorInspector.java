package fr.ird.akado.observe.inspector.metatrip;

import com.google.auto.service.AutoService;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.observe.Constant;
import fr.ird.akado.observe.result.MetaTripResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.TripResult;
import fr.ird.common.DateTimeUtils;
import fr.ird.common.Utils;
import fr.ird.common.message.Message;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.data.ps.logbook.Catch;
import fr.ird.driver.observe.business.data.ps.logbook.Route;
import fr.ird.driver.observe.business.referential.common.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveTripListInspector.class)
public class RaisingFactorInspector extends ObserveTripListInspector {

    public RaisingFactorInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Calculate the raising factor, " + "with and without the local market, for all trips " + "(included trip with partial landings).";
    }

    public static double RaisingFactor1(List<Trip> trips) {

        double totalCatchesWeight = 0;
        double totalLandingWeight = 0;
        Trip previous = null;
        for (Trip trip : trips) {
            double subTotalCatchesWeight = 0;
            if (trip.withLogbookActivities()) {
                subTotalCatchesWeight += catchesWeight(trip);
            }
            float landingTotalWeight = trip.getLandingTotalWeight();
            if (previous == null || !previous.isPartialLanding()) {
                if (subTotalCatchesWeight == 0 && landingTotalWeight != 0) {
                    return 0;
                }
            }
            totalCatchesWeight += subTotalCatchesWeight;
            totalLandingWeight += landingTotalWeight;
            previous = trip;
        }
        if (totalCatchesWeight == 0) {
            return 0;
        }
        return totalLandingWeight / totalCatchesWeight;
    }
//    public static double RaisingFactor1WithLocalMarket(List<Trip> trips) {
//
//        double totalCatchesWeight = 0;
//        double totalLandingWeight = 0;
//        double totalLocalMarketWeight = 0;
//        for (Trip trip : trips) {
//            double subTotalCatchesWeight = 0;
//            if (!trip.getActivites().isEmpty()) {
//                subTotalCatchesWeight += catchesWeight(trip);
//            }
//
//            if (subTotalCatchesWeight == 0 && trip.getTotalLandingWeight() != 0) {
//                return 0;
//            }
//            totalCatchesWeight += subTotalCatchesWeight;
//            totalLandingWeight += trip.getTotalLandingWeight();
//            totalLocalMarketWeight += trip.getLocalMarketWeight();
//
//        }
//        if (totalCatchesWeight == 0) {
//            return 0;
//        }
//        return (totalLandingWeight + totalLocalMarketWeight) / totalCatchesWeight;
//    }

    @Override
    public Results execute() {
        Results results = new Results();

        if (AAProperties.WARNING_INSPECTOR.equals(AAProperties.DISABLE_VALUE)) {
            return results;
        }

        double rf1 = 0d;
//        double rf1WithLocalMarket = 0d;

        List<Trip> allTrips = get();

        List<List<Trip>> extendedTrips = buildExtendedTrips(allTrips);
        for (List<Trip> trips : extendedTrips) {
            double totalCatchesWeight = 0;
            double totalLandingWeight = 0;
//            boolean hasLogbook = true;
//            double totalLocalMarketWeight = 0;
            String tripID = "";
            Trip previous = null;
            for (Trip trip : trips) {
//                hasLogbook &= trip.hasLogbook();
                double subTotalCatchesWeight = 0;
                if (trip.withLogbookActivities()) {
                    subTotalCatchesWeight += catchesWeight(trip);
                }
                if (previous == null || !previous.isPartialLanding()) {
                    if (subTotalCatchesWeight == 0 && trip.getLandingTotalWeight() != 0) {
                        TripResult r = createResult(trip, Message.ERROR, Constant.CODE_TRIP_NO_CATCH, Constant.LABEL_TRIP_NO_CATCH, true, trip.getTopiaId(), trip.getLandingTotalWeight());
                        results.add(r);
                    }
                }
                totalCatchesWeight += subTotalCatchesWeight;
                totalLandingWeight += trip.getLandingTotalWeight();
//                totalLocalMarketWeight += trip.getLocalMarketWeight();
                tripID += "{" + trip.getVessel().getCode() + " " + TripResult.formatDate(trip.getEndDate()) + "}";

                previous = trip;
            }
            try {
                rf1 = Utils.round(totalLandingWeight / totalCatchesWeight, 3);
            } catch (NumberFormatException ex) {
                rf1 = 0d;
            }

            if (!rf1IsConsistent(rf1) && !(totalCatchesWeight == 0 && previous != null && !previous.isPartialLanding())) {
                MetaTripResult mtr = createResult(trips, Message.WARNING, Constant.CODE_TRIP_RAISING_FACTOR, Constant.LABEL_TRIP_RAISING_FACTOR, true, tripID, rf1);
                mtr.setDataInformation(rf1);
                results.add(mtr);
            }

        }
        return results;
    }

    private static final String[] FRENCH_TARGET_SPECIES = new String[]{"1", "2", "3", "4", "9", "11", "42"};
    private static final String[] SPAIN_TARGET_SPECIES = new String[]{"1", "2", "3", "4", "5", "6", "9", "11", "12", "42", "43"};

    public static final Set<String> FORBIDDEN_VESSEL_ACTIVITY_ID = Set.of(
            //TRANSBORDEMENT_VERS_SENNEUR
            "fr.ird.referential.ps.common.VesselActivity#1464000000000#27",
            // TRANSBORDEMENT
            "fr.ird.referential.ps.common.VesselActivity#1464000000000#25",
            // TRANSBORDEMENT_VERS_CANNEUR
            "fr.ird.referential.ps.common.VesselActivity#1464000000000#29"
    );

    private static boolean in(String elt, String[] array) {
        for (String s : array) {
            if (Objects.equals(elt, s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean rf1IsConsistent(double rf1) {
        return rf1 >= 0.9 && rf1 <= 1.1;
    }

    public static double catchesWeight(Trip trip) {
        double subTotalCatchesWeight = 0;
        for (Route route : trip.getLogbookRoute()) {

            for (Activity activity : route.getActivity()) {
                String vesselActivityId = activity.getVesselActivity().getTopiaId();
                if (FORBIDDEN_VESSEL_ACTIVITY_ID.contains(vesselActivityId)) {
                    continue;
                }
                subTotalCatchesWeight += RaisingFactorInspector.catchesFilter(trip, activity);
            }
        }
        return subTotalCatchesWeight;
    }

    public static double catchesFilter(Trip trip, Activity activity) {
        double catchesWeight = 0;
        if (Objects.equals(trip.getVessel().getFlagCountry().getCode(), Country.AVDTH_CODE_COUNTRY_FRANCE)) {
            for (Catch aCatch : activity.getCatches()) {
                if (aCatch.getWeightCategory() != null && aCatch.getWeightCategory().getSpecies() != null && in(aCatch.getWeightCategory().getSpecies().getCode(), FRENCH_TARGET_SPECIES)) {
                    catchesWeight += aCatch.getWeight();
                }
            }
        } else if (Objects.equals(trip.getVessel().getFlagCountry().getCode(), Country.AVDTH_CODE_COUNTRY_SPAIN)) {
            for (Catch aCatch : activity.getCatches()) {
                if (aCatch.getWeightCategory() != null && aCatch.getWeightCategory().getSpecies() != null && in(aCatch.getWeightCategory().getSpecies().getCode(), SPAIN_TARGET_SPECIES)) {
                    catchesWeight += aCatch.getWeight();
                }
            }
        } else {
            for (Catch aCatch : activity.getCatches()) {
                catchesWeight += aCatch.getWeight();
            }
        }
        return catchesWeight;
    }

    public static List<List<Trip>> buildExtendedTrips(List<Trip> trips) {
        List<List<Trip>> extendedTrips = new ArrayList<>();
        List<Trip> extendedTrip = new ArrayList<>();
        Trip previous = null;
        for (Trip trip : trips) {
            if (previous == null && !trip.isPartialLanding()) {
                extendedTrip.add(trip);
                extendedTrips.add(extendedTrip);
                extendedTrip = new ArrayList<>();
                continue;
            }
            if (previous == null && trip.isPartialLanding()) {
                extendedTrip.add(trip);
                previous = trip;
                continue;
            }
            if (!Objects.equals(previous.getVessel(), trip.getVessel())) {
                extendedTrips.add(extendedTrip);
                extendedTrip = new ArrayList<>();
                extendedTrip.add(trip);
                if (trip.isPartialLanding()) {
                    previous = trip;
                } else {
                    extendedTrips.add(extendedTrip);
                    extendedTrip = new ArrayList<>();
                    previous = null;
                }
                continue;
            }
            if (previous.getVessel().equals(trip.getVessel()) && DateTimeUtils.dateAfter(trip.getEndDate(), previous.getEndDate())) {
                extendedTrip.add(trip);

                if (trip.isPartialLanding()) {
                    previous = trip;
                } else {
                    extendedTrips.add(extendedTrip);
                    extendedTrip = new ArrayList<>();
                    previous = null;
                }
            }
        }
        return extendedTrips;
    }

}