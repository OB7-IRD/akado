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

import org.joda.time.DateTime;

import static fr.ird.common.DateTimeUtils.DATE_FORMATTER;

/**
 * This class represents the Well output model.
 * <p>
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public final class WellDataSheet {

    private int vesselCode;
    private String engine;
    private String landingDate;
    private boolean tripExist;
    private boolean wellPlanEmpty;
    // WellPlan
    private String activityDate;
    private int activityNumber;
    private boolean activityExist;
    private int wellNumber;

    public WellDataSheet(int vesselCode, String engine, String landingDate, boolean tripExist, boolean wellPlanEmpty) {
        this.vesselCode = vesselCode;
        this.engine = engine;
        this.landingDate = landingDate;
        this.tripExist = tripExist;
        this.wellPlanEmpty = wellPlanEmpty;
    }

    public WellDataSheet(int vesselCode, String engine, String landingDate, boolean tripExist, boolean wellPlanEmpty, String activityDate, int activityNumber, boolean activityExist) {
        this(vesselCode, engine, landingDate, tripExist, wellPlanEmpty);
        this.activityDate = activityDate;
        this.activityNumber = activityNumber;
        this.activityExist = activityExist;
    }

    public void setLandingDate(DateTime landingDate) {
        if (landingDate != null) {
            this.landingDate = landingDate.toString(DATE_FORMATTER);
        }
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

    public boolean isTripExist() {
        return tripExist;
    }

    public boolean isWellPlanEmpty() {
        return wellPlanEmpty;
    }

    public void setWellPlanEmpty(boolean wellPlanEmpty) {
        this.wellPlanEmpty = wellPlanEmpty;
    }

    public void setTripExist(boolean tripExist) {
        this.tripExist = tripExist;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public void setActivityDate(DateTime activityDate) {
        if (activityDate != null) {
            this.activityDate = activityDate.toString(DATE_FORMATTER);
        }
    }

    public int getActivityNumber() {
        return activityNumber;
    }

    public void setActivityNumber(int activityNumber) {
        this.activityNumber = activityNumber;
    }

    public boolean isActivityExist() {
        return activityExist;
    }

    public void setActivityExist(boolean activityExist) {
        this.activityExist = activityExist;
    }

    public int getWellNumber() {
        return wellNumber;
    }

    public void setWellNumber(int wellNumber) {
        this.wellNumber = wellNumber;
    }

}
