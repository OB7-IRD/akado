package fr.ird.akado.observe.inspector.activity;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;

import static fr.ird.akado.observe.Constant.EPSILON;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveActivityInspector.class)
public class WeightInspector extends ObserveActivityInspector {

    public WeightInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the total catch weight is consistent with elementary catches.";
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();

        Activity activity = get();
        float totalWeight = activity.getTotalWeight();
        double totalCatchWeightExpected = activity.totalCatchWeightFromCatches();
        if (Math.abs(totalCatchWeightExpected - totalWeight) > EPSILON) {
            ActivityResult r = createResult(MessageDescriptions.E_1210_ACTIVITY_TOTAL_CATCH_WEIGHT, activity,
                                            activity.getID(getTrip(), getRoute()),
                                            totalWeight,
                                            totalCatchWeightExpected);
            r.setValueObtained(totalWeight);
            r.setValueExpected(totalCatchWeightExpected);
            results.add(r);
        }
        return results;
    }
}
