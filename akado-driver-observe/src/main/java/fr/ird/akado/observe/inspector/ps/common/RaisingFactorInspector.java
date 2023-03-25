package fr.ird.akado.observe.inspector.ps.common;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.result.Results;

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
        this.description = "Calculate the raising factor, "
                + "with and without the local market, for all trips "
                + "(included trip with partial landings).";
    }
//
//    public static double RaisingFactor1(List<Trip> trips) {
//
//        double totalCatchesWeight = 0;
//        double totalLandingWeight = 0;
//        Trip previous = null;
//        for (Trip trip : trips) {
//
//            double subTotalCatchesWeight = 0;
//            if (!trip.getActivites().isEmpty()) {
//                subTotalCatchesWeight += catchesWeight(trip);
//            }
//            if (previous == null || !previous.isPartialLanding()) {
//                if (subTotalCatchesWeight == 0 && trip.getTotalLandingWeight() != 0) {
//                    return 0;
//                }
//            }
//            totalCatchesWeight += subTotalCatchesWeight;
//            totalLandingWeight += trip.getTotalLandingWeight();
//
//            previous = trip;
//        }
//        if (totalCatchesWeight == 0) {
//            return 0;
//        }
//        return totalLandingWeight / totalCatchesWeight;
//
//    }

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
//        Results results = new Results();
//
//        if (!AAProperties.WARNING_INSPECTOR.equals(AAProperties.DISABLE_VALUE)) {
//
//            ArrayList parameters = null;
//            double rf1 = 0d;
////        double rf1WithLocalMarket = 0d;
//
//            List<Trip> allTrips = get();
//            LogService.getService(RaisingFactorInspector.class).logApplicationDebug("***    ***");
//            for (Trip t : allTrips) {
//                LogService.getService(RaisingFactorInspector.class).logApplicationDebug(t.getID());
//            }
//            LogService.getService(RaisingFactorInspector.class).logApplicationDebug("***    ***");
//            TripResult r;
//            MetaTripResult mtr;
//            List<List<Trip>> extendedTrips = AvdthUtils.buildExtendedTrips(allTrips);
//            for (List<Trip> trips : extendedTrips) {
//                double totalCatchesWeight = 0;
//                double totalLandingWeight = 0;
//                boolean hasLogbook = true;
////            double totalLocalMarketWeight = 0;
//                String tripID = "";
//                Trip previous = null;
//                for (Trip trip : trips) {
//                    hasLogbook &= trip.getFlagEnquete() != 0;
//                    double subTotalCatchesWeight = 0;
//                    if (!trip.getActivites().isEmpty()) {
//                        subTotalCatchesWeight += catchesWeight(trip);
//                    }
//                    if (previous == null || !previous.isPartialLanding()) {
//                        if (subTotalCatchesWeight == 0 && trip.getTotalLandingWeight() != 0) {
//                            r = new TripResult();
//                            r.set(trip);
//                            r.setMessageType(Message.ERROR);
//                            r.setMessageCode(CODE_TRIP_NO_CATCH);
//                            r.setMessageLabel(LABEL_TRIP_NO_CATCH);
//                            r.setInconsistent(true);
//
//                            parameters = new ArrayList<>();
//                            parameters.add(trip.getID());
//                            parameters.add(trip.getTotalLandingWeight());
//                            r.setMessageParameters(parameters);
//                            results.add(r);
//                        }
//                    }
//                    totalCatchesWeight += subTotalCatchesWeight;
//                    totalLandingWeight += trip.getTotalLandingWeight();
////                totalLocalMarketWeight += trip.getLocalMarketWeight();
//                    tripID += "{" + trip.getVessel().getCode() + " " + DATE_FORMATTER.print(trip.getLandingDate()) + "}";
//
//                    previous = trip;
//                }
//
//                try {
//                    rf1 = Utils.round(totalLandingWeight / totalCatchesWeight, 3);
//                } catch (NumberFormatException ex) {
//                    rf1 = 0d;
//                }
//                LogService.getService(RaisingFactorInspector.class).logApplicationDebug("******");
//                for (Trip t : trips) {
//                    LogService.getService(RaisingFactorInspector.class).logApplicationDebug(t.getID());
//                }
//                LogService.getService(RaisingFactorInspector.class).logApplicationDebug(tripID);
//                if (!rf1IsConsistent(rf1) && !(totalCatchesWeight == 0 && previous != null && !previous.isPartialLanding())) {
//                    mtr = new MetaTripResult();
//
//                    mtr.set(trips);
//                    mtr.setMessageType(Message.WARNING);
//                    mtr.setMessageCode(CODE_TRIP_RAISING_FACTOR);
//                    mtr.setMessageLabel(LABEL_TRIP_RAISING_FACTOR);
//                    mtr.setDataInformation(rf1);
//
//                    parameters = new ArrayList<>();
//                    parameters.add(tripID);
//                    parameters.add("" + rf1);
//                    mtr.setMessageParameters(parameters);
//                    results.add(mtr);
//                }
////            try {
////                rf1WithLocalMarket = Utils.round((totalLandingWeight + totalLocalMarketWeight) / totalCatchesWeight, 3);
////            } catch (NumberFormatException ex) {
////                rf1WithLocalMarket = 0d;
////            }
////            if (!rf1IsConsistent(rf1WithLocalMarket)) {
////                mtr = new MetaTripResult();
////                mtr.set(trips);
////                mtr.setMessageType(Message.WARNING);
////                mtr.setMessageCode(CODE_TRIP_RAISING_FACTOR_WITH_LOCAL_MARKET);
////                mtr.setMessageLabel(LABEL_TRIP_RAISING_FACTOR_WITH_LOCAL_MARKET);
////                mtr.setDataInformation(rf1WithLocalMarket);
////                parameters = new ArrayList<>();
////                parameters.add(tripID);
////                parameters.add("" + rf1WithLocalMarket);
////                mtr.setMessageParameters(parameters);
////                results.add(mtr);
////            }
//            }
//        }
//
//        return results;
        return null;
    }
