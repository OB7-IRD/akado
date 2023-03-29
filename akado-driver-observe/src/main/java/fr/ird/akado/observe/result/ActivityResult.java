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

import fr.ird.akado.observe.WithRoute;
import fr.ird.akado.observe.inspector.activity.FishingContextInspector;
import fr.ird.akado.observe.inspector.activity.PositionInspector;
import fr.ird.akado.observe.inspector.activity.WeightInspector;
import fr.ird.akado.observe.inspector.activity.WeightingSampleInspector;
import fr.ird.akado.observe.result.model.ActivityDataSheet;
import fr.ird.common.OTUtils;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.data.ps.logbook.Route;
import fr.ird.driver.observe.business.referential.common.Ocean;
import fr.ird.driver.observe.business.referential.ps.common.ObservedSystem;
import fr.ird.driver.observe.business.referential.ps.common.SchoolType;
import io.ultreia.java4all.util.Dates;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Présente les données à afficher d'une activité qui a généré une erreur durant
 * de l'analyse d'AKaDo.
 * <p>
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ActivityResult extends Result<Activity> implements WithRoute {

    public static ActivityDataSheet factory(Trip trip, Route route, Activity a) {
        ActivityDataSheet result = new ActivityDataSheet();

        result.setVesselCode(trip.getVessel().getCode());
        result.setEngine(trip.getVessel().getVesselType().getLabel2());
        result.setLandingDate(TripResult.formatDate(trip.getEndDate()));

        result.setActivityDate(TripResult.formatDate(Dates.getDateAndTime(route.getDate(), a.getTime(), false, false)));
        result.setActivityNumber(a.getNumber());

        String operation = a.getVesselActivity().getCode();

        result.setOperationCode(operation);

        if (a.getTotalWeight() != null) {
            result.setCatchWeight(Double.valueOf(a.getTotalWeight()));
        }
        result.setElementaryCatchesWeight(WeightInspector.totalCatchWeight(a));
        result.setSampleWeightedWeight(WeightingSampleInspector.sumOfSampleWeightedWeight(trip, a));

        String schoolType = "?";
        if (a.getSchoolType() != null) {
            schoolType = a.getSchoolType().getCode() + " " + a.getSchoolType().getLabel2();
        }

        result.setSchoolType(schoolType);
        StringBuilder fishingContext = new StringBuilder();
        if (a.getObservedSystem() != null && !a.getObservedSystem().isEmpty()) {
            for (ObservedSystem fc : a.getObservedSystem()) {
                fishingContext.append(" | ");
                if (!FishingContextInspector.fishingContextIsConsistentWithArtificialSchool(fc)) {
                    fishingContext.append("? ");
                }
                //FIXME was fishingContext += fc.getFishingContextType().getCode() + " ";
                fishingContext.append(fc.getSchoolType().getCode()).append(" ");
            }
        } else if (Objects.equals(a.getSchoolType().getCode(), SchoolType.ARTIFICIAL_SCHOOL)) {
            fishingContext = new StringBuilder("?");
        }
        result.setFishingContext(fishingContext.toString());

        result.setQuadrant(a.getQuadrant());

        String ocean = "-";
        if (Objects.equals(trip.getOcean().getTopiaId(), Ocean.ATLANTIQUE)) {
            ocean = "AO";
        }
        if (Objects.equals(trip.getOcean().getTopiaId(), Ocean.INDIEN)) {
            ocean = "IO";
        }
        result.setOceanCode(ocean);

        String inLand = "";
        boolean inAtlanticOcean = PositionInspector.inAtlanticOcean(a);
        boolean inIndianOcean = PositionInspector.inIndianOcean(a);
        if (!inAtlanticOcean && !inIndianOcean) {
            inLand = PositionInspector.inLand(a);
        }
        ocean = "-";
        if (inAtlanticOcean) {
            ocean = "AO";
        }
        if (inIndianOcean) {
            ocean = "IO";
        }

        result.setInOcean(ocean);
        result.setInLand(inLand);

        if (a.getSeaSurfaceTemperature() != null) {
            result.setTemperature(Double.valueOf(a.getSeaSurfaceTemperature()));
        }

        if (a.getLatitude() != null) {
            result.setLatitude(OTUtils.degreesDecimalToStringDegreesMinutes(
                    Double.valueOf(a.getLatitude()), true) + " [" + a.getLatitude() + "]");
        }
        if (a.getLongitude() != null) {
            result.setLongitude(OTUtils.degreesDecimalToStringDegreesMinutes(
                    Double.valueOf(a.getLongitude()), false) + " [" + a.getLongitude() + "]");
        }
        return result;
    }

    public ActivityResult(Activity datum) {
        set(datum);
    }

    private Trip trip;
    private Route route;

    @Override
    public Trip getTrip() {
        return trip;
    }

    @Override
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    @Override
    public Route getRoute() {
        return route;
    }

    @Override
    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public List<ActivityDataSheet> extractResults() {
        List<ActivityDataSheet> list = new ArrayList<>();
        Activity a = get();
        if (a == null) {
            return list;
        }

        list.add(factory(getTrip(), getRoute(), a));
        return list;
    }

}
