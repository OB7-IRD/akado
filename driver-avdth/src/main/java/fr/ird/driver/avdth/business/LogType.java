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
 * Module: TypeObjet.java Author: Julien Lebranchu Purpose: Defines the Class
 * TypeObjet
 * *********************************************************************
 */
/**
 * Les types d'objets utilisés pour les DCPs.
 */
public class LogType {

    public static final int ARTIFICIEL_DCP_ANCRE = 1;
    public static final int ARTIFICIEL_DCP_DERIVANT = 2;
    public static final int OBJET_NON_DCP_DERIVANT = 3;
    public static final int MONT_SOUS_MARIN = 4;
    public static final int REQUIN_BALEINE = 5;
    public static final int BALEINE = 6;
    public static final int DAUPHIN = 7;
    public static final int INDETERMINE = 9;
    /**
     * @pdOid 5a2e2833-e140-411d-b739-c4b2db7c38df
     */
    private int code;
    /**
     * @pdOid 59e97a67-a7b9-4242-8e0a-e0bdd0d9567b
     */
    private java.lang.String rfmoCode;
    /**
     * @pdOid 36a1a04f-0a64-4cfa-a53c-7a3d693b1c4b
     */
    private java.lang.String libelle;
    /**
     * @pdOid 166b7415-c981-4d7a-944c-42ef58776923
     */
    private boolean status;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public java.lang.String getRfmoCode() {
        return rfmoCode;
    }

    public void setRfmoCode(java.lang.String rfmoCode) {
        this.rfmoCode = rfmoCode;
    }

    public java.lang.String getLibelle() {
        return libelle;
    }

    public void setLibelle(java.lang.String libelle) {
        this.libelle = libelle;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LogType)) {
            return false;
        }

        LogType typeObjet = (LogType) o;

        if (code != typeObjet.code) {
            return false;
        }
        if (status != typeObjet.status) {
            return false;
        }
        if (rfmoCode != null ? !rfmoCode.equals(typeObjet.rfmoCode) : typeObjet.rfmoCode != null) {
            return false;
        }
        return !(libelle != null ? !libelle.equals(typeObjet.libelle) : typeObjet.libelle != null);

    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + (rfmoCode != null ? rfmoCode.hashCode() : 0);
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        result = 31 * result + (status ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TypeObjet{"
                + "codeAvdthTypeObjet=" + code
                + ", codeRfmoDuTypeObjet='" + rfmoCode + '\''
                + ", libelleDuTypeObjet='" + libelle + '\''
                + ", statut=" + status
                + '}';
    }
}
