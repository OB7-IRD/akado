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
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveActivityInspector.class)
public class PositionInEEZInspector extends ObserveActivityInspector {

    private static final Logger log = LogManager.getLogger(PositionInEEZInspector.class);

    public static boolean activityPositionAndEEZInconsistent(Activity a) {
        String eezCountry = Objects.requireNonNull(a.getFpaZone()).getCountry().getIso3Code();
        Float latitude = Objects.requireNonNull(a.getLatitude());
        Float longitude = Objects.requireNonNull(a.getLongitude());
        String eezFromPosition = GISHandler.getService().getEEZ(longitude, latitude);
        log.debug("eezCountry " + eezCountry);
        log.debug("eezFromPosition " + eezFromPosition);
        return !Objects.equals(eezCountry, eezFromPosition);
    }

    public PositionInEEZInspector() {
        this.description = "Check if the EEZ reported is consistent with the eez calculated from the position activity.";
    }

    @Override
    public Results execute() throws Exception {
        if (!AAProperties.isWarningInspectorEnabled()) {
            return null;
        }
        Activity activity = get();
        if (activity.withoutCoordinate() || activity.withoutFpaZone()) {
            return null;
        }
        if (activityPositionAndEEZInconsistent(activity)) {
            ActivityResult r = createResult(MessageDescriptions.W_1234_ACTIVITY_POSITION_EEZ_INCONSISTENCY, activity,
                                            activity.getID(getTrip(), getRoute()),
                                            activity.getFpaZone().getCountry().getIso3Code(),
                                            activity.getLongitude() + " " + activity.getLatitude());
            return Results.of(r);
        }
        return null;
    }
}
