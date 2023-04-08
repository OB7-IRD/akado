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
 * The ActivityInspector class check if the trip has at least one activity and
 * the logbook has been gotten.
 * <p>
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveTripInspector.class)
public class ActivityInspector extends ObserveTripInspector {

    public ActivityInspector() {
        this.description = "Check if the trip has at least one activity and the logbook has been gotten";
    }

    @Override
    public Results execute() {
        Trip trip = get();
        boolean hasLogbook = trip.hasLogbook();
        boolean withLogbookActivities = trip.withLogbookActivities();
        if (hasLogbook && !withLogbookActivities) {
            TripResult r = createResult(MessageDescriptions.E_1018_TRIP_NO_ACTIVITY, trip,
                                        trip.getID());
            return Results.of(r);
        }
        if (!hasLogbook && withLogbookActivities) {
            TripResult r = createResult(MessageDescriptions.E_1024_TRIP_HAS_ACTIVITY_NO_LOGBOOK, trip,
                                        trip.getID());
            return Results.of(r);
        }
        return null;
    }

}
