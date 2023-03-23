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
import fr.ird.common.log.LogService;
import fr.ird.driver.avdth.business.Well;
import fr.ird.driver.avdth.business.WellPlan;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO permettant de faire des requêtes sur la table CUVE_CALEE.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 3.4.5
 * @date 26 mai 2014
 */
public class WellPlanDAO extends AbstractDAO<WellPlan> {

    public WellPlanDAO() {
        super();
    }

    @Override
    public boolean insert(WellPlan w) {
        PreparedStatement statement = null;
        String query = "insert into CUVE_CALEE "
                + " (C_BAT, D_DBQ, N_CUVE, F_POS_CUVE, N_CALESP, D_ACT, N_ACT, C_ESP, C_CAT_POIDS, V_POIDS) "
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, w.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(w.getLandingDate()));
            statement.setInt(3, w.getWellNumber());
            statement.setInt(4, w.getWellPosition());
            statement.setInt(5, w.getNumber());
            statement.setDate(6, DateTimeUtils.convertDate(w.getActivityDate()));
            statement.setInt(7, w.getActivityNumber());
            statement.setInt(8, w.getSpecies().getCode());
            statement.setInt(9, w.getWeightCategory().getCode());
            statement.setDouble(10, w.getWeight());

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
    public boolean delete(WellPlan w) {
        PreparedStatement statement = null;
        String query = "delete from CUVE_CALEE "
                + " where C_BAT = ? and D_DBQ = ? "
                + " and N_CUVE = ? and F_POS_CUVE= ?"
                + " and N_CALESP = ?";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);
            statement.setInt(1, w.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(w.getLandingDate()));
            statement.setInt(3, w.getWellNumber());
            statement.setInt(4, w.getWellPosition());
            statement.setInt(5, w.getNumber());

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

    public List<WellPlan> findWellPlan(Well well) {
        List<WellPlan> wellPlans = new ArrayList<WellPlan>();

        String query = "select * from CUVE_CALEE "
                + " where C_BAT = ? and D_DBQ = ?"
                + " and N_CUVE = ? and F_POS_CUVE = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, well.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(well.getLandingDate()));
            statement.setInt(3, well.getNumber());
            statement.setInt(4, well.getPosition());

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                wellPlans.add(factory(rs));
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        } catch (AvdthDriverException ex) {
            LogService.getService(this.getClass()).logApplicationError(ex.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    JDBCUtilities.printSQLException(ex);
                }
            }
        }
        return wellPlans;
    }

    @Override
    protected WellPlan factory(ResultSet rs) throws SQLException, AvdthDriverException {

        WellPlan wellPlan = new WellPlan();
        wellPlan.setVessel(new VesselDAO().findVesselByCode(rs.getInt("C_BAT")));
        wellPlan.setLandingDate(DateTimeUtils.convertDate(rs.getDate("D_DBQ")));
        wellPlan.setWellNumber(rs.getInt("N_CUVE"));
        wellPlan.setWellPosition(rs.getInt("F_POS_CUVE"));

        wellPlan.setNumber(rs.getInt("N_CALESP"));
        wellPlan.setActivityDate(DateTimeUtils.convertDate(rs.getDate("D_ACT")));
        wellPlan.setActivityNumber(rs.getInt("N_ACT"));
        wellPlan.setSpecies((new SpeciesDAO()).findSpeciesByCode(rs.getInt("C_ESP")));
        wellPlan.setWeightCategory((new WeightCategoryWellPlanDAO()).findWeightCategoryByCode(rs.getInt("C_CAT_POIDS")));
        wellPlan.setWeight(rs.getDouble("V_POIDS"));
        return wellPlan;
    }
}
