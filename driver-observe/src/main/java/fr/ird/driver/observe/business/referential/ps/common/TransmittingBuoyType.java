package fr.ird.driver.observe.business.referential.ps.common;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class TransmittingBuoyType extends I18nReferentialEntity {

    private String technology;

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }
}
