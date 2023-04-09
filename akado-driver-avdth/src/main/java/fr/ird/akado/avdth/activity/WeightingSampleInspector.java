/*
 * Copyright (C) 2017 Observatoire des écosystèmes pélagiques tropicaux exploités, IRD
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
package fr.ird.akado.avdth.activity;

import static fr.ird.akado.avdth.Constant.CODE_ACTIVITY_CATCH_WEIGHT_SAMPLE_WEIGHTED_WEIGHT;
import static fr.ird.akado.avdth.Constant.LABEL_ACTIVITY_CATCH_WEIGHT_SAMPLE_WEIGHTED_WEIGHT;

import fr.ird.akado.avdth.result.ActivityResult;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.sample.SpeciesInspector;
import fr.ird.akado.core.Inspector;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.*;
import fr.ird.driver.avdth.dao.SampleDAO;
import fr.ird.driver.avdth.dao.SampleWellDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 17 octobre 2017
 */
public class WeightingSampleInspector extends Inspector<Activity> {

    public WeightingSampleInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "";
    }

    public static double sumOfSampleWeightedWeight(Activity a) {
        double sumOfSampleWeightedWeight = 0d;
//        Sample s = (new SampleDAO()).findSampleByVessefIdAndDate(a.getVessel().getCode(), a.getLandingDate(),a.getNumber());
//        s.getSampleSpecies();
//        s.getSampleWells();
        List<SampleWell> sampleWells = (new SampleWellDAO()).findSampleWellByActivity(
                a.getVessel().getCode(), a.getLandingDate(),
                a.getDate(), a.getNumber());
        for (SampleWell sw : sampleWells) {
            sumOfSampleWeightedWeight += sw.getWeightedWeight();
        }
        return sumOfSampleWeightedWeight;
    }

    public static boolean isDiscard(Species species) {
        return species != null && (species.getCode() == 8 || (species.getCode() >= 800 && species.getCode() < 900));
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Activity a = get();
        Double sumOfElementaryCatches = 0d;
        for (ElementaryCatch ec : a.getElementaryCatchs()) {
            if (ec.getSizeCategory().getSpecies() != null && SpeciesInspector.specieMustBeSampled(ec.getSizeCategory().getSpecies().getCode()))
                sumOfElementaryCatches += ec.getCatchWeight();
        }
        Double sumOfSampleWeightedWeight = sumOfSampleWeightedWeight(a);

        if (sumOfSampleWeightedWeight != 0
                && Math.abs(sumOfElementaryCatches - sumOfSampleWeightedWeight) > EPSILON) {
            ActivityResult r = new ActivityResult();
            r.set(a);
            r.setMessageType(Message.ERROR);
            r.setMessageCode(CODE_ACTIVITY_CATCH_WEIGHT_SAMPLE_WEIGHTED_WEIGHT);
            r.setMessageLabel(LABEL_ACTIVITY_CATCH_WEIGHT_SAMPLE_WEIGHTED_WEIGHT);

            r.setValueObtained(sumOfElementaryCatches);
            r.setValueExpected(sumOfSampleWeightedWeight);

            ArrayList parameters = new ArrayList<>();
            parameters.add(a.getID());
            parameters.add(sumOfElementaryCatches);
            parameters.add(sumOfSampleWeightedWeight);

            r.setMessageParameters(parameters);
            results.add(r);

        }
        return results;
    }
}
