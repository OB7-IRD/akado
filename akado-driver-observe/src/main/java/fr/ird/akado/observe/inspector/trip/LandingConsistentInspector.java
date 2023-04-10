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
 * Check if the vessel capacity is consistent with the landing total weight.
 * <p>
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveTripInspector.class)
public class LandingConsistentInspector extends ObserveTripInspector {

    public static float COEFF_M3_TO_TON = 0.7f;

    public LandingConsistentInspector() {
        this.description = "Check if the vessel capacity is consistent with the landing total weight.";
    }

    @Override
    public Results execute() {
        Trip trip = get();
        if (!trip.getVessel().isPurseSeine()) {
            return null;
        }
        double capacityMax = trip.getVessel().getCapacity() * COEFF_M3_TO_TON;
        if (capacityMax == 0) {
            TripResult r = createResult(MessageDescriptions.W_1002_VESSEL_NO_CAPACITY, trip,
                                        trip.getVessel().getLabel2());
            r.setDataInformation(trip.getVessel());
            return Results.of(r);
        }
        double catchesWeight = trip.getLandingTotalWeight() + trip.getLocalMarketTotalWeight();
        if (catchesWeight > capacityMax) {
            TripResult r = createResult(MessageDescriptions.E_1022_TRIP_CAPACITY_OVERRIDE, trip,
                                        trip.getID(),
                                        catchesWeight,
                                        capacityMax);
            r.setValueObtained(catchesWeight);
            r.setValueExpected(capacityMax);
            return Results.of(r);
        }
        return null;
    }

}
