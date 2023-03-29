/*
 *
 * Copyright (C) 2017 Ob7, IRD
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
package fr.ird.akado.observe.inspector.activity;

import com.google.auto.service.AutoService;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.core.spatial.GISHandler;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.common.log.LogService;
import fr.ird.common.message.Message;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;

import java.util.Objects;

import static fr.ird.akado.observe.Constant.CODE_ACTIVITY_POSITION_EEZ_INCONSISTENCY;
import static fr.ird.akado.observe.Constant.LABEL_ACTIVITY_POSITION_EEZ_INCONSISTENCY;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveActivityInspector.class)
public class PositionInEEZInspector extends ObserveActivityInspector {

    public static boolean activityPositionAndEEZInconsistent(Activity a) {
        if (a.getFpaZone() == null || Objects.equals(a.getFpaZone().getCode(), "0") || a.getFpaZone().getCountry() == null) {
            return false;
        }
        String eezCountry = a.getFpaZone().getCountry().getIso3Code();
        Float latitude = a.getLatitude();
        Float longitude = a.getLongitude();
        String eezFromPosition = GISHandler.getService().getEEZ(longitude, latitude);
        LogService.getService(PositionInEEZInspector.class).logApplicationDebug("eezCountry " + eezCountry);
        LogService.getService(PositionInEEZInspector.class).logApplicationDebug("eezFromPosition " + eezFromPosition);
        return eezCountry != null && eezFromPosition != null && !eezCountry.equals(eezFromPosition);
    }

    public PositionInEEZInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the EEZ reported is consistent with the eez calculated from the position activity.";
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();

        Activity a = get();
        if (AAProperties.WARNING_INSPECTOR.equals(AAProperties.ACTIVE_VALUE)) {
            if (activityPositionAndEEZInconsistent(a)) {

                String eez = "-";
                if (a.getFpaZone() != null && a.getFpaZone().getCountry() != null) {
                    eez = "" + a.getFpaZone().getCountry().getIso3Code();
                }
                ActivityResult r = createResult(a, Message.WARNING, CODE_ACTIVITY_POSITION_EEZ_INCONSISTENCY, LABEL_ACTIVITY_POSITION_EEZ_INCONSISTENCY, true,
                                                a.getTopiaId(), a.getLongitude() + " " + a.getLatitude(), eez);
                results.add(r);
            }
        }
        return results;
    }
}
