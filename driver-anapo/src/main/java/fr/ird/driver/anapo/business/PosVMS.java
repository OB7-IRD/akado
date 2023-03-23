/**
 * Copyright (C) 2014 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.driver.anapo.business;

import org.joda.time.DateTime;

/**
 * The class <em>PosVMS</em> represents the table of ANAPO database, including
 * the vessel's id, the vessel's position, the ocean and the date.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @version 1.0
 * @since 1.0
 * @date 14 févr. 2014
 */
public class PosVMS {

    /**
     *
     * @param vesselId
     * @param date
     * @param ocean
     * @param latitude
     * @param longitude
     */
    public PosVMS(int vesselId, DateTime date, int ocean, int latitude, int longitude) {
        this.vesselId = vesselId;
        this.date = date;
        this.longitude = longitude;
        this.latitude = latitude;
        this.ocean = ocean;
    }

    /**
     * The vessel's identifier.
     */
    private int vesselId;
    /**
     * The CFR vessel's identifier.
     */
    private String cfrId;
    /**
     * The vessel's name.
     */
    private String vesselName;
    /**
     * The date of the position's emission.
     */
    private DateTime date;
    /**
     * The longitude of the position's emission.
     */
    private int longitude;
    /**
     * The latitude of the position's emission.
     */
    private int latitude;
    /**
     * The longitude of the position's emission in Degre Minute.
     */
    private String longitudeDegMin;
    /**
     * The latitude of the position's emission in Degre Minute.
     */
    private String latitudeDegMin;

    /**
     * The direction of the wessel.
     */
    private int direction;

    /**
     * The speed of the wessel.
     */
    private double speed;

    /**
     * The ocean where the position is emitted.
     */
    private int ocean;

    public int getVesselId() {
        return vesselId;
    }

    public void setVesselId(int vesselId) {
        this.vesselId = vesselId;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getOcean() {
        return ocean;
    }

    public void setOcean(int ocean) {
        this.ocean = ocean;
    }

    public String getLongitudeDegMin() {
        return longitudeDegMin;
    }

    public void setLongitudeDegMin(String longitudeDegMin) {
        this.longitudeDegMin = longitudeDegMin;
    }

    public String getLatitudeDegMin() {
        return latitudeDegMin;
    }

    public void setLatitudeDegMin(String latitudeDegMin) {
        this.latitudeDegMin = latitudeDegMin;
    }

    @Override
    public String toString() {
        return "PosVMS{" + "vesselId=" + vesselId + ", cfrId=" + cfrId + ", vesselName=" + vesselName + ", date=" + date + ", longitude=" + longitude + ", latitude=" + latitude + ", longitudeDegMin=" + longitudeDegMin + ", latitudeDegMin=" + latitudeDegMin + ", direction=" + direction + ", speed=" + speed + ", ocean=" + ocean + '}';
    }


    public String getCfrId() {
        return cfrId;
    }

    public void setCfrId(String cfrId) {
        this.cfrId = cfrId;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

}
