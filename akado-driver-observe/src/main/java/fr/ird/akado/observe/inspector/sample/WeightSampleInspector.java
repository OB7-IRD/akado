package fr.ird.akado.observe.inspector.sample;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.SampleResult;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveSampleInspector.class)
public class WeightSampleInspector extends ObserveSampleInspector {

    public WeightSampleInspector() {
        this.description = "Check if the sample weight (m10 and p10) is consistent with the global weight sample.";
    }

    @Override
    public Results execute() throws Exception {
        Sample sample = get();
        double smallsWeight = sample.getSmallsWeight();
        double bigsWeight = sample.getBigsWeight();
        double totalWeight = sample.getTotalWeight();
        if ((smallsWeight + bigsWeight != 0 || totalWeight != 0) && (!(smallsWeight + bigsWeight > 0) || totalWeight == 0)) {
            return null;
        }
        SampleResult r = createResult(MessageDescriptions.E1319_SAMPLE_WEIGHT_INCONSISTENCY, sample,
                                      sample.getID(getTrip()),
                                      smallsWeight + bigsWeight,
                                      totalWeight);
        return Results.of(r);
    }
}
