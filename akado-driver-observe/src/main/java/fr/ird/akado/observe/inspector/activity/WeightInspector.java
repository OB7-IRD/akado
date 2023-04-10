package fr.ird.akado.observe.inspector.activity;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveActivityInspector.class)
public class WeightInspector extends ObserveActivityInspector {

    public WeightInspector() {
        this.description = "Check if the total catch weight is consistent with elementary catches.";
    }

    @Override
    public Results execute() throws Exception {
        Activity activity = get();
        double  totalWeight = activity.getTotalWeight();
        double totalCatchWeightExpected = activity.totalCatchWeightFromCatches();
        if (equals(totalCatchWeightExpected , totalWeight)) {
            return null;
        }
        // weights are different
        ActivityResult r = createResult(MessageDescriptions.E1210_ACTIVITY_TOTAL_CATCH_WEIGHT, activity,
                                        activity.getID(getTrip(), getRoute()),
                                        totalWeight,
                                        totalCatchWeightExpected);
        r.setValueObtained(totalWeight);
        r.setValueExpected(totalCatchWeightExpected);
        return Results.of(r);
    }
}
