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
 * Check if the fishing time in trip is consistency with all activities.
 * <p>
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveTripInspector.class)
public class FishingTimeInspector extends ObserveTripInspector {

    public FishingTimeInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the fishing time in trip is consistency with all activities.";
    }

//    /**
//     * Calculate the fishing time based on activities
//     *
//     * @param trip the trip
//     * @return the time in hour
//     */
//    public static int fishingTimeExpected(Trip trip) {
//        int fishingTimeExpected = 0;
//        for (Activity activity : trip.getActivites()) {
//            fishingTimeExpected += activity.getFishingTime();
//        }
//        return fishingTimeExpected;
//    }
//
//    /**
//     * Check if the fishing time in trip is consistency with all activities.
//     *
//     * @param trip the trip
//     * @return true if the value are same
//     */
//    public static boolean isFishingTimeConsistency(Trip trip) {
//        return trip.getFishingTime() == fishingTimeExpected(trip);
//    }
//
//    /**
//     * Check if the fishing time in trip is consistency with all activities.
//     *
//     * @param trip the trip
//     * @param fishingTimeExpected
//     * @return true if the value are same
//     */
//    public static boolean isFishingTimeConsistency(Trip trip, int fishingTimeExpected) {
//        return trip.getFishingTime() == fishingTimeExpected(trip);
//    }

    @Override
    public Results execute() {
        Results results = new Results();
        Trip trip = get();
//        int fishingTimeExpected = fishingTimeExpected(trip);
//
//        if (!isFishingTimeConsistency(trip)) {
//            TripResult r = new TripResult();
//            r.set(trip);
//            r.setMessageType(Message.ERROR);
//            r.setMessageCode(CODE_TRIP_FISHING_TIME);
//            r.setMessageLabel(LABEL_TRIP_FISHING_TIME);
//            r.setValueObtained(trip.getFishingTime());
//            r.setValueExpected(fishingTimeExpected);
//
//            ArrayList parameters = new ArrayList<>();
//            parameters.add(trip.getID());
//            parameters.add(trip.getFishingTime());
//            parameters.add(fishingTimeExpected);
//            r.setMessageParameters(parameters);
//            results.add(r);
//        }
        return results;
    }

}
