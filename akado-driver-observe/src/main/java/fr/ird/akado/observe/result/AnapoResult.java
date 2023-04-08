/*
 * Copyright (C) 2015 Observatoire thonier, IRD
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
package fr.ird.akado.observe.result;

import fr.ird.akado.core.common.MessageDescription;
import fr.ird.akado.observe.inspector.anapo.AnapoInspector;
import fr.ird.akado.observe.result.model.AnapoDataSheet;
import fr.ird.akado.observe.result.object.Anapo;
import fr.ird.common.DateUtils;
import fr.ird.common.MapUtils;
import fr.ird.common.OTUtils;
import fr.ird.common.Utils;
import fr.ird.driver.anapo.business.PosVMS;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Présente les données à afficher pour les analyses des traces VMS des
 * activités.
 * <p>
 * Created on 25/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class AnapoResult extends Result<Anapo> {

    private static final Logger log = LogManager.getLogger(AnapoResult.class);

    public static Map<String, Results> filter(Results anapoResults) {
        Map<String, Results> anapoResultsByCFR = new HashMap<>();
        for (Result<?> r : anapoResults) {
            AnapoResult ar = (AnapoResult) r;
            Anapo anapo = ar.get();
            Results results = anapoResultsByCFR.get(anapo.getCfrVessel());
            if (results == null) {
                results = new Results();
            }
            results.add(ar);
            anapoResultsByCFR.put(anapo.getCfrVessel(), results);
        }
        return anapoResultsByCFR;
    }

    public AnapoResult(Anapo datum, MessageDescription messageDescription) {
        super(datum, messageDescription);
    }

    @Override
    public List<AnapoDataSheet> extractResults() {
        List<AnapoDataSheet> list = new ArrayList<>();
        Anapo anapo = get();
        if (anapo == null) {
            return list;
        }
        list.addAll(factory(anapo));
        return list;
    }

    @Override
    public String toString() {
        return "AnapoResult{" + "cfrVessel=" + get().getCfrVessel() + '}';
    }

    public List<AnapoDataSheet> factory(Anapo anapo) {
        log.debug(" # 1 #");
        List<AnapoDataSheet> list = new ArrayList<>();

        Activity activity = anapo.getActivity();

        HashMap<PosVMS, Double> dist = new HashMap<>();
        int vesselCode = -1;
        String engine = "?";
        String landingDate = "?";
        String activityDate = "?";
        if (activity == null) {
            vesselCode = anapo.getVessel().getCodeAsInt();
            PosVMS pvms = anapo.getPosVMS();
            String vmsPositionDate = pvms.getDate().toString(DateTimeFormat.forPattern("dd/MM/yyyy HH:MM"));
            Double distancePosition = 0d;
            Double vmsScore = 0d;
            String vmsLatLong = pvms.getLatitude() + "/" + pvms.getLongitude();
            int vmsQuadrant = OTUtils.getQuadrant(pvms.getLatitude(), pvms.getLongitude());
            String vmsLatLongDegMin = pvms.getLatitudeDegMin() + "/" + pvms.getLongitudeDegMin();
            int vmsDirection = pvms.getDirection();
            double vmsSpeed = pvms.getSpeed();
            int vmsOcean = pvms.getOcean();

            AnapoDataSheet anapoDataSheet = new AnapoDataSheet(vesselCode, engine, landingDate, activityDate, "?", 0, 0, 0, "?", "?", 0, vmsPositionDate, distancePosition, vmsScore, vmsQuadrant, vmsLatLong, vmsLatLongDegMin, vmsDirection, vmsSpeed, vmsOcean);
            list.add(anapoDataSheet);
            return list;
        }

        if (activity.getVessel() != null) {
            vesselCode = activity.getVessel().getCodeAsInt();
            engine = activity.getVessel().getVesselType().getLabel2();
        }
        if (activity.getLandingDate() != null) {
            landingDate = DateUtils.formatDate(activity.getLandingDate());
        }
        if (activity.getFullDate() != null) {
            activityDate = DateUtils.formatDateAndTime(activity.getFullDate());
        }
        String activityHour = activity.withoutTime() ? "Heure non définie" : DateUtils.formatTime(activity.getTime());

        int activityNumber = activity.getNumber();

        int activityOperation = -1;
        if (activity.getVesselActivity() != null) {
            activityOperation = activity.getVesselActivity().getCodeAsInt();
        }
        //NPE if no coordinate
        boolean withoutCoordinate = activity.withoutCoordinate();
        int activityQuadrant;
        String activityLagLong;
        String activityLagLongDegMin;
        if (    withoutCoordinate) {
            activityQuadrant = 0;
            activityLagLong = "na";
            activityLagLongDegMin = "na";
        } else {
            activityQuadrant = activity.getQuadrant();
            activityLagLong = activity.getLatitude() + "/" + activity.getLongitude();
            activityLagLongDegMin = OTUtils.degreesDecimalToStringDegreesMinutes(activity.getLatitude(), true)
                    + "/" + OTUtils.degreesDecimalToStringDegreesMinutes(activity.getLongitude(), false);
        }
        int vmsPositionCount = anapo.getVMSPositionCount();
        if (activity.getVessel() != null && activity.getLandingDate() != null && anapo.getPositions().isEmpty() && vmsPositionCount >= AnapoInspector.NB_POSITIONS_VMS_MIN) {
            AnapoDataSheet anapoDataSheet = new AnapoDataSheet(vesselCode, engine, landingDate, activityDate, activityHour, activityNumber, activityOperation, activityQuadrant, activityLagLong, activityLagLongDegMin, vmsPositionCount);
            list.add(anapoDataSheet);
            return list;
        }

        log.debug(" # 2 #");
        for (Map.Entry<PosVMS, Double> entry : anapo.getPositions().entrySet()) {

            PosVMS pvms = entry.getKey();
            log.debug(" # PosVMS :" + pvms);
            dist.put(pvms, Utils.round(AnapoInspector.calculateDistanceProximity(pvms, activity), 3));
        }
        log.debug(" # 3 #");
        for (Map.Entry<PosVMS, Double> entry : MapUtils.sortByValue(dist).entrySet()) {
            PosVMS pvms = entry.getKey();

            log.debug(" # PosVMS :" + pvms);
            String vmsPositionDate = pvms.getDate().toString(DateTimeFormat.forPattern("dd/MM/yyyy HH:MM"));
            Double distancePosition = Utils.round(AnapoInspector.calculateDistanceProximity(pvms, activity), 3);
            Double vmsScore = Utils.round(AnapoInspector.calculateScore(pvms,  activity), 3);
            String vmsLatLong = pvms.getLatitude() + "/" + pvms.getLongitude();
            int vmsQuadrant = OTUtils.getQuadrant(pvms.getLatitude(), pvms.getLongitude());
            String vmsLatLongDegMin = pvms.getLatitudeDegMin() + "/" + pvms.getLongitudeDegMin();
            int vmsDirection = pvms.getDirection();
            double vmsSpeed = pvms.getSpeed();
            int vmsOcean = pvms.getOcean();

            AnapoDataSheet anapoDataSheet = new AnapoDataSheet(vesselCode, engine, landingDate, activityDate, activityHour, activityNumber, activityOperation, activityQuadrant, activityLagLong, activityLagLongDegMin, vmsPositionCount, vmsPositionDate, distancePosition, vmsScore, vmsQuadrant, vmsLatLong, vmsLatLongDegMin, vmsDirection, vmsSpeed, vmsOcean);
            list.add(anapoDataSheet);
            return list;
        }
        return list;
    }
}
