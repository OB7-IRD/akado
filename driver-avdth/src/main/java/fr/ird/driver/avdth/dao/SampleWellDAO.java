/* 
 * Copyright (C) 2014 Julien Lebranchu <julien.lebranchu@ird.fr>
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
package fr.ird.driver.avdth.dao;

import fr.ird.common.DateTimeUtils;
import fr.ird.common.JDBCUtilities;
import fr.ird.driver.avdth.business.Sample;
import fr.ird.driver.avdth.business.SampleWell;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

/**
 * DAO permettant de faire des requêtes sur la table ECH_CALEE.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 3.4.5
 * @date 26 mai 2014
 *
 */
public class SampleWellDAO extends AbstractDAO<SampleWell> {

    public SampleWellDAO() {
        super();
    }

    @Override
    public boolean insert(SampleWell t) {
        PreparedStatement statement = null;
        String query = "insert into ECH_CALEE "
                + " (C_BAT, D_DBQ, N_ECH, N_S_ECH, D_ACT, N_ACT, C_ZONE_GEO, Q_ACT, V_LAT, V_LON, C_TBANC, V_POND) "
                + " values (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, t.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(t.getLandingDate()));
            statement.setInt(3, t.getSampleNumber());

            statement.setDate(4, DateTimeUtils.convertDate(t.getActivityDate()));
            statement.setInt(5, t.getActivityNumber());
            statement.setInt(6, t.getGeo().getCode());
            statement.setInt(7, t.getQuadrant());
            statement.setInt(8, t.getLatitude());
            statement.setInt(9, t.getLongitude());
            statement.setInt(10, t.getSchoolType().getCode());
            statement.setDouble(11, t.getWeightedWeight());

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

    @Override
    public boolean delete(SampleWell t) {
        PreparedStatement statement = null;
        String query = "delete from ECH_CALEE "
                + " where C_BAT = ? and D_DBQ = ? and N_ECH = ? "
                + " and D_ACT = ? and N_ACT = ? ";

        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, t.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(t.getLandingDate()));
            statement.setInt(3, t.getSampleNumber());

            statement.setDate(4, DateTimeUtils.convertDate(t.getActivityDate()));
            statement.setInt(5, t.getActivityNumber());

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

    public List<SampleWell> findSampleWell(Sample sample) {
        List<SampleWell> sampleWells = new ArrayList<>();
        PreparedStatement statement = null;
        String query = "select * from ECH_CALEE "
                + " where C_BAT = ? and D_DBQ = ? and N_ECH = ? ";

        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, sample.getVessel().getCode());

            statement.setDate(2, DateTimeUtils.convertDate(sample.getLandingDate()));
            statement.setInt(3, sample.getSampleNumber());

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                sampleWells.add(factory(rs));
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
        return sampleWells;
    }

    public List<SampleWell> findSampleWellByActivity(int vesselCode, DateTime landingDate, DateTime activityDate, int activityNumber) {
        List<SampleWell> sampleWells = new ArrayList<>();
        PreparedStatement statement = null;
        String query = "select * from ECH_CALEE "
                + " where C_BAT = ? and D_DBQ = ? and D_ACT = ? and N_ACT = ? ";

        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, vesselCode);

            statement.setDate(2, DateTimeUtils.convertDate(landingDate));
            statement.setDate(3, DateTimeUtils.convertDate(activityDate));
            statement.setInt(4, activityNumber);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                sampleWells.add(factory(rs));
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
        return sampleWells;
    }

    @Override
    public SampleWell factory(ResultSet rs) throws SQLException {
        SampleWell sampleWell = new SampleWell();
        sampleWell.setVessel((new VesselDAO().findVesselByCode(rs.getInt("C_BAT"))));
        sampleWell.setLandingDate(DateTimeUtils.convertDate(rs.getDate("D_DBQ")));
        sampleWell.setSampleNumber(rs.getInt("N_ECH"));
        sampleWell.setActivityDate(DateTimeUtils.convertDate(rs.getDate("D_ACT")));
        sampleWell.setActivityNumber(rs.getInt("N_ACT"));
        sampleWell.setGeo((new GeographicalAreaDAO()).findGeographicalAreaByCode(rs.getInt("C_ZONE_GEO")));
        sampleWell.setQuadrant(rs.getInt("Q_ACT"));
        sampleWell.setLatitude(rs.getInt("V_LAT"));
        sampleWell.setLongitude(rs.getInt("V_LON"));
        sampleWell.setSchoolType((new SchoolTypeDAO()).findSchoolTypeByCode(rs.getInt("C_TBANC")));
        sampleWell.setWeightedWeight(rs.getDouble("V_POND"));
        return sampleWell;
    }

}
