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
import fr.ird.akado.observe.Constant;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.TripResult;
import fr.ird.common.DateTimeUtils;
import fr.ird.common.message.Message;
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
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the temporal limit of the trip are consistent with activities.";
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Trip trip = get();
        if (!trip.withLogbookActivities()) {
            return results;
        }
        Route firstRoute = trip.firstRouteWithActivity();
        Route lastRoute = trip.lastRouteWithActivity();
        if (!DateTimeUtils.dateEqual(trip.getStartDate(), firstRoute.getDate())) {
            TripResult r = createResult(trip, Message.ERROR, Constant.CODE_TRIP_TEMPORAL_LIMIT, Constant.LABEL_TRIP_TEMPORAL_LIMIT, false, trip.getTopiaId(), dateFormat.format(lastRoute.getDate()), dateFormat.format(trip.getStartDate()), dateFormat.format(firstRoute.getDate()));
            r.setValueObtained(trip.getStartDate());
            r.setValueExpected(firstRoute.getDate());
            results.add(r);
        }
        if (!DateTimeUtils.dateEqual(trip.getEndDate(), lastRoute.getDate())) {
            TripResult r = createResult(trip, Message.ERROR, Constant.CODE_TRIP_TEMPORAL_LIMIT, Constant.LABEL_TRIP_TEMPORAL_LIMIT, false, trip.getTopiaId(), dateFormat.format(lastRoute.getDate()),
                                        dateFormat.format(trip.getStartDate()),
                                        dateFormat.format(firstRoute.getDate()));
            r.setValueObtained(trip.getEndDate());
            r.setValueExpected(lastRoute.getDate());
            results.add(r);
        }
        return results;
    }
}