//
//    private static final int[] FRENCH_TARGET_SPECIES = new int[]{1, 2, 3, 4, 9, 11, 42};
//    private static final int[] SPAIN_TARGET_SPECIES = new int[]{1, 2, 3, 4, 5, 6, 9, 11, 12, 42, 43};
//
//    private static boolean in(int elt, int[] array) {
//        for (int i = 0; i < array.length; i++) {
//            if (elt == array[i]) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public static boolean rf1IsConsistent(double rf1) {
////        return false;
//        return rf1 >= 0.9 && rf1 <= 1.1;
//    }
//
//    public static double catchesWeight(Trip trip) {
//        double subTotalCatchesWeight = 0;
//        for (Activity activity : trip.getActivites()) {
//            if (activity.getOperation().getCode() != Operation.TRANSBORDEMENT_VERS_SENNEUR
//                    && activity.getOperation().getCode() != Operation.TRANSBORDEMENT
//                    && activity.getOperation().getCode() != Operation.TRANSBORDEMENT_VERS_CANNEUR) {
//                subTotalCatchesWeight += RaisingFactorInspector.catchesFilter(activity);
//            }
//        }
//        return subTotalCatchesWeight;
//    }
//
//    public static double catchesFilter(Activity activity) {
//        double catchesWeight = 0;
//        if (activity.getVessel().getCountry().getCode() == Country.AVDTH_CODE_COUNTRY_FRANCE) {
//            for (ElementaryCatch captureElementaire : activity.getElementaryCatchs()) {
//                if (in(captureElementaire.getSizeCategory().getSpecies().getCode(), FRENCH_TARGET_SPECIES)) {
//                    catchesWeight += captureElementaire.getCatchWeight();
//                }
//            }
//        } else if (activity.getVessel().getCountry().getCode() == Country.AVDTH_CODE_COUNTRY_SPAIN) {
//            for (ElementaryCatch captureElementaire : activity.getElementaryCatchs()) {
//
//                if (in(captureElementaire.getSizeCategory().getSpecies().getCode(), SPAIN_TARGET_SPECIES)) {
//
//                    catchesWeight += captureElementaire.getCatchWeight();
//                }
//            }
//        } else {
//            for (ElementaryCatch captureElementaire : activity.getElementaryCatchs()) {
//                catchesWeight += captureElementaire.getCatchWeight();
//            }
//        }
//        return catchesWeight;
//    }
}