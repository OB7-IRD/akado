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
//        if (!trip.getActivites().isEmpty()) {
//            Activity firstActivity = trip.firstActivity();
//            Activity lastActivity = trip.lastActivity();
//            ArrayList parameters = null;
//            TripResult r;
//            if (!DateTimeUtils.dateEqual(trip.getDepartureDate(), firstActivity.getDate())) {
//                r = new TripResult();
//                r.set(trip);
//                r.setMessageType(Message.ERROR);
//                r.setMessageCode(CODE_TRIP_TEMPORAL_LIMIT);
//                r.setMessageLabel(LABEL_TRIP_TEMPORAL_LIMIT);
//                r.setValueObtained(trip.getDepartureDate());
//                r.setValueExpected(firstActivity.getDate());
//
//                parameters = new ArrayList<>();
//                parameters.add(trip.getID());
//                parameters.add(DATE_FORMATTER.print(lastActivity.getDate()));
//                parameters.add(DATE_FORMATTER.print(trip.getDepartureDate()));
//                parameters.add(DATE_FORMATTER.print(firstActivity.getDate()));
//                r.setMessageParameters(parameters);
//                results.add(r);
//            }
//            if (!DateTimeUtils.dateEqual(trip.getLandingDate(), lastActivity.getDate())) {
//                r = new TripResult();
//                r.set(trip);
//                r.setMessageType(Message.ERROR);
//                r.setMessageCode(CODE_TRIP_TEMPORAL_LIMIT);
//                r.setMessageLabel(LABEL_TRIP_TEMPORAL_LIMIT);
//                r.setValueObtained(trip.getLandingDate());
//                r.setValueExpected(lastActivity.getDate());
//
//                parameters = new ArrayList<>();
//                parameters.add(trip.getID());
//                parameters.add(DATE_FORMATTER.print(lastActivity.getDate()));
//                parameters.add(DATE_FORMATTER.print(trip.getDepartureDate()));
//                parameters.add(DATE_FORMATTER.print(firstActivity.getDate()));
//                r.setMessageParameters(parameters);
//                results.add(r);
//            }
//        }
        return results;
    }
}
