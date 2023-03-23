package fr.ird.driver.observe.business.referential.common;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Ocean extends I18nReferentialEntity {

    private boolean northEastAllowed;
    private boolean southEastAllowed;
    private boolean southWestAllowed;
    private boolean northWestAllowed;

    public boolean isNorthEastAllowed() {
        return northEastAllowed;
    }

    public void setNorthEastAllowed(boolean northEastAllowed) {
        this.northEastAllowed = northEastAllowed;
    }

    public boolean isSouthEastAllowed() {
        return southEastAllowed;
    }

    public void setSouthEastAllowed(boolean southEastAllowed) {
        this.southEastAllowed = southEastAllowed;
    }

    public boolean isSouthWestAllowed() {
        return southWestAllowed;
    }

    public void setSouthWestAllowed(boolean southWestAllowed) {
        this.southWestAllowed = southWestAllowed;
    }

    public boolean isNorthWestAllowed() {
        return northWestAllowed;
    }

    public void setNorthWestAllowed(boolean northWestAllowed) {
        this.northWestAllowed = northWestAllowed;
    }
}
