/* $Id: Vessel.java 433 2014-07-29 14:37:42Z lebranch $
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

import org.joda.time.DateTime;

/**
 * Les bateaux ( Occéan Atlantique et Océan Indien ).
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 *
 *
 * $LastChangedDate: 2014-07-29 16:37:42 +0200 (mar. 29 juil. 2014) $
 *
 * $LastChangedRevision: 433 $
 */
public class Vessel implements Identifier {

    private int code;
    private int keelCode;
    private int fleetCode;
    private int yearService;
    private DateTime flagChangeDate;
    private float length;
    private float capacity;
    private int power;
    private Float vitesseMaxDeProspection;
    private String libelle;
    private String comment;
    private DateTime validityStart;
    private DateTime expiry;
    private String nationalID;
    /**
     * <p>
     * Identifiant utilisé dans l'ERS, code CFR</p>
     */
    private String communautaryID;
    private String faoID;
    private boolean statut = true;
    private Country country;
    private VesselType vesselType;
    private VesselSizeCategory vesselSizeCategory;
    private Integer vitesseChantier;
    private Integer vitesseMax;
    private Integer lPP;
    private Integer cGen;

    public Integer getVitesseChantier() {
        return vitesseChantier;
    }

    public void setVitesseChantier(Integer vitesseChantier) {
        this.vitesseChantier = vitesseChantier;
    }

    public Integer getVitesseMax() {
        return vitesseMax;
    }

    public void setVitesseMax(Integer vitesseMax) {
        this.vitesseMax = vitesseMax;
    }

    public Integer getlPP() {
        return lPP;
    }

    public void setlPP(Integer lPP) {
        this.lPP = lPP;
    }

    public Integer getcGen() {
        return cGen;
    }

    public void setcGen(Integer cGen) {
        this.cGen = cGen;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        if (this.country == null || !this.country.equals(country)) {
            if (country != null) {
                this.country = country;
            }
        }
    }

    public VesselType getVesselType() {
        return vesselType;
    }

    public void setVesselType(VesselType vesselType) {
        if (this.vesselType == null || !this.vesselType.equals(vesselType)) {
            if (vesselType != null) {
                this.vesselType = vesselType;
            }
        }
    }

    public VesselSizeCategory getVesselSizeCategory() {
        return vesselSizeCategory;
    }

    public void setVesselSizeCategory(VesselSizeCategory vesselSizeCategory) {
        if (this.vesselSizeCategory == null || !this.vesselSizeCategory.equals(vesselSizeCategory)) {
            if (vesselSizeCategory != null) {
                this.vesselSizeCategory = vesselSizeCategory;
            }
        }
    }

    public int getKeelCode() {
        return keelCode;
    }

    public void setKeelCode(int keelCode) {
        this.keelCode = keelCode;
    }

    public int getFleetCode() {
        return fleetCode;
    }

    public void setFleetCode(int fleetCode) {
        this.fleetCode = fleetCode;
    }

    public int getYearService() {
        return yearService;
    }

    public void setYearService(int yearService) {
        this.yearService = yearService;
    }

    public DateTime getFlagChangeDate() {
        return flagChangeDate;
    }

