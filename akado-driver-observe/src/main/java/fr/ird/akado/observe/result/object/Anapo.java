/*
 * Copyright (C) 2015 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.akado.observe.result.object;

import fr.ird.driver.anapo.business.PosVMS;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.referential.common.Vessel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class Anapo {

    private final Activity activity;
    private final Vessel vessel;
    private final String cfrVessel;
    private PosVMS posVMS;
    private Map<PosVMS, Double> positions = new HashMap<>();
    private int vmsPositionCount = 0;

    public Anapo(Vessel vessel) {
        this.activity = null;
        this.vessel = vessel;
        this.cfrVessel = vessel.getCfrId();
    }

    public Anapo(Activity activity, String cfrVessel) {
        this.activity = activity;
        this.vessel = activity.getVessel();
        this.cfrVessel = cfrVessel;
    }

    public Anapo(Activity activity) {
        this(activity, activity.getVessel().getCfrId());
    }

    public String getCfrVessel() {
        return cfrVessel;
    }

    /**
     * @return the activity
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * @return the number of vms position
     */
    public int getVMSPositionCount() {
        return vmsPositionCount;
    }

    public void setVmsPositionCount(int vmsPositionCount) {
        this.vmsPositionCount = vmsPositionCount;
    }

    public Map<PosVMS, Double> getPositions() {
        return positions;
    }

    public void setPositions(Map<PosVMS, Double> positions) {
        this.positions = positions;
    }

    public Vessel getVessel() {
        return vessel;
    }

    public int getVmsPositionCount() {
        return vmsPositionCount;
    }

    public PosVMS getPosVMS() {
        return posVMS;
    }

    public void setPosVMS(PosVMS posVMS) {
        this.posVMS = posVMS;
    }

}
