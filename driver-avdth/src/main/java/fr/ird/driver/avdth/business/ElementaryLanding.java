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

import org.joda.time.DateTime;

/**
 * Defines the Class ElementaryLanding. This class name in AVDTH is
 * LotCommercial.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @version 3.4.4
 * @date 11 déc. 2013
 */
public class ElementaryLanding implements Identifier {

    private Vessel vessel;
    private DateTime landingDate;
    private long index;
    private WeightCategoryLanding weightCategoryLanding;
    private LandingDestiny landingDestiny;
    private double weightLanding;


    public ElementaryLanding(Trip trip, int index) {
        this(trip.getVessel(), trip.getLandingDate(), index);
    }

    public ElementaryLanding(Vessel vessel, DateTime landingDate, long index, WeightCategoryLanding weightCategoryLanding, double weightLanding) {
        this.vessel = vessel;
        this.landingDate = landingDate;
        this.index = index;
        this.weightCategoryLanding = weightCategoryLanding;
        this.weightLanding = weightLanding;
    }

    public ElementaryLanding(Vessel vessel, DateTime landingDate, long index, WeightCategoryLanding weightCategoryLanding) {
        this(vessel, landingDate, index, weightCategoryLanding, 0d);
    }

    public ElementaryLanding(Vessel vessel, DateTime landingDate, long index) {
        this(vessel, landingDate, index, null, 0d);
    }

    public ElementaryLanding() {
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public WeightCategoryLanding getWeightCategoryLanding() {
        return weightCategoryLanding;
    }

    public void setWeightCategoryLanding(WeightCategoryLanding weightCategoryLanding) {
        this.weightCategoryLanding = weightCategoryLanding;
    }

    public double getWeightLanding() {
        return weightLanding;
    }

    public void setWeightLanding(double weightLanding) {
        this.weightLanding = weightLanding;
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

    public void setLandingDestiny(LandingDestiny landingDestiny) {
        this.landingDestiny = landingDestiny;
    }

    public LandingDestiny getLandingDestiny() {
        return landingDestiny;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.vessel != null ? this.vessel.hashCode() : 0);
        hash = 59 * hash + (this.landingDate != null ? this.landingDate.hashCode() : 0);
        hash = 59 * hash + (int) (this.index ^ (this.index >>> 32));
        hash = 59 * hash + (this.weightCategoryLanding != null ? this.weightCategoryLanding.hashCode() : 0);
        hash = 59 * hash + (this.landingDestiny != null ? this.landingDestiny.hashCode() : 0);
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.weightLanding) ^ (Double.doubleToLongBits(this.weightLanding) >>> 32));
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
        final ElementaryLanding other = (ElementaryLanding) obj;
        if (this.vessel != other.vessel && (this.vessel == null || !this.vessel.equals(other.vessel))) {
            return false;
        }
        if (this.landingDate != other.landingDate && (this.landingDate == null || !this.landingDate.equals(other.landingDate))) {
            return false;
        }
        if (this.index != other.index) {
            return false;
        }
        if (this.weightCategoryLanding != other.weightCategoryLanding && (this.weightCategoryLanding == null || !this.weightCategoryLanding.equals(other.weightCategoryLanding))) {
            return false;
        }
        if (this.landingDestiny != other.landingDestiny && (this.landingDestiny == null || !this.landingDestiny.equals(other.landingDestiny))) {
            return false;
        }
        if (Double.doubleToLongBits(this.weightLanding) != Double.doubleToLongBits(other.weightLanding)) {
            return false;
        }
        return true;
    }

    @Override
    public String getID() {
        return vessel + " " + landingDate + " " + index;
    }

}
