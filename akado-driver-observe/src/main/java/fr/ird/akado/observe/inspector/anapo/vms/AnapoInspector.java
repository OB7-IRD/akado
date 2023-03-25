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
package fr.ird.akado.observe.inspector.anapo.vms;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.result.Results;

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

    public static final String OK = "ok";
    public static final String WARNING = "warning";
    public static final String ERROR = "error";
    public static final int CONVERT_DIST_MILES = 3431;
    public static int NB_POSITIONS_VMS_MIN = 20;

    public AnapoInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if an activity is consistent with the VMS trace.";
    }

//    public static double calculateScore(PosVMS pvms, Activity a) {
//        double distance = calculateDistanceProximity(pvms, a);
//        double temporal = calculateTemporalProximity(pvms, a);
//        return scoring(distance, THRESHOLD_CLASS_TWO, THRESHOLD_CLASS_TWO * 2)
//                * scoring(temporal, DateTimeUtils.convertHoursInMillis(2), DateTimeUtils.convertHoursInMillis(4));
//    }

    @Override
    public Results execute() {
        Results results = new Results();
//        LogService.getService(AnapoInspector.class).logApplicationDebug(name + " " + description);
//
//        if (AAProperties.ANAPO_INSPECTOR != null
//                && AAProperties.ANAPO_INSPECTOR.equals(AAProperties.DISABLE_VALUE)) {
//            return results;
//        }
//
//        Activity a = get();
//
////        for (Activity a : activities) {
////            LogService.getService(AnapoInspector.class).logApplicationDebug(a.toString());
//        Boolean analyseVessel = false;
//        for (String s : AAProperties.ANAPO_VMS_COUNTRY.split("\\s*\\|\\s*")) {
//            analyseVessel |= s.equals("" + a.getVessel().getCountry().getCode());
//        }
//        if (!analyseVessel) {
//            return results;
//        }
//
//        AnapoResult r;
//        Anapo anapo;
//
//        PosVMSDAO dao = new PosVMSDAO();
//        List<PosVMS> positions = dao.findAllPositions(a.getVessel().getCode(), a.getDate());
////        for (PosVMS pvms : positions) {
//////                LogService.getService(AnapoInspector.class).logApplicationDebug("---> " + pvms);
////        }
//        if (positions.isEmpty()) {
//            anapo = new Anapo(a);
//            r = new AnapoResult();
//            r.set(anapo);
//            r.setMessageType(Message.INFO);
//            r.setMessageCode(CODE_ACTIVITY_NO_TRACE_VMS);
//            r.setMessageLabel(LABEL_ACTIVITY_NO_TRACE_VMS);
//
//            ArrayList parameters = new ArrayList<>();
//            parameters.add(a.getID());
//            r.setMessageParameters(parameters);
//            results.add(r);
//            return results;
//        }
//        LogService.getService(AnapoInspector.class).logApplicationDebug("[" + a + "]");
//        LogService.getService(AnapoInspector.class).logApplicationDebug("Nb positions : " + positions.size());
//        if (positions.size() < NB_POSITIONS_VMS_MIN) {
//            anapo = new Anapo(a);
//            anapo.setVmsPositionCount(positions.size());
//            r = new AnapoResult();
//            r.set(anapo);
//            r.setMessageType(Message.INFO);
//            r.setMessageCode(CODE_INCONSISTENCY_VMS_POSITION_COUNT);
//            r.setMessageLabel(LABEL_INCONSISTENCY_VMS_POSITION_COUNT);
//
//            ArrayList parameters = new ArrayList<>();
//            parameters.add(a.getID());
//            parameters.add(anapo.getVMSPositionCount());
//            r.setMessageParameters(parameters);
//            results.add(r);
//        }
//
//        Map<PosVMS, Double> scores = new HashMap<>();
//        boolean foundValidPosition = false;
//        for (PosVMS pvms : positions) {
//            if (!PositionInspector.inHarbour(pvms.getLongitude(), pvms.getLatitude())) {
//                double distance = calculateDistanceProximity(pvms, a);
//
//                if (distance < THRESHOLD_CLASS_TWO) {
//                    return results;
//                }
//                double temporal = calculateTemporalProximity(pvms, a);
//                double score = scoring(distance, THRESHOLD_CLASS_TWO, THRESHOLD_CLASS_TWO * 2)
//                        * scoring(temporal, DateTimeUtils.convertHoursInMillis(2), DateTimeUtils.convertHoursInMillis(4));
//
////                LogService.getService().logApplicationInfo("[" + a.getID() + "]  distance :" + Utils.round(distance, 2) + " valid " + (distance < THRESHOLD_CLASS_TWO));
////
////                String activitylagLongDegMin = OTUtils.degreesDecimalToStringDegreesMinutes(OTUtils.convertLatitude(a.getQuadrant(), a.getLatitude()), true)
////                        + "/" + OTUtils.degreesDecimalToStringDegreesMinutes(OTUtils.convertLongitude(a.getQuadrant(), a.getLongitude()), false);
////
////                String vmsLatLongDegMin = pvms.getLatitudeDegMin() + "/" + pvms.getLongitudeDegMin();
////
////                LogService.getService().logApplicationInfo("A:[" + activitylagLongDegMin + "]" + " | VMS:[" + vmsLatLongDegMin + "]");
////                LogService.getService().logApplicationInfo("PVMS:[" + pvms + "]" + " | S:[" + score + "]");
//                scores.put(pvms, score);
//
//            }
//        }
//        if (!foundValidPosition) {
//
//            Map<PosVMS, Double> m = filterPositionValid(scores);
//            LogService.getService(AnapoInspector.class).logApplicationDebug("Position valid is empty " + m.isEmpty());
//            if (m.isEmpty()) {
//                m = filterPositionNotValid(scores);
//                if (!m.isEmpty()) {
//                    anapo = new Anapo(a);
//                    anapo.setVmsPositionCount(positions.size());
//                    anapo.setPositions(m);
//
//                    r = new AnapoResult();
//                    r.set(anapo);
//                    r.setMessageType(Message.WARNING);
//                    r.setMessageCode(Constant.CODE_ACTIVITY_TRACE_VMS_CL2);
//                    r.setMessageLabel(Constant.LABEL_ACTIVITY_TRACE_VMS_CL2);
//
//                    ArrayList parameters = new ArrayList<>();
//                    parameters.add(a.getID());
//                    parameters.add("[" + 0 + "-" + THRESHOLD_CLASS_TWO + "]");
//                    parameters.add(m.size());
//                    r.setMessageParameters(parameters);
//                    results.add(r);
//                } else {
//                    anapo = new Anapo(a);
//                    anapo.setVmsPositionCount(positions.size());
//                    anapo.setPositions(scores);
//
//                    r = new AnapoResult();
//                    r.set(anapo);
//                    r.setMessageType(Message.ERROR);
//                    r.setMessageCode(Constant.CODE_ACTIVITY_TRACE_VMS_NO_MATCH);
//                    r.setMessageLabel(Constant.LABEL_ACTIVITY_TRACE_VMS_NO_MATCH);
//
//                    ArrayList parameters = new ArrayList<>();
//                    parameters.add(a.getID());
//                    r.setMessageParameters(parameters);
//                    results.add(r);
//                }
//            }
//        }

        return results;
    }
