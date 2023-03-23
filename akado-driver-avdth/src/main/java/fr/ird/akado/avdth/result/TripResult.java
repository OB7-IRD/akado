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
package fr.ird.akado.avdth.result;

import fr.ird.akado.avdth.result.model.TripDataSheet;
import fr.ird.akado.avdth.trip.FishingTimeInspector;
import fr.ird.akado.avdth.trip.HarbourInspector;
import fr.ird.akado.avdth.trip.LandingTotalWeightInspector;
import fr.ird.akado.avdth.trip.RecoveryTimeInspector;
import fr.ird.akado.avdth.trip.TimeAtSeaInspector;
import fr.ird.common.Utils;
import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.Harbour;
import fr.ird.driver.avdth.business.Trip;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 9 juil. 2014
 */
public class TripResult extends Result<Trip> {

    public static TripDataSheet factory(Trip trip) {
        TripDataSheet tdto = new TripDataSheet();

        tdto.setVesselCode(trip.getVessel().getCode());
        tdto.setEngine(trip.getVessel().getVesselType().getName());
        tdto.setLandingDate(trip.getLandingDate());
        Harbour harbour = trip.getLandingHarbour();
        String locode = "?";
        if (harbour != null) {
            locode = harbour.getLocode();
        }
        tdto.setLandingHarbour(locode);
        tdto.setHasLogbook(trip.getFlagEnquete());
        tdto.setDepartureDate(trip.getDepartureDate());

        harbour = trip.getDepartureHarbour();
        locode = "?";
        if (harbour != null) {
            locode = harbour.getLocode();
            if (!HarbourInspector.harbourAreIdentical(trip)) {
                locode = "? " + locode;
            }
        }
        tdto.setDepartureHarbour(locode);

        Activity a = trip.firstActivity();
        DateTime d = null;
        if (a != null) {
            d = a.getDate();
        }
        tdto.setFirstActivityDate(d);
        a = trip.lastActivity();
        d = null;
        if (a != null) {
            d = a.getDate();
        }
        tdto.setLastActivityDate(d);

        tdto.setRecoveryTimeDate(RecoveryTimeInspector.continuous(trip));

        tdto.setTimeAtSea(trip.getTimeAtSea());
        tdto.setTimeAtSeaExpected(TimeAtSeaInspector.timeAtSeaExpected(trip));
        tdto.setFishingTime(trip.getFishingTime());
        tdto.setFishingTimeExpected(FishingTimeInspector.fishingTimeExpected(trip));
        tdto.setLandingWeight(Utils.round(trip.getTotalLandingWeight(), 3));
        tdto.setLandingWeightExpected(LandingTotalWeightInspector.landingTotalWeightExpected(trip));

        tdto.setPartialLandingIndicator(trip.getFlagCaleVide());
//        LogService.getService(TripResult.class).logApplicationDebug(tdto.toString());
        return tdto;
    }

    public TripResult() {
        super();
    }

    @Override
    public List extractResults() {
        List<Object> list = new ArrayList<>();
        Trip trip = get();
        if (trip == null) {
            return list;
        }
        TripDataSheet tds = factory(trip);
        list.add(tds);
        return list;
    }

}
