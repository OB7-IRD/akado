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
package fr.ird.akado.observe.result.model;

/**
 * This class represents the Activity output model.
 * <p>
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public final class ActivityDataSheet {

    private String vesselCode;
    private String engine;
    private String landingDate;
    private String activityDate;
    private int activityNumber;
    private String operationCode;
    private Double catchWeight;
    private Double elementaryCatchesWeight;
    private Double sampleWeightedWeight;
    private String schoolType = "?";
    private String fishingContext = "";
    private int quadrant;
    private String oceanCode = "-";
    private String inOcean = "";
    private String inLand = "";
    private Double temperature;
    private String latitude;
    private String longitude;
    private int eezCode;

    public ActivityDataSheet() {
    }

    public String getInOcean() {
        return inOcean;
    }

    public void setInOcean(String inOcean) {
        this.inOcean = inOcean;
    }

    public String getVesselCode() {
        return vesselCode;
    }

    public void setVesselCode(String vesselCode) {
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

    public void setLandingDate(String landingDate) {
        this.landingDate = landingDate;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public int getActivityNumber() {
        return activityNumber;
    }

    public void setActivityNumber(int activityNumber) {
        this.activityNumber = activityNumber;
    }

    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    public Double getCatchWeight() {
        return catchWeight;
    }

    public void setCatchWeight(Double catchWeight) {
        this.catchWeight = catchWeight;
    }

    public Double getElementaryCatchesWeight() {
        return elementaryCatchesWeight;
    }

    public void setElementaryCatchesWeight(Double elementaryCatchesWeight) {
        this.elementaryCatchesWeight = elementaryCatchesWeight;
    }

    public void setSampleWeightedWeight(Double sampleWeightedWeight) {
        this.sampleWeightedWeight = sampleWeightedWeight;
    }

    public Double getSampleWeightedWeight() {
        return sampleWeightedWeight;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public String getFishingContext() {
        return fishingContext;
    }

    public void setFishingContext(String fishingContext) {
        this.fishingContext = fishingContext;
    }

    public int getQuadrant() {
        return quadrant;
    }

    public void setQuadrant(int quadrant) {
        this.quadrant = quadrant;
    }

    public String getOceanCode() {
        return oceanCode;
    }

    public void setOceanCode(String oceanCode) {
        this.oceanCode = oceanCode;
    }

    public String getInLand() {
        return inLand;
    }

    public void setInLand(String inLand) {
        this.inLand = inLand;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getEezCode() {
        return eezCode;
    }

    public void setEezCode(int eezCode) {
        this.eezCode = eezCode;
    }

}
