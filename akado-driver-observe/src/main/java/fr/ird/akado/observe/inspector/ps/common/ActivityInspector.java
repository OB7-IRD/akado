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
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the trip has at least one activity and the logbook has been gotten";
    }

    @Override
    public Results execute() {
        Results results = new Results();
//        Trip trip = get();
//        if (trip.hasLogbook() && trip.getActivites().isEmpty()) {
//            TripResult r = new TripResult();
//            r.set(trip);
//            r.setMessageType(Message.ERROR);
//            r.setMessageCode(CODE_TRIP_NO_ACTIVITY);
//            r.setMessageLabel(LABEL_TRIP_NO_ACTIVTY);
//            r.setInconsistent(true);
//
//            ArrayList parameters = new ArrayList<>();
//            parameters.add(trip.getID());
//            r.setMessageParameters(parameters);
//            results.add(r);
//        } else if (!trip.hasLogbook() && !trip.getActivites().isEmpty()) {
//            TripResult r = new TripResult();
//            r.set(trip);
//            r.setMessageType(Message.ERROR);
//            r.setMessageCode(CODE_TRIP_HAS_ACTIVITY_NO_LOGBOOK);
//            r.setMessageLabel(LABEL_TRIP_HAS_ACTIVTY_NO_LOGBOOK);
//            r.setInconsistent(true);
//
//            ArrayList parameters = new ArrayList<>();
//            parameters.add(trip.getID());
//            r.setMessageParameters(parameters);
//            results.add(r);
//
//        }
        return results;
    }
}