//
//    public static double calculateTemporalProximity(PosVMS pvms, Activity a) {
//        if (a.getHour() == 0
//                && a.getMinute() == 0) {
//            return 1d;
//        }
//        return Math.abs(a.getFullDate().getMillis() - pvms.getDate().getMillis());
//    }
//
//    public static double calculateDistanceProximity(PosVMS pvms, Activity a) {
//        double lat1 = OTUtils.convertLatitude(OTUtils.signLatitude(a.getQuadrant(), a.getLatitude()));
//        double lon1 = OTUtils.convertLongitude(OTUtils.signLongitude(a.getQuadrant(), a.getLongitude()));
//        double lat2 = OTUtils.convertLatitude(pvms.getLatitude());
//        double lon2 = OTUtils.convertLongitude(pvms.getLongitude());
//        lat1 = Math.toRadians(lat1);
//        lon1 = Math.toRadians(lon1);
//        lat2 = Math.toRadians(lat2);
//        lon2 = Math.toRadians(lon2);
//        return CONVERT_DIST_MILES * WGS84.distanceVolOiseauEntre2PointsAvecPrecision(lat1, lon1, lat2, lon2);
//    }
//
//    /**
//     * The scoring method is based on an exponential decay.
//     *
//     * @param value
//     * @param halflife
//     * @return
//     */
//    public static double scoring(double value, double halflife) {
//        return Math.pow(2d, -value / halflife);
//    }
//
//    /**
//     * The scoring method is based on an exponential decay.
//     *
//     * @param value
//     * @param halflife
//     * @param endlife
//     * @return
//     */
//    public static double scoring(double value, double halflife, double endlife) {
//        if (value > endlife) {
//            return 0d;
//        }
//        return Math.pow(2d, -value / halflife);
//    }
//
//    private Map filterPositionValid(Map<PosVMS, Double> m) {
//        Map filtered = new HashMap();
//        for (Entry<PosVMS, Double> entry : m.entrySet()) {
//            if (entry.getValue() >= 0.5) {
//                filtered.put(entry.getKey(), entry.getValue());
//            }
//        }
//        return filtered;
//    }
//
//    private Map filterPositionNotValid(Map<PosVMS, Double> m) {
//        Map filtered = new HashMap();
//        for (Entry<PosVMS, Double> entry : m.entrySet()) {
//            if (entry.getValue() != 0d && entry.getValue() < 0.5) {
//                filtered.put(entry.getKey(), entry.getValue());
//            }
//        }
//        return filtered;
//    }

}
