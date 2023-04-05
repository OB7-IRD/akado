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
 * This class represents the Meta Trip output model.
 * <p>
 * Created on 25/03/2023.
 * *
 * * @author Tony Chemit - dev@tchemit.fr
 * * @since 1.0.0
 */
public final class MetaTripDataSheet {

    private String vesselCode;
    private String engine;
    private String landingDate;
    private String departureDate;
    private String firstActivityDate;
    private String lastActivityDate;
    private boolean partialLandingIndicator;
    private double raisingFactor;
    //    private double raisingFactor1WithLocalMarket;
    private boolean hasCatches;
    private String flag;

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

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getFirstActivityDate() {
        return firstActivityDate;
    }

    public void setFirstActivityDate(String firstActivityDate) {
        this.firstActivityDate = firstActivityDate;
    }

    public String getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(String lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    public boolean isPartialLandingIndicator() {
        return partialLandingIndicator;
    }

    public void setPartialLandingIndicator(boolean partialLandingIndicator) {
        this.partialLandingIndicator = partialLandingIndicator;
    }

    public double getRaisingFactor() {
        return raisingFactor;
    }

    public void setRaisingFactor(double raisingFactor) {
        this.raisingFactor = raisingFactor;
    }

    //    public double getRaisingFactor1WithLocalMarket() {
//        return raisingFactor1WithLocalMarket;
//    }
//
//    public void setRaisingFactor1WithLocalMarket(double raisingFactor1WithLocalMarket) {
//        this.raisingFactor1WithLocalMarket = raisingFactor1WithLocalMarket;
//    }
    public void setHasCatches(boolean hasCatches) {
        this.hasCatches = hasCatches;
    }

    public boolean isHasCatches() {
        return hasCatches;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
