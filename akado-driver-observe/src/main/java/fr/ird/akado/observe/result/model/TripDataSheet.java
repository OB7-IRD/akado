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
 * This class represents the Trip output model.
 * <p>
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public final class TripDataSheet {

    public String landingHarbour;
    public String departureHarbour;
    private String vesselCode;
    private String engine;
    private String landingDate;
    private String departureDate;
    private boolean hasLogbook;
    private String firstActivityDate;
    private String lastActivityDate;

    private int timeAtSea;
    private int timeAtSeaExpected;
    private int fishingTime;
    private int fishingTimeExpected;
    private double landingWeight;
    private double landingWeightExpected;

    private boolean partialLandingIndicator;

    private String recoveryTimeDate;

    public String getVesselCode() {
        return vesselCode;
    }

    public String getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(String lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
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

    public String getLandingHarbour() {
        return landingHarbour;
    }

    public void setLandingHarbour(String landingHarbour) {
        this.landingHarbour = landingHarbour;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public boolean getHasLogbook() {
        return hasLogbook;
    }

    public void setHasLogbook(boolean hasLogbook) {
        this.hasLogbook = hasLogbook;
    }

    public String getFirstActivityDate() {
        return firstActivityDate;
    }

    public void setFirstActivityDate(String firstActivityDate) {
        this.firstActivityDate = firstActivityDate;
    }

    public int getTimeAtSea() {
        return timeAtSea;
    }

    public void setTimeAtSea(int timeAtSea) {
        this.timeAtSea = timeAtSea;
    }

    public int getTimeAtSeaExpected() {
        return timeAtSeaExpected;
    }

    public void setTimeAtSeaExpected(int timeAtSeaExpected) {
        this.timeAtSeaExpected = timeAtSeaExpected;
    }

    public int getFishingTime() {
        return fishingTime;
    }

    public void setFishingTime(int fishingTime) {
        this.fishingTime = fishingTime;
    }

    public int getFishingTimeExpected() {
        return fishingTimeExpected;
    }

    public void setFishingTimeExpected(int fishingTimeExpected) {
        this.fishingTimeExpected = fishingTimeExpected;
    }

    public double getLandingWeight() {
        return landingWeight;
    }

    public void setLandingWeight(double landingWeight) {
        this.landingWeight = landingWeight;
    }

    public double getLandingWeightExpected() {
        return landingWeightExpected;
    }

    public void setLandingWeightExpected(double landingWeightExpected) {
        this.landingWeightExpected = landingWeightExpected;
    }

    public boolean getPartialLandingIndicator() {
        return partialLandingIndicator;
    }

    public void setPartialLandingIndicator(boolean partialLandingIndicator) {
        this.partialLandingIndicator = partialLandingIndicator;
    }

    public String getRecoveryTimeDate() {
        return recoveryTimeDate;
    }

    public void setRecoveryTimeDate(String recoveryTimeDate) {
        this.recoveryTimeDate = recoveryTimeDate;
    }

    public String getDepartureHarbour() {
        return departureHarbour;
    }

    public void setDepartureHarbour(String departureHarbour) {
        this.departureHarbour = departureHarbour;
    }

    @Override
    public String toString() {
        return "TripDataSheet{" + "vesselCode=" + vesselCode + ", engine=" + engine + ", landingDate=" + landingDate + ", landingHarbour=" + landingHarbour + ", departureDate=" + departureDate + ", departureHarbour=" + departureHarbour + ", hasLogbook=" + hasLogbook + ", firstActivityDate=" + firstActivityDate + ", lastActivityDate=" + lastActivityDate + ", timeAtSea=" + timeAtSea + ", timeAtSeaExpected=" + timeAtSeaExpected + ", fishingTime=" + fishingTime + ", fishingTimeExpected=" + fishingTimeExpected + ", landingWeight=" + landingWeight + ", landingWeightExpected=" + landingWeightExpected + ", partialLandingIndicator=" + partialLandingIndicator + ", recoveryTimeDate=" + recoveryTimeDate + '}';
    }

}
