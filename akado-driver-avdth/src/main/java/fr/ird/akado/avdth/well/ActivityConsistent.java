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
package fr.ird.akado.avdth.well;

import static fr.ird.akado.avdth.Constant.CODE_WELL_PLAN_ACTIVITY_INCONSISTENCY;
import static fr.ird.akado.avdth.Constant.LABEL_WELL_PLAN_ACTIVITY_INCONSISTENCY;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.result.WellResult;
import fr.ird.akado.core.Inspector;
import fr.ird.common.DateTimeUtils;
import static fr.ird.common.DateTimeUtils.DATE_FORMATTER;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.Trip;
import fr.ird.driver.avdth.business.Well;
import fr.ird.driver.avdth.business.WellPlan;
import fr.ird.driver.avdth.dao.TripDAO;
import java.util.ArrayList;

/**
 * Check if the activity information for each well plan is consistent.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 18 juin 2014
 */
public class ActivityConsistent extends Inspector<Well> {

    public ActivityConsistent() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the activity information for each well plan is consistent.";
    }

    /**
     * Check if the activity associate to the well plan exists in the trip.
     *
     * @param wp the well plan to test
     * @return true if the activity exists
     */
    public static boolean activityExists(WellPlan wp) {
        boolean exist = false;
        Trip trip = (new TripDAO()).findTripByVesselIdAndDate(wp.getVessel(), wp.getLandingDate());
        if (trip != null) {
            for (Activity a : trip.getActivites()) {
                exist |= DateTimeUtils.dateEqual(wp.getActivityDate(), a.getDate())
                        && wp.getActivityNumber() == a.getNumber();
            }
        }
        return exist;
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Well w = get();
        WellResult r;

        for (WellPlan wp : w.getWellPlans()) {

            if (!activityExists(wp)) {
                r = new WellResult();
                r.set(w);
                r.setMessageType(Message.ERROR);
                r.setMessageCode(CODE_WELL_PLAN_ACTIVITY_INCONSISTENCY);
                r.setMessageLabel(LABEL_WELL_PLAN_ACTIVITY_INCONSISTENCY);

                ArrayList parameters = new ArrayList<>();
                parameters.add(wp.getID());

                parameters.add(DATE_FORMATTER.print(wp.getActivityDate()));
                parameters.add(wp.getActivityNumber());

                r.setInconsistent(true);
                r.setMessageParameters(parameters);
                results.add(r);
            }
        }

        return results;
    }

}
