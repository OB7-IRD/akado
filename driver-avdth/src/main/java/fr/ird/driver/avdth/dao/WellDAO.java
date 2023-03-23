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
import fr.ird.driver.avdth.business.Country;
import fr.ird.driver.avdth.business.Trip;
import fr.ird.driver.avdth.business.Vessel;
import fr.ird.driver.avdth.business.Well;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import org.joda.time.DateTime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO permettant de faire des requêtes sur la table CUVE.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 3.4.5
 * @date 26 mai 2014
 */
public class WellDAO extends AbstractDAO<Well> {

    public WellDAO() {
        super();
    }

    public Integer count() {
        String query = "select count(*) from (SELECT C_BAT, D_DBQ FROM CUVE)";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                return rs.getInt(1);
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
        return null;
    }

    @Override
    public boolean insert(Well w) {
        PreparedStatement statement = null;
        String query = "insert into CUVE "
                + " (C_BAT, D_DBQ, N_CUVE, F_POS_CUVE, C_DEST) "
                + " values (?, ?, ?, ?, ?);";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, w.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(w.getLandingDate()));
            statement.setInt(3, w.getNumber());
            statement.setInt(4, w.getPosition());
            statement.setInt(5, w.getDestiny().getCode());

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
    public boolean delete(Well w) {
        PreparedStatement statement = null;
        String query = "delete from CUVE "
                + " where C_BAT = ? and D_DBQ = ? "
                + " and N_CUVE = ? and F_POS_CUVE= ?";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);
            statement.setInt(1, w.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(w.getLandingDate()));
            statement.setInt(3, w.getNumber());
            statement.setInt(4, w.getDestiny().getCode());

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

    public Well findWell(int vesselCode, DateTime landingDate, int wellNumber, int wellPosition) throws AvdthDriverException {
        Well well = null;

        String query = "select * from CUVE "
                + " where C_BAT = ? and D_DBQ = ?"
                + " and N_CUVE = ? and F_POS_CUVE = ?";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, vesselCode);
            statement.setDate(2, DateTimeUtils.convertDate(landingDate));
            statement.setInt(3, wellNumber);
            statement.setInt(4, wellPosition);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                well = factory(rs);
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
        return well;
    }

    public List<Well> findWells(int vesselCode, DateTime landingDate) throws AvdthDriverException {
        List<Well> wells = new ArrayList<Well>();

        String query = "select * from CUVE "
                + " where C_BAT = ? and D_DBQ = ?";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, vesselCode);
            statement.setDate(2, DateTimeUtils.convertDate(landingDate));

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                wells.add(factory(rs));
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
        return wells;
    }

    public Well findWell(Trip trip, int wellNumber, int wellPosition) throws AvdthDriverException {
        Well well = null;

        String query = "select * from CUVE "
                + " where C_BAT = ? and D_DBQ = ?"
                + " and N_CUVE = ? and F_POS_CUVE = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, trip.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(trip.getLandingDate()));
            statement.setInt(3, wellNumber);
            statement.setInt(4, wellPosition);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                well = factory(rs);
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
        return well;
    }

    @Override
    protected Well factory(ResultSet rs) throws SQLException {
        Well well = new Well();
        well.setVessel(new VesselDAO().findVesselByCode(rs.getInt("C_BAT")));
        well.setLandingDate(DateTimeUtils.convertDate(rs.getDate("D_DBQ")));
        well.setNumber(rs.getInt("N_CUVE"));
        well.setPosition(rs.getInt("F_POS_CUVE"));
        well.setDestiny((new WellDestinyDAO()).findWellDestinyByCode(rs.getInt("C_DEST")));

        WellPlanDAO wellPlanDAO = new WellPlanDAO();
        well.setWellPlans(wellPlanDAO.findWellPlan(well));

        return well;
    }

    public List<Well> findWell(List<Vessel> vessels, List<Country> countries, DateTime start, DateTime end) throws AvdthDriverException {
        List<Well> wells = new ArrayList<Well>();
        List<Vessel> vesselsForCountries = new ArrayList<Vessel>();
        if (countries != null && !countries.isEmpty()) {
            vesselsForCountries = (new VesselDAO()).findVessels(countries);
        }
        if (vessels == null || vessels.isEmpty()) {
            vessels = (new VesselDAO()).getAllVessels();
        }

        if (!vessels.isEmpty() && !vesselsForCountries.isEmpty()) {
            vessels.retainAll(vesselsForCountries);
        }
        for (Vessel b : vessels) {
//            System.out.println(b);
            wells.addAll(findWell(b, start, end));
        }
        return wells;
    }

    public List<Well> findWell(Vessel vessel, DateTime start, DateTime end) throws AvdthDriverException {
        List<Well> wells = null;

        String query = "select * from CUVE where C_BAT = ? "
                + "AND D_DBQ BETWEEN ? AND ?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, vessel.getCode());
            statement.setDate(2, DateTimeUtils.convertDate((start == null ? new DateTime(1900, 1, 1, 0, 0) : start)));
            statement.setDate(3, DateTimeUtils.convertDate((end == null ? new DateTime() : end)));

            ResultSet rs = statement.executeQuery();
            wells = new ArrayList<Well>();

            while (rs.next()) {

                wells.add(factory(rs));

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
        return wells;
    }

    public List<Well> findAllWells() throws AvdthDriverException {
        List<Well> wells = null;

        String query = "select * from CUVE "
                + " order by D_DBQ asc";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            wells = new ArrayList<Well>();

            while (rs.next()) {
                wells.add(factory(rs));
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
        return wells;

    }

}
