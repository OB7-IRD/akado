package fr.ird.akado.observe.inspector.metatrip;

import com.google.auto.service.AutoService;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.MetaTripResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.TripResult;
import fr.ird.common.DateTimeUtils;
import fr.ird.common.Utils;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.data.ps.logbook.Catch;
import fr.ird.driver.observe.business.data.ps.logbook.Route;
import fr.ird.driver.observe.business.referential.common.Species;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveTripListInspector.class)
public class RaisingFactorInspector extends ObserveTripListInspector {

    // Replaced by method isFrenchTargetSpecies
//    private static final String[] FRENCH_TARGET_SPECIES = new String[]{"1", "2", "3", "4", "9", "11", "42"};
    private static boolean isFrenchTargetSpecies(Species species) {
        return species.isYFT() // avdth code 1
                || species.isSKJ() // avdth code 2
                || species.isBET() // avdth code 3
                || species.isALB() // avdth code 4
                || species.isTUN() // avdth code 9
                || species.isLOT() // avdth code 11
                ;
    }

    // Replaced by method isSpainTargetSpecies
//    private static final String[] SPAIN_TARGET_SPECIES = new String[]{"1", "2", "3", "4", "5", "6", "9", "11", "12", "42", "43"};
    private static boolean isSpainTargetSpecies(Species species) {
        return species.isYFT() // avdth code 1
                || species.isSKJ() // avdth code 2
                || species.isBET() // avdth code 3
                || species.isALB() // avdth code 4
                || species.isALB() // avdth code 5
                || species.isALB() // avdth code 6
                || species.isTUN() // avdth code 9
                || species.isLOT() // avdth code 11
                || species.isBLF() // avdth code 12
                || species.isRAV() // avdth code 43 (is deprecated but still in usage in old AVDTH databases)
                // See https://github.com/OB7-IRD/akado/issues/7
                || species.isLTA() // replace RAV*
                || species.isKAW() // replace RAV*
                || species.isFRZ() // replace RAV*
                || species.isBLT() // replace RAV*
                || species.isFRI() // replace RAV*
                ;
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

    public static double RaisingFactor1(List<Trip> trips) {

        double totalCatchesWeight = 0;
        double totalLandingWeight = 0;
        Trip previous = null;
        for (Trip trip : trips) {
            double subTotalCatchesWeight = 0;
            if (trip.withLogbookActivities()) {
                subTotalCatchesWeight += catchesWeight(trip);
            }
            double landingTotalWeight = trip.getLandingTotalWeight();
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
                if (activity.getVesselActivity().isTransshipment()
                        || activity.getVesselActivity().isBait()
                        || activity.containsFishingBaitsObservedSystem()) {
                    continue;
                }
                subTotalCatchesWeight += RaisingFactorInspector.catchesFilter(trip, activity);
            }
        }
        return subTotalCatchesWeight;
    }

    public static double catchesFilter(Trip trip, Activity activity) {
        double catchesWeight = 0;
        if (trip.getVessel().getFleetCountry().isFrance()) {
            for (Catch aCatch : activity.getCatches()) {
                if (aCatch.getWeightCategory() != null && aCatch.getWeightCategory().getSpecies() != null && isFrenchTargetSpecies(aCatch.getWeightCategory().getSpecies())) {
                    catchesWeight += aCatch.getWeight();
                }
            }
        } else if (trip.getVessel().getFleetCountry().isSpain()) {
            for (Catch aCatch : activity.getCatches()) {
                if (aCatch.getWeightCategory() != null && aCatch.getWeightCategory().getSpecies() != null && isSpainTargetSpecies(aCatch.getWeightCategory().getSpecies())) {
                    catchesWeight += aCatch.getWeight();
                }
            }
        } else {
            catchesWeight = activity.totalCatchWeightFromCatches();
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

    public RaisingFactorInspector() {
        this.description = "Calculate the raising factor, with and without the local market, for all trips (included trip with partial landings).";
    }

    @Override
    public Results execute() {

        if (!AAProperties.isWarningInspectorEnabled()) {
            return null;
        }

        Results results = new Results();
        double rf1;
//        double rf1WithLocalMarket = 0d;

        List<Trip> allTrips = get();

        List<List<Trip>> extendedTrips = buildExtendedTrips(allTrips);
        for (List<Trip> trips : extendedTrips) {
            double totalCatchesWeight = 0;
            double totalLandingWeight = 0;
//            boolean hasLogbook = true;
//            double totalLocalMarketWeight = 0;
            StringBuilder tripID = new StringBuilder();
            Trip previous = null;
            for (Trip trip : trips) {
//                hasLogbook &= trip.hasLogbook();
                double subTotalCatchesWeight = 0;
                if (trip.withLogbookActivities()) {
                    subTotalCatchesWeight += catchesWeight(trip);
                }
                if (previous == null || !previous.isPartialLanding()) {
                    if (subTotalCatchesWeight == 0 && trip.getLandingTotalWeight() != 0) {
                        TripResult r = createResult(MessageDescriptions.E1025_TRIP_NO_CATCH, trip,
                                                    trip.getID(), trip.getLandingTotalWeight());
                        results.add(r);
                    }
                }
                totalCatchesWeight += subTotalCatchesWeight;
                totalLandingWeight += trip.getLandingTotalWeight();
//                totalLocalMarketWeight += trip.getLocalMarketWeight();
                tripID.append("{").append(trip.getID()).append("}");

                previous = trip;
            }
            try {
                rf1 = Utils.round(totalLandingWeight / totalCatchesWeight, 3);
            } catch (NumberFormatException ex) {
                rf1 = 0d;
            }

            if (!rf1IsConsistent(rf1) && !(totalCatchesWeight == 0 && previous != null && !previous.isPartialLanding())) {
                MetaTripResult mtr = createResult(MessageDescriptions.W1020_TRIP_RAISING_FACTOR, trips,
                                                  tripID.toString(), rf1);
                mtr.setDataInformation(rf1);
                results.add(mtr);
            }
        }
        return results;
    }

}