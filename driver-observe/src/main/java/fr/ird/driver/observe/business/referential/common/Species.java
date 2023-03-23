package fr.ird.driver.observe.business.referential.common;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;
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
public class Species extends I18nReferentialEntity {

    private String faoCode;
    private String scientificLabel;
    private Long wormsId;
    private Float minLength;
    private Float maxLength;
    private Float minWeight;
    private Float maxWeight;
    private Supplier<SpeciesGroup> speciesGroup = () -> null;
    private Supplier<SizeMeasureType> sizeMeasureType = () -> null;
    private Supplier<WeightMeasureType> weightMeasureType = () -> null;
    private Supplier<Set<Ocean>> ocean = SingletonSupplier.of(LinkedHashSet::new);

    public String getFaoCode() {
        return faoCode;
    }

    public void setFaoCode(String faoCode) {
        this.faoCode = faoCode;
    }

    public String getScientificLabel() {
        return scientificLabel;
    }

    public void setScientificLabel(String scientificLabel) {
        this.scientificLabel = scientificLabel;
    }

    public Long getWormsId() {
        return wormsId;
    }

    public void setWormsId(Long wormsId) {
        this.wormsId = wormsId;
    }

    public Float getMinLength() {
        return minLength;
    }

    public void setMinLength(Float minLength) {
        this.minLength = minLength;
    }

    public Float getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Float maxLength) {
        this.maxLength = maxLength;
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

    public SpeciesGroup getSpeciesGroup() {
        return speciesGroup.get();
    }

    public void setSpeciesGroup(Supplier<SpeciesGroup> speciesGroup) {
        this.speciesGroup = Objects.requireNonNull(speciesGroup);
    }

    public SizeMeasureType getSizeMeasureType() {
        return sizeMeasureType.get();
    }

    public void setSizeMeasureType(Supplier<SizeMeasureType> sizeMeasureType) {
        this.sizeMeasureType = Objects.requireNonNull(sizeMeasureType);
    }

    public WeightMeasureType getWeightMeasureType() {
        return weightMeasureType.get();
    }

    public void setWeightMeasureType(Supplier<WeightMeasureType> weightMeasureType) {
        this.weightMeasureType = Objects.requireNonNull(weightMeasureType);
    }

    public Set<Ocean> getOcean() {
        return ocean.get();
    }

    public void setOcean(Supplier<Set<Ocean>> ocean) {
        this.ocean = Objects.requireNonNull(ocean);
    }
}
