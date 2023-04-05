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
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.TripResult;
import fr.ird.driver.observe.business.data.ps.common.Trip;

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

    public TimeAtSeaInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the time at sea in trip is consistency with all activities.";
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Trip trip = get();
        int timeAtSea = trip.getTimeAtSea();
        int timeAtSeaExpected = trip.timeAtSeaExpected();
        //FIXME a séparer en deux messages
        if (0 == trip.getTimeAtSea() || timeAtSeaExpected != trip.getTimeAtSea()) {
            TripResult r = createResult(MessageDescriptions.E_1011_TRIP_TIME_AT_SEA, trip,
                                        trip.getID(), timeAtSea, timeAtSeaExpected);
            r.setValueObtained(timeAtSea);
            r.setValueExpected(timeAtSeaExpected);
            results.add(r);
        }
        return results;
    }
}
