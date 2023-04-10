package fr.ird.driver.observe.business.data.ps.localmarket;

import fr.ird.driver.observe.business.data.DataEntity;
import fr.ird.driver.observe.business.referential.common.Species;
import fr.ird.driver.observe.business.referential.ps.localmarket.Buyer;
import fr.ird.driver.observe.business.referential.ps.localmarket.Packaging;

import java.util.Date;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Batch extends DataEntity {

    private Date date;
    private int count;
    private double weight;
    private String weightComputedSource;
    private String origin;
    private String comment;
    private Supplier<Species> species = () -> null;
    private Supplier<Packaging> packaging = () -> null;
    private Supplier<Buyer> buyer = () -> null;
    private Supplier<Survey> survey = () -> null;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getWeightComputedSource() {
        return weightComputedSource;
    }

    public void setWeightComputedSource(String weightComputedSource) {
        this.weightComputedSource = weightComputedSource;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Species getSpecies() {
        return species.get();
    }

    public void setSpecies(Supplier<Species> species) {
        this.species = Objects.requireNonNull(species);
    }

    public Packaging getPackaging() {
        return packaging.get();
    }

    public void setPackaging(Supplier<Packaging> packaging) {
        this.packaging = Objects.requireNonNull(packaging);
    }

    public Buyer getBuyer() {
        return buyer.get();
    }

    public void setBuyer(Supplier<Buyer> buyer) {
        this.buyer = Objects.requireNonNull(buyer);
    }

    public Survey getSurvey() {
        return survey.get();
    }

    public void setSurvey(Supplier<Survey> survey) {
        this.survey = Objects.requireNonNull(survey);
    }
}
