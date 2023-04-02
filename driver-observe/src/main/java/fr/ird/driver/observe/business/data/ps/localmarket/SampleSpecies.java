package fr.ird.driver.observe.business.data.ps.localmarket;

import fr.ird.driver.observe.business.data.DataEntity;
import fr.ird.driver.observe.business.referential.common.SizeMeasureType;
import fr.ird.driver.observe.business.referential.common.Species;
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
public class SampleSpecies extends DataEntity {

    private String comment;
    private int measuredCount;
    private Supplier<Species> species = () -> null;
    private Supplier<SizeMeasureType> sizeMeasureType = () -> null;
    private Supplier<Set<SampleSpeciesMeasure>> sampleSpeciesMeasure = SingletonSupplier.of(LinkedHashSet::new);

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getMeasuredCount() {
        return measuredCount;
    }

    public void setMeasuredCount(int measuredCount) {
        this.measuredCount = measuredCount;
    }

    public Species getSpecies() {
        return species.get();
    }

    public void setSpecies(Supplier<Species> species) {
        this.species = Objects.requireNonNull(species);
    }

    public SizeMeasureType getSizeMeasureType() {
        return sizeMeasureType.get();
    }

    public void setSizeMeasureType(Supplier<SizeMeasureType> sizeMeasureType) {
        this.sizeMeasureType = Objects.requireNonNull(sizeMeasureType);
    }

    public Set<SampleSpeciesMeasure> getSampleSpeciesMeasure() {
        return sampleSpeciesMeasure.get();
    }

    public void setSampleSpeciesMeasure(Supplier<Set<SampleSpeciesMeasure>> sampleSpeciesMeasure) {
        this.sampleSpeciesMeasure = Objects.requireNonNull(sampleSpeciesMeasure);
    }
}
