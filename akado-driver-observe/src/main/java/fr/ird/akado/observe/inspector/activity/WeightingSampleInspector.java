package fr.ird.akado.observe.inspector.activity;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.data.ps.logbook.Catch;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;
import fr.ird.driver.observe.business.data.ps.logbook.SampleActivity;
import fr.ird.driver.observe.business.referential.common.Species;

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

    public static boolean specieMustBeSampled(Species species) {
        return species.isYFT() // avdth code 1
                || species.isSKJ() // avdth code 2
                || species.isBET() // avdth code 3
                || species.isALB() // avdth code 4
                || species.isLTA() // avdth code 5
                || species.isFRI() // avdth code 6
                || species.isTUN() // avdth code 9
                // FIXME See if we need this one (avdth code 9 should be TUN + TUS)
//                || species.isTUS() // avdth code 40
                || species.isKAW() // avdth code 10
                || species.isLOT(); // avdth code 11
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
            if (specieMustBeSampled(aCatch.getSpecies())) {
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
