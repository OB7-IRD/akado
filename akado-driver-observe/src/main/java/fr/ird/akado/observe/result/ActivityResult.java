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
import fr.ird.akado.observe.WithRoute;
import fr.ird.akado.observe.inspector.activity.FishingContextInspector;
import fr.ird.akado.observe.inspector.activity.PositionInspector;
import fr.ird.akado.observe.inspector.activity.WeightingSampleInspector;
import fr.ird.akado.observe.result.model.ActivityDataSheet;
import fr.ird.common.DateUtils;
import fr.ird.common.OTUtils;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.data.ps.logbook.Route;
import fr.ird.driver.observe.business.referential.common.Ocean;
import fr.ird.driver.observe.business.referential.ps.common.ObservedSystem;
import io.ultreia.java4all.util.Dates;

import java.util.ArrayList;
import java.util.Date;
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

    private Trip trip;
    private Route route;

    public ActivityResult(Activity datum, MessageDescription messageDescription) {
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
//        if (a == null) {
//            return list;
//        }
        list.add(factory(a));
        return list;
    }

    public ActivityDataSheet factory(Activity activity) {
        ActivityDataSheet result = new ActivityDataSheet();

        result.setVesselCode(trip.getVessel().getCode());
        result.setEngine(trip.getVessel().getVesselType().getLabel2());
        result.setLandingDate(DateUtils.formatDate(trip.getEndDate()));

        Date dateAndTime = activity.getTime() == null ? route.getDate() : Dates.getDateAndTime(route.getDate(), activity.getTime(), false, false);
        result.setActivityDate(DateUtils.formatDate(dateAndTime));
        result.setActivityNumber(activity.getNumber());

        String operation = activity.getVesselActivity().getCode();

        result.setOperationCode(operation);

        result.setCatchWeight((double) activity.getTotalWeight());
        result.setElementaryCatchesWeight(activity.totalCatchWeightFromCatches());
        result.setSampleWeightedWeight(WeightingSampleInspector.sumOfSampleWeightedWeight(trip, activity));

        String schoolType = "?";
        if (activity.getSchoolType() != null) {
            schoolType = activity.getSchoolType().getCode() + " " + activity.getSchoolType().getLabel2();
        }

        result.setSchoolType(schoolType);
        StringBuilder fishingContext = new StringBuilder();
        if (activity.getObservedSystem() != null && !activity.getObservedSystem().isEmpty()) {
            for (ObservedSystem fc : activity.getObservedSystem()) {
                fishingContext.append(" | ");
                if (!FishingContextInspector.fishingContextIsConsistentWithArtificialSchool(fc)) {
                    fishingContext.append("? ");
                }
                //FIXME was fishingContext += fc.getFishingContextType().getCode() + " ";
                fishingContext.append(fc.getSchoolType().getCode()).append(" ");
            }
        } else if (activity.getSchoolType() != null && activity.getSchoolType().isArtificial()) {
            fishingContext = new StringBuilder("?");
        }
        result.setFishingContext(fishingContext.toString());

        result.setQuadrant(activity.getQuadrant());

        String ocean = "-";
        if (Objects.equals(trip.getOcean().getTopiaId(), Ocean.ATLANTIQUE)) {
            ocean = "AO";
        }
        if (Objects.equals(trip.getOcean().getTopiaId(), Ocean.INDIEN)) {
            ocean = "IO";
        }
        result.setOceanCode(ocean);

        String inLand = "";
        boolean inAtlanticOcean = PositionInspector.inAtlanticOcean(activity);
        boolean inIndianOcean = PositionInspector.inIndianOcean(activity);
        if (!inAtlanticOcean && !inIndianOcean) {
            inLand = PositionInspector.inLand(activity);
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

        result.setTemperature((double) activity.getSeaSurfaceTemperature());

        if (activity.getLatitude() != null) {
            result.setLatitude(OTUtils.degreesDecimalToStringDegreesMinutes(
                    Double.valueOf(activity.getLatitude()), true) + " [" + activity.getLatitude() + "]");
        }
        if (activity.getLongitude() != null) {
            result.setLongitude(OTUtils.degreesDecimalToStringDegreesMinutes(
                    Double.valueOf(activity.getLongitude()), false) + " [" + activity.getLongitude() + "]");
        }
        return result;
    }

}
