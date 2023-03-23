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

import static fr.ird.akado.avdth.Constant.CODE_ACTIVITY_OCEAN_INCONSISTENCY;
import static fr.ird.akado.avdth.Constant.CODE_ACTIVITY_POSITION_NOT_IN_OCEAN;
import static fr.ird.akado.avdth.Constant.CODE_ACTIVITY_POSITION_WEIRD;
import static fr.ird.akado.avdth.Constant.LABEL_ACTIVITY_OCEAN_INCONSISTENCY;
import static fr.ird.akado.avdth.Constant.LABEL_ACTIVITY_POSITION_NOT_IN_OCEAN;
import static fr.ird.akado.avdth.Constant.LABEL_ACTIVITY_POSITION_WEIRD;
import fr.ird.akado.avdth.result.ActivityResult;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.core.Inspector;
import fr.ird.akado.core.common.GISHandler;
import fr.ird.common.JDBCUtilities;
import fr.ird.common.OTUtils;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.Ocean;
import fr.ird.driver.avdth.common.AvdthUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Check if the position activity are in ocean or inland, and if the position
 * activity and ocean are consistent. TODO: Implements the functionalities of
 * ANAPO, i.e. check if the position is consistent with the VMS position.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 21 mai 2014
 */
public class PositionInspector extends Inspector<Activity> {

    public PositionInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the position activity are in ocean or inland, "
                + "and if the position activity and ocean are consistent.";
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Activity activite = get();
        ArrayList parameters;
        ActivityResult r;
        boolean inIO = inIndianOcean(
                OTUtils.convertLongitude(activite.getQuadrant(), activite.getLongitude()),
                OTUtils.convertLatitude(activite.getQuadrant(), activite.getLatitude()));
        boolean inAO = inAtlanticOcean(
                OTUtils.convertLongitude(activite.getQuadrant(), activite.getLongitude()),
                OTUtils.convertLatitude(activite.getQuadrant(), activite.getLatitude()));
        boolean inHarbour = inHarbour(
                OTUtils.convertLongitude(activite.getQuadrant(), activite.getLongitude()),
                OTUtils.convertLatitude(activite.getQuadrant(), activite.getLatitude()));
        if (inHarbour) {
            return results;
        }

