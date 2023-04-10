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
import fr.ird.driver.observe.business.data.ps.common.Trip;

/**
 * Check if the landing total weight is consistent with the elementary landing
 * weight
 * <p>
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveTripInspector.class)
public class LandingTotalWeightInspector extends ObserveTripInspector {

    public LandingTotalWeightInspector() {
        this.description = "Check if the landing total weight is consistent with the elementary landing weight.";
    }

    @Override
    public Results execute() {
        Trip trip = get();
        double landingTotalWeightExpected = trip.landingTotalWeightExpected();
        double landingTotalWeight = trip.getLandingTotalWeight();
        if (equals(landingTotalWeight , landingTotalWeightExpected)) {
            return null;
        }
        TripResult r = createResult(MessageDescriptions.E1016_TRIP_LANDING_TOTAL_WEIGHT, trip,
                                    trip.getID(),
                                    landingTotalWeight,
                                    landingTotalWeightExpected);
        r.setValueObtained(landingTotalWeight);
        r.setValueExpected(landingTotalWeightExpected);
        return Results.of(r);
    }

}
