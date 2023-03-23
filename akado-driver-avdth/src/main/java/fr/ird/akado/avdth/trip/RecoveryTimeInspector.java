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
package fr.ird.akado.avdth.trip;

import static fr.ird.akado.avdth.Constant.CODE_TRIP_RECOVERY_TIME;
import static fr.ird.akado.avdth.Constant.LABEL_TRIP_RECOVERY_TIME;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.result.TripResult;
import fr.ird.akado.core.Inspector;
import fr.ird.common.DateTimeUtils;
import static fr.ird.common.DateTimeUtils.DATE_FORMATTER;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.Trip;
import java.util.ArrayList;
import org.joda.time.DateTime;

/**
 * Check if activities are continuous during a trip.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 22 mai 2014 *
 */
public class RecoveryTimeInspector extends Inspector<Trip> {

    public RecoveryTimeInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the activities are continuous during a trip.";
    }

    /**
     * Check if the trip's activities are continuous.
     *
     * @param trip the trip
     * @return true if all activites are continuous
     */
    public static boolean isContinuous(Trip trip) {
        Activity lastActivity = null;
        for (Activity activity : trip.getActivites()) {
            if (lastActivity == null || DateTimeUtils.dateEqual(activity.getDate(), lastActivity.getDate())) {
                lastActivity = activity;
                continue;
            }
            if (!DateTimeUtils.dateIsTheNextDay(lastActivity.getDate(), activity.getDate())) {
                return false;
            }
            lastActivity = activity;
        }
        return true;
    }

    /**
     * Check if the trip's activities are continuous.
     *
     * @param trip the trip
     * @return the date before the break
     */
    public static DateTime continuous(Trip trip) {
        Activity lastActivity = null;
        for (Activity activity : trip.getActivites()) {
            if (lastActivity == null || DateTimeUtils.dateEqual(activity.getDate(), lastActivity.getDate())) {
                lastActivity = activity;
                continue;
            }
            if (!DateTimeUtils.dateIsTheNextDay(lastActivity.getDate(), activity.getDate())) {
                return lastActivity.getDate();
            }
            lastActivity = activity;
        }
        return null;
    }

//    public static List<Activity> continuous(Trip trip) {
//        List<Activity> notContinuous = new ArrayList<>();
//        Activity lastActivity = null;
//        for (Activity activity : trip.getActivites()) {
//            if (lastActivity == null || DateTimeUtils.dateEqual(activity.getDate(), lastActivity.getDate())) {
//                lastActivity = activity;
//                continue;
//            }
//            if (!DateTimeUtils.dateIsTheNextDay(lastActivity.getDate(), activity.getDate())) {
//                notContinuous.add(activity);
//            }
//            lastActivity = activity;
//        }
//        return notContinuous;
//    }
    @Override
    public Results execute() {
        Results results = new Results();
        Trip trip = get();
        if (!trip.getActivites().isEmpty()) {
            Activity lastActivity = null;
            for (Activity activity : trip.getActivites()) {
                if (lastActivity == null || DateTimeUtils.dateEqual(activity.getDate(), lastActivity.getDate())) {
                    lastActivity = activity;
                    continue;
                }
                if (!DateTimeUtils.dateIsTheNextDay(lastActivity.getDate(), activity.getDate())) {
                    TripResult r = new TripResult();
                    r.set(trip);
                    r.setMessageType(Message.ERROR);
                    r.setMessageCode(CODE_TRIP_RECOVERY_TIME);
                    r.setMessageLabel(LABEL_TRIP_RECOVERY_TIME);
                    r.setInconsistent(true);

                    ArrayList parameters = new ArrayList<>();
                    parameters.add(trip.getID());
                    parameters.add(DATE_FORMATTER.print(lastActivity.getDate()));
                    parameters.add(DATE_FORMATTER.print(activity.getDate()));

                    r.setMessageParameters(parameters);
                    results.add(r);

                }
                lastActivity = activity;
            }

        }

        return results;
    }

}
