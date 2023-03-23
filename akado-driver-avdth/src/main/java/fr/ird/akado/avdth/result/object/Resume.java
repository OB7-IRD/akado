/*
 * Copyright (C) 2014 Observatoire thonier, IRD
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
package fr.ird.akado.avdth.result.object;

import fr.ird.akado.core.common.AkadoResume;
import org.joda.time.DateTime;

/**
 * The class represents the properties of an analysis: trip and activity count,
 * error count,...
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 18 juil. 2014
 */
public class Resume implements AkadoResume {

    private String databaseName;
    private Integer tripCount;
    private DateTime firstDateOfTrip;
    private DateTime lastDateOfTrip;

    private Integer activityCount;
    private DateTime firstDateOfActivity;
    private DateTime lastDateOfActivity;

    private Integer sampleCount;
    private Integer wellCount;

    public Integer getTripCount() {
        return tripCount;
    }

    public void setTripCount(Integer tripCount) {
        this.tripCount = tripCount;
    }

    /**
     *
     * @return the first trip's start date
     */
    public DateTime getFirstDateOfTrip() {
        return firstDateOfTrip;
    }

    /**
     * Set the first trip's start date
     *
     * @param firstDateOfTrip the start date
     */
    public void setFirstDateOfTrip(DateTime firstDateOfTrip) {
        this.firstDateOfTrip = firstDateOfTrip;
    }

    /**
     *
     * @return the last trip's end date
     */
    public DateTime getLastDateOfTrip() {
        return lastDateOfTrip;
    }

    /**
     * Set the last trip's end date
     *
     * @param lastDateOfTrip the end date
     */
    public void setLastDateOfTrip(DateTime lastDateOfTrip) {
        this.lastDateOfTrip = lastDateOfTrip;
    }

    /**
     *
     * @return the number of activity
     */
    public Integer getActivityCount() {
        return activityCount;
    }

    /**
     * Set the number of activity
     *
     * @param activityCount the number of activity
     */
    public void setActivityCount(Integer activityCount) {
        this.activityCount = activityCount;
    }

    public DateTime getFirstDateOfActivity() {
        return firstDateOfActivity;
    }

    public void setFirstDateOfActivity(DateTime firstDateOfActivity) {
        this.firstDateOfActivity = firstDateOfActivity;
    }

    public DateTime getLastDateOfActivity() {
        return lastDateOfActivity;
    }

    public void setLastDateOfActivity(DateTime lastDateOfActivity) {
        this.lastDateOfActivity = lastDateOfActivity;
    }

    public Integer getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(Integer sampleCount) {
        this.sampleCount = sampleCount;
    }

    public Integer getWellCount() {
        return wellCount;
    }

    public void setWellCount(Integer wellCount) {
        this.wellCount = wellCount;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public String toString() {
        return "Resume{" + "databaseName=" + databaseName + ", tripCount=" + tripCount + ", firstDateOfTrip=" + firstDateOfTrip + ", lastDateOfTrip=" + lastDateOfTrip + ", activityCount=" + activityCount + ", firstDateOfActivity=" + firstDateOfActivity + ", lastDateOfActivity=" + lastDateOfActivity + ", sampleCount=" + sampleCount + ", wellCount=" + wellCount + '}';
    }

}
