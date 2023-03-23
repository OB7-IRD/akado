package fr.ird.driver.observe.business.referential.common;

import fr.ird.driver.observe.business.referential.ReferentialEntity;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Person extends ReferentialEntity {

    private String lastName;
    private String firstName;
    private boolean observer;
    private boolean captain;
    private boolean dataEntryOperator;
    private boolean dataSource;
    private boolean psSampler;
    private Supplier<Country> country = () -> null;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean isObserver() {
        return observer;
    }

    public void setObserver(boolean observer) {
        this.observer = observer;
    }

    public boolean isCaptain() {
        return captain;
    }

    public void setCaptain(boolean captain) {
        this.captain = captain;
    }

    public boolean isDataEntryOperator() {
        return dataEntryOperator;
    }

    public void setDataEntryOperator(boolean dataEntryOperator) {
        this.dataEntryOperator = dataEntryOperator;
    }

    public boolean isDataSource() {
        return dataSource;
    }

    public void setDataSource(boolean dataSource) {
        this.dataSource = dataSource;
    }

    public boolean isPsSampler() {
        return psSampler;
    }

    public void setPsSampler(boolean psSampler) {
        this.psSampler = psSampler;
    }

    public Country getCountry() {
        return country.get();
    }

    public void setCountry(Supplier<Country> country) {
        this.country = Objects.requireNonNull(country);
    }
}
