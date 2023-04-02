package fr.ird.driver.observe.business.data.ps.landing;

import fr.ird.driver.observe.business.data.DataEntity;
import fr.ird.driver.observe.business.referential.common.Species;
import fr.ird.driver.observe.business.referential.common.Vessel;
import fr.ird.driver.observe.business.referential.ps.common.WeightCategory;
import fr.ird.driver.observe.business.referential.ps.landing.Destination;
import fr.ird.driver.observe.business.referential.ps.landing.Fate;

import java.util.Date;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Landing extends DataEntity {

    private Date date;
    private float weight;
    private Supplier<Species> species = () -> null;
    private Supplier<WeightCategory> weightCategory = () -> null;
    private Supplier<Destination> destination = () -> null;
    private Supplier<Fate> fate = () -> null;
    private Supplier<Vessel> fateVessel = () -> null;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
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

    public Destination getDestination() {
        return destination.get();
    }

    public void setDestination(Supplier<Destination> destination) {
        this.destination = Objects.requireNonNull(destination);
    }

    public Fate getFate() {
        return fate.get();
    }

    public void setFate(Supplier<Fate> fate) {
        this.fate = Objects.requireNonNull(fate);
    }

    public Vessel getFateVessel() {
        return fateVessel.get();
    }

    public void setFateVessel(Supplier<Vessel> fateVessel) {
        this.fateVessel = Objects.requireNonNull(fateVessel);
    }
}
