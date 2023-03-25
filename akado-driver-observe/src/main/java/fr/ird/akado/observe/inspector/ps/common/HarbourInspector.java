/*
 * Copyright (C) 2015 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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
 * The HarbourInspector class check if the landing harbour of a previous trip is
 * the same as the departure harbour.
 * <p>
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveTripInspector.class)
public class HarbourInspector extends ObserveTripInspector {

    public HarbourInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the landing harbour of a previous trip is the same as the departure harbour.";
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Trip trip = get();
//        Trip previous = (new TripDAO()).findPreviousTrip(trip.getVessel(), trip.getLandingDate());
//        if (previous != null && !harbourAreIdentical(previous, trip)) {
//            TripResult r = new TripResult();
//            r.set(trip);
//            r.setMessageType(Message.ERROR);
//
//            r.setMessageCode(CODE_TRIP_HAS_DIFFERENT_HARBOUR);
//            r.setMessageLabel(LABEL_TRIP_HAS_DIFFERENT_HARBOUR);
//            r.setInconsistent(true);
//
//            ArrayList parameters = new ArrayList<>();
//            parameters.add(trip.getID());
//            parameters.add(trip.getDepartureHarbour().getCode());
//            parameters.add(previous.getLandingHarbour().getCode());
//            r.setMessageParameters(parameters);
//            results.add(r);
//
//        }
        return results;
    }
//
//    public static boolean harbourAreIdentical(Trip current) {
//        Trip previous = null;
//        try {
//            previous = (new TripDAO()).findPreviousTrip(current.getVessel(), current.getLandingDate());
//            if (previous == null) {
//                return true;
//            }
//            LogService.getService(HarbourInspector.class).logApplicationDebug("Previous trip " + previous);
//            return harbourAreIdentical(previous, current);
//        } catch (AvdthDriverException ex) {
//            LogService.getService(HarbourInspector.class).logApplicationDebug(ex.getMessage());
//        }
//        return false;
//    }
//
//    public static boolean harbourAreIdentical(Trip previous, Trip current) {
//        return current != null && previous != null && current.getDepartureHarbour() != null && previous.getLandingHarbour() != null && current.getDepartureHarbour().getCode() == previous.getLandingHarbour().getCode();
//    }
}
