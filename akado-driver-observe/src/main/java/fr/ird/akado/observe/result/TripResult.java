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
package fr.ird.akado.observe.result;

import fr.ird.akado.observe.inspector.trip.FishingTimeInspector;
import fr.ird.akado.observe.inspector.trip.HarbourInspector;
import fr.ird.akado.observe.inspector.trip.LandingTotalWeightInspector;
import fr.ird.akado.observe.inspector.trip.RecoveryTimeInspector;
import fr.ird.akado.observe.inspector.trip.TimeAtSeaInspector;
import fr.ird.akado.observe.result.model.TripDataSheet;
import fr.ird.common.Utils;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Route;
import fr.ird.driver.observe.business.referential.common.Harbour;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class TripResult extends Result<Trip> {

    protected static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");

    public static String formatDate(Date date) {
        return date == null ? null : dateFormat.format(date);
    }

    public static TripDataSheet factory(Trip trip) {
        TripDataSheet result = new TripDataSheet();

        result.setVesselCode(trip.getVessel().getCode());
        result.setEngine(trip.getVessel().getVesselType().getLabel2());
        result.setLandingDate(formatDate(trip.getEndDate()));
        Harbour harbour = trip.getLandingHarbour();
        String locode = "?";
        if (harbour != null) {
            locode = harbour.getLocode();
        }
        result.setLandingHarbour(locode);
        result.setHasLogbook(trip.hasLogbook());
        result.setDepartureDate(formatDate(trip.getStartDate()));

        harbour = trip.getDepartureHarbour();
        locode = "?";
        if (harbour != null) {
            locode = harbour.getLocode();
            if (!HarbourInspector.harbourAreIdentical(trip)) {
                locode = "? " + locode;
            }
        }
        result.setDepartureHarbour(locode);

        Route a = trip.firstRouteWithActivity();
        Date d = null;
        if (a != null) {
            d = a.getDate();
        }
        result.setFirstActivityDate(formatDate(d));
        a = trip.lastRouteWithActivity();
        d = null;
        if (a != null) {
            d = a.getDate();
        }
        result.setLastActivityDate(formatDate(d));

        result.setRecoveryTimeDate(formatDate(RecoveryTimeInspector.continuous(trip)));

        if (trip.getTimeAtSea() != null) {
            result.setTimeAtSea(trip.getTimeAtSea());
        }
        result.setTimeAtSeaExpected(TimeAtSeaInspector.timeAtSeaExpected(trip));
        if (trip.getFishingTime() != null) {
            result.setFishingTime(trip.getFishingTime());
        }
        result.setFishingTimeExpected(FishingTimeInspector.fishingTimeExpected(trip));
        if (trip.getLandingTotalWeight() != null) {
            result.setLandingWeight(Utils.round(trip.getLandingTotalWeight(), 3));
        }
        result.setLandingWeightExpected(LandingTotalWeightInspector.landingTotalWeightExpected(trip));

        //FIXME
//        result.setPartialLandingIndicator(trip.getFlagCaleVide());
        return result;
    }

    public TripResult() {
        super();
    }

    public TripResult(Trip datum) {
        set(datum);
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
