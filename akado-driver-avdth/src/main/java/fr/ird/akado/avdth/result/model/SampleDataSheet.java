/*
 * Copyright (C) 2015 Observatoire thonier, IRD
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
package fr.ird.akado.avdth.result.model;

import static fr.ird.common.DateTimeUtils.DATE_FORMATTER;
import org.joda.time.DateTime;

/**
 * This class represents the Sample output model.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public final class SampleDataSheet {

    public SampleDataSheet() {
    }

    private int vesselCode;
    private String engine;
    private String landingDate;
    private boolean tripExist;
    private int harbourCode;
    private int hasLogbook;
    private String bigFish;
    private String littleFish;
    private String activityConsistent;
    private String positionConsistent;
    private double sampleSpeciesFrequencyCount;
    private double sampleSpeciesMeasuredCount;

    private double weightedWeight;
    private double sPoids;
    private double globalWeight;
    private double plus10Weight;
    private double minus10Weight;
    private String sampleType;
    private int sampleNumber;

    //SampleSpecies
    private Integer subSampleNumber;
    private String speciesCode;
    private String lengthClassCount;
    private String ldlf;

    private boolean hasWell;
    private String distribution;

    public SampleDataSheet(int vesselCode, String engine, String landingDate,
            int hasLogbook, boolean tripExist, int harbourCode, String bigFish, String littleFish,
            String activityConsistent, String positionConsistent, double sampleSpeciesFrequencyCount,
            double sampleSpeciesMeasuredCount, double weightedWeight, double sPoids,
            double globalWeight, double plus10Weight, double minus10Weight,
            String sampleType, boolean hasWell, int sampleNumber, String distribution) {
        this.vesselCode = vesselCode;
        this.engine = engine;
        this.landingDate = landingDate;
        this.tripExist = tripExist;
        this.harbourCode = harbourCode;
        this.hasLogbook = hasLogbook;
        this.bigFish = bigFish;
        this.littleFish = littleFish;
        this.activityConsistent = activityConsistent;
        this.positionConsistent = positionConsistent;
        this.sampleSpeciesFrequencyCount = sampleSpeciesFrequencyCount;
        this.sampleSpeciesMeasuredCount = sampleSpeciesMeasuredCount;
        this.weightedWeight = weightedWeight;
        this.sPoids = sPoids;
        this.globalWeight = globalWeight;
        this.plus10Weight = plus10Weight;
        this.minus10Weight = minus10Weight;
        this.sampleType = sampleType;
        this.sampleNumber = sampleNumber;
        this.hasWell = hasWell;
        this.distribution = distribution;
    }

    public SampleDataSheet(int vesselCode, String engine, String landingDate,
            int hasLogbook, boolean tripExist, int harbourCode, String bigFish, String littleFish,
            String activityConsistent, String positionConsistent, double sampleSpeciesFrequencyCount,
            double sampleSpeciesMeasuredCount, double weightedWeight, double sPoids,
            double globalWeight, double plus10Weight, double minus10Weight,
            String sampleType, boolean hasWell, int sampleNumber, String distribution,
            Integer subSampleNumber, String speciesCode) {
        this(vesselCode, engine, landingDate, hasLogbook, tripExist, harbourCode, bigFish, littleFish, activityConsistent, positionConsistent, sampleSpeciesFrequencyCount, sampleSpeciesMeasuredCount, weightedWeight, sPoids, globalWeight, plus10Weight, minus10Weight, sampleType, hasWell, sampleNumber, distribution);
        this.subSampleNumber = subSampleNumber;
        this.speciesCode = speciesCode;
    }

    public SampleDataSheet(int vesselCode, String engine, String landingDate, int hasLogbook, boolean tripExist, int harbourCode, String bigFish, String littleFish, String activityConsistent, String positionConsistent, double sampleSpeciesFrequencyCount, double sampleSpeciesMeasuredCount, double weightedWeight, double sPoids, double globalWeight, double plus10Weight, double minus10Weight, String sampleType, boolean hasWell, int sampleNumber, String distribution, Integer subSampleNumber, String speciesCode, String lengthClassCount, String ldlf) {
        this(vesselCode, engine, landingDate, hasLogbook, tripExist, harbourCode, bigFish, littleFish, activityConsistent, positionConsistent, sampleSpeciesFrequencyCount, sampleSpeciesMeasuredCount, weightedWeight, sPoids, globalWeight, plus10Weight, minus10Weight, sampleType, hasWell, sampleNumber, distribution, subSampleNumber, speciesCode);
        this.lengthClassCount = lengthClassCount;
        this.ldlf = ldlf;
    }

    public String getBigFish() {
        return bigFish;
    }

    public String getLittleFish() {
        return littleFish;
    }

    public double getSampleSpeciesFrequencyCount() {
        return sampleSpeciesFrequencyCount;
    }

    public void setSampleSpeciesFrequencyCount(double sampleSpeciesFrequencyCount) {
        this.sampleSpeciesFrequencyCount = sampleSpeciesFrequencyCount;
    }

    public double getSampleSpeciesMeasuredCount() {
        return sampleSpeciesMeasuredCount;
    }

    public void setSampleSpeciesMeasuredCount(double sampleSpeciesMeasuredCount) {
        this.sampleSpeciesMeasuredCount = sampleSpeciesMeasuredCount;
    }

    public double getWeightedWeight() {
        return weightedWeight;
    }

    public void setWeightedWeight(double weightedWeight) {
        this.weightedWeight = weightedWeight;
    }

    public double getsPoids() {
        return sPoids;
    }

    public void setsPoids(double sPoids) {
        this.sPoids = sPoids;
    }

    public double getGlobalWeight() {
        return globalWeight;
    }

    public void setGlobalWeight(double globalWeight) {
        this.globalWeight = globalWeight;
    }

    public double getPlus10Weight() {
        return plus10Weight;
    }

    public void setPlus10Weight(double plus10Weight) {
        this.plus10Weight = plus10Weight;
    }

    public double getMinus10Weight() {
        return minus10Weight;
    }

    public void setMinus10Weight(double minus10Weight) {
        this.minus10Weight = minus10Weight;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public int getVesselCode() {
        return vesselCode;
    }

    public void setVesselCode(int vesselCode) {
        this.vesselCode = vesselCode;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getLandingDate() {
        return landingDate;
    }

    public void setLandingDate(DateTime landingDate) {
        if (landingDate != null) {
            this.landingDate = landingDate.toString(DATE_FORMATTER);
        }
    }

    public void setLandingDate(String landingDate) {
        this.landingDate = landingDate;
    }

    public boolean isTripExist() {
        return tripExist;
    }

    public void setTripExist(boolean tripExist) {
        this.tripExist = tripExist;
    }

    public int getHasLogbook() {
        return hasLogbook;
    }

    public void setHasLogbook(int hasLogbook) {
        this.hasLogbook = hasLogbook;
    }

    public void setSamplingType(String sampleType) {
        this.sampleType = sampleType;
    }

    public void setSamplinWeightM10(double minus10Weight) {
        this.minus10Weight = minus10Weight;
    }

    public void setSamplinWeightP10(double plus10Weight) {
        this.plus10Weight = plus10Weight;
    }

    public void setSamplinGlobalWeight(double globalWeight) {
        this.globalWeight = globalWeight;
    }

    public void setSPoids(double sPoids) {
        this.sPoids = sPoids;
    }

    public void setSPond(double weightedWeight) {
        this.weightedWeight = weightedWeight;
    }

    public void setMeasuredCount(double sampleSpeciesMeasuredCount) {
        this.sampleSpeciesMeasuredCount = sampleSpeciesMeasuredCount;
    }

    public void setSampleFreqCount(double sampleSpeciesFrequencyCount) {
        this.sampleSpeciesFrequencyCount = sampleSpeciesFrequencyCount;
    }

    public void setLittleFish(String littleFish) {
        this.littleFish = littleFish;
    }

    public void setBigFish(String bigFish) {
        this.bigFish = bigFish;
    }

    public int getSampleNumber() {
        return sampleNumber;
    }

    public void setSampleNumber(int sampleNumber) {
        this.sampleNumber = sampleNumber;
    }

    public Integer getSubSampleNumber() {
        return subSampleNumber;
    }

    public void setSubSampleNumber(Integer subSampleNumber) {
        this.subSampleNumber = subSampleNumber;
    }

    public String getSpeciesCode() {
        return speciesCode;
    }

    public void setSpeciesCode(String speciesCode) {
        this.speciesCode = speciesCode;
    }

    public String getLengthClassCount() {
        return lengthClassCount;
    }

    public void setLengthClassCount(String lengthClassCount) {
        this.lengthClassCount = lengthClassCount;
    }

    public String getLdlf() {
        return ldlf;
    }

    public void setLdlf(String ldlf) {
        this.ldlf = ldlf;
    }

    public String getActivityConsistent() {
        return activityConsistent;
    }

    public void setActivityConsistent(String activityConsistent) {
        this.activityConsistent = activityConsistent;
    }

    public String getPositionConsistent() {
        return positionConsistent;
    }

    public void setPositionConsistent(String positionConsistent) {
        this.positionConsistent = positionConsistent;
    }

    public boolean isHasWell() {
        return hasWell;
    }

    public void setHasWell(boolean hasWell) {
        this.hasWell = hasWell;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public int getHarbourCode() {
        return harbourCode;
    }

    public void setHarbourCode(int harbourCode) {
        this.harbourCode = harbourCode;
    }

    @Override
    public String toString() {
        return "SampleDataSheet{" + "vesselCode=" + vesselCode + ", engine=" + engine + ", landingDate=" + landingDate + ", tripExist=" + tripExist + ", hasLogbook=" + hasLogbook + ", bigFish=" + bigFish + ", littleFish=" + littleFish + ", activityConsistent=" + activityConsistent + ", positionConsistent=" + positionConsistent + ", sampleSpeciesFrequencyCount=" + sampleSpeciesFrequencyCount + ", sampleSpeciesMeasuredCount=" + sampleSpeciesMeasuredCount + ", weightedWeight=" + weightedWeight + ", sPoids=" + sPoids + ", globalWeight=" + globalWeight + ", plus10Weight=" + plus10Weight + ", minus10Weight=" + minus10Weight + ", sampleType=" + sampleType + ", sampleNumber=" + sampleNumber + ", subSampleNumber=" + subSampleNumber + ", speciesCode=" + speciesCode + ", lengthClassCount=" + lengthClassCount + ", ldlf=" + ldlf + ", hasWell=" + hasWell + ", distribution=" + distribution + '}';
    }
}
