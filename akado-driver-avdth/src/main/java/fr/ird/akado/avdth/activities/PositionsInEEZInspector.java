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
package fr.ird.akado.avdth.activities;

import static fr.ird.akado.avdth.Constant.CODE_ACTIVITY_OPERATION_EEZ_INCONSISTENCY;
import static fr.ird.akado.avdth.Constant.LABEL_ACTIVITY_OPERATION_EEZ_INCONSISTENCY;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.core.spatial.GISHandler;
import fr.ird.akado.avdth.result.ActivityResult;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.core.Inspector;
import fr.ird.common.JDBCUtilities;
import fr.ird.common.OTUtils;
import fr.ird.common.log.LogService;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Activity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Check if the EEZ reported is consistent with the eez calculated from the
 * position activity.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 17 mai 2017
 *
 */
public class PositionsInEEZInspector extends Inspector<List<Activity>> {

    public PositionsInEEZInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the EEZ reported is consistent with the eez calculated from the position activity.";
    }

    public static Map<Activity, Boolean> activityPositionAndEEZInconsistent(List<Activity> l) {
        Map<Activity, Boolean> result = new HashMap<>();
        String multiPoint = "";
        for (Activity a : l) {
            if (!multiPoint.equals("")) {
                multiPoint += ",";
            }
            Double latitude = OTUtils.convertLatitude(a.getQuadrant(), a.getLatitude());
            Double longitude = OTUtils.convertLongitude(a.getQuadrant(), a.getLongitude());
            multiPoint += "(" + longitude + " " + latitude + ")";
        }
        List eezList = getEEZList(multiPoint);
        String eezCountry;
        int eezIndex = 0;
        for (Activity a : l) {
            if ((a.getFpaZone() == null || a.getFpaZone().getCode() == 0 || a.getFpaZone().getCountry() == null)) {
                result.put(a, Boolean.FALSE);
            } else {
                eezCountry = a.getFpaZone().getCountry().getCodeIso3();
                LogService.getService(PositionsInEEZInspector.class).logApplicationDebug("eezCountry " + eezCountry);
                LogService.getService(PositionsInEEZInspector.class).logApplicationDebug("eezFromPosition " + eezList.get(eezIndex));
                result.put(a, (eezCountry != null && eezList.get(eezIndex) != null && !eezCountry.equals(eezList.get(eezIndex))));
            }
            eezIndex += 1;
        }
        return result;
    }

    public static boolean activityPositionAndEEZInconsistent(Activity a) {
        if (a.getFpaZone() == null || a.getFpaZone().getCode() == 0 || a.getFpaZone().getCountry() == null) {
            return false;
        }

        String eezCountry = a.getFpaZone().getCountry().getCodeIso3();
        Double latitude = OTUtils.convertLatitude(a.getQuadrant(), a.getLatitude());
        Double longitude = OTUtils.convertLongitude(a.getQuadrant(), a.getLongitude());
        String eezFromPosition = getEEZ(longitude, latitude);
        LogService
                .getService(PositionsInEEZInspector.class
                ).logApplicationDebug("eezCountry " + eezCountry);
        LogService
                .getService(PositionsInEEZInspector.class
                ).logApplicationDebug("eezFromPosition " + eezFromPosition);
        return (eezCountry != null && eezFromPosition != null && !eezCountry.equals(eezFromPosition));

    }

    @Override
    public Results execute() {
        Results results = new Results();
        List<Activity> l = get();

        ActivityResult r;
        if (AAProperties.WARNING_INSPECTOR.equals(AAProperties.ACTIVE_VALUE)) {
            Map<Activity, Boolean> inconsistentActivities = activityPositionAndEEZInconsistent(l);
            for (Entry<Activity, Boolean> entry : inconsistentActivities.entrySet()) {
                if (entry.getValue()) {
                    Activity a = entry.getKey();

                    r = new ActivityResult();

                    r.set(a);
                    r.setMessageType(Message.WARNING);
                    r.setMessageCode(CODE_ACTIVITY_OPERATION_EEZ_INCONSISTENCY);
                    r.setMessageLabel(LABEL_ACTIVITY_OPERATION_EEZ_INCONSISTENCY);

                    r.setInconsistent(true);

                    ArrayList parameters = new ArrayList<>();
                    parameters.add(a.getID());
                    String operationCode = "-";
                    if (a.getOperation() != null) {
                        operationCode = "" + a.getOperation().getCode();
                    }
                    parameters.add(operationCode);
                    parameters.add(a.getOperationCount());

                    r.setMessageParameters(parameters);
                    results.add(r);

                }
            }
        }
        return results;
    }

    public static List<String> getEEZList(String multipoint) {
//        //System.out.println("IN IO position (long, lat) " + longitude + " , " + latitude);
        List<String> eezList = new ArrayList<>();
        Statement statement = null;
        try {

            statement = GISHandler.getService().getConnection().createStatement();
            String sql = "SELECT * FROM eez e"
                    + " WHERE "
                    + "	ST_Within( "
                    + "         ST_GeomFromText('MULTIPOINT(" + multipoint + ")', 4326 ),"
                    + "		ST_SetSRID(e.the_geom, 4326)"
                    + "	)";
            LogService.getService(PositionsInEEZInspector.class).logApplicationDebug(sql);
            ResultSet rs = statement.executeQuery(sql);
            LogService.getService(PositionsInEEZInspector.class).logApplicationDebug(rs.toString());
            while (rs.next()) {
                LogService.getService(PositionsInEEZInspector.class).logApplicationDebug(rs.getString("TERRITORY1") + " - " + rs.getString("ISO_Ter1"));
                eezList.add(rs.getString("ISO_Ter1"));
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    JDBCUtilities.printSQLException(ex);
                }
            }
        }
        return eezList;
    }

    public static String getEEZ(double longitude, double latitude) {
//        //System.out.println("IN IO position (long, lat) " + longitude + " , " + latitude);
        String eez = "-";
        Statement statement = null;
        try {

            statement = GISHandler.getService().getConnection().createStatement();
            String sql = "SELECT * FROM eez e"
                    + " WHERE "
                    + "	ST_Covers( "
                    + "		ST_SetSRID(e.the_geom, 4326),"
                    + "         ST_GeomFromText('POINT(" + longitude + " " + latitude + ")', 4326 ) "
                    + "	)";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                LogService.getService(PositionsInEEZInspector.class
                ).logApplicationDebug(rs.getString("TERRITORY1") + " - " + rs.getString("ISO_Ter1"));
                eez = rs.getString("ISO_Ter1");
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    JDBCUtilities.printSQLException(ex);
                }
            }
        }
        return eez;
    }
}
