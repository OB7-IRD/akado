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
 * Module: TypeTypeBateau.java Author: Julien Lebranchu Purpose: Defines the
 * Class TypeTypeBateau -- Classe référentiel
 * *********************************************************************
 */
/**
 * This class name in AVDTH is TypeTypeBateau.
 *
 */
public class VesselSimpleType {

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("TypeTypeBateau [cTypeTypeB=%s, lTypeTypeB=%s, cFaoIsscfg=%s]",
                code, name, faoIsscfg);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((faoIsscfg == null) ? 0 : faoIsscfg.hashCode());
        result = prime * result + code;
        result = prime * result
                + ((name == null) ? 0 : name.hashCode());
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
        VesselSimpleType other = (VesselSimpleType) obj;
        if (faoIsscfg == null) {
            if (other.faoIsscfg != null) {
                return false;
            }
        } else if (!faoIsscfg.equals(other.faoIsscfg)) {
            return false;
        }
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
        return true;
    }

    /**
     * @pdOid fd0f5b1e-de42-4c22-b064-fbc8abb275e3
     */
    private int code;
    /**
     * @pdOid 87065c9f-1971-4a6a-b29d-ac0ab5e06b0b
     */
    private java.lang.String name;
    /**
     * @pdOid 2d9166ed-68ee-48b2-85e7-656168a405ff
     */
    private java.lang.String faoIsscfg;

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

    public java.lang.String getFaoIsscfg() {
        return faoIsscfg;
    }

    public void setFaoIsscfg(java.lang.String faoIsscfg) {
        this.faoIsscfg = faoIsscfg;
    }
}