        if (!inIO && !inAO) {
            String country = inLand(OTUtils.convertLongitude(activite.getQuadrant(), activite.getLongitude()),
                    OTUtils.convertLatitude(activite.getQuadrant(), activite.getLatitude()));

            if (country != null) {
//                if (onCoastLine(OTUtils.convertLongitude(activite.getQuadrant(), activite.getLongitude()),
//                        OTUtils.convertLatitude(activite.getQuadrant(), activite.getLatitude()))) {
//                    r = new ActivityResult();
//                    r.set(activite);
//                    r.setMessageType(Message.ERROR);
//                    r.setMessageCode(CODE_ACTIVITY_POSITION_ON_COASTLINE);
//                    r.setMessageLabel(LABEL_ACTIVITY_POSITION_ON_COASTLINE);
//
//                    parameters = new ArrayList<>();
//                    parameters.add(activite.getID());
//                    parameters.add("(" + round(OTUtils.convertLongitude(activite.getQuadrant(), activite.getLongitude()), 2) + "," + round(OTUtils.convertLatitude(activite.getQuadrant(), activite.getLatitude()), 2) + ")");
//                    parameters.add(country);
//                    r.setMessageParameters(parameters);
//                    results.add(r);
//
//                } else {
                r = new ActivityResult();
                r.set(activite);
                r.setMessageType(Message.ERROR);
                r.setMessageCode(CODE_ACTIVITY_POSITION_NOT_IN_OCEAN);
                r.setMessageLabel(LABEL_ACTIVITY_POSITION_NOT_IN_OCEAN);

                parameters = new ArrayList<>();
                parameters.add(activite.getID());
//                parameters.add("(" + round(OTUtils.convertLongitude(activite.getQuadrant(), activite.getLongitude()), 2) + "," + round(OTUtils.convertLatitude(activite.getQuadrant(), activite.getLatitude()), 2) + ")");
                parameters.add("(" + OTUtils.degreesDecimalToStringDegreesMinutes(OTUtils.convertLatitude(activite.getQuadrant(), activite.getLatitude()), true) + "/" + OTUtils.degreesDecimalToStringDegreesMinutes(OTUtils.convertLongitude(activite.getQuadrant(), activite.getLongitude()), false) + ")");
                parameters.add(country);

                r.setMessageParameters(parameters);
                results.add(r);
//                }
            } else {
                r = new ActivityResult();
                r.set(activite);
                r.setMessageType(Message.WARNING);
                r.setMessageCode(CODE_ACTIVITY_POSITION_WEIRD);
                r.setMessageLabel(LABEL_ACTIVITY_POSITION_WEIRD);

                parameters = new ArrayList<>();
                parameters.add(activite.getID());
//                parameters.add("(" + round(OTUtils.convertLongitude(activite.getQuadrant(), activite.getLongitude()), 2) + "," + round(OTUtils.convertLatitude(activite.getQuadrant(), activite.getLatitude()), 2) + ")");
                parameters.add("(" + OTUtils.degreesDecimalToStringDegreesMinutes(OTUtils.convertLatitude(activite.getQuadrant(), activite.getLatitude()), true) + "/" + OTUtils.degreesDecimalToStringDegreesMinutes(OTUtils.convertLongitude(activite.getQuadrant(), activite.getLongitude()), false) + ")");

                r.setMessageParameters(parameters);
                results.add(r);
            }
        } else if ((activite.getCodeOcean() == Ocean.INDIEN && !inIO) || (activite.getCodeOcean() == Ocean.ATLANTIQUE && !inAO)) {
            r = new ActivityResult();
            r.set(activite);
            r.setMessageType(Message.ERROR);
            r.setMessageCode(CODE_ACTIVITY_OCEAN_INCONSISTENCY);
            r.setMessageLabel(LABEL_ACTIVITY_OCEAN_INCONSISTENCY);

            r.setValueObtained(activite.getCodeOcean());
            r.setValueExpected(AvdthUtils.getOcean(
                    OTUtils.convertLongitude(activite.getQuadrant(), activite.getLongitude()),
                    OTUtils.convertLatitude(activite.getQuadrant(), activite.getLatitude())));

            parameters = new ArrayList<>();
            parameters.add(activite.getID());
            parameters.add(activite.getCodeOcean());
            parameters.add(AvdthUtils.getOcean(
                    OTUtils.convertLongitude(activite.getQuadrant(), activite.getLongitude()),
                    OTUtils.convertLatitude(activite.getQuadrant(), activite.getLatitude())));

            r.setMessageParameters(parameters);
            results.add(r);
        }

