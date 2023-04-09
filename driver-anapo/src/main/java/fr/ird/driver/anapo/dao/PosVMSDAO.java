/**
 * Copyright (C) 2014 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.driver.anapo.dao;

import fr.ird.common.DateTimeUtils;
import fr.ird.common.JDBCUtilities;
import fr.ird.driver.anapo.business.PosVMS;
import fr.ird.driver.anapo.service.ANAPOService;
import org.joda.time.DateTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DAO to make queries on the table <em>PosVMSDAO</em>.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @version 1.0
 * @since 1.0
 * @date 14 févr. 2014
 */
public class PosVMSDAO {

    private final Connection connection;

    public PosVMSDAO() {
        this.connection = ANAPOService.getService().getConnection();
    }

    public boolean insert(PosVMS posVMS) {

        PreparedStatement statement = null;
        String query = "insert into POSVMS (C_BAT, D_POS, C_OCEA, V_LAT, V_LON, CAP, VITESSE, CFR_CODE,NOMBAT, LATITUDE, LONGITUDE) "
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);
            statement.setInt(1, posVMS.getVesselId());
            statement.setDate(2, DateTimeUtils.convertDate(posVMS.getDate()));
            statement.setInt(3, posVMS.getOcean());
            statement.setInt(4, posVMS.getLatitude());
            statement.setInt(5, posVMS.getLongitude());

            statement.setInt(6, posVMS.getDirection());
            statement.setDouble(7, posVMS.getSpeed());
            statement.setString(8, posVMS.getCfrId());
            statement.setString(9, posVMS.getVesselName());
            statement.setString(10, posVMS.getLatitudeDegMin());
            statement.setString(11, posVMS.getLongitudeDegMin());

            statement.execute();
            statement.close();

