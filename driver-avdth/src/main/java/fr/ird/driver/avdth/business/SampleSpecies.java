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
import java.util.List;
import org.joda.time.DateTime;

/**
 * This class name in AVDTH is EchantillonEspece.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 3.4.5
 * @date 26 mai 2014
 *
 */
public class SampleSpecies {

    public static int SAMPLE_LENGTH_CLASS_FOR_DORSAL = 1;
    public static int SAMPLE_LENGTH_CLASS_FOR_FORK = 2;
    public static int SAMPLE_LENGTH_CLASS_FOR_DORSAL_ONE_CENTIMER_FREQUENCY = 3;

    private Vessel vessel;
    private DateTime landingDate;
    private int sampleNumber;
    private int subSampleNumber;
    private Species species;
    private double measuredCount;
    private double totalCount;
    private int ldlf;

    private List<SampleSpeciesFrequency> sampleSpeciesFrequencys;

    public List<SampleSpeciesFrequency> getSampleSpeciesFrequencys() {
        return sampleSpeciesFrequencys;
    }

    public void setSampleSpeciesFrequencys(List<SampleSpeciesFrequency> sampleSpeciesFrequencys) {
        this.sampleSpeciesFrequencys = sampleSpeciesFrequencys;
    }

    public int getSubSampleNumber() {
        return subSampleNumber;
    }

    public void setSubSampleNumber(int subSampleNumber) {
        this.subSampleNumber = subSampleNumber;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public double getMeasuredCount() {
        return measuredCount;
    }

    public void setMeasuredCount(double measuredCount) {
        this.measuredCount = measuredCount;
    }

    public double getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(double totalCount) {
        this.totalCount = totalCount;
    }

    public int getLdlf() {
        return ldlf;
    }

    public void setLdlf(int ldlf) {
        this.ldlf = ldlf;
    }

    @Override
    public String toString() {
        return "SampleSpecies{" + "vessel=" + vessel + ", landingDate=" + landingDate + ", sampleNumber=" + sampleNumber + ", superSampleNumber=" + subSampleNumber + ", species=" + species + ", measuredCount=" + measuredCount + ", totalCount=" + totalCount + ", ldlf=" + ldlf + ", sampleSpeciesFrequencys=" + sampleSpeciesFrequencys + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.vessel != null ? this.vessel.hashCode() : 0);
        hash = 97 * hash + (this.landingDate != null ? this.landingDate.hashCode() : 0);
        hash = 97 * hash + this.sampleNumber;
        hash = 97 * hash + this.subSampleNumber;
        hash = 97 * hash + (this.species != null ? this.species.hashCode() : 0);
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.measuredCount) ^ (Double.doubleToLongBits(this.measuredCount) >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.totalCount) ^ (Double.doubleToLongBits(this.totalCount) >>> 32));
        hash = 97 * hash + this.ldlf;
        hash = 97 * hash + (this.sampleSpeciesFrequencys != null ? this.sampleSpeciesFrequencys.hashCode() : 0);
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
        final SampleSpecies other = (SampleSpecies) obj;
        if (this.vessel != other.vessel && (this.vessel == null || !this.vessel.equals(other.vessel))) {
            return false;
        }
        if (this.landingDate != other.landingDate && (this.landingDate == null || !this.landingDate.equals(other.landingDate))) {
            return false;
        }
        if (this.sampleNumber != other.sampleNumber) {
            return false;
        }
        if (this.subSampleNumber != other.subSampleNumber) {
            return false;
        }
        if (this.species != other.species && (this.species == null || !this.species.equals(other.species))) {
            return false;
        }
        if (Double.doubleToLongBits(this.measuredCount) != Double.doubleToLongBits(other.measuredCount)) {
            return false;
        }
        if (Double.doubleToLongBits(this.totalCount) != Double.doubleToLongBits(other.totalCount)) {
            return false;
        }
        if (this.ldlf != other.ldlf) {
            return false;
        }
        if (this.sampleSpeciesFrequencys != other.sampleSpeciesFrequencys && (this.sampleSpeciesFrequencys == null || !this.sampleSpeciesFrequencys.equals(other.sampleSpeciesFrequencys))) {
            return false;
        }
        return true;
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

    public int getSampleNumber() {
        return sampleNumber;
    }

    public void setSampleNumber(int sampleNumber) {
        this.sampleNumber = sampleNumber;
    }

    public String getID() {
        return String.format("%s %s %s %s %s %s",
                getVessel().getCode(),
                DATE_FORMATTER.print(landingDate),
                sampleNumber,
                subSampleNumber,
                species.getCode(),
                ldlf);
    }
}
