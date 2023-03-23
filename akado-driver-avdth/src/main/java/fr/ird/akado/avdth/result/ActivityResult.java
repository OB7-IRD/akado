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

import fr.ird.akado.avdth.activity.FishingContextInspector;
import fr.ird.akado.avdth.activity.PositionInspector;
import fr.ird.akado.avdth.activity.WeightInspector;
import fr.ird.akado.avdth.activity.WeightingSampleInspector;
import fr.ird.akado.avdth.result.model.ActivityDataSheet;
import fr.ird.common.OTUtils;
import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.FishingContext;
import fr.ird.driver.avdth.business.Ocean;
import fr.ird.driver.avdth.business.SchoolType;
import java.util.ArrayList;
import java.util.List;

/**
 * Présente les données à afficher d'une activité qui a généré une erreur durant
 * de l'analyse d'AKaDo.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 9 juil. 2014
 *
 */
public class ActivityResult extends Result<Activity> {

    public ActivityResult() {
        super();
    }

    /**
     * Extrait les données d'une activité ayant généré une erreur dans l'analyse
     * métier.
     *
     * @param a l'activité
     * @return
     */
    public static ActivityDataSheet factory(Activity a) {
        ActivityDataSheet activityDto = new ActivityDataSheet();

        activityDto.setVesselCode(a.getVessel().getCode());
        activityDto.setEngine(a.getVessel().getVesselType().getName());
        activityDto.setLandingDate(a.getLandingDate());

        activityDto.setActivityDate(a.getFullDate());
        activityDto.setActivityNumber(a.getNumber());

        int operation = -1;
        if (a.getOperation() != null) {
            operation = a.getOperation().getCode();
        }

        activityDto.setOperationCode(operation);

        activityDto.setCatchWeight(a.getCatchWeight());
        activityDto.setElementaryCatchesWeight(WeightInspector.totalCatchWeight(a));
        activityDto.setSampleWeightedWeight(WeightingSampleInspector.sumOfSampleWeightedWeight(a));

        String schoolType = "?";
        if (a.getSchoolType() != null) {
            schoolType = a.getSchoolType().getCode() + " " + a.getSchoolType().getLibelle();
        }

        activityDto.setSchoolType(schoolType);
        String fishingContext = "";
        if (a.getFishingContexts() != null && !a.getFishingContexts().isEmpty()) {
//            fishingContext = a.get Type().getCode() + " " + a.getSchoolType().getLibelle();
            for (FishingContext fc : a.getFishingContexts()) {
                fishingContext += " | ";
                if (!FishingContextInspector.fishingContextIsConsistentWithArtificialSchool(fc)) {
                    fishingContext += "? ";
                }
                fishingContext += fc.getFishingContextType().getCode() + " ";
            }
        } else if (a.getSchoolType().getCode() == SchoolType.ARTIFICIAL_SCHOOL) {
            fishingContext = "?";
        }
        activityDto.setFishingContext(fishingContext);

        activityDto.setQuadrant(a.getQuadrant());

        String ocean = "-";
        if (a.getCodeOcean() == Ocean.ATLANTIQUE) {
            ocean = "AO";
        }
        if (a.getCodeOcean() == Ocean.INDIEN) {
            ocean = "IO";
        }
        activityDto.setOceanCode(ocean);

        String inLand = "";
        Boolean inAtlanticOcean = PositionInspector.inAtlanticOcean(a);
        Boolean inIndianOcean = PositionInspector.inIndianOcean(a);
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

        activityDto.setInOcean(ocean);
        activityDto.setInLand(inLand);

        activityDto.setTemperature(a.getTemperatureSurface());

        activityDto.setLatitude(OTUtils.degreesDecimalToStringDegreesMinutes(
                OTUtils.convertLatitude(a.getQuadrant(), a.getLatitude()), true) + " [" + a.getLatitude() + "]");
        activityDto.setLongitude(OTUtils.degreesDecimalToStringDegreesMinutes(
                OTUtils.convertLongitude(a.getQuadrant(), a.getLongitude()), false) + " [" + a.getLongitude() + "]");

        return activityDto;
    }

    @Override
    public List extractResults() {
        List<Object> list = new ArrayList<>();
        Activity a = get();
        if (a == null) {
            return list;
        }

        list.add(factory(a));
        return list;
    }

}
