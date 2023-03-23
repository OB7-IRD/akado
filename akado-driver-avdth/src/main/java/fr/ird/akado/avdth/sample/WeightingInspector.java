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
package fr.ird.akado.avdth.sample;

import fr.ird.akado.core.common.AAProperties;
import static fr.ird.akado.avdth.Constant.CODE_SAMPLE_WEIGHTING_BB_LC;
import static fr.ird.akado.avdth.Constant.CODE_SAMPLE_WEIGHTING_BB_WEIGHT;
import static fr.ird.akado.avdth.Constant.CODE_SAMPLE_WEIGHTING_SUP_100;
import static fr.ird.akado.avdth.Constant.CODE_SAMPLE_WEIGHTING_RATIO;
import static fr.ird.akado.avdth.Constant.LABEL_SAMPLE_WEIGHTING_BB_LC;
import static fr.ird.akado.avdth.Constant.LABEL_SAMPLE_WEIGHTING_BB_WEIGHT;
import static fr.ird.akado.avdth.Constant.LABEL_SAMPLE_WEIGHTING_SUP_100;
import static fr.ird.akado.avdth.Constant.LABEL_SAMPLE_WEIGHTING_RATIO;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.result.SampleResult;
import fr.ird.akado.core.Inspector;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.ElementaryLanding;
import fr.ird.driver.avdth.business.Sample;
import fr.ird.driver.avdth.business.SampleWell;
import fr.ird.driver.avdth.business.Trip;
import fr.ird.driver.avdth.dao.TripDAO;
import java.util.ArrayList;

/**
 * Check if the weighting information for each sample well is consistent.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 18 juin 2014
 *
 */
public class WeightingInspector extends Inspector<Sample> {

    public WeightingInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the weighting information for each sample well is consistent.";
    }

//    public static double weightedWeight(Well w) {
//        double weightedWeight = 0;
//        if (w != null) {
//            for (WellPlan wp : w.getWellPlans()) {
//                weightedWeight += wp.getWeight();
//            }
//        }
//        return weightedWeight;
//    }
    public static double weightedWeight(Sample s) {
        double weightedWeight = 0;
        for (SampleWell sw : s.getSampleWells()) {
            weightedWeight += sw.getWeightedWeight();
        }
        return weightedWeight;
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Sample s = get();
        Trip trip = (new TripDAO()).findTripByVesselIdAndDate(s.getVessel(), s.getLandingDate());
        if (TripDAO.exist(trip)) {
            SampleResult r;
            double weight = s.getGlobalWeight();
            if (weight == 0) {
                weight = s.getMinus10Weight() + s.getPlus10Weight();
            }
            double weightedWeight = weightedWeight(s);
            if (AAProperties.WARNING_INSPECTOR.equals(AAProperties.ACTIVE_VALUE)) {

                if (trip.getVessel().isPurseSeine()) {
                    if (weight > 100) {
                        r = new SampleResult();
                        r.set(s);
                        r.setMessageType(Message.WARNING);
                        r.setMessageCode(CODE_SAMPLE_WEIGHTING_SUP_100);
                        r.setMessageLabel(LABEL_SAMPLE_WEIGHTING_SUP_100);

                        ArrayList parameters = new ArrayList<>();
                        parameters.add(s.getID());
                        parameters.add(weight);
                        r.setMessageParameters(parameters);
                        r.setInconsistent(true);

                        results.add(r);
                    }
//                    if (weightedWeight >= weight) {
//                        r = new SampleResult();
//                        r.set(s);
//                        r.setMessageType(Message.WARNING);
//                        r.setMessageCode(Constant.CODE_SAMPLE_WEIGHTING_INF_WEIGHT);
//                        r.setMessageLabel(Constant.LABEL_SAMPLE_WEIGHTING_INF_WEIGHT);
//
//                        ArrayList parameters = new ArrayList<>();
//                        parameters.add(s.getID());
//                        parameters.add(weightedWeight);
//                        parameters.add(weight);
//                        r.setMessageParameters(parameters);
//                        r.setInconsistent(true);
//
//                        results.add(r);
//                    }
                    if (weightedWeight < weight && !((weightedWeight / weight) >= 0.95)) {
                        r = new SampleResult();
                        r.set(s);
                        r.setMessageType(Message.WARNING);
                        r.setMessageCode(CODE_SAMPLE_WEIGHTING_RATIO);
                        r.setMessageLabel(LABEL_SAMPLE_WEIGHTING_RATIO);

                        ArrayList parameters = new ArrayList<>();
                        parameters.add(s.getID());
                        parameters.add(weightedWeight);
                        parameters.add(weight);
                        parameters.add(weightedWeight / weight);

                        r.setMessageParameters(parameters);
                        r.setInconsistent(true);

                        results.add(r);

                    }
                }
            }

            if (trip.getVessel().isBaitBoat()) {
                if (s.getSampleType().getCode() != 11) {
                    if (Math.abs(weightedWeight - weight) > 1) {
                        r = new SampleResult();
                        r.set(s);
                        r.setMessageType(Message.ERROR);
                        r.setMessageCode(CODE_SAMPLE_WEIGHTING_BB_WEIGHT);
                        r.setMessageLabel(LABEL_SAMPLE_WEIGHTING_BB_WEIGHT);

                        ArrayList parameters = new ArrayList<>();
                        parameters.add(s.getID());
                        parameters.add(weightedWeight);
                        parameters.add(weight);

                        r.setMessageParameters(parameters);
                        r.setInconsistent(true);

                        results.add(r);
                    }
                } else {
                    double lc = 0;
                    for (ElementaryLanding lotCommercial : trip.getLotsCommerciaux()) {
                        if (lotCommercial.getWeightCategoryLanding().getCode() == 10) {
                            lc += lotCommercial.getWeightLanding();
                        }
                    }
                    if (Math.abs(weightedWeight - lc) > 1) {
                        r = new SampleResult();
                        r.set(s);
                        r.setMessageType(Message.ERROR);
                        r.setMessageCode(CODE_SAMPLE_WEIGHTING_BB_LC);
                        r.setMessageLabel(LABEL_SAMPLE_WEIGHTING_BB_LC);

                        ArrayList parameters = new ArrayList<>();
                        parameters.add(s.getID());
                        parameters.add(weightedWeight);
                        parameters.add(lc);

                        r.setMessageParameters(parameters);
                        r.setInconsistent(true);

                        results.add(r);
                    }
                }
            }
        }
        return results;
    }

}
