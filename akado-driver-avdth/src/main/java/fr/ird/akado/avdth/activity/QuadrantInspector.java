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
package fr.ird.akado.avdth.activity;

import fr.ird.akado.avdth.Constant;
import static fr.ird.akado.avdth.Constant.CODE_ACTIVITY_QUADRANT_INCONSISTENCY;
import static fr.ird.akado.avdth.Constant.CODE_ACTIVITY_QUADRANT_INCONSISTENCY_POSITION;
import static fr.ird.akado.avdth.Constant.LABEL_ACTIVITY_QUADRANT_INCONSISTENCY;
import static fr.ird.akado.avdth.Constant.LABEL_ACTIVITY_QUADRANT_INCONSISTENCY_POSITION;
import fr.ird.akado.avdth.result.ActivityResult;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.core.Inspector;
import fr.ird.common.OTUtils;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.Ocean;
import fr.ird.driver.avdth.common.AvdthUtils;
import java.util.ArrayList;

/**
 * Check if the quadrant and the position activity are consistency.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 26 juin 2014
 */
public class QuadrantInspector extends Inspector<Activity> {

    public QuadrantInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the quadrant and the position activity are consistency.";
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Activity activite = get();
        ActivityResult r;
        ArrayList parameters;
//        System.out.println(description + " " + activite);
        if (activite.getLatitude() == 0 && !(activite.getQuadrant() == 1 || activite.getQuadrant() == 4)) {
            //System.out.println(activite.getCodeOcean());
            parameters = new ArrayList<>();
            parameters.add(activite.getID());
            parameters.add(activite.getQuadrant());

            r = new ActivityResult();
            r.set(activite);
            r.setMessageType(Message.ERROR);
            r.setMessageCode(Constant.CODE_ACTIVITY_QUADRANT_LAT_INCONSISTENCY);
            r.setMessageLabel(Constant.LABEL_ACTIVITY_QUADRANT_LAT_INCONSISTENCY);

            r.setInconsistent(true);
            r.setMessageParameters(parameters);
            results.add(r);
        }
        if (activite.getLongitude() == 0 && !(activite.getQuadrant() == 2 || activite.getQuadrant() == 1)) {
            //System.out.println(activite.getCodeOcean());
            parameters = new ArrayList<>();
            parameters.add(activite.getID());
            parameters.add(activite.getQuadrant());

            r = new ActivityResult();
            r.set(activite);
            r.setMessageType(Message.ERROR);
            r.setMessageCode(Constant.CODE_ACTIVITY_QUADRANT_LON_INCONSISTENCY);
            r.setMessageLabel(Constant.LABEL_ACTIVITY_QUADRANT_LON_INCONSISTENCY);

            r.setInconsistent(true);
            r.setMessageParameters(parameters);
            results.add(r);
        }

        if ((activite.getQuadrant() == 3 || activite.getQuadrant() == 4)
                && activite.getCodeOcean() == Ocean.INDIEN) {
            //System.out.println(activite.getCodeOcean());
            parameters = new ArrayList<>();
            parameters.add(activite.getID());
            parameters.add(activite.getQuadrant());

            r = new ActivityResult();
            r.set(activite);
            r.setMessageType(Message.ERROR);
            r.setMessageCode(CODE_ACTIVITY_QUADRANT_INCONSISTENCY);
            r.setMessageLabel(LABEL_ACTIVITY_QUADRANT_INCONSISTENCY);

            r.setInconsistent(true);
            r.setMessageParameters(parameters);
            results.add(r);

        }

        if ((activite.getQuadrant() == 3 || activite.getQuadrant() == 4)
                && AvdthUtils.getOcean(
                        OTUtils.convertLongitude(activite.getQuadrant(), activite.getLongitude()),
                        OTUtils.convertLatitude(activite.getQuadrant(), activite.getLatitude())) == Ocean.INDIEN) {
            //System.out.println(activite.getCodeOcean());
            parameters = new ArrayList<>();
            parameters.add(activite.getID());
            parameters.add(activite.getQuadrant());

            r = new ActivityResult();
            r.set(activite);
            r.setMessageType(Message.ERROR);
            r.setMessageCode(CODE_ACTIVITY_QUADRANT_INCONSISTENCY_POSITION);
            r.setMessageLabel(LABEL_ACTIVITY_QUADRANT_INCONSISTENCY_POSITION);

            r.setInconsistent(true);
            r.setMessageParameters(parameters);
            results.add(r);
        }
        return results;
    }
}
