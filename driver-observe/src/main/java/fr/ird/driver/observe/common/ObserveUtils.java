package fr.ird.driver.observe.common;

/**
 * Created on 29/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ObserveUtils {


    /**
     * Calcule le quadrant à partir d'une {@code longitude} et {@code latitude}.
     *
     * @param longitude la longitude décimale
     * @param latitude  la latitude décimale
     * @return la valeur du quadrant ou {@code null} si l'une des deux coordonnées est {@code null}.
     * @since 1.2
     */
    public static Integer getQuadrant(Float longitude, Float latitude) {
        if (longitude == null || latitude == null) {
            return null;
        }
        int result;

        if (latitude > 0) {
            result = longitude > 0 ? 1 : 4;
        } else {
            result = longitude > 0 ? 2 : 3;
        }
        return result;
    }

}
