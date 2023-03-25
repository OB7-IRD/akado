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
package fr.ird.akado.observe.inspector.ps.common;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.result.Results;
import fr.ird.driver.observe.business.data.ps.common.Trip;

/**
 * Check if the landing total weight is consistent with the elementary landing
 * weight
 * <p>
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveTripInspector.class)
public class LandingTotalWeightInspector extends ObserveTripInspector {

    public LandingTotalWeightInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the landing total weight is consistent with the elementary landing weight.";
    }
//
//    /**
//     * Calculate the weight of landings.
//     *
//     * @param trip the trip
//     * @return he weight of landings in tons
//     */
//    public static double landingTotalWeightExpected(Trip trip) {
//        double landingTotalWeightExpected = 0;
//        for (ElementaryLanding commercial : trip.getLotsCommerciaux()) {
//            landingTotalWeightExpected += commercial.getWeightLanding();
//        }
//        return landingTotalWeightExpected;
//    }

//    /**
//     * Calculate the weight of landings with the local market's estimation
//     *
//     * @param trip the trip
//     * @return he weight of landings in tons
//     */
//    public static double landingTotalWeightExpectedWithLocalMarket(Trip trip) {
//        return landingTotalWeightExpected(trip) + trip.getLocalMarketWeight();
//    }

    @Override
    public Results execute() {
        Results results = new Results();
        Trip trip = get();
//        double landingTotalWeightExpected = landingTotalWeightExpected(trip);
//        if (Math.abs(trip.getTotalLandingWeight() - landingTotalWeightExpected) > EPSILON) {
//            TripResult r = new TripResult();
//            r.set(trip);
//            r.setMessageType(Message.ERROR);
//            r.setMessageCode(CODE_TRIP_LANDING_TOTAL_WEIGHT);
//            r.setMessageLabel(LABEL_TRIP_LANDING_TOTAL_WEIGHT);
//            r.setValueObtained(trip.getTotalLandingWeight());
//            r.setValueExpected(landingTotalWeightExpected);
//
//            ArrayList parameters = new ArrayList<>();
//            parameters.add(trip.getID());
//            parameters.add(trip.getTotalLandingWeight());
//            parameters.add(landingTotalWeightExpected);
//            r.setMessageParameters(parameters);
//            results.add(r);
//        }
        return results;
    }

}
