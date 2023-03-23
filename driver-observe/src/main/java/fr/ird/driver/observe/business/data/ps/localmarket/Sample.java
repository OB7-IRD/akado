package fr.ird.driver.observe.business.data.ps.localmarket;

import fr.ird.driver.observe.business.data.DataEntity;
import fr.ird.driver.observe.business.referential.ps.common.SampleType;
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
public class Sample extends DataEntity {

    private String number;
    private Date date;
    private String comment;
    private Supplier<SampleType> sampleType = () -> null;
    private Supplier<Set<SampleSpecies>> sampleSpecies = SingletonSupplier.of(LinkedHashSet::new);
    private Supplier<Set<String>> well = SingletonSupplier.of(LinkedHashSet::new);

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public SampleType getSampleType() {
        return sampleType.get();
    }

    public void setSampleType(Supplier<SampleType> sampleType) {
        this.sampleType = Objects.requireNonNull(sampleType);
    }

    public Set<SampleSpecies> getSampleSpecies() {
        return sampleSpecies.get();
    }

    public void setSampleSpecies(Supplier<Set<SampleSpecies>> sampleSpecies) {
        this.sampleSpecies = Objects.requireNonNull(sampleSpecies);
    }

    public Set<String> getWell() {
        return well.get();
    }

    public void setWell(Supplier<Set<String>> well) {
        this.well = Objects.requireNonNull(well);
    }
}
