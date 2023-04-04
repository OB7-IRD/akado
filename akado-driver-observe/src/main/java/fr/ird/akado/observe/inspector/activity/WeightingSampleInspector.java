package fr.ird.akado.observe.inspector.activity;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.Constant;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.common.message.Message;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.data.ps.logbook.Catch;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;
import fr.ird.driver.observe.business.data.ps.logbook.SampleActivity;

import static fr.ird.akado.observe.Constant.EPSILON;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveActivityInspector.class)
public class WeightingSampleInspector extends ObserveActivityInspector {

    public static double sumOfSampleWeightedWeight(Trip trip, Activity a) {
        double sumOfSampleWeightedWeight = 0d;
        for (Sample sample : trip.getLogbookSample()) {
            for (SampleActivity sampleActivity : sample.getSampleActivity()) {
                if (a.equals(sampleActivity.getActivity())) {
                    float weightedWeight = sampleActivity.getWeightedWeight();
                    sumOfSampleWeightedWeight += weightedWeight;
                }
            }
        }
        return sumOfSampleWeightedWeight;
    }

    public static boolean specieMustBeSampled(String speciesCode) {
        if (speciesCode == null) {
            return false;
        }
        int code = Integer.parseInt(speciesCode);
        return (code >= 1 && code <= 6) || code == 9 || code == 10 || code == 11;
    }

    public WeightingSampleInspector() {
        this.name = this.getClass().getName();
        //FIXME
        this.description = "";
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();
        Activity activity = get();
        double sumOfElementaryCatches = 0d;
        for (Catch aCatch : activity.getCatches()) {
            float catchWeight = aCatch.getWeight();
            if (catchWeight == 0) {
                continue;
            }
            if (aCatch.getSpecies() != null && specieMustBeSampled(aCatch.getSpecies().getCode())) {
                sumOfElementaryCatches += catchWeight;
            }
        }
        double sumOfSampleWeightedWeight = sumOfSampleWeightedWeight(getTrip(), activity);

        if (sumOfSampleWeightedWeight != 0 && Math.abs(sumOfElementaryCatches - sumOfSampleWeightedWeight) > EPSILON) {
            ActivityResult r = createResult(activity, Message.ERROR, Constant.CODE_ACTIVITY_CATCH_WEIGHT_SAMPLE_WEIGHTED_WEIGHT, Constant.LABEL_ACTIVITY_CATCH_WEIGHT_SAMPLE_WEIGHTED_WEIGHT, false,
                                            activity.getTopiaId(),
                                            sumOfElementaryCatches,
                                            sumOfSampleWeightedWeight);
            r.setValueObtained(sumOfElementaryCatches);
            r.setValueExpected(sumOfSampleWeightedWeight);

            results.add(r);

        }
        return results;
    }
}
