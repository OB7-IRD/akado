package fr.ird.driver.observe.business.data.ps.logbook;

import fr.ird.driver.observe.business.data.DataEntity;
import fr.ird.driver.observe.business.referential.common.Species;
import fr.ird.driver.observe.business.referential.ps.common.WeightCategory;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class WellActivitySpecies extends DataEntity {
    private Supplier<Species> species = () -> null;
    private Supplier<WeightCategory> weightCategory = () -> null;
    private Float weight;
    private Integer count;
    private Integer setSpeciesNumber;

    public Species getSpecies() {
        return species.get();
    }

    public void setSpecies(Supplier<Species> species) {
        this.species = Objects.requireNonNull(species);
    }

    public WeightCategory getWeightCategory() {
        return weightCategory.get();
    }

    public void setWeightCategory(Supplier<WeightCategory> weightCategory) {
        this.weightCategory = Objects.requireNonNull(weightCategory);
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getSetSpeciesNumber() {
        return setSpeciesNumber;
    }

    public void setSetSpeciesNumber(Integer setSpeciesNumber) {
        this.setSpeciesNumber = setSpeciesNumber;
    }
}
