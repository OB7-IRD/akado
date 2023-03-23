/* $Id: SampleSpeciesFrequency.java 434 2014-08-01 12:23:01Z lebranch $
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
import org.joda.time.DateTime;

/**
 * This class name in AVDTH is EchantillonFREQT.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 3.4.5
 * @date 26 mai 2014
 */
public class SampleSpeciesFrequency {

    private Vessel vessel;
    private DateTime landingDate;
    private int sampleNumber;
    private int superSampleNumber;
    private Species species;
    private int ldlf;

    private double lengthClass;
    private int number;

    public double getLengthClass() {
        return lengthClass;
    }

    public void setLengthClass(double lengthClass) {
        this.lengthClass = lengthClass;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public int getSuperSampleNumber() {
        return superSampleNumber;
    }

    public void setSuperSampleNumber(int superSampleNumber) {
        this.superSampleNumber = superSampleNumber;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.vessel != null ? this.vessel.hashCode() : 0);
        hash = 79 * hash + (this.landingDate != null ? this.landingDate.hashCode() : 0);
        hash = 79 * hash + this.sampleNumber;
        hash = 79 * hash + this.superSampleNumber;
        hash = 79 * hash + (this.species != null ? this.species.hashCode() : 0);
        hash = (int) (79 * hash + this.lengthClass);
        hash = 79 * hash + this.number;
        hash = 79 * hash + this.ldlf;
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
        final SampleSpeciesFrequency other = (SampleSpeciesFrequency) obj;
        if (this.vessel != other.vessel && (this.vessel == null || !this.vessel.equals(other.vessel))) {
            return false;
        }
        if (this.landingDate != other.landingDate && (this.landingDate == null || !this.landingDate.equals(other.landingDate))) {
            return false;
        }
        if (this.sampleNumber != other.sampleNumber) {
            return false;
        }
        if (this.superSampleNumber != other.superSampleNumber) {
            return false;
        }
        if (this.species != other.species && (this.species == null || !this.species.equals(other.species))) {
            return false;
        }
        if (this.lengthClass != other.lengthClass) {
            return false;
        }
        if (this.number != other.number) {
            return false;
        }
        if (this.ldlf != other.ldlf) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SampleSpeciesFrequency{" + "vessel=" + vessel + ", landingDate=" + landingDate + ", sampleNumber=" + sampleNumber + ", superSampleNumber=" + superSampleNumber + ", species=" + species + ", lengthClass=" + lengthClass + ", number=" + number + ", ldlf=" + ldlf + '}';
    }

    public int getLdlf() {
        return ldlf;
    }

    public void setLdlf(int ldlf) {
        this.ldlf = ldlf;
    }

    public String getID() {
        return String.format("%s %s %s %s %s %s %s",
                getVessel().getCode(),
                DATE_FORMATTER.print(landingDate),
                sampleNumber,
                superSampleNumber,
                species.getCode(),
                ldlf,
                lengthClass);
    }

}
