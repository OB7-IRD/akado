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

import org.joda.time.DateTime;

/**
 * This class name in AVDTH is EchantillonCalee.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 3.4.5
 * @date 16 juin 2014
 */
public class SampleWell {

    private Vessel vessel;
    private DateTime landingDate;
    private int sampleNumber;

    private DateTime activityDate;
    private int activityNumber;
    private GeographicalArea geo;
    private int quadrant;
    private int latitude;
    private int longitude;
    private SchoolType schoolType;
    private double weightedWeight;

    public DateTime getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(DateTime activityDate) {
        this.activityDate = activityDate;
    }

    public int getActivityNumber() {
        return activityNumber;
    }

    public void setActivityNumber(int activityNumber) {
        this.activityNumber = activityNumber;
    }

    public GeographicalArea getGeo() {
        return geo;
    }

    public void setGeo(GeographicalArea geo) {
        this.geo = geo;
    }

    public int getQuadrant() {
        return quadrant;
    }

    public void setQuadrant(int quadrant) {
        this.quadrant = quadrant;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public SchoolType getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(SchoolType schoolType) {
        this.schoolType = schoolType;
    }

    public double getWeightedWeight() {
        return weightedWeight;
    }

    public void setWeightedWeight(double weightedWeight) {
        this.weightedWeight = weightedWeight;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.vessel != null ? this.vessel.hashCode() : 0);
        hash = 89 * hash + (this.landingDate != null ? this.landingDate.hashCode() : 0);
        hash = 89 * hash + this.sampleNumber;
        hash = 89 * hash + (this.activityDate != null ? this.activityDate.hashCode() : 0);
        hash = 89 * hash + this.activityNumber;
        hash = 89 * hash + this.quadrant;
        hash = 89 * hash + this.latitude;
        hash = 89 * hash + this.longitude;
        hash = 89 * hash + (this.schoolType != null ? this.schoolType.hashCode() : 0);
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.weightedWeight) ^ (Double.doubleToLongBits(this.weightedWeight) >>> 32));
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
        final SampleWell other = (SampleWell) obj;
        if (this.vessel != other.vessel && (this.vessel == null || !this.vessel.equals(other.vessel))) {
            return false;
        }
        if (this.landingDate != other.landingDate && (this.landingDate == null || !this.landingDate.equals(other.landingDate))) {
            return false;
        }
        if (this.sampleNumber != other.sampleNumber) {
            return false;
        }
        if (this.activityDate != other.activityDate && (this.activityDate == null || !this.activityDate.equals(other.activityDate))) {
            return false;
        }
        if (this.activityNumber != other.activityNumber) {
            return false;
        }
        if (this.quadrant != other.quadrant) {
            return false;
        }
        if (this.latitude != other.latitude) {
            return false;
        }
        if (this.longitude != other.longitude) {
            return false;
        }
        if (this.schoolType != other.schoolType && (this.schoolType == null || !this.schoolType.equals(other.schoolType))) {
            return false;
        }
        if (Double.doubleToLongBits(this.weightedWeight) != Double.doubleToLongBits(other.weightedWeight)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SampleWell{" + "vessel=" + vessel + ", landingDate=" + landingDate + ", sampleNumber=" + sampleNumber + ", activityDate=" + activityDate + ", activityNumber=" + activityNumber + ", geo=" + geo + ", quadrant=" + quadrant + ", latitude=" + latitude + ", longitude=" + longitude + ", schoolType=" + schoolType + ", weightedWeight=" + weightedWeight + '}';
    }

}
