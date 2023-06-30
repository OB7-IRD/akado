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

    /**
     * @return {@code true} if species is YFT (avdth code 1)
     */
    public boolean isYFT() {
        return "fr.ird.referential.common.Species#1239832685474#0.8943253454598569".equals(getTopiaId());
    }

    /**
     * @return {@code true} if species is SKJ (avdth code 2)
     */
    public boolean isSKJ() {
        return "fr.ird.referential.common.Species#1239832685474#0.975344121171992".equals(getTopiaId());
    }

    /**
     * @return {@code true} if species is BET (avdth code 3)
     */
    public boolean isBET() {
        return "fr.ird.referential.common.Species#1239832685475#0.13349466123905152".equals(getTopiaId());
    }

    /**
     * @return {@code true} if species is ALB (avdth code 4)
     */
    public boolean isALB() {
        return "fr.ird.referential.common.Species#1239832685476#0.5618871286604711".equals(getTopiaId());
    }

    /**
     * @return {@code true} if species is LTA (avdth code 5)
     */
    public boolean isLTA() {
        return "fr.ird.referential.common.Species#1239832685477#0.8024257002747615".equals(getTopiaId());
    }

    /**
     * @return {@code true} if species is FRI (avdth code 6)
     */
    public boolean isFRI() {
        return "fr.ird.referential.common.Species#1239832685477#0.3846921632590058".equals(getTopiaId());
    }

    /**
     * @return {@code true} if species is TUN (avdth code 9)
     */
    public boolean isTUN() {
        return "fr.ird.referential.common.Species#1441287921299#0.016754076421811148".equals(getTopiaId());
    }

    /**
     * @return {@code true} if species is KAW (avdth code 10)
     */
    public boolean isKAW() {
        return "fr.ird.referential.common.Species#1239832685477#0.2673009297087321".equals(getTopiaId());
    }

    /**
     * @return {@code true} if species is LOT (avdth code 11)
     */
    public boolean isLOT() {
        return "fr.ird.referential.common.Species#1239832685478#0.7676744877900202".equals(getTopiaId());
    }

    /**
     * @return {@code true} if species is BLF (avdth code 12)
     */
    public boolean isBLF() {
        return "fr.ird.referential.common.Species#1239832685477#0.2908846499255108".equals(getTopiaId());
    }

    /**
     * @return {@code true} if species is TUS (avdth code 40)
     */
    public boolean isTUS() {
        return "fr.ird.referential.common.Species#1433499265113#0.891799515346065".equals(getTopiaId());
    }

    /**
     * FIXME Is this still exist in ObServe?
     * @return {@code true} if species is RAV (avdth code 43)
     */
    public boolean isRAV() {
        return "fr.ird.referential.common.Species#1464000000000#43".equals(getTopiaId());
    }

    /**
     * @return {@code true} if species is FRZ (avdth code 18)
     */
    public boolean isFRZ() {
        return "fr.ird.referential.common.Species#1239832685477#0.5989181185528589".equals(getTopiaId());
    }

    /**
     * @return {@code true} if species is BLT (avdth code 17)
     */
    public boolean isBLT() {
        return "fr.ird.referential.common.Species#1239832685476#0.36339915670317835".equals(getTopiaId());
    }

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
