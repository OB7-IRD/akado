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

import fr.ird.akado.core.common.MessageDescription;
import fr.ird.akado.observe.WithTrip;
import fr.ird.akado.observe.result.model.WellDataSheet;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Well;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class WellResult extends Result<Well> implements WithTrip {

    private Trip trip;

    public WellResult(Well datum, MessageDescription messageDescription) {
        super(datum, messageDescription);
    }

    @Override
    public Trip getTrip() {
        return trip;
    }

    @Override
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @Override
    public List<WellDataSheet> extractResults() {
        List<WellDataSheet> list = new ArrayList<>();
        Well well = get();
        if (well == null) {
            return list;
        }
        list.addAll(factory(well));
        return list;
    }

    public List<WellDataSheet> factory(Well well) {
        List<WellDataSheet> list = new ArrayList<>();
//        Trip trip = (new TripDAO()).findTripByVesselIdAndDate(well.getVessel(), well.getLandingDate());
//        WellDataSheet wellDTO;
//
//        int vesseCode = well.getVessel().getCode();
//        String engine = well.getVessel().getVesselType().getName();
//        String landingDate = well.getLandingDate().toString(DateTimeUtils.DATE_FORMATTER);
//        boolean tripExist = TripDAO.exist(trip);
//
//        boolean wellPlanEmpty = well.getWellPlans().isEmpty();
//
//        if (wellPlanEmpty) {
//            wellDTO = new WellDataSheet(vesseCode, engine, landingDate, tripExist, wellPlanEmpty);
//            wellDTO.setWellNumber(well.getNumber());
//            list.add(wellDTO);
//        } else {
//            for (WellPlan wp : well.getWellPlans()) {
//                String activityDate = wp.getActivityDate().toString(DateTimeUtils.DATE_FORMATTER);
//                int activityNumber = wp.getActivityNumber();
//                boolean activityExist = ActivityConsistent.activityExists(wp);
//
//                wellDTO = new WellDataSheet(vesseCode, engine, landingDate, tripExist, wellPlanEmpty, activityDate, activityNumber, activityExist);
//                wellDTO.setWellNumber(well.getNumber());
//                list.add(wellDTO);
//            }
//        }

        return list;
    }
}
