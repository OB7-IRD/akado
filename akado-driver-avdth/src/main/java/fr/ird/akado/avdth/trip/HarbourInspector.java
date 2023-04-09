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
package fr.ird.akado.avdth.trip;

import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.result.TripResult;
import fr.ird.akado.core.Inspector;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Trip;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import fr.ird.driver.avdth.dao.TripDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static fr.ird.akado.avdth.Constant.CODE_TRIP_HAS_DIFFERENT_HARBOUR;
import static fr.ird.akado.avdth.Constant.LABEL_TRIP_HAS_DIFFERENT_HARBOUR;

/**
 * The HarbourInspector class check if the landing harbour of a previous trip is
 * the same as the departure harbour.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 04 decembre 2015
 *
 */
public class HarbourInspector extends Inspector<Trip> {
    private static final Logger log = LogManager.getLogger(HarbourInspector.class);
    public HarbourInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the landing harbour of a previous trip is the same as the departure harbour.";
    }

    @Override
    public Results execute() throws AvdthDriverException {
        Results results = new Results();
        Trip trip = get();
        Trip previous = (new TripDAO()).findPreviousTrip(trip.getVessel(), trip.getLandingDate());
        if (previous != null && !harbourAreIdentical(previous, trip)) {
            TripResult r = new TripResult();
            r.set(trip);
            r.setMessageType(Message.ERROR);

            r.setMessageCode(CODE_TRIP_HAS_DIFFERENT_HARBOUR);
            r.setMessageLabel(LABEL_TRIP_HAS_DIFFERENT_HARBOUR);
            r.setInconsistent(true);

            ArrayList parameters = new ArrayList<>();
            parameters.add(trip.getID());
            parameters.add(trip.getDepartureHarbour().getCode());
            parameters.add(previous.getLandingHarbour().getCode());
            r.setMessageParameters(parameters);
            results.add(r);

        }
        return results;
    }

    public static boolean harbourAreIdentical(Trip current) {
        Trip previous = null;
        try {
            previous = (new TripDAO()).findPreviousTrip(current.getVessel(), current.getLandingDate());
            if (previous == null) {
                return true;
            }
            log.debug("Previous trip " + previous);
            return harbourAreIdentical(previous, current);
        } catch (AvdthDriverException ex) {
            log.debug(ex.getMessage());
        }
        return false;
    }

    public static boolean harbourAreIdentical(Trip previous, Trip current) {
        return current != null && previous != null && current.getDepartureHarbour() != null && previous.getLandingHarbour() != null && current.getDepartureHarbour().getCode() == previous.getLandingHarbour().getCode();
    }
}
