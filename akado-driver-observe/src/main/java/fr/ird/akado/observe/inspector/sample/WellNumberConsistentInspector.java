package fr.ird.akado.observe.inspector.sample;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.SampleResult;
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

    public static boolean wellIsConsistent(Trip trip, Sample sample) {
        if (trip.getVessel().isBaitBoat()) {
            return true;
        }
        //FIXME In Observe sample.well is mandatory
        return sample.getWell() != null;
    }

    public WellNumberConsistentInspector() {
        this.description = "Check if the well number is consistent with the sample.";
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();
        Sample sample = get();
        if (!wellIsConsistent(getTrip(), sample)) {
            SampleResult r = createResult(MessageDescriptions.E_1313_SAMPLE_WELL_INCONSISTENCY, sample,
                                          sample.getID(getTrip()));
            results.add(r);
        }
        return results;
    }

}
