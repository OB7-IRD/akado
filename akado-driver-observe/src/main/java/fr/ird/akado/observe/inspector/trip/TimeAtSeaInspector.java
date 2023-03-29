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

import static fr.ird.akado.observe.Constant.CODE_TRIP_TIME_AT_SEA;
import static fr.ird.akado.observe.Constant.LABEL_TRIP_TIME_AT_SEA;

/**
 * Check if the time at sea in trip is consistency with all activities.
 * <p>
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveTripInspector.class)
public class TimeAtSeaInspector extends ObserveTripInspector {

    /**
     * Calculate the time at sea based on activities
     *
     * @param trip the trip
     * @return the time in hour
     */
    public static int timeAtSeaExpected(Trip trip) {
        int timeAtSeaExpected = 0;
        for (Route route : trip.getLogbookRoute()) {
            Integer timeAtSea = route.getTimeAtSea();
            if (timeAtSea != null) {
                timeAtSeaExpected += timeAtSea;
            }
        }
        return timeAtSeaExpected;
    }

    public TimeAtSeaInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the time at sea in trip is consistency with all activities.";
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Trip trip = get();
        Integer timeAtSea = trip.getTimeAtSea();
        if (timeAtSea == null) {
            return results;
        }
        int timeAtSeaExpected = timeAtSeaExpected(trip);
        if (0 != timeAtSea && timeAtSeaExpected == timeAtSea) {
            return results;
        }
        TripResult r = createResult(trip, Message.ERROR, CODE_TRIP_TIME_AT_SEA, LABEL_TRIP_TIME_AT_SEA, false, trip.getTopiaId(), timeAtSea, timeAtSeaExpected);
        r.setValueObtained(timeAtSea);
        r.setValueExpected(timeAtSeaExpected);
        results.add(r);
        return results;
    }
}
