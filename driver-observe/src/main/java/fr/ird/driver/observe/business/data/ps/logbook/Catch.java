package fr.ird.driver.observe.business.data.ps.logbook;

import fr.ird.driver.observe.business.data.DataEntity;
import fr.ird.driver.observe.business.referential.common.Species;
import fr.ird.driver.observe.business.referential.common.WeightMeasureMethod;
import fr.ird.driver.observe.business.referential.ps.common.SpeciesFate;
import fr.ird.driver.observe.business.referential.ps.common.WeightCategory;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Catch extends DataEntity {

    private String comment;
    private Float weight;
    private Integer count;
    private String well;
    private Supplier<Species> species = () -> null;
    private Supplier<WeightCategory> weightCategory = () -> null;
    private Supplier<SpeciesFate> speciesFate = () -> null;
    private Supplier<WeightMeasureMethod> weightMeasureMethod = () -> null;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getWell() {
        return well;
    }

    public void setWell(String well) {
        this.well = well;
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

    public SpeciesFate getSpeciesFate() {
        return speciesFate.get();
    }

    public void setSpeciesFate(Supplier<SpeciesFate> speciesFate) {
        this.speciesFate = Objects.requireNonNull(speciesFate);
    }

    public WeightMeasureMethod getWeightMeasureMethod() {
        return weightMeasureMethod.get();
    }

    public void setWeightMeasureMethod(Supplier<WeightMeasureMethod> weightMeasureMethod) {
        this.weightMeasureMethod = Objects.requireNonNull(weightMeasureMethod);
    }
}
