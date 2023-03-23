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
package fr.ird.akado.avdth.sample.well;

import static fr.ird.akado.avdth.Constant.CODE_SAMPLE_WELL_ACTIVITY_INCONSISTENCY;
import static fr.ird.akado.avdth.Constant.LABEL_SAMPLE_WELL_ACTIVITY_INCONSISTENCY;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.result.SampleResult;
import fr.ird.akado.core.Inspector;
import fr.ird.common.DateTimeUtils;
import static fr.ird.common.DateTimeUtils.DATE_FORMATTER;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.Sample;
import fr.ird.driver.avdth.business.SampleWell;
import fr.ird.driver.avdth.business.Trip;
import fr.ird.driver.avdth.dao.TripDAO;
import java.util.ArrayList;

/**
 * Check if the activity information for each sample well is consistent.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 18 juin 2014
 *
 *
 *
 *
 */
public class ActivityConsistentInspector extends Inspector<Sample> {

    public ActivityConsistentInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the activity information for each sample well is consistent.";
    }

    /**
     * Check if the activity information with a sample well is consistent
     *
     * @param trip
     * @param sw
     * @return true if consistent
     */
    public static boolean activityConsistent(Trip trip, SampleWell sw) {
        boolean exist = false;
        if (trip != null) {
            for (Activity a : trip.getActivites()) {
                exist |= DateTimeUtils.dateEqual(sw.getActivityDate(), a.getDate())
                        && sw.getActivityNumber() == a.getNumber()
                        && sw.getLatitude() == a.getLatitude() && sw.getLongitude() == a.getLongitude()
                        && sw.getSchoolType().equals(a.getSchoolType());
            }
        }
        return exist;
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Sample s = get();
        Trip trip = (new TripDAO()).findTripByVesselIdAndDate(s.getVessel(), s.getLandingDate());
        if (TripDAO.exist(trip)) {
            for (SampleWell sw : s.getSampleWells()) {
                boolean exist = activityConsistent(trip, sw);

                if (!exist) {
                    SampleResult r = new SampleResult();
                    r.set(s);
                    r.setMessageType(Message.ERROR);
                    r.setMessageCode(CODE_SAMPLE_WELL_ACTIVITY_INCONSISTENCY);
                    r.setMessageLabel(LABEL_SAMPLE_WELL_ACTIVITY_INCONSISTENCY);

                    ArrayList parameters = new ArrayList<>();
                    parameters.add(s.getID());

                    parameters.add(DATE_FORMATTER.print(sw.getActivityDate()));
                    parameters.add(sw.getActivityNumber());
                    parameters.add(sw.getQuadrant());
                    parameters.add(sw.getLatitude());
                    parameters.add(sw.getLongitude());

                    parameters.add(sw.getSchoolType().getCode());

                    r.setMessageParameters(parameters);
                    r.setInconsistent(true);

                    results.add(r);

                }
            }
        }
        return results;
    }

}
