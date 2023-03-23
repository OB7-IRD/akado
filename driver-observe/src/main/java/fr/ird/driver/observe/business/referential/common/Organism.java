package fr.ird.driver.observe.business.referential.common;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Organism extends I18nReferentialEntity {
    private String description;
    private Supplier<Country> country = () -> null;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Country getCountry() {
        return country.get();
    }

    public void setCountry(Supplier<Country> country) {
        this.country = Objects.requireNonNull(country);
    }
}
