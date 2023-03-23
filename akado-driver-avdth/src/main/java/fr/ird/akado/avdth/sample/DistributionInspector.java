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
import static fr.ird.akado.avdth.Constant.LABEL_SAMPLE_DISTRIBUTION_M10_P10;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.result.SampleResult;
import fr.ird.akado.core.Inspector;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Sample;
import fr.ird.driver.avdth.business.WeightCategoryWellPlan;
import fr.ird.driver.avdth.business.WellPlan;
import java.util.ArrayList;

/**
 * Compare la somme des +10/-10 saisie dans les plans de cuve avec celle saisie
 * dans l'échantillon.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class DistributionInspector extends Inspector<Sample> {

    public static boolean distributionIsInconsistent(Sample s) {
        double m10Weight = 0d;
        double p10Weight = 0d;
        for (WellPlan wp : s.getWell().getWellPlans()) {
            if (wp.getWeightCategory().getCode() == WeightCategoryWellPlan.M10) {
                m10Weight += wp.getWeight();
            }
            if (wp.getWeightCategory().getCode() == WeightCategoryWellPlan.UNKNOWN 
                    && wp.getSpecies().getCode() == 2) {
                m10Weight += wp.getWeight();
            }
            if (wp.getWeightCategory().getCode() == WeightCategoryWellPlan.P10) {
                p10Weight += wp.getWeight();
            }
        }
        return m10Weight != s.getMinus10Weight() || p10Weight != s.getPlus10Weight();
    }

    @Override
    public Results execute() {
        Results results = new Results();

        Sample s = get();
        SampleResult r;

        if (s.getWell() == null) {
            return results;
        }

        if (distributionIsInconsistent(s)) {
            r = new SampleResult();
            r.set(s);
            r.setMessageType(Message.ERROR);
            r.setMessageCode(Constant.CODE_SAMPLE_DISTRIBUTION_M10_P10);
            r.setMessageLabel(LABEL_SAMPLE_DISTRIBUTION_M10_P10);

            ArrayList parameters = new ArrayList<>();
            parameters.add(s.getID());
            parameters.add(s.getWell().getID());
            r.setMessageParameters(parameters);
            r.setInconsistent(true);

            results.add(r);
        }
        return results;

    }

}
