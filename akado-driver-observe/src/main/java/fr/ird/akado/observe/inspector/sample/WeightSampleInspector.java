package fr.ird.akado.observe.inspector.sample;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.Constant;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.SampleResult;
import fr.ird.common.message.Message;
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
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the sample weight (m10 and p10) is consistent with the global weight sample.";
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();
        Sample s = get();
        float smallsWeight = s.getSmallsWeight();
        float bigsWeight = s.getBigsWeight();
        float totalWeight = s.getTotalWeight();
        if ((smallsWeight + bigsWeight == 0 && totalWeight == 0) || (smallsWeight + bigsWeight > 0 && totalWeight != 0)) {
            SampleResult r = createResult(s, Message.ERROR, Constant.CODE_SAMPLE_WEIGHT_INCONSISTENCY, Constant.LABEL_SAMPLE_WEIGHT_INCONSISTENCY, true,
                                          s.getTopiaId(),
                                          smallsWeight + bigsWeight,
                                          totalWeight);
            results.add(r);
        }
        return results;
    }
}
