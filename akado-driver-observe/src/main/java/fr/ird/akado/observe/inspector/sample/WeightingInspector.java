package fr.ird.akado.observe.inspector.sample;

import com.google.auto.service.AutoService;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.observe.Constant;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.SampleResult;
import fr.ird.common.message.Message;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.landing.Landing;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;
import fr.ird.driver.observe.business.data.ps.logbook.SampleActivity;

import java.util.Objects;

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
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the weighting information for each sample well is consistent.";
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();
        Sample s = get();
        Trip trip = getTrip();
        double weight = s.getTotalWeight();
        if (weight == 0) {
            float smallsWeight = s.getSmallsWeight();
            float bigsWeight = s.getBigsWeight();
            weight = smallsWeight + bigsWeight;
        }
        double weightedWeight = weightedWeight(s);
        if (AAProperties.WARNING_INSPECTOR.equals(AAProperties.ACTIVE_VALUE)) {

            if (trip.getVessel().isPurseSeine()) {
                if (weight > 100) {
                    SampleResult r = createResult(s, Message.WARNING, Constant.CODE_SAMPLE_WEIGHTING_SUP_100, Constant.LABEL_SAMPLE_WEIGHTING_SUP_100, true, s.getTopiaId(), weight);
                    results.add(r);
                }
                if (weightedWeight < weight && !((weightedWeight / weight) >= 0.95)) {
                    SampleResult r = createResult(s, Message.WARNING, Constant.CODE_SAMPLE_WEIGHTING_RATIO, Constant.LABEL_SAMPLE_WEIGHTING_RATIO, true, s.getTopiaId(), weightedWeight, weight, weightedWeight / weight);
                    results.add(r);

                }
            }
        }
        if (trip.getVessel().isBaitBoat()) {
            //FIXME
            if (!Objects.equals(s.getSampleType().getCode(), "11")) {
                if (Math.abs(weightedWeight - weight) > 1) {
                    SampleResult r = createResult(s, Message.ERROR, Constant.CODE_SAMPLE_WEIGHTING_BB_WEIGHT, Constant.LABEL_SAMPLE_WEIGHTING_BB_WEIGHT, true, s.getTopiaId(), weightedWeight, weight);
                    results.add(r);
                }
            } else {
                double lc = 0;
                for (Landing landing : trip.getLanding()) {
                    //FIXME
                    if (landing.getWeightCategory() != null && Objects.equals(landing.getWeightCategory().getCode(), "10")) {
                        lc += landing.getWeight();
                    }
                }
                if (Math.abs(weightedWeight - lc) > 1) {
                    SampleResult r = createResult(s, Message.ERROR, Constant.CODE_SAMPLE_WEIGHTING_BB_LC, Constant.LABEL_SAMPLE_WEIGHTING_BB_LC, true, s.getTopiaId(), weightedWeight, lc);
                    results.add(r);
                }
            }
        }
        return results;
    }
}
