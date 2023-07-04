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

import static fr.ird.akado.avdth.Constant.CODE_SAMPLE_WELL_POSITION_ACTIVITY_INCONSISTENCY_LATITUDE;
import static fr.ird.akado.avdth.Constant.CODE_SAMPLE_WELL_POSITION_ACTIVITY_INCONSISTENCY_LONGITUDE;
import static fr.ird.akado.avdth.Constant.CODE_SAMPLE_WELL_POSITION_ACTIVITY_INCONSISTENCY_QUADRANT;
import static fr.ird.akado.avdth.Constant.LABEL_SAMPLE_WELL_POSITION_ACTIVITY_INCONSISTENCY_LATITUDE;
import static fr.ird.akado.avdth.Constant.LABEL_SAMPLE_WELL_POSITION_ACTIVITY_INCONSISTENCY_LONGITUDE;
import static fr.ird.akado.avdth.Constant.LABEL_SAMPLE_WELL_POSITION_ACTIVITY_INCONSISTENCY_QUADRANT;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.result.SampleResult;
import fr.ird.akado.core.Inspector;
import fr.ird.common.DateTimeUtils;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.Sample;
import fr.ird.driver.avdth.business.SampleWell;
import fr.ird.driver.avdth.business.Trip;
import fr.ird.driver.avdth.dao.TripDAO;
import java.util.ArrayList;

/**
 * Check if the position activity for each sample well is consistent.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 18 juin 2014
 *
 */
public class PositionActivityConsistentInspector extends Inspector<Sample> {

    public PositionActivityConsistentInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the position activity for each sample well is consistent.";
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Sample s = get();
        Trip trip = (new TripDAO()).findTripByVesselIdAndDate(s.getVessel(), s.getLandingDate());

        ArrayList parameters;
        SampleResult r;
        if (TripDAO.exist(trip)) {
            for (SampleWell sw : s.getSampleWells()) {

                for (Activity a : trip.getActivites()) {
                    if (DateTimeUtils.dateEqual(sw.getActivityDate(), a.getDate())
                            && sw.getActivityNumber() == a.getNumber()) {

                        if (a.getQuadrant() != sw.getQuadrant()) {
                            r = new SampleResult();
                            r.set(s);
                            r.setMessageType(Message.ERROR);
                            r.setMessageCode(CODE_SAMPLE_WELL_POSITION_ACTIVITY_INCONSISTENCY_QUADRANT);
                            r.setMessageLabel(LABEL_SAMPLE_WELL_POSITION_ACTIVITY_INCONSISTENCY_QUADRANT);

                            r.setValueObtained(sw.getQuadrant());
                            r.setValueExpected(a.getQuadrant());

                            parameters = new ArrayList<>();
                            parameters.add(s.getID());
//                            parameters.add(DATE_FORMATTER.print(sw.getActivityDate()));
//                            parameters.add(sw.getActivityNumber());

                            parameters.add(sw.getQuadrant());
                            parameters.add(a.getQuadrant());
                            r.setMessageParameters(parameters);
                            results.add(r);
                        }
                        if (a.getLatitude() != sw.getLatitude()) {
                            r = new SampleResult();
                            r.set(s);
                            r.setMessageType(Message.ERROR);
                            r.setMessageCode(CODE_SAMPLE_WELL_POSITION_ACTIVITY_INCONSISTENCY_LATITUDE);
                            r.setMessageLabel(LABEL_SAMPLE_WELL_POSITION_ACTIVITY_INCONSISTENCY_LATITUDE);
                            r.setValueObtained(sw.getLatitude());
                            r.setValueExpected(a.getLatitude());

                            parameters = new ArrayList<>();
                            parameters.add(s.getID());
//                            parameters.add(DATE_FORMATTER.print(sw.getActivityDate()));
//                            parameters.add(sw.getActivityNumber());

                            parameters.add(sw.getLatitude());
                            parameters.add(a.getLatitude());
                            r.setMessageParameters(parameters);
                            results.add(r);
                        }

                        if (a.getLongitude() != sw.getLongitude()) {
                            r = new SampleResult();
                            r.set(s);
                            r.setMessageType(Message.ERROR);
                            r.setMessageCode(CODE_SAMPLE_WELL_POSITION_ACTIVITY_INCONSISTENCY_LONGITUDE);
                            r.setMessageLabel(LABEL_SAMPLE_WELL_POSITION_ACTIVITY_INCONSISTENCY_LONGITUDE);

                            r.setValueObtained(sw.getLongitude());
                            r.setValueExpected(a.getLongitude());

                            parameters = new ArrayList<>();
                            parameters.add(s.getID());
//                            parameters.add(DATE_FORMATTER.print(sw.getActivityDate()));
//                            parameters.add(sw.getActivityNumber());

                            parameters.add(sw.getLongitude());
                            parameters.add(a.getLongitude());
                            r.setMessageParameters(parameters);
                            results.add(r);
                        }
                    }
                }

            }
        }
        return results;
    }

}
