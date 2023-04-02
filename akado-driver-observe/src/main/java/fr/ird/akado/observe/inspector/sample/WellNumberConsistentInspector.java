package fr.ird.akado.observe.inspector.sample;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.Constant;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.SampleResult;
import fr.ird.common.message.Message;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveSampleInspector.class)
public class WellNumberConsistentInspector extends ObserveSampleInspector {

    public WellNumberConsistentInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the well number is consistent with the sample.";
    }

    public static boolean wellIsConsistent(Trip trip, Sample sample) {
        if (trip.getVessel().isBaitBoat()) {
            return true;
        }
        //FIXME In Observe sample.well is mandatory
        return sample.getWell() != null;
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();
        Sample s = get();
        if (!wellIsConsistent(getTrip(), s)) {
            SampleResult r = createResult(s, Message.ERROR, Constant.LABEL_SAMPLE_WELL_INCONSISTENCY, Constant.LABEL_SAMPLE_WELL_INCONSISTENCY, true, s.getTopiaId());
            results.add(r);
        }
        return results;
    }

}
