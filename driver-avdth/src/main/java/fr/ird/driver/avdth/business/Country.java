/* $Id: Country.java 385 2014-07-11 10:17:43Z lebranch $
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
 */package fr.ird.driver.avdth.business;

/**
 * Defines the <em>Country<em>.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class Country {

    public static int AVDTH_CODE_COUNTRY_FRANCE = 1;
    public static int AVDTH_CODE_COUNTRY_SPAIN = 4;

    @Override
    public String toString() {
        return String
                .format("Country [code=%s, name=%s, codeIso2=%s, codeIso3=%s]",
                        code, name, codeIso2, codeIso3);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + code;
        result = prime
                * result
                + ((codeIso2 == null) ? 0 : codeIso2
                        .hashCode());
        result = prime
                * result
                + ((codeIso3 == null) ? 0 : codeIso3
                        .hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Country other = (Country) obj;
        if (code != other.code) {
            return false;
        }
        if (codeIso2 == null) {
            if (other.codeIso2 != null) {
                return false;
            }
        } else if (!codeIso2.equals(other.codeIso2)) {
            return false;
        }
        if (codeIso3 == null) {
            if (other.codeIso3 != null) {
                return false;
            }
        } else if (!codeIso3.equals(other.codeIso3)) {
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

    public Country() {
    }

    public Country(int code, String name, String codeIso2, String codeIso3) {
        this.code = code;
        this.name = name;
        this.codeIso2 = codeIso2;
        this.codeIso3 = codeIso3;
    }

    /**
     * Code numérique du pays
     *
     * @pdOid b8d88cc7-5f49-4e30-a3aa-6e746fd81839
     */
    private int code;
    /**
     * Nom du pays
     *
     * @pdOid 91303c90-5cab-4e87-a836-a73f1f0b5646
     */
    private String name;
    /**
     * @pdOid 376dc761-b2d5-4b45-aa66-38edc154e4f8
     */
    private String codeIso2;
    /**
     * @pdOid b9167668-2345-4796-99d3-5aab54be1806
     */
    private String codeIso3;

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

    public String getCodeIso2() {
        return codeIso2;
    }

    public void setCodeIso2(String codeIso2) {
        this.codeIso2 = codeIso2;
    }

    public String getCodeIso3() {
        return codeIso3;
    }

    public void setCodeIso3(String codeIso3) {
        this.codeIso3 = codeIso3;
    }
}
