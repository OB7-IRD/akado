package fr.ird.akado.observe.inspector.sample;

import com.google.auto.service.AutoService;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.SampleResult;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.landing.Landing;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;
import fr.ird.driver.observe.business.data.ps.logbook.SampleActivity;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveSampleInspector.class)
public class WeightingInspector extends ObserveSampleInspector {
    public static double weightedWeight(Sample s) {
        double result = 0;
        for (SampleActivity sw : s.getSampleActivity()) {
            float weightedWeight = sw.getWeightedWeight();
            result += weightedWeight;
        }
        return result;
    }

    public WeightingInspector() {
        this.description = "Check if the weighting information for each sample well is consistent.";
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();
        Sample sample = get();
        Trip trip = getTrip();
        double weight = sample.getTotalWeight();
        if (weight == 0) {
            weight = sample.getSmallsWeight() + sample.getBigsWeight();
        }
        double weightedWeight = weightedWeight(sample);
        if (AAProperties.isWarningInspectorEnabled()) {
            if (trip.getVessel().isPurseSeine()) {
                if (weight > 100) {
                    SampleResult r = createResult(MessageDescriptions.W_1320_SAMPLE_WEIGHTING_SUP_100, sample,
                                                  sample.getID(getTrip()),
                                                  weight);
                    results.add(r);
                }
                if (weightedWeight < weight && !((weightedWeight / weight) >= 0.95)) {
                    SampleResult r = createResult(MessageDescriptions.W_1322_SAMPLE_WEIGHTING_RATIO, sample,
                                                  sample.getID(getTrip()),
                                                  weightedWeight,
                                                  weight,
                                                  weightedWeight / weight);
                    results.add(r);

                }
            }
        }
        if (trip.getVessel().isBaitBoat()) {
            if (!sample.getSampleType().isAtLandingFreshFishBaitBoat()) {
                if (Math.abs(weightedWeight - weight) > 1) {
                    SampleResult r = createResult(MessageDescriptions.E_1327_SAMPLE_WEIGHTING_BB_WEIGHT, sample,
                                                  sample.getID(getTrip()),
                                                  weightedWeight,
                                                  weight);
                    results.add(r);
                }
            } else {
                double lc = 0;
                for (Landing landing : trip.getLanding()) {
                    if (landing.getWeightCategory() != null
                            && landing.getWeightCategory().getCode().startsWith("L-")
                            && landing.getWeightCategory().getCode().endsWith("-10")) {
                        lc += landing.getWeight();
                    }
                }
                if (Math.abs(weightedWeight - lc) > 1) {
                    SampleResult r = createResult(MessageDescriptions.E_1326_SAMPLE_WEIGHTING_BB_LC, sample,
                                                  sample.getID(getTrip()),
                                                  weightedWeight,
                                                  lc);
                    results.add(r);
                }
            }
        }
        return results;
    }
}
