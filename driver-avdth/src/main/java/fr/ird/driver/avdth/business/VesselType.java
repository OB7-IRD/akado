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
 * Les types d'unité de pêche.
 */
public class VesselType {

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String
                .format("TypeBateau [codeTypeBateau=%s, "
                        + "libelleTypeBateau=%s, "
                        //                + "simpleType=%s"
                        + "]",
                        code, name);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + code;
        result = prime
                * result
                + ((name == null) ? 0 : name
                        .hashCode());
        result = prime * result
                + ((simpleType == null) ? 0 : simpleType.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
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
        VesselType other = (VesselType) obj;
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
        if (simpleType == null) {
            if (other.simpleType != null) {
                return false;
            }
        } else if (!simpleType.equals(other.simpleType)) {
            return false;
        }
        return true;
    }

    /**
     * Code numérique du type de bateau
     */
    private int code;

    private java.lang.String name;

    private VesselSimpleType simpleType;

    public VesselSimpleType getSimpleType() {
        return simpleType;
    }

    /**
     * @param newTypeTypeBateau
     */
    public void setSimpleType(VesselSimpleType newTypeTypeBateau) {
        if (this.simpleType == null || !this.simpleType.equals(newTypeTypeBateau)) {
            if (newTypeTypeBateau != null) {
                this.simpleType = newTypeTypeBateau;
            }
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }
}
