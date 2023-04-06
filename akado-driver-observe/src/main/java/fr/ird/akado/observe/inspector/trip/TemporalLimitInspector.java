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
import fr.ird.common.DateTimeUtils;
import fr.ird.common.DateUtils;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Route;

/**
 * The TemporalLimitInspector check if the temporal limit of the trip are
 * consistent with activities.
 * <p>
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveTripInspector.class)
public class TemporalLimitInspector extends ObserveTripInspector {

    public TemporalLimitInspector() {
        this.description = "Check if the temporal limit of the trip are consistent with activities.";
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Trip trip = get();
        if (!trip.withLogbookActivities()) {
            return results;
        }
        Route firstRoute = trip.firstRoute();
        Route lastRoute = trip.lastRoute();
        if (!DateTimeUtils.dateEqual(trip.getStartDate(), firstRoute.getDate())) {
            TripResult r = createResult(MessageDescriptions.E_1012_TRIP_TEMPORAL_LIMIT, trip,
                                        trip.getID(),
                                        DateUtils.formatDate(lastRoute.getDate()),
                                        DateUtils.formatDate(trip.getStartDate()),
                                        DateUtils.formatDate(firstRoute.getDate()));
            r.setValueObtained(trip.getStartDate());
            r.setValueExpected(firstRoute.getDate());
            results.add(r);
        }
        if (!DateTimeUtils.dateEqual(trip.getEndDate(), lastRoute.getDate())) {
            TripResult r = createResult(MessageDescriptions.E_1012_TRIP_TEMPORAL_LIMIT, trip,
                                        trip.getID(),
                                        DateUtils.formatDate(lastRoute.getDate()),
                                        DateUtils.formatDate(trip.getStartDate()),
                                        DateUtils.formatDate(firstRoute.getDate()));
            r.setValueObtained(trip.getEndDate());
            r.setValueExpected(lastRoute.getDate());
            results.add(r);
        }
        return results;
    }
}
