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
import fr.ird.common.DateTimeUtils;
import fr.ird.common.message.Message;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Route;

import java.util.Date;

import static fr.ird.akado.observe.Constant.CODE_TRIP_RECOVERY_TIME;
import static fr.ird.akado.observe.Constant.LABEL_TRIP_RECOVERY_TIME;

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
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the activities are continuous during a trip.";
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Trip trip = get();
        if (!trip.withLogbookActivities()) {
            return results;
        }
        Route lastRoute = null;
        for (Route route : trip.getLogbookRoute()) {

            Date routeDate = route.getDate();

            if (lastRoute == null || DateTimeUtils.dateEqual(routeDate, lastRoute.getDate())) {
                lastRoute = route;
                continue;
            }
            if (!DateTimeUtils.dateIsTheNextDay(lastRoute.getDate(), routeDate)) {
                TripResult r = createResult(trip, Message.ERROR, CODE_TRIP_RECOVERY_TIME, LABEL_TRIP_RECOVERY_TIME, true, trip.getTopiaId(), dateFormat.format(lastRoute.getDate()), dateFormat.format(routeDate));
                results.add(r);
            }
        }
        return results;
    }

}