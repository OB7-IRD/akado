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

import static fr.ird.akado.avdth.Constant.CODE_TRIP_TEMPORAL_LIMIT;
import static fr.ird.akado.avdth.Constant.LABEL_TRIP_TEMPORAL_LIMIT;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.result.TripResult;
import fr.ird.akado.core.Inspector;
import fr.ird.common.DateTimeUtils;
import static fr.ird.common.DateTimeUtils.DATE_FORMATTER;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.Trip;
import java.util.ArrayList;

/**
 * The TemporalLimitInspector check if the temporal limit of the trip are
 * consistent with activities.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 22 mai 2014
 *
 */
public class TemporalLimitInspector extends Inspector<Trip> {

    public TemporalLimitInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the temporal limit of the trip are consistent with activities.";
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Trip trip = get();
        if (!trip.getActivites().isEmpty()) {
            Activity firstActivity = trip.firstActivity();
            Activity lastActivity = trip.lastActivity();
            ArrayList parameters = null;
            TripResult r;
            if (!DateTimeUtils.dateEqual(trip.getDepartureDate(), firstActivity.getDate())) {
                r = new TripResult();
                r.set(trip);
                r.setMessageType(Message.ERROR);
                r.setMessageCode(CODE_TRIP_TEMPORAL_LIMIT);
                r.setMessageLabel(LABEL_TRIP_TEMPORAL_LIMIT);
                r.setValueObtained(trip.getDepartureDate());
                r.setValueExpected(firstActivity.getDate());

                parameters = new ArrayList<>();
                parameters.add(trip.getID());
                parameters.add(DATE_FORMATTER.print(lastActivity.getDate()));
                parameters.add(DATE_FORMATTER.print(trip.getDepartureDate()));
                parameters.add(DATE_FORMATTER.print(firstActivity.getDate()));
                r.setMessageParameters(parameters);
                results.add(r);
            }
            if (!DateTimeUtils.dateEqual(trip.getLandingDate(), lastActivity.getDate())) {
                r = new TripResult();
                r.set(trip);
                r.setMessageType(Message.ERROR);
                r.setMessageCode(CODE_TRIP_TEMPORAL_LIMIT);
                r.setMessageLabel(LABEL_TRIP_TEMPORAL_LIMIT);
                r.setValueObtained(trip.getLandingDate());
                r.setValueExpected(lastActivity.getDate());

                parameters = new ArrayList<>();
                parameters.add(trip.getID());
                parameters.add(DATE_FORMATTER.print(lastActivity.getDate()));
                parameters.add(DATE_FORMATTER.print(trip.getDepartureDate()));
                parameters.add(DATE_FORMATTER.print(firstActivity.getDate()));
                r.setMessageParameters(parameters);
                results.add(r);
            }
        }
        return results;
    }
}
