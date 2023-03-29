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
package fr.ird.akado.observe.inspector.trip;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.TripResult;
import fr.ird.common.message.Message;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Route;

import static fr.ird.akado.observe.Constant.CODE_TRIP_FISHING_TIME;
import static fr.ird.akado.observe.Constant.LABEL_TRIP_FISHING_TIME;


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

    /**
     * Calculate the fishing time based on activities
     *
     * @param trip the trip
     * @return the time in hour
     */
    public static int fishingTimeExpected(Trip trip) {
        int fishingTimeExpected = 0;
        for (Route route : trip.getLogbookRoute()) {
            Integer fishingTime = route.getFishingTime();
            if (fishingTime != null) {
                fishingTimeExpected += fishingTime;
            }
        }
        return fishingTimeExpected;
    }

    /**
     * Check if the fishing time in trip is consistency with all activities.
     *
     * @param trip the trip
     * @return true if the value are same
     */
    public static boolean isFishingTimeConsistency(Trip trip) {
        return trip.getFishingTime() != null && trip.getFishingTime() == fishingTimeExpected(trip);
    }

    public FishingTimeInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the fishing time in trip is consistency with all activities.";
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Trip trip = get();
        Integer fishingTime = trip.getFishingTime();
        if (fishingTime == null) {
            return results;
        }
        int fishingTimeExpected = fishingTimeExpected(trip);
        if (!isFishingTimeConsistency(trip)) {
            TripResult r = createResult(trip, Message.ERROR, CODE_TRIP_FISHING_TIME, LABEL_TRIP_FISHING_TIME, true, trip.getTopiaId(), fishingTime, fishingTimeExpected);
            r.setValueObtained(fishingTime);
            r.setValueExpected(fishingTimeExpected);
            results.add(r);
        }
        return results;
    }

}
