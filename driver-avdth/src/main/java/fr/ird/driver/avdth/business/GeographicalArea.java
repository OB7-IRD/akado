/* 
* Copyright (C) 2013 Julien Lebranchu <julien.lebranchu@ird.fr>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.driver.avdth.business;

/**
 * *********************************************************************
 * Module: ZoneGeographique.java Author: Julien Lebranchu Lebranchu Purpose:
 * Defines the Class ZoneGeographique -- Classe référentiel
 * *********************************************************************
 */
/**
 * Ver. 3.x : zones ET + qq autres éventuellement compatible avec le schéma
 * balbaya (BDR Oracle)
 *
 */
public class GeographicalArea {

    /**
     * Code zone géographique = un entier sur 4 digits :
     * <p/>
     * océan(1), banc(1), zone(2)
     *
     * @pdOid a740fbb3-193e-40ec-a9a7-50b406898b08
     */
    private int code;
    /**
     * @pdOid 893f003b-2b3b-4aea-a3aa-88c04afb5661
     */
    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //    public void setLimitesZoneGeo(LimitesZoneGeo limitesZoneGeo) {
//        this.limitesZoneGeo = limitesZoneGeo;
//    }
    @Override
    public String toString() {
        return String
                .format("ZoneGeographique [codeZoneGeo=%s, libelleZoneGeo=%s"
                        //                        + ", limitesZoneGeo=%s"
                        + "]",
                        code, name);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + code;
        result = prime * result
                + ((name == null) ? 0 : name.hashCode());
//		result = prime * result
//				+ ((limitesZoneGeo == null) ? 0 : limitesZoneGeo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        GeographicalArea other = (GeographicalArea) obj;
        if (code != other.code) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
//		if (limitesZoneGeo == null) {
//			if (other.limitesZoneGeo != null)
//				return false;
//		} else if (!limitesZoneGeo.equals(other.limitesZoneGeo))
//			return false;
        return true;
    }
}