        return results;
    }

    public boolean inIndianOcean(double longitude, double latitude) {
//        //System.out.println("IN IO position (long, lat) " + longitude + " , " + latitude);
        boolean inIO = false;
        Statement statement = null;
        try {

            statement = GISHandler.getService().getConnection().createStatement();
            String sql = "SELECT * FROM SeasAndOceans s"
                    + " WHERE "
                    + "	ST_Within( "
                    + "		ST_GeomFromText('POINT(" + longitude + " " + latitude + ")', 4326 ) , "
                    + "		ST_BUFFER(s.the_geom, 1/120.0) "
                    + "	)"
                    + " AND s.id IN('45','45A','44','46A','62','42', '43', '38','39')";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                inIO = true;
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
        return inIO;
    }

    public static boolean inHarbour(double longitude, double latitude) {
//        //System.out.println("IN IO position (long, lat) " + longitude + " , " + latitude);
        boolean inHarbour = false;
        Statement statement = null;
        try {

            statement = GISHandler.getService().getConnection().createStatement();
            String sql = "SELECT * FROM harbour h"
                    + " WHERE "
                    + "	ST_Within( "
                    + "		ST_GeomFromText('POINT(" + longitude + " " + latitude + ")', 4326 ) , "
                    + "		ST_BUFFER(h.the_geom, 0.2) "
                    + "	)";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                inHarbour = true;
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
        return inHarbour;
    }

    public static boolean inIndianOcean(Activity activity) {
        PositionInspector pi = new PositionInspector();
        return pi.inIndianOcean(
                OTUtils.convertLongitude(activity.getQuadrant(), activity.getLongitude()),
                OTUtils.convertLatitude(activity.getQuadrant(), activity.getLatitude()));
    }

    public static boolean inAtlanticOcean(Activity activity) {
        PositionInspector pi = new PositionInspector();
        return pi.inAtlanticOcean(
                OTUtils.convertLongitude(activity.getQuadrant(), activity.getLongitude()),
                OTUtils.convertLatitude(activity.getQuadrant(), activity.getLatitude()));
    }

    public boolean inAtlanticOcean(double longitude, double latitude) {
//        //System.out.println("IN AO position (long, lat) " + longitude + " , " + latitude);
        boolean inAO = false;
        Statement statement = null;
        try {

            statement = GISHandler.getService().getConnection().createStatement();
            String sql = "SELECT * FROM SEASANDOCEANS S"
                    + " WHERE "
                    + "	ST_Within( "
                    + "		ST_GeomFromText('POINT(" + longitude + " " + latitude + ")', 4326 ) , "
                    + "		ST_BUFFER(S.THE_GEOM, 1/120.0) "
                    + "	)"
                    + " AND S.ID IN('15','15A','21', '21A', '22','23','26','27','32','34')";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                inAO = true;
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
        return inAO;
    }

    public static Boolean onCoastLine(Activity activity) {
        PositionInspector pi = new PositionInspector();
        return pi.onCoastLine(
                OTUtils.convertLongitude(activity.getQuadrant(), activity.getLongitude()),
                OTUtils.convertLatitude(activity.getQuadrant(), activity.getLatitude()));
    }

    public boolean onCoastLine(double longitude, double latitude) {
//        //System.out.println("IN LAND position (long, lat) " + longitude + " , " + latitude);

        boolean onCoastLine = false;
        Statement st = null;
        try {

            st = GISHandler.getService().getConnection().createStatement();
            String sql = "SELECT S.NAME FROM SEASANDOCEANS S "
                    + " WHERE  ST_Within(ST_GeomFromText('POINT(" + longitude + " " + latitude + ")', 4326), "
                    + " ST_BUFFER(S.THE_GEOM, 1/120.0))";
            try (ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    onCoastLine = true;
                }
                rs.close();
            }
            st.close();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    JDBCUtilities.printSQLException(ex);
                }
            }
        }
        return onCoastLine;
    }

    public static String inLand(Activity activity) {
        PositionInspector pi = new PositionInspector();
        return pi.inLand(
                OTUtils.convertLongitude(activity.getQuadrant(), activity.getLongitude()),
                OTUtils.convertLatitude(activity.getQuadrant(), activity.getLatitude()));
    }

    public String inLand(double longitude, double latitude) {
//        //System.out.println("IN LAND position (long, lat) " + longitude + " , " + latitude);

        String inLand = null;
        Statement st = null;
        try {

            st = GISHandler.getService().getConnection().createStatement();
            String sql = "SELECT * FROM COUNTRIES C"
                    + " WHERE "
                    + "	ST_Within( "
                    + "		ST_GeomFromText('POINT(" + longitude + " " + latitude + ")', 4326 ) , "
                    + "		C.the_geom "
                    + "	)";
            try (ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    inLand = rs.getString("ENGLISH");
                }
                rs.close();
            }
            st.close();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    JDBCUtilities.printSQLException(ex);
                }
            }
        }
        return inLand;
    }
}