            connection.commit();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
            try {
                connection.rollback();
            } catch (SQLException e) {
                JDBCUtilities.printSQLException(e);
            }
            return false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                JDBCUtilities.printSQLException(e);
                return false;
            }
        }
        return true;

    }

    public boolean update(PosVMS posVMS) {
        return delete(posVMS) && insert(posVMS);
    }

    public boolean delete(PosVMS posVMS) {
        PreparedStatement statement = null;
        String query = "delete from POSVMS "
                + " where C_BAT = ? and D_POS = ? and C_OCEA = ? and V_LAT = ? and V_LON= ?";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);
            statement.setInt(1, posVMS.getVesselId());
            statement.setDate(2, DateTimeUtils.convertDate(posVMS.getDate()));
            statement.setInt(3, posVMS.getOcean());
            statement.setInt(4, posVMS.getLatitude());
            statement.setInt(5, posVMS.getLongitude());
            statement.execute();
            statement.close();

            connection.commit();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
            try {
                connection.rollback();
            } catch (SQLException e) {
                JDBCUtilities.printSQLException(e);
            }
            return false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                JDBCUtilities.printSQLException(e);
                return false;
            }
        }
        return true;
    }

    /**
     * Return all positions for the vessel code and the activity date
     *
     * @param code the vessel code of VMS positions
     * @param date the date of the activity
     * @return a list of positions
     */
    public List<PosVMS> findAllPositions(int code, DateTime date) {
        return findAllPositions(code, DateTimeUtils.convertFilteredDate(date));
    }

    /**
     * Return all positions for the vessel code and the activity date
     *
     * @param code the vessel code of VMS positions
     * @param date the date of the activity
     * @return a list of positions
     */
    public List<PosVMS> findAllPositions(int code, Date date) {
//        System.out.println(code + " " + date);
        List<PosVMS> positions = null;
        String query = "select * from POSVMS "
                + " where C_BAT = ? and D_POS = ?";
        PreparedStatement statement = null;
        try {

            statement = connection.prepareStatement(query);
            statement.setInt(1, code);
            statement.setDate(2, new java.sql.Date(date.getTime()));

            ResultSet rs = statement.executeQuery();
            positions = new ArrayList<>();

            while (rs.next()) {

                positions.add(factory(rs));

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
        return positions;
    }

    /**
     * Return a list of distinct positions for all vessel and between the
     * specified date
     *
     * @param start the date
     * @param end
     * @return a list of positions
     */
    public List<PosVMS> listDistinctPositionsByDayForAllVessel(DateTime start, DateTime end) {
//        System.out.println(code + " " + date);
        List<PosVMS> positions = null;
        String query = "select * from POSVMS "
                + " where D_POS BETWEEN ? AND ?";
        PreparedStatement statement = null;
        try {

            statement = connection.prepareStatement(query);

            statement.setDate(1, DateTimeUtils.convertFilteredDate(start));
            statement.setDate(2, DateTimeUtils.convertFilteredDate(end));

            ResultSet rs = statement.executeQuery();

            positions = new ArrayList<PosVMS>();

            while (rs.next()) {

                positions.add(factory(rs));

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
        return positions;
    }

    /**
     * Return a list of distinct positions for all vessel and between the
     * specified date
     *
     * @param start the date
     * @param end
     * @param vesselCode
     * @return a list of positions
     */
    public List<PosVMS> listDistinctPositionsByDayForVessel(DateTime start, DateTime end, int vesselCode) {
//        System.out.println(code + " " + date);
        List<PosVMS> positions = null;
        String query = "select * from POSVMS "
                + " where D_POS BETWEEN ? AND ? AND C_BAT = ?";
        PreparedStatement statement = null;
        try {

            statement = connection.prepareStatement(query);

            statement.setDate(1, DateTimeUtils.convertFilteredDate(start));
            statement.setDate(2, DateTimeUtils.convertFilteredDate(end));
            statement.setInt(3, vesselCode);
            ResultSet rs = statement.executeQuery();

            positions = new ArrayList<PosVMS>();

            while (rs.next()) {

                positions.add(factory(rs));

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
        return positions;
    }

    /**
     * Return a list of distinct positions for all vessel and before the
     * specified date
     *
     * @param end the date
     *
     * @return a list of positions
     */
    public List<PosVMS> listDistinctPositionsByDayForAllVessel(DateTime end) {
//        System.out.println(code + " " + date);
        List<PosVMS> positions = null;
        String query = "select * from POSVMS "
                + " where D_POS <= ?";
        PreparedStatement statement = null;
        try {

            statement = connection.prepareStatement(query);

            statement.setDate(1, DateTimeUtils.convertFilteredDate(end));

            ResultSet rs = statement.executeQuery();

            positions = new ArrayList<PosVMS>();

            while (rs.next()) {

                positions.add(factory(rs));

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
        return positions;
    }

    /**
     * Return a list of distinct positions for the vessel and before the
     * specified date
     *
     * @param end the date
     * @param vesselCode the vessel id
     *
     * @return a list of positions
     */
    public List<PosVMS> listDistinctPositionsByDayForVessel(DateTime end, int vesselCode) {
//        System.out.println(code + " " + date);
        List<PosVMS> positions = null;
        String query = "select * from POSVMS "
                + " where D_POS <= ? AND C_BAT = ?";
        PreparedStatement statement = null;
        try {

            statement = connection.prepareStatement(query);

            statement.setDate(1, DateTimeUtils.convertFilteredDate(end));
            statement.setInt(2, vesselCode);

            ResultSet rs = statement.executeQuery();

            positions = new ArrayList<PosVMS>();

            while (rs.next()) {

                positions.add(factory(rs));

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
        return positions;
    }

    private PosVMS factory(ResultSet rs) throws SQLException {
        PosVMS posVMS = new PosVMS(rs.getInt("C_BAT"),
                DateTimeUtils.addTimeTo(DateTimeUtils.convertDate(rs.getDate("D_POS")), rs.getString("HEURE")),
                rs.getInt("C_OCEA"),
                rs.getInt("V_LAT"),
                rs.getInt("V_LON"));

        posVMS.setCfrId(rs.getString("CFR_CODE"));
        posVMS.setVesselName(rs.getString("NOMBAT"));
        posVMS.setDirection(rs.getInt("CAP"));
        posVMS.setSpeed(rs.getInt("VITESSE"));

        posVMS.setLatitudeDegMin(rs.getString("LATITUDE"));
        posVMS.setLongitudeDegMin(rs.getString("LONGITUDE"));

        return posVMS;
    }

}
