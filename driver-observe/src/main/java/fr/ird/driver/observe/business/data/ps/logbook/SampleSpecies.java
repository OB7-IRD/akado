package fr.ird.driver.observe.business.data.ps.logbook;

import fr.ird.driver.observe.business.data.DataEntity;
import fr.ird.driver.observe.business.referential.common.SizeMeasureType;
import fr.ird.driver.observe.business.referential.common.Species;
import io.ultreia.java4all.util.SingletonSupplier;

import java.util.Date;
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

    public static String SAMPLE_LENGTH_CLASS_FOR_DORSAL = "fr.ird.referential.common.SizeMeasureType#1433499466774#0.529249255312607";
    public static String SAMPLE_LENGTH_CLASS_FOR_FORK = "fr.ird.referential.common.SizeMeasureType#1433499465700#0.0902433863375336";
    //FIXME
    public static String SAMPLE_LENGTH_CLASS_FOR_DORSAL_ONE_CENTIMER_FREQUENCY = "fr.ird.referential.common.SizeMeasureType#1433499465700#0.0902433863375336";
    private String comment;
    private Date startTime;
    private Date endTime;
    private Integer subSampleNumber;
    private Integer measuredCount;
    private Integer totalCount;
    private Supplier<Species> species = () -> null;
    private Supplier<SizeMeasureType> sizeMeasureType = () -> null;
    private Supplier<Set<SampleSpeciesMeasure>> sampleSpeciesMeasure = SingletonSupplier.of(LinkedHashSet::new);

    public boolean isLd() {
        return getSizeMeasureType().getTopiaId().equals(SAMPLE_LENGTH_CLASS_FOR_DORSAL);
    }
    public boolean isLf() {
        return getSizeMeasureType().getTopiaId().equals(SAMPLE_LENGTH_CLASS_FOR_FORK);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getSubSampleNumber() {
        return subSampleNumber;
    }

    public void setSubSampleNumber(Integer subSampleNumber) {
        this.subSampleNumber = subSampleNumber;
    }

    public Integer getMeasuredCount() {
        return measuredCount;
    }

    public void setMeasuredCount(Integer measuredCount) {
        this.measuredCount = measuredCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
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
