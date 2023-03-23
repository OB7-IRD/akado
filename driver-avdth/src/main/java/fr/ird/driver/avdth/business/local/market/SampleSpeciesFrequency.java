/*
 * Copyright (C) 2016 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.driver.avdth.business.local.market;

import fr.ird.driver.avdth.business.Species;
import fr.ird.driver.avdth.business.Vessel;
import org.joda.time.DateTime;

/**
 * This class name in AVDTH is FP_ECH_FREQT.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class SampleSpeciesFrequency {

    private Vessel vessel;
    private DateTime landingDate;
    private String sampleNumber;
    private Species species;
    private int ldlf;
    private double lengthClass;
    private int number;

    public int getLdlf() {
        return ldlf;
    }

    public void setLdlf(int ldlf) {
        this.ldlf = ldlf;
    }

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

    public String getSampleNumber() {
        return sampleNumber;
    }

    public void setSampleNumber(String sampleNumber) {
        this.sampleNumber = sampleNumber;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }
}
