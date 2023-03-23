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

/**
 * This class represents the Anapo output model.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public final class AnapoDataSheet {

    private int vesselCode;
    private String engine;
    private String landingDate;

    private String activityDate;
    private int activityNumber;
    private int activityOperation;
    private int activityQuadrant;
    private String activitylagLong;
    private String activitylagLongDegMin;
    private int vmsPositionCount;

    private String vmsPositionDate;
    private Double distancePosition;
    private Double vmsScore;
    private String vmsLatLong;
    private int vmsQuadrant;
    private String vmsLatLongDegMin;
    private int vmsDirection;
    private double vmsSpeed;
    private int vmsOcean;
    private String activityHour;

    public AnapoDataSheet(int vesselCode, String engine, String landingDate,
            String activityDate, String activityHour, int activityNumber, int activityOperation, int activityQuadrant,
            String activitylagLong, String activitylagLongDegMin, int vmsPositionCount, String vmsPositionDate, Double distancePosition,
            Double vmsScore, int vmsQuadrant, String vmsLatLong, String vmsLatLongDegMin, int vmsDirection, double vmsSpeed, int vmsOcean) {
        this(vesselCode, engine, landingDate, activityDate, activityHour, activityNumber, activityOperation, activityQuadrant, activitylagLong, activitylagLongDegMin, vmsPositionCount);

        this.vmsPositionDate = vmsPositionDate;
        this.distancePosition = distancePosition;
        this.vmsScore = vmsScore;
        this.vmsLatLong = vmsLatLong;
        this.vmsQuadrant = vmsQuadrant;
        this.vmsDirection = vmsDirection;
        this.vmsSpeed = vmsSpeed;
        this.vmsOcean = vmsOcean;
        this.activitylagLongDegMin = activitylagLongDegMin;
        this.vmsLatLongDegMin = vmsLatLongDegMin;

    }

    public AnapoDataSheet(int vesselCode, String engine, String landingDate, String activityDate, String activityHour, int activityNumber, int activityOperation, int activityQuadrant, String activitylagLong, String activitylagLongDegMin, int vmsPositionCount) {
        this.vesselCode = vesselCode;
        this.engine = engine;
        this.landingDate = landingDate;
        this.activityDate = activityDate;
        this.activityHour = activityHour;
        this.activityNumber = activityNumber;
        this.activityOperation = activityOperation;
        this.activityQuadrant = activityQuadrant;
        this.activitylagLong = activitylagLong;
        this.vmsPositionCount = vmsPositionCount;
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

    public int getActivityQuadrant() {
        return activityQuadrant;
    }

    public void setActivityQuadrant(int activityQuadrant) {
        this.activityQuadrant = activityQuadrant;
    }

    public String getActivitylagLong() {
        return activitylagLong;
    }

    public void setActivitylagLong(String activitylagLong) {
        this.activitylagLong = activitylagLong;
    }

    public int getVmsPositionCount() {
        return vmsPositionCount;
    }

    public void setVmsPositionCount(int vmsPositionCount) {
        this.vmsPositionCount = vmsPositionCount;
    }

    public String getVmsPositionDate() {
        return vmsPositionDate;
    }

    public void setVmsPositionDate(String vmsPositionDate) {
        this.vmsPositionDate = vmsPositionDate;
    }

    public Double getVmsScore() {
        return vmsScore;
    }

    public void setVmsScore(Double vmsScore) {
        this.vmsScore = vmsScore;
    }

    public String getVmsLatLong() {
        return vmsLatLong;
    }

    public void setVmsLatLong(String vmsLatLong) {
        this.vmsLatLong = vmsLatLong;
    }

    public int getVmsDirection() {
        return vmsDirection;
    }

    public void setVmsDirection(int vmsDirection) {
        this.vmsDirection = vmsDirection;
    }

    public double getVmsSpeed() {
        return vmsSpeed;
    }

    public void setVmsSpeed(double vmsSpeed) {
        this.vmsSpeed = vmsSpeed;
    }

    public int getVmsOcean() {
        return vmsOcean;
    }

    public void setVmsOcean(int vmsOcean) {
        this.vmsOcean = vmsOcean;
    }

    public String getActivitylagLongDegMin() {
        return activitylagLongDegMin;
    }

    public void setActivitylagLongDegMin(String activitylagLongDegMin) {
        this.activitylagLongDegMin = activitylagLongDegMin;
    }

    public String getVmsLatLongDegMin() {
        return vmsLatLongDegMin;
    }

    public void setVmsLatLongDegMin(String vmsLatLongDegMin) {
        this.vmsLatLongDegMin = vmsLatLongDegMin;
    }

    public Double getDistancePosition() {
        return distancePosition;
    }

    public void setDistancePosition(Double distancePosition) {
        this.distancePosition = distancePosition;
    }

    public int getActivityOperation() {
        return activityOperation;
    }

    public void setActivityOperation(int activityOperation) {
        this.activityOperation = activityOperation;
    }

    public int getVmsQuadrant() {
        return vmsQuadrant;
    }

    public void setVmsQuadrant(int vmsQuadrant) {
        this.vmsQuadrant = vmsQuadrant;
    }

    public String getActivityHour() {
        return activityHour;
    }

    public void setActivityHour(String activityHour) {
        this.activityHour = activityHour;
    }

}
