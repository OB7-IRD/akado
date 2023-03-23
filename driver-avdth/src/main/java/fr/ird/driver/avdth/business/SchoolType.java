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
 * Module: TypeBanc.java Author: Julien Lebranchu Purpose: Defines the Class
 * TypeBanc -- Classe référentiel
 * *********************************************************************
 */
/**
 * Les types de banc
 *
 * @pdOid 9c1af2fc-7066-4f12-8285-b97e836c2802
 *
 */
public class SchoolType {

    public static final int ARTIFICIAL_SCHOOL = 1;
    public static final int FREE_SCHOOL = 2;
    public static final int INTEDTERMINE = 3;
    /**
     * Code type de banc
     *
     * @pdOid 9b176cda-db1f-4f0e-9a4c-c541ea21af90
     */
    private int code;
    /**
     * Libellé type de banc court
     *
     * @pdOid d0a74de5-1cab-4a84-92df-6e6b0915c15c
     */
    private java.lang.String libelle4;
    /**
     * Libellé type de banc long
     *
     * @pdOid f0cfbb47-b42f-4aeb-9043-185e548cc164
     */
    private java.lang.String libelle;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public java.lang.String getLibelle4() {
        return libelle4;
    }

    public void setLibelle4(java.lang.String libelle4) {
        this.libelle4 = libelle4;
    }

    public java.lang.String getLibelle() {
        return libelle;
    }

    public void setLibelle(java.lang.String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "TypeBanc{"
                + "codeTypeDeBanc=" + code
                + ", libelleTypeDeBancCourt='" + libelle4 + '\''
                + ", libelleTypeDeBancLong='" + libelle + '\''
                + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.code;
        hash = 89 * hash + (this.libelle4 != null ? this.libelle4.hashCode() : 0);
        hash = 89 * hash + (this.libelle != null ? this.libelle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SchoolType other = (SchoolType) obj;
        if (this.code != other.code) {
            return false;
        }
        if ((this.libelle4 == null) ? (other.libelle4 != null) : !this.libelle4.equals(other.libelle4)) {
            return false;
        }
        if ((this.libelle == null) ? (other.libelle != null) : !this.libelle.equals(other.libelle)) {
            return false;
        }
        return true;
    }
}
