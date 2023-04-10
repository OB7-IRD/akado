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
package fr.ird.akado.core.spatial;

import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.core.common.AkadoException;
import fr.ird.common.JDBCUtilities;
import io.ultreia.java4all.lang.Objects2;
import io.ultreia.java4all.util.TimeLog;
import io.ultreia.java4all.util.sql.SqlQuery;
import org.apache.logging.log4j.LogManager;
import org.h2gis.functions.factory.H2GISFunctions;

import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Create the GIS databases for validation rules including spatial areas. It is
 * based on H2Gis library developed at CNRS and loads data from the SHP file
 * format.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @date 30 juin 2014
 * @see <a href="http://www.h2gis.org/">site de H2Gis</a>
 * @since 2.0
 */
@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection"})
public class GISHandler {

    private static final TimeLog TIMELOG = new TimeLog(GISHandler.class, 500, 1000);
    static {
        Objects2.forName("org.h2.Driver");
    }

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(GISHandler.class);

    private static final GISHandler SERVICE = new GISHandler();
    public static final String OT_DB_GIS_NAME = "OTStandardGIS";

    private String dbPath;
    private String countryShapePath;
    private String eezShapePath;
    private String oceanShapePath;

    public static GISHandler getService() {
        return SERVICE;
    }

    private String harbourShapePath;

    public void close() throws SQLException {
        if (connection!=null && !connection.isClosed()) {
            try {
                connection.close();
            } finally {
                connection=null;
            }
        }
    }

    public void init(String directoryPath, String countryShapePath, String oceanShapePath, String harbourShapePath, String eezShapePath) throws AkadoException {
        if (directoryPath == null) {
            throw new AkadoException("The directory path is null.");
        } else {
            this.dbPath = Path.of(directoryPath).resolve(OT_DB_GIS_NAME).toFile().getAbsolutePath();
        }
        this.countryShapePath = countryShapePath;
        this.oceanShapePath = oceanShapePath;
        this.harbourShapePath = harbourShapePath;
        this.eezShapePath = eezShapePath;
    }

    /**
     * Checks whether the databases are already created.
     *
     * @return true if exists
     */
    public boolean exists() {
        return dbPath != null && databaseFile().exists();
    }

    /**
     * Delete existing databases.
     *
     * @return true if the db are deleted
     */
    public boolean delete() {
        return databaseFile().delete();
    }

    public File databaseFile() {
        return new File(dbPath + ".mv.db");
    }

    /**
     * Create the GIS databe by loading the file data
     */
    public void create(boolean force) {
        if (force) {
            delete();
        }
        create();
    }

    public void create() {
        long t0 = TimeLog.getTime();
        log.debug("File: " + dbPath + ", File exits: " + exists());
        if (!exists()) {
            log.info("Create the GIS database at {}.", databaseFile());

            try (Connection connection = DriverManager.getConnection("jdbc:h2:" + dbPath)) {
                H2GISFunctions.load(connection);
                createSeasAndOceans(connection);
                createCountries(connection);
                createHarbours(connection);
                createZee(connection);
            } catch (SQLException ex) {
                JDBCUtilities.printSQLException(ex);
            }
        }
        TIMELOG.log(t0,"Create GIS database");
    }

    private void createZee(Connection connection) throws SQLException {
        try (Statement st = connection.createStatement()) {
            log.debug("SHP_EEZ_PATH " + eezShapePath);

            st.execute("DROP TABLE IF EXISTS eez;");
            st.execute("CALL SHPREAD('" + eezShapePath + "', 'eez')");
            st.execute("CREATE SPATIAL INDEX eez_spatialindex ON eez (the_geom);");
            try (ResultSet rs = st.executeQuery("SELECT count(*) AS rowcount FROM eez")) {
                while (rs.next()) {
                    String tmp = "There is " + rs.getInt("rowcount") + " eez in the database.";
                    log.info(tmp);
                }
            }
            try (ResultSet rs = st.executeQuery("SELECT INDEX_NAME FROM INFORMATION_SCHEMA.INDEXES WHERE TABLE_NAME = 'eez'")) {
                while (rs.next()) {
                    log.debug(rs.getString("INDEX_NAME"));
                }
            }
        }
    }

