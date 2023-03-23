/* 
 * Copyright (C) 2014 Julien Lebranchu <julien.lebranchu@ird.fr>
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

import static fr.ird.common.DateTimeUtils.DATE_FORMATTER;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

/**
 * This class name in AVDTH is Echantillon.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 3.4.5
 * @date 26 mai 2014
 */
public class Sample implements Identifier {

    private Vessel vessel;
    private DateTime landingDate;
    private int sampleNumber;
    private Harbour landingHarbour;
    private SampleQuality sampleQuality;
    private SampleType sampleType;

    private Well well;
    private double minus10Weight;
    private double plus10Weight;
    private double globalWeight;
    private int superSampleFlag;

    private List<SampleSpecies> sampleSpecies = new ArrayList<>();

    private List<SampleWell> sampleWells = new ArrayList<>();

    public List<SampleWell> getSampleWells() {

        return sampleWells;
    }

    public void setSampleWells(List<SampleWell> sampleWells) {
        this.sampleWells = sampleWells;
    }

    public List<SampleSpecies> getSampleSpecies() {

        return sampleSpecies;
    }

    public void setSampleSpecies(List<SampleSpecies> sampleSpecies) {
        this.sampleSpecies = sampleSpecies;
    }

    public int getSampleNumber() {
        return sampleNumber;
    }

    public void setSampleNumber(int sampleNumber) {
        this.sampleNumber = sampleNumber;
    }

    public SampleQuality getSampleQuality() {
        return sampleQuality;
    }

    public void setSampleQuality(SampleQuality sampleQuality) {
        this.sampleQuality = sampleQuality;
    }

    public SampleType getSampleType() {
        return sampleType;
    }

    public void setSampleType(SampleType sampleType) {
        this.sampleType = sampleType;
    }

    public Harbour getLandingHarbour() {
        return landingHarbour;
    }

    public void setLandingHarbour(Harbour landingHarbour) {
        this.landingHarbour = landingHarbour;
    }

    public Well getWell() {
        return well;
    }

    public void setWell(Well well) {
        this.well = well;
    }

    public double getMinus10Weight() {
        return minus10Weight;
    }

    public void setMinus10Weight(double minus10Weight) {
        this.minus10Weight = minus10Weight;
    }

    public double getPlus10Weight() {
        return plus10Weight;
    }

    public void setPlus10Weight(double plus10Weight) {
        this.plus10Weight = plus10Weight;
    }

    public double getGlobalWeight() {
        return globalWeight;
    }

    public void setGlobalWeight(double globalWeight) {
        this.globalWeight = globalWeight;
    }

    public int getSuperSampleFlag() {
        return superSampleFlag;
    }

    public void setSuperSampleFlag(int superSampleFlag) {
        this.superSampleFlag = superSampleFlag;
    }

    @Override
    public String getID() {
        return getVessel().getCode() + " " + DATE_FORMATTER.print(landingDate) + " " + sampleNumber;
    }

    public Vessel getVessel() {
        return vessel;
    }

    public void setVessel(Vessel vessel) {
        this.vessel = vessel;
    }

    public DateTime getLandingDate() {
        return landingDate;
    }

    public void setLandingDate(DateTime landingDate) {
        this.landingDate = landingDate;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.vessel != null ? this.vessel.hashCode() : 0);
        hash = 59 * hash + (this.landingDate != null ? this.landingDate.hashCode() : 0);
        hash = 59 * hash + this.sampleNumber;
        hash = 59 * hash + (this.sampleQuality != null ? this.sampleQuality.hashCode() : 0);
        hash = 59 * hash + (this.sampleType != null ? this.sampleType.hashCode() : 0);
        hash = 59 * hash + (this.landingHarbour != null ? this.landingHarbour.hashCode() : 0);
        hash = 59 * hash + (this.well != null ? this.well.hashCode() : 0);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.minus10Weight) ^ (Double.doubleToLongBits(this.minus10Weight) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.plus10Weight) ^ (Double.doubleToLongBits(this.plus10Weight) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.globalWeight) ^ (Double.doubleToLongBits(this.globalWeight) >>> 32));
        hash = 59 * hash + this.superSampleFlag;
        hash = 59 * hash + (this.sampleSpecies != null ? this.sampleSpecies.hashCode() : 0);
        hash = 59 * hash + (this.sampleWells != null ? this.sampleWells.hashCode() : 0);
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
        final Sample other = (Sample) obj;
        if (this.vessel != other.vessel && (this.vessel == null || !this.vessel.equals(other.vessel))) {
            return false;
        }
        if (this.landingDate != other.landingDate && (this.landingDate == null || !this.landingDate.equals(other.landingDate))) {
            return false;
        }
        if (this.sampleNumber != other.sampleNumber) {
            return false;
        }
        if (this.sampleQuality != other.sampleQuality && (this.sampleQuality == null || !this.sampleQuality.equals(other.sampleQuality))) {
            return false;
        }
        if (this.sampleType != other.sampleType && (this.sampleType == null || !this.sampleType.equals(other.sampleType))) {
            return false;
        }
        if (this.landingHarbour != other.landingHarbour && (this.landingHarbour == null || !this.landingHarbour.equals(other.landingHarbour))) {
            return false;
        }
        if (this.well != other.well && (this.well == null || !this.well.equals(other.well))) {
            return false;
        }
        if (Double.doubleToLongBits(this.minus10Weight) != Double.doubleToLongBits(other.minus10Weight)) {
            return false;
        }
        if (Double.doubleToLongBits(this.plus10Weight) != Double.doubleToLongBits(other.plus10Weight)) {
            return false;
        }
        if (Double.doubleToLongBits(this.globalWeight) != Double.doubleToLongBits(other.globalWeight)) {
            return false;
        }
        if (this.superSampleFlag != other.superSampleFlag) {
            return false;
        }
        if (this.sampleSpecies != other.sampleSpecies && (this.sampleSpecies == null || !this.sampleSpecies.equals(other.sampleSpecies))) {
            return false;
        }
        if (this.sampleWells != other.sampleWells && (this.sampleWells == null || !this.sampleWells.equals(other.sampleWells))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sample{" + "vessel=" + vessel + ", landingDate=" + landingDate + ", sampleNumber=" + sampleNumber + ", landingHarbour=" + landingHarbour + ", sampleQuality=" + sampleQuality + ", sampleType=" + sampleType + ", well=" + well + ", minus10Weight=" + minus10Weight + ", plus10Weight=" + plus10Weight + ", globalWeight=" + globalWeight + ", superSampleFlag=" + superSampleFlag + ", sampleSpecies=" + sampleSpecies + ", sampleWells=" + sampleWells + '}';
    }

}
