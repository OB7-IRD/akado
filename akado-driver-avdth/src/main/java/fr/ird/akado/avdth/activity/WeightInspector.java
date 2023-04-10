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
package fr.ird.akado.avdth.activity;

import static fr.ird.akado.avdth.Constant.CODE_ACTIVITY_TOTAL_CATCH_WEIGHT;
import static fr.ird.akado.avdth.Constant.LABEL_ACTIVITY_TOTAL_CATCH_WEIGHT;
import fr.ird.akado.avdth.result.ActivityResult;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.core.Inspector;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.ElementaryCatch;
import java.util.ArrayList;

/**
 * Check if the total catch weight is consistent with elementary catches.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 21 mai 2014
 *
 */
public class WeightInspector extends Inspector<Activity> {

    public WeightInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the total catch weight is consistent with elementary catches.";
    }

    /**
     * Calculate the total catches weight without discards.
     *
     * @param a activity
     * @return the sum of catches weight.
     */
    public static double totalCatchWeightWithoutDiscards(Activity a) {
        double totalCatchWeight = 0d;
        for (ElementaryCatch capture : a.getElementaryCatchs()) {
            if (!((capture.getSizeCategory().getSpecies().getCode() >= 800
                    && capture.getSizeCategory().getSpecies().getCode() <= 899)
                    || capture.getSizeCategory().getSpecies().getCode() == 8)) {
                totalCatchWeight += capture.getCatchWeight();
            }
        }
        return totalCatchWeight;
    }

    /**
     * Calculate the total catches weight with discards.
     *
     * @param a activity
     * @return the sum of catches weight.
     */
    public static double totalCatchWeight(Activity a) {
        double totalCatchWeight = 0d;
        for (ElementaryCatch capture : a.getElementaryCatchs()) {
            totalCatchWeight += capture.getCatchWeight();
        }
        return totalCatchWeight;
    }

    public static double totalCatchWeightWithTargetedSpecies(Activity a) {
        double totalCatchWeight = 0;
        for (ElementaryCatch capture : a.getElementaryCatchs()) {
            if ((capture.getSizeCategory().getSpecies().getCode() >= 1
                    && capture.getSizeCategory().getSpecies().getCode() <= 6)
                    || capture.getSizeCategory().getSpecies().getCode() == 11
                    || capture.getSizeCategory().getSpecies().getCode() == 43) {
                totalCatchWeight += capture.getCatchWeight();
            }
        }
        return totalCatchWeight;
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Activity a = get();
        double totalCatchWeightExpected = totalCatchWeight(a);
        if (!equals(totalCatchWeightExpected , a.getCatchWeight())) {
            ActivityResult r = new ActivityResult();
            r.set(a);
            r.setMessageType(Message.ERROR);
            r.setMessageCode(CODE_ACTIVITY_TOTAL_CATCH_WEIGHT);
            r.setMessageLabel(LABEL_ACTIVITY_TOTAL_CATCH_WEIGHT);

            r.setValueObtained(a.getCatchWeight());
            r.setValueExpected(totalCatchWeightExpected);

            ArrayList parameters = new ArrayList<>();
            parameters.add(a.getID());
            parameters.add(a.getCatchWeight());
            parameters.add(totalCatchWeightExpected);

            r.setMessageParameters(parameters);
            results.add(r);

        }
        return results;
    }
}