    private void createHarbours(Connection connection) throws SQLException {
        try (Statement st = connection.createStatement()) {
            log.debug("SHP_HARBOUR_PATH " + harbourShapePath);
            st.execute("DROP TABLE IF EXISTS harbour;");
            st.execute("CALL SHPREAD('" + harbourShapePath + "', 'harbour')");
            st.execute("CREATE SPATIAL INDEX harbour_spatialindex ON harbour (the_geom);");
            try (ResultSet rs = st.executeQuery("SELECT count(*) AS rowcount FROM harbour")) {
                while (rs.next()) {
                    String tmp = "There is " + rs.getInt("rowcount") + " harbours in the database.";
                    log.info(tmp);
                }
            }
            try (ResultSet rs = st.executeQuery("SELECT INDEX_NAME FROM INFORMATION_SCHEMA.INDEXES WHERE TABLE_NAME = 'harbour'")) {
                while (rs.next()) {
                    log.debug(rs.getString("INDEX_NAME"));
                }
            }
        }
    }

    private void createSeasAndOceans(Connection connection) throws SQLException {
        try (Statement st = connection.createStatement()) {
            log.debug("SHP_OCEAN_PATH " + oceanShapePath);
            st.execute("DROP TABLE IF EXISTS seasandoceans;");
            st.execute("CALL SHPREAD('" + oceanShapePath + "', 'seasandoceans')");
            st.execute("CREATE SPATIAL INDEX ocean_spatialindex ON seasandoceans(the_geom);");
            try (ResultSet rs = st.executeQuery("SELECT count(*) AS rowcount FROM seasandoceans")) {
                while (rs.next()) {
                    String tmp = "There is " + rs.getInt("rowcount") + " seas and oceans in the database.";
                    log.info(tmp);
                }
            }
            try (ResultSet rs = st.executeQuery("SELECT * FROM INFORMATION_SCHEMA.INDEXES WHERE TABLE_NAME = 'seasandoceans'")) {
                while (rs.next()) {
                    log.debug(rs.getString("INDEX_NAME"));
                }
            }
        }
    }

    private void createCountries(Connection connection) throws SQLException {
        try (Statement st = connection.createStatement()) {
            log.debug("SHP_COUNTRIES_PATH " + AAProperties.SHP_COUNTRIES_PATH);
            st.execute("DROP TABLE IF EXISTS countries;");
            st.execute("CALL SHPREAD('" + countryShapePath + "', 'countries')");
            st.execute("CREATE SPATIAL INDEX countries_spatialindex ON countries (the_geom);");
            try (ResultSet rs = st.executeQuery("SELECT count(*) AS rowcount FROM countries")) {
                while (rs.next()) {
                    String tmp = "There is " + rs.getInt("rowcount") + " countries in the database.";
                    log.info(tmp);
                }
            }
            try (ResultSet rs = st.executeQuery("SELECT * FROM INFORMATION_SCHEMA.INDEXES WHERE TABLE_NAME = 'countries';")) {
                while (rs.next()) {
                    log.debug(rs.getString("INDEX_NAME"));
                }
            }
        }
    }

    private Connection connection;

    /**
     * Getter on the database connection
     *
     * @return a database connection
     */
    public Connection getConnection() {
        if (connection == null && dbPath != null) {
            try {
                String url = "jdbc:h2:" + dbPath + ";ACCESS_MODE_DATA=R;DB_CLOSE_ON_EXIT=FALSE";
                connection = DriverManager.getConnection(url);
            } catch (SQLException ex) {
                JDBCUtilities.printSQLException(ex);
            }
        }
        return connection;
    }

    public String getEEZ(double longitude, double latitude) {
        SqlQuery<String> query = SqlQuery.wrap("SELECT ISO_Ter1 FROM eez e" +
                                                       " WHERE "
                                                       + "	ST_Covers( "
                                                       + "		ST_SetSRID(e.the_geom, 4326),"
                                                       + "      ST_GeomFromText('POINT(" + longitude + " " + latitude + ")', 4326 )" +
                                                       "    )",
                                               rs -> rs.getString("ISO_Ter1"));

        return JDBCUtilities.findFirstFirst(getConnection(), query).orElse("-");
    }


