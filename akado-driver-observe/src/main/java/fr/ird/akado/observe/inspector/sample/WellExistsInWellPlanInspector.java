package fr.ird.akado.observe.inspector.sample;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.SampleResult;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;
import fr.ird.driver.observe.business.data.ps.logbook.Well;

import java.util.Set;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveSampleInspector.class)
public class WellExistsInWellPlanInspector extends ObserveSampleInspector {

    public static boolean wellExistsInWellPlan(Trip trip, Sample sample) {
        Set<Well> tripWell = trip.getWell();
        if (tripWell.isEmpty()) {
            // no well plan found
            return true;
        }
        String wellId = sample.getWell();
        for (Well well : tripWell) {
            if (well.getWell().equals(wellId)) {
                return true;
            }
        }
        return false;
    }

    public WellExistsInWellPlanInspector() {
        this.description = "Check if the sample well exists in the optional trip well plan.";
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();
        Sample sample = get();
        if (!wellExistsInWellPlan(getTrip(), sample)) {
            SampleResult r = createResult(MessageDescriptions.E_1314_SAMPLE_WELL_INCONSISTENCY, sample,
                                          sample.getID(getTrip()),
                                          sample.getWell());
            results.add(r);
        }
        return results;
    }

}
