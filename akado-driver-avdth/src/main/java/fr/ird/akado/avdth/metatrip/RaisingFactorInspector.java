/*
 * Copyright (C) 2014 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.akado.avdth.metatrip;

import fr.ird.akado.avdth.result.MetaTripResult;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.result.TripResult;
import fr.ird.akado.core.Inspector;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.common.Utils;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.Country;
import fr.ird.driver.avdth.business.ElementaryCatch;
import fr.ird.driver.avdth.business.Operation;
import fr.ird.driver.avdth.business.Trip;
import fr.ird.driver.avdth.common.AvdthUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static fr.ird.akado.avdth.Constant.CODE_TRIP_NO_CATCH;
import static fr.ird.akado.avdth.Constant.CODE_TRIP_RAISING_FACTOR;
import static fr.ird.akado.avdth.Constant.LABEL_TRIP_NO_CATCH;
import static fr.ird.akado.avdth.Constant.LABEL_TRIP_RAISING_FACTOR;
import static fr.ird.common.DateTimeUtils.DATE_FORMATTER;

/**
 * Calculate the raising factor, with and without the local market, for all
 * trips (included trip with partial landings).
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 12 juin 2014
 */
public class RaisingFactorInspector extends Inspector<List<Trip>> {
    private static final Logger log = LogManager.getLogger(RaisingFactorInspector.class);
    public RaisingFactorInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Calculate the raising factor, "
                + "with and without the local market, for all trips "
                + "(included trip with partial landings).";
    }

    public static double RaisingFactor1(List<Trip> trips) {

        double totalCatchesWeight = 0;
        double totalLandingWeight = 0;
        Trip previous = null;
        for (Trip trip : trips) {

            double subTotalCatchesWeight = 0;
            if (!trip.getActivites().isEmpty()) {
                subTotalCatchesWeight += catchesWeight(trip);
            }
            if (previous == null || !previous.isPartialLanding()) {
                if (subTotalCatchesWeight == 0 && trip.getTotalLandingWeight() != 0) {
                    return 0;
                }
            }
            totalCatchesWeight += subTotalCatchesWeight;
            totalLandingWeight += trip.getTotalLandingWeight();

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

        if (AAProperties.isWarningInspectorEnabled()) {

            ArrayList parameters = null;
            double rf1 = 0d;
//        double rf1WithLocalMarket = 0d;

            List<Trip> allTrips = get();
            log.debug("***    ***");
            for (Trip t : allTrips) {
                log.debug(t.getID());
            }
            log.debug("***    ***");
            TripResult r;
            MetaTripResult mtr;
            List<List<Trip>> extendedTrips = AvdthUtils.buildExtendedTrips(allTrips);
            for (List<Trip> trips : extendedTrips) {
                double totalCatchesWeight = 0;
                double totalLandingWeight = 0;
                boolean hasLogbook = true;
//            double totalLocalMarketWeight = 0;
                String tripID = "";
                Trip previous = null;
                for (Trip trip : trips) {
                    hasLogbook &= trip.getFlagEnquete() != 0;
                    double subTotalCatchesWeight = 0;
                    if (!trip.getActivites().isEmpty()) {
                        subTotalCatchesWeight += catchesWeight(trip);
                    }
                    if (previous == null || !previous.isPartialLanding()) {
                        if (subTotalCatchesWeight == 0 && trip.getTotalLandingWeight() != 0) {
                            r = new TripResult();
                            r.set(trip);
                            r.setMessageType(Message.ERROR);
                            r.setMessageCode(CODE_TRIP_NO_CATCH);
                            r.setMessageLabel(LABEL_TRIP_NO_CATCH);
                            r.setInconsistent(true);

                            parameters = new ArrayList<>();
                            parameters.add(trip.getID());
                            parameters.add(trip.getTotalLandingWeight());
                            r.setMessageParameters(parameters);
                            results.add(r);
                        }
                    }
                    totalCatchesWeight += subTotalCatchesWeight;
                    totalLandingWeight += trip.getTotalLandingWeight();
//                totalLocalMarketWeight += trip.getLocalMarketWeight();
                    tripID += "{" + trip.getVessel().getCode() + " " + DATE_FORMATTER.print(trip.getLandingDate()) + "}";

                    previous = trip;
                }

                try {
                    rf1 = Utils.round(totalLandingWeight / totalCatchesWeight, 3);
                } catch (NumberFormatException ex) {
                    rf1 = 0d;
                }
                log.debug("******");
                for (Trip t : trips) {
                    log.debug(t.getID());
                }
                log.debug(tripID);
                if (!rf1IsConsistent(rf1) && !(totalCatchesWeight == 0 && previous != null && !previous.isPartialLanding())) {
                    mtr = new MetaTripResult();

                    mtr.set(trips);
                    mtr.setMessageType(Message.WARNING);
                    mtr.setMessageCode(CODE_TRIP_RAISING_FACTOR);
                    mtr.setMessageLabel(LABEL_TRIP_RAISING_FACTOR);
                    mtr.setDataInformation(rf1);

                    parameters = new ArrayList<>();
                    parameters.add(tripID);
                    parameters.add("" + rf1);
                    mtr.setMessageParameters(parameters);
                    results.add(mtr);
                }
//            try {
//                rf1WithLocalMarket = Utils.round((totalLandingWeight + totalLocalMarketWeight) / totalCatchesWeight, 3);
//            } catch (NumberFormatException ex) {
//                rf1WithLocalMarket = 0d;
//            }
//            if (!rf1IsConsistent(rf1WithLocalMarket)) {
//                mtr = new MetaTripResult();
//                mtr.set(trips);
//                mtr.setMessageType(Message.WARNING);
//                mtr.setMessageCode(CODE_TRIP_RAISING_FACTOR_WITH_LOCAL_MARKET);
//                mtr.setMessageLabel(LABEL_TRIP_RAISING_FACTOR_WITH_LOCAL_MARKET);
//                mtr.setDataInformation(rf1WithLocalMarket);
//                parameters = new ArrayList<>();
//                parameters.add(tripID);
//                parameters.add("" + rf1WithLocalMarket);
//                mtr.setMessageParameters(parameters);
//                results.add(mtr);
//            }
            }
        }

        return results;
    }

    private static final int[] FRENCH_TARGET_SPECIES = new int[]{1, 2, 3, 4, 9, 11, 42};
    private static final int[] SPAIN_TARGET_SPECIES = new int[]{1, 2, 3, 4, 5, 6, 9, 11, 12, 42, 43};

    private static boolean in(int elt, int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (elt == array[i]) {
                return true;
            }
        }
        return false;
    }

    public static boolean rf1IsConsistent(double rf1) {
//        return false;
        return rf1 >= 0.9 && rf1 <= 1.1;
    }

    public static double catchesWeight(Trip trip) {
        double subTotalCatchesWeight = 0;
        for (Activity activity : trip.getActivites()) {
            if (activity.getOperation().getCode() != Operation.TRANSBORDEMENT_VERS_SENNEUR
                    && activity.getOperation().getCode() != Operation.TRANSBORDEMENT
                    && activity.getOperation().getCode() != Operation.TRANSBORDEMENT_VERS_CANNEUR) {
                subTotalCatchesWeight += RaisingFactorInspector.catchesFilter(activity);
            }
        }
        return subTotalCatchesWeight;
    }

    public static double catchesFilter(Activity activity) {
        double catchesWeight = 0;
        if (activity.getVessel().getCountry().getCode() == Country.AVDTH_CODE_COUNTRY_FRANCE) {
            for (ElementaryCatch captureElementaire : activity.getElementaryCatchs()) {
                if (in(captureElementaire.getSizeCategory().getSpecies().getCode(), FRENCH_TARGET_SPECIES)) {
                    catchesWeight += captureElementaire.getCatchWeight();
                }
            }
        } else if (activity.getVessel().getCountry().getCode() == Country.AVDTH_CODE_COUNTRY_SPAIN) {
            for (ElementaryCatch captureElementaire : activity.getElementaryCatchs()) {

                if (in(captureElementaire.getSizeCategory().getSpecies().getCode(), SPAIN_TARGET_SPECIES)) {

                    catchesWeight += captureElementaire.getCatchWeight();
                }
            }
        } else {
            for (ElementaryCatch captureElementaire : activity.getElementaryCatchs()) {
                catchesWeight += captureElementaire.getCatchWeight();
            }
        }
        return catchesWeight;
    }
}
