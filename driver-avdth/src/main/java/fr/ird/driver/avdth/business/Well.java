/* $Id: Well.java 434 2014-08-01 12:23:01Z lebranch $
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
import java.util.List;
import org.joda.time.DateTime;

/**
 * This class name in AVDTH is Cuve.
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 3.4.5
 * @date 26 mai 2014
 *
 */
public class Well implements Identifier {

    private Vessel vessel;
    private DateTime landingDate;
    private int number;
    private int position;
    private WellDestiny destiny;
    private List<WellPlan> wellPlans;

    public List<WellPlan> getWellPlans() {
        return wellPlans;
    }

    public void setWellPlans(List<WellPlan> wellPlans) {
        this.wellPlans = wellPlans;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public WellDestiny getDestiny() {
        return destiny;
    }

    public void setDestiny(WellDestiny destiny) {
        this.destiny = destiny;
    }

    @Override
    public String getID() {
        return getVessel().getCode() + " " + DATE_FORMATTER.print(landingDate) + " " + number + " " + position;
    }

    @Override
    public String toString() {
        return "Well{" + "vessel=" + vessel + ", landingDate=" + landingDate + ", number=" + number + ", position=" + position + ", destiny=" + destiny + ", wellPlans=" + wellPlans + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.vessel != null ? this.vessel.hashCode() : 0);
        hash = 97 * hash + (this.landingDate != null ? this.landingDate.hashCode() : 0);
        hash = 97 * hash + this.number;
        hash = 97 * hash + this.position;
        hash = 97 * hash + (this.destiny != null ? this.destiny.hashCode() : 0);
        hash = 97 * hash + (this.wellPlans != null ? this.wellPlans.hashCode() : 0);
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
        final Well other = (Well) obj;
        if (this.vessel != other.vessel && (this.vessel == null || !this.vessel.equals(other.vessel))) {
            return false;
        }
        if (this.landingDate != other.landingDate && (this.landingDate == null || !this.landingDate.equals(other.landingDate))) {
            return false;
        }
        if (this.number != other.number) {
            return false;
        }
        if (this.position != other.position) {
            return false;
        }
        if (this.destiny != other.destiny && (this.destiny == null || !this.destiny.equals(other.destiny))) {
            return false;
        }
        if (this.wellPlans != other.wellPlans && (this.wellPlans == null || !this.wellPlans.equals(other.wellPlans))) {
            return false;
        }
        return true;
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

}
