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
package fr.ird.akado.avdth.trip;

import static fr.ird.akado.avdth.Constant.CODE_TRIP_TIME_AT_SEA;
import static fr.ird.akado.avdth.Constant.LABEL_TRIP_TIME_AT_SEA;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.result.TripResult;
import fr.ird.akado.core.Inspector;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.Trip;
import java.util.ArrayList;

/**
 * Check if the time at sea in trip is consistency with all activities.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 22 mai 2014
 *
 */
public class TimeAtSeaInspector extends Inspector<Trip> {

    /**
     * Calculate the time at sea based on activities
     *
     * @param trip the trip
     * @return the time in hour
     */
    public static int timeAtSeaExpected(Trip trip) {
        int timeAtSeaExpected = 0; // OTUtils.hoursBetween(m.getDepartureDate(), m.getLandingDate());
        for (Activity activity : trip.getActivites()) {
            timeAtSeaExpected += activity.getTimeAtSea();
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
        int timeAtSeaExpected = timeAtSeaExpected(trip);
        if (0 == trip.getTimeAtSea() || timeAtSeaExpected != trip.getTimeAtSea()) {
            TripResult r = new TripResult();
            r.set(trip);
            r.setMessageType(Message.ERROR);
            r.setMessageCode(CODE_TRIP_TIME_AT_SEA);
            r.setMessageLabel(LABEL_TRIP_TIME_AT_SEA);
            r.setValueObtained(trip.getTimeAtSea());
            r.setValueExpected(timeAtSeaExpected);

            ArrayList parameters = new ArrayList<>();
            parameters.add(trip.getID());
            parameters.add(trip.getTimeAtSea());
            parameters.add(timeAtSeaExpected);
            r.setMessageParameters(parameters);
            results.add(r);

        }
        return results;
    }
}
