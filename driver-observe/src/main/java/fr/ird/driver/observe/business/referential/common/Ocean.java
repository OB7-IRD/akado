package fr.ird.driver.observe.business.referential.common;

import fr.ird.driver.observe.business.referential.I18nReferentialEntity;

/**
 * Created on 18/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Ocean extends I18nReferentialEntity {

    public static final String ATLANTIQUE = "1";
    public static final String INDIEN = "2";
    public static final String PACIFIQUE_OUEST = "3";
    public static final String PACIFIQUE_EST = "4";

    /**
     * Returns the ocean in which the coordinates is located.
     *
     * @param longitude request latitude
     * @param latitude  request longitude
     * @return the ocean code, or {@code null} if not found
     */
    public static String getOcean(Double longitude, Double latitude) {
        if (longitude >= 20 && longitude < 146.031389) {
            return Ocean.INDIEN;
        }
        if (longitude >= -67.271667 && longitude < 20) {
            return Ocean.ATLANTIQUE;
        }

//        if(longitude >= -180 && longitude < -67.271667 &&
//                longitude >= 146.031389 && longitude <= 180) return Ocean.PACIFIQUE;
        if (longitude >= 146.031389 && longitude <= 180) {
            return Ocean.PACIFIQUE_EST;
        }
        if (longitude >= -180 && longitude < -67.271667) {
            return Ocean.PACIFIQUE_OUEST;
        }
        return null;
    }

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
