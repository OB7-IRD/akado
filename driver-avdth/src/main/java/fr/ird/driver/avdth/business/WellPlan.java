/* $Id: WellPlan.java 434 2014-08-01 12:23:01Z lebranch $
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

import static fr.ird.common.DateTimeUtils.DATE_FORMATTER;
import org.joda.time.DateTime;

/**
 * This class name in AVDTH is CuveCalee.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 3.4.5
 * @date 26 mai 2014
 */
public class WellPlan implements Identifier {

    private Vessel vessel;
    private DateTime landingDate;
    private int wellNumber;
    private int wellPosition;
    private int number;
    private Species species;
    private WeightCategoryWellPlan weightCategory;
    private double weight;
    private DateTime activityDate;
    private int activityNumber;

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

    public int getWellNumber() {
        return wellNumber;
    }

    public void setWellNumber(int wellNumber) {
        this.wellNumber = wellNumber;
    }

    public int getWellPosition() {
        return wellPosition;
    }

    public void setWellPosition(int wellPosition) {
        this.wellPosition = wellPosition;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.vessel != null ? this.vessel.hashCode() : 0);
        hash = 41 * hash + (this.landingDate != null ? this.landingDate.hashCode() : 0);
        hash = 41 * hash + this.wellNumber;
        hash = 41 * hash + this.wellPosition;
        hash = 41 * hash + this.number;
        hash = 41 * hash + (this.species != null ? this.species.hashCode() : 0);
        hash = 41 * hash + (this.weightCategory != null ? this.weightCategory.hashCode() : 0);
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.weight) ^ (Double.doubleToLongBits(this.weight) >>> 32));
        hash = 41 * hash + (this.activityDate != null ? this.activityDate.hashCode() : 0);
        hash = 41 * hash + this.activityNumber;
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
        final WellPlan other = (WellPlan) obj;
        if (this.vessel != other.vessel && (this.vessel == null || !this.vessel.equals(other.vessel))) {
            return false;
        }
        if (this.landingDate != other.landingDate && (this.landingDate == null || !this.landingDate.equals(other.landingDate))) {
            return false;
        }
        if (this.wellNumber != other.wellNumber) {
            return false;
        }
        if (this.wellPosition != other.wellPosition) {
            return false;
        }
        if (this.number != other.number) {
            return false;
        }
        if (this.species != other.species && (this.species == null || !this.species.equals(other.species))) {
            return false;
        }
        if (this.weightCategory != other.weightCategory && (this.weightCategory == null || !this.weightCategory.equals(other.weightCategory))) {
            return false;
        }
        if (Double.doubleToLongBits(this.weight) != Double.doubleToLongBits(other.weight)) {
            return false;
        }
        if (this.activityDate != other.activityDate && (this.activityDate == null || !this.activityDate.equals(other.activityDate))) {
            return false;
        }
        if (this.activityNumber != other.activityNumber) {
            return false;
        }
        return true;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public WeightCategoryWellPlan getWeightCategory() {
        return weightCategory;
    }

    public void setWeightCategory(WeightCategoryWellPlan weightCategory) {
        this.weightCategory = weightCategory;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
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

    @Override
    public String getID() {
        return getVessel().getCode() + " " + DATE_FORMATTER.print(landingDate) + " " + wellNumber + " " + wellPosition + " " + this.number;
    }

    @Override
    public String toString() {
        return "WellPlan{" + "vessel=" + vessel + ", landingDate=" + landingDate + ", wellNumber=" + wellNumber + ", wellPosition=" + wellPosition + ", number=" + number + ", species=" + species + ", weightCategory=" + weightCategory + ", weight=" + weight + ", activityDate=" + activityDate + ", activityNumber=" + activityNumber + '}';
    }

}
