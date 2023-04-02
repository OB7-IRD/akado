package fr.ird.akado.observe.inspector.activity;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.Constant;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.common.message.Message;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.data.ps.logbook.Catch;

import static fr.ird.akado.observe.Constant.EPSILON;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveActivityInspector.class)
public class WeightInspector extends ObserveActivityInspector {

    /**
     * Calculate the total catches weight with discards.
     *
     * @param a activity
     * @return the sum of catches weight.
     */
    public static double totalCatchWeight(Activity a) {
        double totalCatchWeight = 0d;
        for (Catch aCatch : a.getCatches()) {
            float weight = aCatch.getWeight();
            totalCatchWeight += weight;
        }
        return totalCatchWeight;
    }

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
        double totalCatchWeightExpected = totalCatchWeight(activity);
        if (Math.abs(totalCatchWeightExpected - totalWeight) > EPSILON) {
            ActivityResult r = createResult(activity, Message.ERROR, Constant.CODE_ACTIVITY_TOTAL_CATCH_WEIGHT, Constant.LABEL_ACTIVITY_TOTAL_CATCH_WEIGHT, false,
                                            activity.getTopiaId(),
                                            totalWeight,
                                            totalCatchWeightExpected);
            r.setValueObtained(totalWeight);
            r.setValueExpected(totalCatchWeightExpected);
            results.add(r);
        }
        return results;
    }
}
