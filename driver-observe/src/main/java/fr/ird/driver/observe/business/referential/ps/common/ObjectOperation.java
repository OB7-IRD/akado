package fr.ird.driver.observe.business.referential.ps.common;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ObjectOperation extends I18nReferentialEntity {
    private boolean whenArriving;
    private boolean whenLeaving;

    public boolean isWhenArriving() {
        return whenArriving;
    }

    public void setWhenArriving(boolean whenArriving) {
        this.whenArriving = whenArriving;
    }

    public boolean isWhenLeaving() {
        return whenLeaving;
    }

    public void setWhenLeaving(boolean whenLeaving) {
        this.whenLeaving = whenLeaving;
    }
}
