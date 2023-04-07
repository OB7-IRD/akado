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
    private float weight;
    private int count;
    private int setSpeciesNumber;

    public boolean isWeightCategoryMinus10() {
        return getWeightCategory().getTopiaId().equals("fr.ird.referential.ps.common.WeightCategory#1464000000000#00001");
    }
    public boolean isWeightCategoryPlus10() {
        return getWeightCategory().getTopiaId().equals("fr.ird.referential.ps.common.WeightCategory#1464000000000#00002");
    }
    public boolean isWeightCategoryUnknown() {
        return getWeightCategory().getTopiaId().equals("fr.ird.referential.ps.common.WeightCategory#1464000000000#00004");
    }

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

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSetSpeciesNumber() {
        return setSpeciesNumber;
    }

    public void setSetSpeciesNumber(int setSpeciesNumber) {
        this.setSpeciesNumber = setSpeciesNumber;
    }
}
