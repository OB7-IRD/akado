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
 * The class <em>BatVMS</em> represents the table of ANAPO database. It contains
 * the vessel's id, the slot of all positions and the vessel's name.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @version 1.0
 * @since 1.0
 * @date 14 févr. 2014
 */
public class BatVMS {

    /**
     *
     * @param vesselId
     * @param beginningDate
     * @param endingDate
     * @param vesselName
     */
    public BatVMS(int vesselId, DateTime beginningDate, DateTime endingDate, String vesselName) {
        this.vesselId = vesselId;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
        this.vesselName = vesselName;
    }

    /**
     * The vessel's identifier.
     */
    private int vesselId;
    /**
     * The beginning date of the position's emission.
     */
    private DateTime beginningDate;
    /**
     * The ending date of the position's emission.
     */
    private DateTime endingDate;
    /**
     * The longitude of the position's emission.
     */
    private String vesselName;

    public int getVesselId() {
        return vesselId;
    }

    public void setVesselId(int vesselId) {
        this.vesselId = vesselId;
    }

    public DateTime getBeginningDate() {
        return beginningDate;
    }

    public void setBeginningDate(DateTime beginningDate) {
        this.beginningDate = beginningDate;
    }

    public DateTime getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(DateTime endingDate) {
        this.endingDate = endingDate;
    }

    public String getVesselName() {
        return vesselName;
    }

    public void setVesselName(String vesselName) {
        this.vesselName = vesselName;
    }
}
