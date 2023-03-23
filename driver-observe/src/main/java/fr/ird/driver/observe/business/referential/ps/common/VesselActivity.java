package fr.ird.driver.observe.business.referential.ps.common;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class VesselActivity extends I18nReferentialEntity {

    private boolean allowFad;

    public boolean isAllowFad() {
        return allowFad;
    }

    public void setAllowFad(boolean allowFad) {
        this.allowFad = allowFad;
    }
}
