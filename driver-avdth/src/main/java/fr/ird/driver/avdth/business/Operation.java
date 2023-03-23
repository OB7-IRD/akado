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
 */package fr.ird.driver.avdth.business;

/**
 * *********************************************************************
 * Module: Operation.java Author: Julien Lebranchu Purpose: Defines the Class
 * Operation -- Classe référentiel
 * *********************************************************************
 */
/**
 * Les codes opérations
 *
 * @pdOid 666ad7fb-90d8-44d6-a829-5fcb8007cfa1
 *
 */
public class Operation {

    public static final int COUP_NEGATIF = 0;
    public static final int COUP_POSITIF = 1;
    public static final int COUP_INCONNU = 2;
    public static final int RECHERCHE = 3;
    public static final int ROUTE = 4;
    public static final int POSE_DCP = 5;
    public static final int RELEVE_DCP = 6;
    public static final int AVARIE = 7;
    public static final int CAPE = 8;
    public static final int APPAT = 9;
    public static final int ATTENTE = 10;
    public static final int TRANSBORDEMENT = 11;
    public static final int TRANSBORDEMENT_DEPUIS_SENNEUR = 12;
    public static final int TRANSBORDEMENT_VERS_SENNEUR = 13;
    public static final int CHAVIRE_POCHE = 14;
    public static final int AU_PORT = 15;

    public static final int DEPRECATED_CHGT_BALISE = 16;
    public static final int DEPRECATED_VISTE_OBJET = 17;

    public static final int TRANSBORDEMENT_DEPUIS_CANNEUR = 20;
    public static final int TRANSBORDEMENT_VERS_CANNEUR = 19;

    public static final int RELEVE_DCP_SANS_OPERATION_BALISE = 22;
    public static final int POSE_DCP_ET_POSE_BALISE = 23;
    public static final int RELEVE_DCP_ET_RELEVE_BALISE = 24;
    public static final int POSE_BALISE_SUR_DCP_EXISTANT = 25;
    public static final int RELEVE_BALISE_SUR_DCP_EXISTANT = 26;
    public static final int VISITE_DE_DCP_EQUIPE_UNE_BALISE = 29;
    public static final int VISITE_DE_DCP_SANS_BALISE = 30;
    public static final int RENFORCEMENT_UN_OBJET_SANS_OPERATION_BALISE = 31;
    public static final int RENFORCEMENT_UN_OBJET_AVEC_POSE_BALISE = 32;
    public static final int PERTE_FIN_TRANS_BALISE = 33;
    public static final int RETRAIT_BALISE_ISOLEE = 34;

    /**
     * Code opération
     *
     * @pdOid 33b87063-7e87-4cce-a0c4-d36caffd46fd
     */
    private int code;
    /**
     * Libellé opération court
     *
     * @pdOid effa3004-5871-40d5-bb01-ebe95d0b177e
     */
    private String shortName;
    /**
     * Libellé opération long
     *
     * @pdOid b67abbd1-5cee-4c8d-84e4-9671a19a0323
     */
    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Operation{" + "code=" + code + ", shortName=" + shortName + ", name=" + name + '}';
    }



    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.code;
        hash = 97 * hash + (this.shortName != null ? this.shortName.hashCode() : 0);
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
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
        final Operation other = (Operation) obj;
        if (this.code != other.code) {
            return false;
        }
        if ((this.shortName == null) ? (other.shortName != null) : !this.shortName.equals(other.shortName)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

}
