package fr.ird.driver.observe.business.data.ps.localmarket;

import fr.ird.driver.observe.business.data.DataEntity;
import fr.ird.driver.observe.business.referential.common.Species;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class SurveyPart extends DataEntity {

    private int proportion;
    private Supplier<Species> species = () -> null;

    public Species getSpecies() {
        return species.get();
    }

    public void setSpecies(Supplier<Species> species) {
        this.species = Objects.requireNonNull(species);
    }

    public int getProportion() {
        return proportion;
    }

    public void setProportion(int proportion) {
        this.proportion = proportion;
    }
}
