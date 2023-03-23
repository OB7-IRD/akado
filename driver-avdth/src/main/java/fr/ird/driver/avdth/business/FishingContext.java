/* 
 * Copyright (C) 2013 Julien Lebranchu <julien.lebranchu@ird.fr>
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

import static fr.ird.common.DateTimeUtils.DATE_FORMATTER;
import org.joda.time.DateTime;

/**
 * Defines the Class FishingContext. This class name in AVDTH is
 * ActiviteAssociation.
 *
 */
public class FishingContext implements Identifier {

    private Vessel vessel;
    private DateTime landingDate;
    private DateTime activityDate;
    private int activityNumber;
    private long index;
    private FishingContextType fishingContextType;

    public FishingContext(Vessel vessel, DateTime landingDate, DateTime activityDate, int activityNumber, Integer index) {
        this.vessel = vessel;
        this.landingDate = landingDate;
        this.activityDate = activityDate;
        this.activityNumber = activityNumber;
        this.index = index;
    }

    /**
     * Constructor which copies the vessel, the landing date, the activity date
     * and the activity number.
     *
     * @param activity
     * @param index
     */
    public FishingContext(Activity activity, Integer index) {
        this(activity.getVessel(), activity.getLandingDate(), activity.getDate(), activity.getNumber(), index);
    }

    public FishingContext() {
    }

    /**
     * @return
     */
    public FishingContextType getFishingContextType() {
        return fishingContextType;
    }

    public void setFishingContextType(FishingContextType fishingContextType) {
        this.fishingContextType = fishingContextType;
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

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "FishingContext{" + "vessel=" + vessel + ", landingDate=" + landingDate + ", activityDate=" + activityDate + ", activityNumber=" + activityNumber + ", indexAssociation=" + index + ", fishingContextType=" + fishingContextType + '}';
    }

    @Override
    public String getID() {
        return String.format("%s %s %s %s %s", getVessel().getCode(),
                DATE_FORMATTER.print(landingDate),
                DATE_FORMATTER.print(this.activityNumber), index);
    }

}