    public void setFlagChangeDate(DateTime flagChangeDate) {
        this.flagChangeDate = flagChangeDate;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public float getCapacity() {
        return capacity;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Float getVitesseMaxDeProspection() {
        return vitesseMaxDeProspection;
    }

    public void setVitesseMaxDeProspection(Float vitesseMaxDeProspection) {
        this.vitesseMaxDeProspection = vitesseMaxDeProspection;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public DateTime getValidityStart() {
        return validityStart;
    }

    public void setValidityStart(DateTime validityStart) {
        this.validityStart = validityStart;
    }

    public DateTime getExpiry() {
        return expiry;
    }

    public void setExpiry(DateTime expiry) {
        this.expiry = expiry;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getCommunautaryID() {
        return communautaryID;
    }

    public void setCommunautaryID(String communautaryID) {
        this.communautaryID = communautaryID;
    }

    public String getFaoID() {
        return faoID;
    }

    public void setFaoID(String faoID) {
        this.faoID = faoID;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.code;
        hash = 11 * hash + this.keelCode;
        hash = 11 * hash + this.fleetCode;
        hash = 11 * hash + this.yearService;
        hash = 11 * hash + (this.flagChangeDate != null ? this.flagChangeDate.hashCode() : 0);
        hash = 11 * hash + Float.floatToIntBits(this.length);
        hash = 11 * hash + Float.floatToIntBits(this.capacity);
        hash = 11 * hash + this.power;
        hash = 11 * hash + Float.floatToIntBits(this.vitesseMaxDeProspection);
        hash = 11 * hash + (this.libelle != null ? this.libelle.hashCode() : 0);
        hash = 11 * hash + (this.comment != null ? this.comment.hashCode() : 0);
        hash = 11 * hash + (this.validityStart != null ? this.validityStart.hashCode() : 0);
        hash = 11 * hash + (this.expiry != null ? this.expiry.hashCode() : 0);
        hash = 11 * hash + (this.nationalID != null ? this.nationalID.hashCode() : 0);
        hash = 11 * hash + (this.communautaryID != null ? this.communautaryID.hashCode() : 0);
        hash = 11 * hash + (this.faoID != null ? this.faoID.hashCode() : 0);
        hash = 11 * hash + (this.country != null ? this.country.hashCode() : 0);
        hash = 11 * hash + (this.vesselType != null ? this.vesselType.hashCode() : 0);
        hash = 11 * hash + (this.vesselSizeCategory != null ? this.vesselSizeCategory.hashCode() : 0);
        hash = 11 * hash + (this.vitesseChantier != null ? this.vitesseChantier.hashCode() : 0);
        hash = 11 * hash + (this.vitesseMax != null ? this.vitesseMax.hashCode() : 0);
        hash = 11 * hash + (this.lPP != null ? this.lPP.hashCode() : 0);
        hash = 11 * hash + (this.cGen != null ? this.cGen.hashCode() : 0);
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
        final Vessel other = (Vessel) obj;
        if (this.code != other.code) {
            return false;
        }
        if (this.keelCode != other.keelCode) {
            return false;
        }
        if (this.fleetCode != other.fleetCode) {
            return false;
        }
        if (this.yearService != other.yearService) {
            return false;
        }
        if (this.flagChangeDate != other.flagChangeDate && (this.flagChangeDate == null || !this.flagChangeDate.equals(other.flagChangeDate))) {
            return false;
        }
        if (Float.floatToIntBits(this.length) != Float.floatToIntBits(other.length)) {
            return false;
        }
        if (Float.floatToIntBits(this.capacity) != Float.floatToIntBits(other.capacity)) {
            return false;
        }
        if (this.power != other.power) {
            return false;
        }
        if (Float.floatToIntBits(this.vitesseMaxDeProspection) != Float.floatToIntBits(other.vitesseMaxDeProspection)) {
            return false;
        }
        if ((this.libelle == null) ? (other.libelle != null) : !this.libelle.equals(other.libelle)) {
            return false;
        }
        if ((this.comment == null) ? (other.comment != null) : !this.comment.equals(other.comment)) {
            return false;
        }
        if (this.validityStart != other.validityStart && (this.validityStart == null || !this.validityStart.equals(other.validityStart))) {
            return false;
        }
        if (this.expiry != other.expiry && (this.expiry == null || !this.expiry.equals(other.expiry))) {
            return false;
        }
        if ((this.nationalID == null) ? (other.nationalID != null) : !this.nationalID.equals(other.nationalID)) {
            return false;
        }
        if ((this.communautaryID == null) ? (other.communautaryID != null) : !this.communautaryID.equals(other.communautaryID)) {
            return false;
        }
        if ((this.faoID == null) ? (other.faoID != null) : !this.faoID.equals(other.faoID)) {
            return false;
        }
        if (this.country != other.country && (this.country == null || !this.country.equals(other.country))) {
            return false;
        }
        if (this.vesselType != other.vesselType && (this.vesselType == null || !this.vesselType.equals(other.vesselType))) {
            return false;
        }
        if (this.vesselSizeCategory != other.vesselSizeCategory && (this.vesselSizeCategory == null || !this.vesselSizeCategory.equals(other.vesselSizeCategory))) {
            return false;
        }
        if (this.vitesseChantier != other.vitesseChantier && (this.vitesseChantier == null || !this.vitesseChantier.equals(other.vitesseChantier))) {
            return false;
        }
        if (this.vitesseMax != other.vitesseMax && (this.vitesseMax == null || !this.vitesseMax.equals(other.vitesseMax))) {
            return false;
        }
        if (this.lPP != other.lPP && (this.lPP == null || !this.lPP.equals(other.lPP))) {
            return false;
        }
        if (this.cGen != other.cGen && (this.cGen == null || !this.cGen.equals(other.cGen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Bateau{" + "codeBateau=" + code + ", codeQuille=" + keelCode + ", codeFlotte=" + fleetCode + ", anneeService=" + yearService + ", dateChgtPavillon=" + flagChangeDate + ", longueurHorsToute=" + length + ", capaciteDeTransport=" + capacity + ", puissanceDuGroupePrincipal=" + power + ", vitesseMaxDeProspection=" + vitesseMaxDeProspection + ", libelleNomBateau=" + libelle + ", commentaireBateau=" + comment + ", debutValidite=" + validityStart + ", finValidite=" + expiry + ", numeroImmatNational=" + nationalID + ", numeroImmatCommunautaire=" + communautaryID + ", numeroImmatFao=" + faoID + ", statut=" + statut + ", pays=" + country + ", typeBateau=" + vesselType + ", categorieBateau=" + vesselSizeCategory + ", vitesseChantier=" + vitesseChantier + ", vitesseMax=" + vitesseMax + ", lPP=" + lPP + ", cGen=" + cGen + '}';
    }

    @Override
    public String getID() {
        return "" + getCode();
    }

    public boolean isPurseSeine() {
        return this.vesselType.getSimpleType().getCode() == 1;
    }

    public boolean isBaitBoat() {
        return this.vesselType.getSimpleType().getCode() == 2;
    }

    public boolean isLongLine() {
        return this.vesselType.getSimpleType().getCode() == 3;
    }
}
