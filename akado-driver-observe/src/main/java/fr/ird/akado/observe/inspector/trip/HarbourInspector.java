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
package fr.ird.akado.observe.inspector.trip;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.TripResult;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.service.ObserveService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

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
    private static final Logger log = LogManager.getLogger(HarbourInspector.class);

    public static boolean harbourAreIdentical(Trip previous, Trip current) {
        return current != null && previous != null && Objects.equals(current.getDepartureHarbour(), previous.getLandingHarbour());
    }

    public static boolean harbourAreIdentical(Trip current) {
        Trip previous = ObserveService.getService().getDaoSupplier().getPsCommonTripDao().findPreviousTrip(current.getVessel(), current.getEndDate());
        if (previous == null) {
            return true;
        }
        log.debug("Previous trip " + previous);
        return harbourAreIdentical(previous, current);
    }

    public HarbourInspector() {
        this.description = "Check if the landing harbour of a previous trip is the same as the departure harbour.";
    }

    @Override
    public Results execute() {
        Trip trip = get();
        Trip previous = ObserveService.getService().getDaoSupplier().getPsCommonTripDao().findPreviousTrip(trip.getVessel(), trip.getEndDate());
        if (previous != null && !harbourAreIdentical(previous, trip)) {
            TripResult r = createResult(MessageDescriptions.E1023_TRIP_HAS_DIFFERENT_HARBOUR, trip,
                                        trip.getID(),
                                        trip.getDepartureHarbour().getCode(),
                                        previous.getLandingHarbour().getCode());
            return Results.of(r);
        }
        return null;
    }
}
