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
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the vessel capacity is consistent with the landing total weight.";
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Trip trip = get();
//        TripResult r;
//        float capacityMax = trip.getVessel().getCapacity() * COEFF_M3_TO_TON;
//        double catchesWeight = trip.getTotalLandingWeight() + trip.getLocalMarketWeight();
//        ArrayList parameters = null;
//        if (trip.getVessel().isPurseSeine()) {
//            if (capacityMax == 0) {
//                r = new TripResult();
//                r.set(trip);
//                r.setMessageType(Message.ERROR);
//                r.setMessageCode(CODE_VESSEL_NO_CAPACITY);
//                r.setMessageLabel(LABEL_VESSEL_NO_CAPACITY);
//                r.setDataInformation(trip.getVessel());
//
//                parameters = new ArrayList<>();
//                parameters.add(trip.getVessel().getLibelle());
//                r.setMessageParameters(parameters);
//                results.add(r);
//
//            } else if (catchesWeight > capacityMax) {
//                r = new TripResult();
//                r.set(trip);
//                r.setMessageType(Message.ERROR);
//                r.setMessageCode(CODE_TRIP_CAPACITY_OVERRIDE);
//                r.setMessageLabel(LABEL_TRIP_CAPACITY_OVERRIDE);
//                r.setValueObtained(catchesWeight);
//                r.setValueExpected(capacityMax);
//                results.add(r);
//
//                parameters = new ArrayList<>();
//                parameters.add(trip.getID());
//                parameters.add(catchesWeight);
//                parameters.add(capacityMax);
//                r.setMessageParameters(parameters);
//                results.add(r);
//            }
//        }
        return results;
    }

}