    public List<String> getEEZList(String multipoint) {
        SqlQuery<String> query = SqlQuery.wrap("SELECT ISO_Ter1 FROM eez e"
                                                       + " WHERE "
                                                       + "	ST_Within( "
                                                       + "         ST_GeomFromText('MULTIPOINT(" + multipoint + ")', 4326 ),"
                                                       + "		ST_SetSRID(e.the_geom, 4326)"
                                                       + "	)",
                                               rs -> rs.getString("ISO_Ter1"));
        return JDBCUtilities.findList(getConnection(), query);
    }

    public boolean inIndianOcean(double longitude, double latitude) {
        SqlQuery<Boolean> query = SqlQuery.wrap("SELECT COUNT(*) FROM SeasAndOceans s"
                                                        + " WHERE "
                                                        + "	ST_Within( "
                                                        + "		ST_GeomFromText('POINT(" + longitude + " " + latitude + ")', 4326 ) , "
                                                        + "		ST_BUFFER(s.the_geom, 1/120.0) "
                                                        + "	)"
                                                        + " AND s.id IN('45','45A','44','46A','62','42', '43', '38','39')",
                                                rs -> rs.getInt(1) > 0);

        return JDBCUtilities.findFirstFirst(getConnection(), query).orElse(false);
    }

    public boolean inAtlanticOcean(double longitude, double latitude) {
        SqlQuery<Boolean> query = SqlQuery.wrap("SELECT COUNT(*) FROM SeasAndOceans S"
                                                        + " WHERE "
                                                        + "	ST_Within( "
                                                        + "		ST_GeomFromText('POINT(" + longitude + " " + latitude + ")', 4326 ) , "
                                                        + "		ST_BUFFER(S.THE_GEOM, 1/120.0) "
                                                        + "	)"
                                                        + " AND S.ID IN('15','15A','21', '21A', '22','23','26','27','32','34')",
                                                rs -> rs.getInt(1) > 0);
        return JDBCUtilities.findFirstFirst(getConnection(), query).orElse(false);
    }

    public boolean inHarbour(double longitude, double latitude) {
        SqlQuery<Boolean> query = SqlQuery.wrap("SELECT COUNT(*) FROM harbour h"
                                                        + " WHERE "
                                                        + "	ST_Within( "
                                                        + "		ST_GeomFromText('POINT(" + longitude + " " + latitude + ")', 4326 ) , "
                                                        + "		ST_BUFFER(h.the_geom, 0.2) "
                                                        + "	)",
                                                rs -> rs.getInt(1) > 0);

        return JDBCUtilities.findFirstFirst(getConnection(), query).orElse(false);
    }

    public boolean onCoastLine(double longitude, double latitude) {
        SqlQuery<Boolean> query = SqlQuery.wrap("SELECT COUNT(*) FROM SEASANDOCEANS S "
                                                        + " WHERE  ST_Within(ST_GeomFromText('POINT(" + longitude + " " + latitude + ")', 4326), "
                                                        + " ST_BUFFER(S.THE_GEOM, 1/120.0))",
                                                rs -> rs.getInt(1) > 0);
        return JDBCUtilities.findFirstFirst(getConnection(), query).orElse(false);
    }

    public String inLand(double longitude, double latitude) {
        SqlQuery<String> query = SqlQuery.wrap("SELECT ENGLISH FROM COUNTRIES C"
                                                       + " WHERE "
                                                       + "	ST_Within( "
                                                       + "		ST_GeomFromText('POINT(" + longitude + " " + latitude + ")', 4326 ) , "
                                                       + "		C.the_geom "
                                                       + "	)",
                                               rs -> rs.getString("ENGLISH"));
        return JDBCUtilities.findFirstFirst(getConnection(), query).orElse(null);
    }

}
