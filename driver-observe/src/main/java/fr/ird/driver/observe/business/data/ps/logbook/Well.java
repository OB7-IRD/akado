package fr.ird.driver.observe.business.data.ps.logbook;

import fr.ird.driver.observe.business.data.DataEntity;
import fr.ird.driver.observe.business.referential.ps.logbook.WellSamplingConformity;
import fr.ird.driver.observe.business.referential.ps.logbook.WellSamplingStatus;
import io.ultreia.java4all.util.SingletonSupplier;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Well extends DataEntity {

    private String well;
    private String wellVessel;
    private String wellFactory;
    private Supplier<WellSamplingConformity> wellSamplingConformity = () -> null;
    private Supplier<WellSamplingStatus> wellSamplingStatus = () -> null;
    private Supplier<Set<WellActivity>> wellActivity = SingletonSupplier.of(LinkedHashSet::new);

    public String getWell() {
        return well;
    }

    public void setWell(String well) {
        this.well = well;
    }

    public String getWellVessel() {
        return wellVessel;
    }

    public void setWellVessel(String wellVessel) {
        this.wellVessel = wellVessel;
    }

    public String getWellFactory() {
        return wellFactory;
    }

    public void setWellFactory(String wellFactory) {
        this.wellFactory = wellFactory;
    }

    public WellSamplingConformity getWellSamplingConformity() {
        return wellSamplingConformity.get();
    }

    public void setWellSamplingConformity(Supplier<WellSamplingConformity> wellSamplingConformity) {
        this.wellSamplingConformity = Objects.requireNonNull(wellSamplingConformity);
    }

    public WellSamplingStatus getWellSamplingStatus() {
        return wellSamplingStatus.get();
    }

    public void setWellSamplingStatus(Supplier<WellSamplingStatus> wellSamplingStatus) {
        this.wellSamplingStatus = Objects.requireNonNull(wellSamplingStatus);
    }

    public Set<WellActivity> getWellActivity() {
        return wellActivity.get();
    }

    public void setWellActivity(Supplier<Set<WellActivity>> wellActivity) {
        this.wellActivity = Objects.requireNonNull(wellActivity);
    }
}
