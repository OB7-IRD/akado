/**
 * Copyright (C) 2014 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.driver.anapo.common;

import fr.ird.common.OTUtils;

/**
 * Miscellaneous class utility methods for the purposes of the ANAPO database.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @version 1.0
 * @date 14 fevr. 2014
 */
public class ANAPOUtils extends OTUtils {

    public static final int ATLANTIQUE = 1;
    public static final int INDIEN = 2;
    public static final int PACIFIQUE_OUEST = 3;
    public static final int PACIFIQUE_EST = 4;
    public static final int PACIFIQUE = 5;

    /**
     * Returns the ocean where the coordinates is located.
     *
     * @param longitude
     * @param latitude
     * @return
     */
    public static int getOcean(Double longitude, Double latitude) {
        if (longitude >= 20 && longitude < 146.031389) {
            return ANAPOUtils.INDIEN;
        }
        if (longitude >= -67.271667 && longitude < 20) {
            return ANAPOUtils.ATLANTIQUE;
        }

//        if(longitude >= -180 && longitude < -67.271667 &&
//                longitude >= 146.031389 && longitude <= 180) return Ocean.PACIFIQUE;
        if (longitude >= 146.031389 && longitude <= 180) {
            return ANAPOUtils.PACIFIQUE_EST;
        }
        if (longitude >= -180 && longitude < -67.271667) {
            return ANAPOUtils.PACIFIQUE_OUEST;
        }
        return -1;
    }
}
