package fr.ird.akado.observe.inspector.activity;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.inspector.sample.ObserveSampleInspector;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.data.ps.logbook.Catch;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;
import fr.ird.driver.observe.business.data.ps.logbook.SampleActivity;

import java.util.Objects;

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
                    double weightedWeight = sampleActivity.getWeightedWeight();
                    sumOfSampleWeightedWeight += weightedWeight;
                }
            }
        }
        return sumOfSampleWeightedWeight;
    }

    public WeightingSampleInspector() {
        this.description = "Check consistency between elementary catches weight and sample activity weighted weight.";
    }

    @Override
    public Results execute() throws Exception {
        Activity activity = get();
        double sumOfSampleWeightedWeight = sumOfSampleWeightedWeight(getTrip(), activity);
        if (sumOfSampleWeightedWeight == 0) {
            return null;
        }
        double sumOfElementaryCatches = 0d;
        for (Catch aCatch : activity.getCatches()) {
            double catchWeight = aCatch.getWeight();
            if (catchWeight == 0) {
                continue;
            }
            if (aCatch.getSpeciesFate() != null && Objects.equals(aCatch.getSpeciesFate().getDiscard(), true)) {
                // reject discard catches
                continue;
            }
            if (ObserveSampleInspector.specieMustBeSampled(aCatch.getSpecies())) {
                sumOfElementaryCatches += catchWeight;
            }
        }

        if (equals(sumOfElementaryCatches , sumOfSampleWeightedWeight)) {
            return null;
        }
        ActivityResult r = createResult(MessageDescriptions.E1233_ACTIVITY_CATCH_WEIGHT_SAMPLE_WEIGHTED_WEIGHT, activity,
                                        activity.getID(getTrip(), getRoute()),
                                        sumOfElementaryCatches,
                                        sumOfSampleWeightedWeight);
        r.setValueObtained(sumOfElementaryCatches);
        r.setValueExpected(sumOfSampleWeightedWeight);

        return Results.of(r);

    }
}
