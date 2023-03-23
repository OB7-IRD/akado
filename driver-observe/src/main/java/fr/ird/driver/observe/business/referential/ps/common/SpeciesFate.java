package fr.ird.driver.observe.business.referential.ps.common;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class SpeciesFate extends I18nReferentialEntity {

    private Boolean discard;
    private boolean weightRangeAllowed;

    public Boolean getDiscard() {
        return discard;
    }

    public void setDiscard(Boolean discard) {
        this.discard = discard;
    }

    public boolean isWeightRangeAllowed() {
        return weightRangeAllowed;
    }

    public void setWeightRangeAllowed(boolean weightRangeAllowed) {
        this.weightRangeAllowed = weightRangeAllowed;
    }
}
