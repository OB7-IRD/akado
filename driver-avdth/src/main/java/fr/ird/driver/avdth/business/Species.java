/* $Id: Species.java 385 2014-07-11 10:17:43Z lebranch $
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
 * Les espèces. 
 */
public class Species {

    /**
     * Code numérique de l'espèce
     *
     * @pdOid 477ee2db-5713-4ed1-8d9f-19e358156145
     */
    private int code;
    /**
     * Code espèce (3 lettres )
     *
     * @pdOid c5a3533d-ab16-4e39-a0ff-52f5c4351f7d
     */
    private String codeISO;
    /**
     * Nom espèce
     *
     * @pdOid 151ac3ff-880d-4709-99fe-c2ef0ea0bdf4
     */
    private String name;
    /**
     * Nom scientifique espèce
     *
     * @pdOid f0265801-0eee-490b-a7a2-8eede0a9c009
     */
    private String scientificName;
    /**
     * @pdOid 8fe21e5e-c73d-4998-9fbb-2633a812219f
     */
    private boolean status = true;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCodeISO() {
        return codeISO;
    }

    public void setCodeISO(String codeISO) {
        this.codeISO = codeISO;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String
                .format("Espece [codeEspece=%s, codeEspece3l=%s, libelleEspece=%s, libelleScientifiqueEspece=%s, statut=%s]",
                        code, codeISO, name,
                        scientificName, status);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + code;
        result = prime * result
                + ((codeISO == null) ? 0 : codeISO.hashCode());
        result = prime * result
                + ((name == null) ? 0 : name.hashCode());
        result = prime
                * result
                + ((scientificName == null) ? 0
                        : scientificName.hashCode());
        result = prime * result + (status ? 1231 : 1237);
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
        Species other = (Species) obj;
        if (code != other.code) {
            return false;
        }
        if (codeISO == null) {
            if (other.codeISO != null) {
                return false;
            }
        } else if (!codeISO.equals(other.codeISO)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (scientificName == null) {
            if (other.scientificName != null) {
                return false;
            }
        } else if (!scientificName
                .equals(other.scientificName)) {
            return false;
        }
        return status == other.status;
    }
}
