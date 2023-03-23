package fr.ird.driver.observe.business.referential.ps.common;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;
import fr.ird.driver.observe.business.referential.common.Species;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class WeightCategory extends I18nReferentialEntity {

    private Float minWeight;
    private Float maxWeight;
    private Float meanWeight;
    private boolean allowLanding;
    private boolean allowLogbook;
    private boolean allowWellPlan;
    private Supplier<Species> species = () -> null;

    public Species getSpecies() {
        return species.get();
    }

    public void setSpecies(Supplier<Species> species) {
        this.species = Objects.requireNonNull(species);
    }

    public Float getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(Float minWeight) {
        this.minWeight = minWeight;
    }

    public Float getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Float maxWeight) {
        this.maxWeight = maxWeight;
    }

    public Float getMeanWeight() {
        return meanWeight;
    }

    public void setMeanWeight(Float meanWeight) {
        this.meanWeight = meanWeight;
    }

    public boolean isAllowLanding() {
        return allowLanding;
    }

    public void setAllowLanding(boolean allowLanding) {
        this.allowLanding = allowLanding;
    }

    public boolean isAllowLogbook() {
        return allowLogbook;
    }

    public void setAllowLogbook(boolean allowLogbook) {
        this.allowLogbook = allowLogbook;
    }

    public boolean isAllowWellPlan() {
        return allowWellPlan;
    }

    public void setAllowWellPlan(boolean allowWellPlan) {
        this.allowWellPlan = allowWellPlan;
    }

}
