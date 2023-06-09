package fr.ird.driver.observe.business.referential.common;

import fr.ird.driver.observe.business.referential.ReferentialEntity;

import java.util.Date;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ShipOwner extends ReferentialEntity {

    private String label;
    private Date startDate;
    private Date endDate;
    private Supplier<Country> country = () -> null;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Country getCountry() {
        return country.get();
    }

    public void setCountry(Supplier<Country> country) {
        this.country = Objects.requireNonNull(country);
    }
}
