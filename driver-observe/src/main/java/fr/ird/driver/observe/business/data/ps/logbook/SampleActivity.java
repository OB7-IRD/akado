package fr.ird.driver.observe.business.data.ps.logbook;

import fr.ird.driver.observe.business.data.DataEntity;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class SampleActivity extends DataEntity {

    private Supplier<Activity> activity = () -> null;
    private double weightedWeight;

    public Activity getActivity() {
        return activity.get();
    }

    public void setActivity(Supplier<Activity> activity) {
        this.activity = Objects.requireNonNull(activity);
    }

    public double getWeightedWeight() {
        return weightedWeight;
    }

    public void setWeightedWeight(double weightedWeight) {
        this.weightedWeight = weightedWeight;
    }
}
