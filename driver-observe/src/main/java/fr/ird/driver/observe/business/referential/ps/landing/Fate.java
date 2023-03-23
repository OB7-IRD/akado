package fr.ird.driver.observe.business.referential.ps.landing;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Fate extends I18nReferentialEntity {
    private boolean discard;

    public boolean isDiscard() {
        return discard;
    }

    public void setDiscard(boolean discard) {
        this.discard = discard;
    }
}
