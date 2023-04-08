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
package fr.ird.akado.observe.inspector.anapo;

import com.google.auto.service.AutoService;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.core.spatial.GISHandler;
import fr.ird.akado.core.spatial.WGS84;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.AnapoResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.object.Anapo;
import fr.ird.common.DateTimeUtils;
import fr.ird.common.OTUtils;
import fr.ird.driver.anapo.business.PosVMS;
import fr.ird.driver.anapo.dao.PosVMSDAO;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.service.ObserveService;
import io.ultreia.java4all.util.Dates;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.ird.akado.core.common.AAProperties.THRESHOLD_CLASS_TWO;

/**
 * The AnapoInspector class check if an activity is consistent with the VMS
 * trace. This inspector implements ANAPO functionalities.
 * <p>
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveAnapoActivityInspector.class)
public class AnapoInspector extends ObserveAnapoActivityInspector {

    private static final Logger log = LogManager.getLogger(AnapoInspector.class);
    public static final String OK = "ok";
    public static final int CONVERT_DIST_MILES = 3431;
    public static int NB_POSITIONS_VMS_MIN = 20;

    public AnapoInspector() {
        this.description = "Check if an activity is consistent with the VMS trace.";
    }

    public static double calculateScore(PosVMS pvms, Activity a) {
        double distance = calculateDistanceProximity(pvms, a);
        double temporal = calculateTemporalProximity(pvms, a);
        return scoring(distance, THRESHOLD_CLASS_TWO, THRESHOLD_CLASS_TWO * 2)
                * scoring(temporal, DateTimeUtils.convertHoursInMillis(2), DateTimeUtils.convertHoursInMillis(4));
    }

    @Override
    public Results execute() {

        Activity activity = get();

        boolean analyseVessel = false;
        for (String s : AAProperties.ANAPO_VMS_COUNTRY.split("\\s*\\|\\s*")) {
            analyseVessel |= s.equals(activity.getVessel().getFleetCountry().getCode());
        }
        if (!analyseVessel) {
            return null;
        }

        PosVMSDAO dao = ObserveService.getService().getDaoSupplier().getVmsDao();
        List<PosVMS> positions = dao.findAllPositions(activity.getVessel().getCodeAsInt(), activity.getDate());
        if (positions.isEmpty()) {
            Anapo anapo = new Anapo(activity);
            AnapoResult r = createResult(MessageDescriptions.I_1221_ACTIVITY_NO_TRACE_VMS, anapo,
                                         activity.getID());
            return Results.of(r);
        }
        Results results = new Results();
        log.debug("[" + activity + "] Nb positions : " + positions.size());
        if (positions.size() < NB_POSITIONS_VMS_MIN) {
            Anapo anapo = new Anapo(activity);
            anapo.setVmsPositionCount(positions.size());
            AnapoResult r = createResult(MessageDescriptions.I_1224_INCONSISTENCY_VMS_POSITION_COUNT, anapo,
                                         activity.getID(),
                                         anapo.getVMSPositionCount()
            );
            results.add(r);
        }

        Map<PosVMS, Double> scores = new HashMap<>();
        for (PosVMS pvms : positions) {
            if (!GISHandler.getService().inHarbour(pvms.getLongitude(), pvms.getLatitude())) {
                double distance = calculateDistanceProximity(pvms, activity);
                if (distance < THRESHOLD_CLASS_TWO) {
                    return results;
                }
                double temporal = calculateTemporalProximity(pvms, activity);
                double score = scoring(distance, THRESHOLD_CLASS_TWO, THRESHOLD_CLASS_TWO * 2)
                        * scoring(temporal, DateTimeUtils.convertHoursInMillis(2), DateTimeUtils.convertHoursInMillis(4));
                scores.put(pvms, score);
            }
        }

        Map<PosVMS, Double> m = filterPositionValid(scores);
        log.debug("Position valid is empty " + m.isEmpty());
        if (!m.isEmpty()) {
            return results;
        }
        m = filterPositionNotValid(scores);
        if (!m.isEmpty()) {
            Anapo anapo = new Anapo(activity);
            anapo.setVmsPositionCount(positions.size());
            anapo.setPositions(m);
            AnapoResult r = createResult(MessageDescriptions.W_1223_ACTIVITY_TRACE_VMS_CL2, anapo,
                                         activity.getID(),
                                         "[" + 0 + "-" + THRESHOLD_CLASS_TWO + "]",
                                         m.size());
            results.add(r);
            return results;
        }

        Anapo anapo = new Anapo(activity);
        anapo.setVmsPositionCount(positions.size());
        anapo.setPositions(scores);

        AnapoResult r = createResult(MessageDescriptions.E_1227_ACTIVITY_TRACE_VMS_NO_MATCH, anapo,
                                     activity.getID());
        results.add(r);
        return results;
    }

    public static double calculateTemporalProximity(PosVMS pvms, Activity a) {
        if (a.withoutTime()) {
            return 1d;
        }
        return Math.abs(Dates.getDateAndTime(a.getDate(), a.getTime(), false, false).getTime() - pvms.getDate().getMillis());
    }

    public static double calculateDistanceProximity(PosVMS pvms, Activity a) {
        double lat1 = a.getLatitude();
        double lon1 = a.getLongitude();
        double lat2 = OTUtils.convertLatitude(pvms.getLatitude());
        double lon2 = OTUtils.convertLongitude(pvms.getLongitude());
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);
        return CONVERT_DIST_MILES * WGS84.distanceVolOiseauEntre2PointsAvecPrecision(lat1, lon1, lat2, lon2);
    }

    /**
     * The scoring method is based on an exponential decay.
     *
     * @param value    ?
     * @param halfLife ?
     * @return ?
     */
    public static double scoring(double value, double halfLife) {
        return Math.pow(2d, -value / halfLife);
    }

    /**
     * The scoring method is based on an exponential decay.
     *
     * @param value    ?
     * @param halfLife ?
     * @param endLife  ?
     * @return ?
     */
    public static double scoring(double value, double halfLife, double endLife) {
        if (value > endLife) {
            return 0d;
        }
        return Math.pow(2d, -value / halfLife);
    }

    private Map<PosVMS, Double> filterPositionValid(Map<PosVMS, Double> m) {
        Map<PosVMS, Double> filtered = new HashMap<>();
        for (Map.Entry<PosVMS, Double> entry : m.entrySet()) {
            if (entry.getValue() >= 0.5) {
                filtered.put(entry.getKey(), entry.getValue());
            }
        }
        return filtered;
    }

    private Map<PosVMS, Double> filterPositionNotValid(Map<PosVMS, Double> m) {
        Map<PosVMS, Double> filtered = new HashMap<>();
        for (Map.Entry<PosVMS, Double> entry : m.entrySet()) {
            if (entry.getValue() != 0d && entry.getValue() < 0.5) {
                filtered.put(entry.getKey(), entry.getValue());
            }
        }
        return filtered;
    }

}
