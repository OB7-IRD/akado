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
public class SpeciesGroup extends I18nReferentialEntity {

    private Supplier<Set<SpeciesGroupReleaseMode>> speciesGroupReleaseMode = SingletonSupplier.of(LinkedHashSet::new);

    public Set<SpeciesGroupReleaseMode> getSpeciesGroupReleaseMode() {
        return speciesGroupReleaseMode.get();
    }

    public void setSpeciesGroupReleaseMode(Supplier<Set<SpeciesGroupReleaseMode>> speciesGroupReleaseMode) {
        this.speciesGroupReleaseMode = Objects.requireNonNull(speciesGroupReleaseMode);
    }
}
