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

import java.util.Date;

/**
 * Check if activities are continuous during a trip.
 * <p>
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveTripInspector.class)
public class RecoveryTimeInspector extends ObserveTripInspector {


    /**
     * Check if the trip's activities are continuous.
     *
     * @param trip the trip
     * @return the date before the break
     */
    public static Date continuous(Trip trip) {
        Route lastRoute = null;
        for (Route route : trip.getLogbookRoute()) {
            if (lastRoute == null || DateTimeUtils.dateEqual(route.getDate(), lastRoute.getDate())) {
                lastRoute = route;
                continue;
            }
            if (!DateTimeUtils.dateIsTheNextDay(lastRoute.getDate(), route.getDate())) {
                return lastRoute.getDate();
            }
            lastRoute = route;
        }
        return null;
    }

    public RecoveryTimeInspector() {
        this.description = "Check if the activities are continuous during a trip.";
    }

    @Override
    public Results execute() {
        Trip trip = get();
        if (!trip.withLogbookActivities()) {
            return null;
        }
        Results results = new Results();
        Route lastRoute = null;
        for (Route route : trip.getLogbookRoute()) {
            Date routeDate = route.getDate();
            if (route.getActivity().isEmpty()) {
                TripResult r = createResult(MessageDescriptions.E_1014_TRIP_ROUTE_NO_ACTIVITY, trip,
                                            trip.getID(),
                                            DateUtils.formatDate(routeDate));
                results.add(r);
            }

            if (lastRoute == null || DateTimeUtils.dateEqual(routeDate, lastRoute.getDate())) {
                lastRoute = route;
                continue;
            }
            if (!DateTimeUtils.dateIsTheNextDay(lastRoute.getDate(), routeDate)) {
                TripResult r = createResult(MessageDescriptions.E_1013_TRIP_RECOVERY_TIME, trip,
                                            trip.getID(),
                                            DateUtils.formatDate(lastRoute.getDate()),
                                            DateUtils.formatDate(routeDate));
                results.add(r);
            }
        }
        return results;
    }

}
