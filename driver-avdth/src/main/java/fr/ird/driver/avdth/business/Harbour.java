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
 * Les ports.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class Harbour {

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + code;
        result = prime * result
                + ((comment == null) ? 0 : comment.hashCode());
        result = prime * result + Float.floatToIntBits(latitude);
        result = prime * result
                + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((locode == null) ? 0 : locode.hashCode());
        result = prime * result + Float.floatToIntBits(longitude);
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
        Harbour other = (Harbour) obj;
        if (code != other.code) {
            return false;
        }
        if (comment == null) {
            if (other.comment != null) {
                return false;
            }
        } else if (!comment.equals(other.comment)) {
            return false;
        }
        if (Float.floatToIntBits(latitude) != Float
                .floatToIntBits(other.latitude)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (locode == null) {
            if (other.locode != null) {
                return false;
            }
        } else if (!locode.equals(other.locode)) {
            return false;
        }
        return Float.floatToIntBits(longitude) == Float
                .floatToIntBits(other.longitude);
    }

    /**
     * Harbour's id
     *
     * @pdOid 79eb548a-a8a7-4cc3-8cac-145907530df2
     */
    private int code;
    /**
     * Harbour's name
     *
     * @pdOid 532225d2-2ff2-43ea-a23d-44047d2df71f
     */
    private String name;
    /**
     * Harbour's latitude
     *
     * @pdOid 2931622f-1835-4fb8-958a-604e541eaf18
     */
    private float latitude;
    /**
     * Harbour's longitude
     *
     * @pdOid 1063c69b-6256-46b9-927e-bda47907423b
     */
    private float longitude;
    /**
     * @pdOid c75197ee-60e4-4aab-b178-6610a5a8574d
     */
    private String comment;
    /**
     * @pdOid d09d8375-3191-4eb2-8ca6-fcc638f1ebf6
     */
    private String locode;
    private Country country;

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

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLocode() {
        return locode;
    }

    public void setLocode(String locode) {
        this.locode = locode;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Port{" + "codePort=" + code + ", libellePort=" + name + ", latitudePort=" + latitude + ", longitudePort=" + longitude + ", commentairePort=" + comment + ", locode=" + locode + ", country=" + country + '}';
    }

}
