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
package fr.ird.akado.avdth.activity;

import fr.ird.akado.avdth.result.ActivityResult;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.core.Inspector;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.core.spatial.GISHandler;
import fr.ird.common.OTUtils;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Activity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static fr.ird.akado.avdth.Constant.CODE_ACTIVITY_POSITION_EEZ_INCONSISTENCY;
import static fr.ird.akado.avdth.Constant.LABEL_ACTIVITY_POSITION_EEZ_INCONSISTENCY;

/**
 * Check if the EEZ reported is consistent with the eez calculated from the
 * position activity.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @date 17 mai 2017
 * @since 2.0
 */
public class PositionInEEZInspector extends Inspector<Activity> {
    private static final Logger log = LogManager.getLogger(PositionInEEZInspector.class);
    public PositionInEEZInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the EEZ reported is consistent with the eez calculated from the position activity.";
    }

    public static String computedEEZFromPosition(Activity activity) {
        Double latitude = OTUtils.convertLatitude(activity.getQuadrant(), activity.getLatitude());
        Double longitude = OTUtils.convertLongitude(activity.getQuadrant(), activity.getLongitude());
        return GISHandler.getService().getEEZ(longitude, latitude);
    }

    public static boolean activityPositionAndEEZInconsistent(Activity a) {
        if (a.getFpaZone() == null || a.getFpaZone().getCode() == 0 || a.getFpaZone().getCountry() == null) {
            return false;
        }

        String eezCountry = a.getFpaZone().getCountry().getCodeIso3();
        Double latitude = OTUtils.convertLatitude(a.getQuadrant(), a.getLatitude());
        Double longitude = OTUtils.convertLongitude(a.getQuadrant(), a.getLongitude());
        String eezFromPosition = GISHandler.getService().getEEZ(longitude, latitude);
        log.debug("eezCountry " + eezCountry);
        log.debug("eezFromPosition " + eezFromPosition);
        if (Objects.equals(eezFromPosition, "-") && Objects.equals("XIN", eezCountry)) {
            // This is a normal case
            return false;
        }
        return (eezCountry != null && eezFromPosition != null && !eezCountry.equals(eezFromPosition));

    }

    @Override
    public Results execute() {
        Results results = new Results();
        Activity a = get();
        if (a.getFpaZone() == null || a.getFpaZone().getCode() == 0 || a.getFpaZone().getCountry() == null) {
            return results;
        }
        ActivityResult r;
        if (AAProperties.isWarningInspectorEnabled()) {

            String eezFromPosition = computedEEZFromPosition(a);
            String eezCountry = Objects.requireNonNull(a.getFpaZone()).getCountry().getCodeIso3();
            if (!Objects.equals(eezCountry, eezFromPosition)) {
                if (Objects.equals(eezFromPosition, "-") && Objects.equals("XIN", eezCountry)) {
                    // This is a normal case
                    return results;
                }
                r = new ActivityResult();
                r.set(a);
                r.setMessageType(Message.WARNING);
                r.setMessageCode(CODE_ACTIVITY_POSITION_EEZ_INCONSISTENCY);
                r.setMessageLabel(LABEL_ACTIVITY_POSITION_EEZ_INCONSISTENCY);

                r.setInconsistent(true);

                List<String> parameters = new ArrayList<>();
                parameters.add(a.getID());
                String eez = a.getFpaZone().getCountry().getCodeIso3();

                parameters.add(eez);
                parameters.add(OTUtils.convertLongitude(a.getQuadrant(), a.getLongitude()) + " " + OTUtils.convertLatitude(a.getQuadrant(), a.getLatitude()));
                parameters.add(eezFromPosition);
                r.setMessageParameters(parameters);
                results.add(r);
            }

        }
        return results;
    }
}
