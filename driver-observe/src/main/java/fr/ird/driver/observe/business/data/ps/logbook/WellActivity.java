package fr.ird.driver.observe.business.data.ps.logbook;

import fr.ird.driver.observe.business.data.DataEntity;
import io.ultreia.java4all.util.SingletonSupplier;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class WellActivity extends DataEntity {
    private Supplier<Activity> activity = () -> null;
    private Supplier<List<WellActivitySpecies>> wellActivitySpecies = SingletonSupplier.of(LinkedList::new);

    public Activity getActivity() {
        return activity.get();
    }

    public void setActivity(Supplier<Activity> activity) {
        this.activity = Objects.requireNonNull(activity);
    }

    public List<WellActivitySpecies> getWellActivitySpecies() {
        return wellActivitySpecies.get();
    }

    public void setWellActivitySpecies(Supplier<List<WellActivitySpecies>> wellActivitySpecies) {
        this.wellActivitySpecies = Objects.requireNonNull(wellActivitySpecies);
    }
}
