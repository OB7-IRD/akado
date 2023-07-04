/*
 * Copyright (C) 2016 Observatoire thonier, IRD
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
package fr.ird.akado.avdth.sample;

import fr.ird.akado.avdth.Constant;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.result.SampleResult;
import fr.ird.akado.core.Inspector;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Sample;
import fr.ird.driver.avdth.business.WeightCategoryWellPlan;
import fr.ird.driver.avdth.business.WellPlan;

import java.util.ArrayList;
import java.util.List;

import static fr.ird.akado.avdth.Constant.LABEL_SAMPLE_DISTRIBUTION_M10_P10;

/**
 * Compare la somme des +10/-10 saisie dans les plans de cuve avec celle saisie
 * dans l'échantillon.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class DistributionInspector extends Inspector<Sample> {

    public static double wellM10Weight(Sample sample) {
        double m10Weight = 0d;
        for (WellPlan wp : sample.getWell().getWellPlans()) {
            if (wp.getWeightCategory().getCode() == WeightCategoryWellPlan.M10) {
                m10Weight += wp.getWeight();
            }
            if (wp.getWeightCategory().getCode() == WeightCategoryWellPlan.UNKNOWN
                    && wp.getSpecies().getCode() == 2) {
                m10Weight += wp.getWeight();
            }
        }
        return m10Weight;
    }

    public static double wellP10Weight(Sample sample) {
        double p10Weight = 0d;
        for (WellPlan wp : sample.getWell().getWellPlans()) {
            if (wp.getWeightCategory().getCode() == WeightCategoryWellPlan.P10) {
                p10Weight += wp.getWeight();
            }
        }
        return p10Weight;
    }

    public static boolean distributionIsInconsistent(Sample sample) {
        double wellM10Weight = wellM10Weight(sample);
        double wellP10Weight = wellP10Weight(sample);
        double smallsWeight = sample.getMinus10Weight();
        double bigsWeight = sample.getPlus10Weight();
        return !equals(wellM10Weight, smallsWeight) || !equals(wellP10Weight, bigsWeight);
    }

    @Override
    public Results execute() {
        Results results = new Results();

        Sample s = get();
        if (s.getWell() == null) {
            return results;
        }

        double wellM10Weight = wellM10Weight(s);
        double wellP10Weight = wellP10Weight(s);
        double smallsWeight = s.getMinus10Weight();
        double bigsWeight = s.getPlus10Weight();

        if (!equals(wellM10Weight, smallsWeight) || !equals(wellP10Weight, bigsWeight)) {
            SampleResult r = new SampleResult();
            r.set(s);
            r.setMessageType(Message.ERROR);
            r.setMessageCode(Constant.CODE_SAMPLE_DISTRIBUTION_M10_P10);
            r.setMessageLabel(LABEL_SAMPLE_DISTRIBUTION_M10_P10);

            List<String> parameters = new ArrayList<>();
            parameters.add(s.getID());
            parameters.add(String.valueOf(smallsWeight));
            parameters.add(String.valueOf(bigsWeight));
            parameters.add(s.getWell().getID());
            parameters.add(String.valueOf(wellM10Weight));
            parameters.add(String.valueOf(wellP10Weight));
            r.setMessageParameters(parameters);
            r.setInconsistent(true);

            results.add(r);
        }
        return results;

    }

}
