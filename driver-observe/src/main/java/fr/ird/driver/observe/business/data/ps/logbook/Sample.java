package fr.ird.driver.observe.business.data.ps.logbook;

import fr.ird.driver.observe.business.data.DataEntity;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.referential.ps.common.SampleType;
import fr.ird.driver.observe.business.referential.ps.logbook.SampleQuality;
import io.ultreia.java4all.util.SingletonSupplier;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Sample extends DataEntity {

    private int number;
    private String comment;
    private String well;
    private boolean superSample;
    private float smallsWeight;
    private float bigsWeight;
    private float totalWeight;
    private Supplier<SampleType> sampleType = () -> null;
    private Supplier<SampleQuality> sampleQuality = () -> null;
    private Supplier<Set<SampleActivity>> sampleActivity = SingletonSupplier.of(LinkedHashSet::new);
    private Supplier<List<SampleSpecies>> sampleSpecies = SingletonSupplier.of(LinkedList::new);

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getWell() {
        return well;
    }

    public void setWell(String well) {
        this.well = well;
    }

    public boolean isSuperSample() {
        return superSample;
    }

    public void setSuperSample(boolean superSample) {
        this.superSample = superSample;
    }

    public float getSmallsWeight() {
        return smallsWeight;
    }

    public void setSmallsWeight(float smallsWeight) {
        this.smallsWeight = smallsWeight;
    }

    public float getBigsWeight() {
        return bigsWeight;
    }

    public void setBigsWeight(float bigsWeight) {
        this.bigsWeight = bigsWeight;
    }

    public float getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(float totalWeight) {
        this.totalWeight = totalWeight;
    }

    public SampleType getSampleType() {
        return sampleType.get();
    }

    public void setSampleType(Supplier<SampleType> sampleType) {
        this.sampleType = Objects.requireNonNull(sampleType);
    }

    public SampleQuality getSampleQuality() {
        return sampleQuality.get();
    }

    public void setSampleQuality(Supplier<SampleQuality> sampleQuality) {
        this.sampleQuality = Objects.requireNonNull(sampleQuality);
    }

    public Set<SampleActivity> getSampleActivity() {
        return sampleActivity.get();
    }

    public void setSampleActivity(Supplier<Set<SampleActivity>> sampleActivity) {
        this.sampleActivity = Objects.requireNonNull(sampleActivity);
    }

    public List<SampleSpecies> getSampleSpecies() {
        return sampleSpecies.get();
    }

    public void setSampleSpecies(Supplier<List<SampleSpecies>> sampleSpecies) {
        this.sampleSpecies = Objects.requireNonNull(sampleSpecies);
    }

    public String getID(Trip trip) {
        return String.format("%s %s", trip.getID(), getNumber());
    }
}
