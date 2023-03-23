/* 
 * Copyright (C) 2013 Julien Lebranchu <julien.lebranchu@ird.fr>
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
import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.FishingContext;
import fr.ird.driver.avdth.business.FishingContextType;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import org.joda.time.DateTime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO to make queries on the table <em>ACT_ASSOC</em>.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @version 1.0
 * @date 26 sept. 2013
 */
public class FishingContextDAO extends AbstractDAO<FishingContext> {

    public FishingContextDAO() {
        super();
    }

    /**
     *
     * @param rs
     * @return
     * @throws SQLException
     * @throws AvdthDriverException
     *
     */
    @Override
    public FishingContext factory(ResultSet rs) throws SQLException, AvdthDriverException {

        FishingContext fishingContext;

        fishingContext = new FishingContext();
        fishingContext.setVessel((new VesselDAO().findVesselByCode(rs.getInt("C_BAT"))));
        fishingContext.setLandingDate(DateTimeUtils.convertDate(rs.getDate("D_DBQ")));
        fishingContext.setActivityDate(DateTimeUtils.convertDate(rs.getDate("D_ACT")));
        fishingContext.setActivityNumber(rs.getInt("N_ACT"));

        fishingContext.setIndex(rs.getInt("N_ASSOC"));

        FishingContextTypeDAO associationDAO = new FishingContextTypeDAO();
        FishingContextType association = associationDAO.findFishingContextTypeByCode(rs.getInt("C_ASSOC"));
        fishingContext.setFishingContextType(association);
        return fishingContext;
    }

    /**
     *
     *
     * @param activity
     * @return
     */
    public List<FishingContext> findFishingContextByActivity(Activity activity) {
        ArrayList<FishingContext> activiteAssociations = null;
        int vesselCode = activity.getVessel().getCode();
        DateTime landingDate = activity.getLandingDate();
        String query = "select * from ACT_ASSOC "
                + " where C_BAT = ? and D_DBQ = ? "
                + " and D_ACT = ? and N_ACT = ? "
                + " order by N_ASSOC ";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, vesselCode);
            statement.setDate(2, DateTimeUtils.convertDate(landingDate));
            statement.setDate(3, DateTimeUtils.convertDate(activity.getDate()));
            statement.setInt(4, activity.getNumber());

            ResultSet rs = statement.executeQuery();
            activiteAssociations = new ArrayList<FishingContext>();

            while (rs.next()) {
                activiteAssociations.add(factory(rs));
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
        return activiteAssociations;
    }

    /**
     *
     *
     *
     * @param vesselCode
     * @param landingDate
     * @param activityDate
     * @param activityNumber
     * @return
     *
     */
    public List<FishingContext> findFishingContextByActivity(int vesselCode, DateTime landingDate, DateTime activityDate, int activityNumber) {
        ArrayList<FishingContext> activiteAssociations = null;

        String query = "select * from ACT_ASSOC "
                + " where C_BAT = ? and D_DBQ = ? "
                + " and D_ACT = ? and N_ACT = ? "
                + " order by N_ASSOC ";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, vesselCode);
            statement.setDate(2, DateTimeUtils.convertDate(landingDate));
            statement.setDate(3, DateTimeUtils.convertDate(activityDate));
            statement.setInt(4, activityNumber);

            ResultSet rs = statement.executeQuery();
            activiteAssociations = new ArrayList<FishingContext>();

            while (rs.next()) {
                activiteAssociations.add(factory(rs));
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        } catch (AvdthDriverException ex) {
            LogService.getService(FishingContextDAO.class).logApplicationError(ex.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    JDBCUtilities.printSQLException(ex);
                }

            }
        }
        return activiteAssociations;

    }

    @Override
    public boolean insert(FishingContext fishingContext) {

        PreparedStatement statement = null;
        String query = "insert into ACT_ASSOC (C_BAT, D_DBQ, D_ACT, N_ACT, N_ASSOC, C_ASSOC) "
                + " values (?, ?, ?, ?, ?, ?) ";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);
            statement.setInt(1, fishingContext.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(fishingContext.getLandingDate()));
            statement.setDate(3, DateTimeUtils.convertDate(fishingContext.getActivityDate()));
            statement.setInt(4, fishingContext.getActivityNumber());
            statement.setLong(5, fishingContext.getIndex());
            int codeAssociation = 0;
            if (fishingContext.getFishingContextType() != null) {
                codeAssociation = fishingContext.getFishingContextType().getCode();
            }

            statement.setInt(6, codeAssociation);

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
    public boolean update(FishingContext fishingContext) {
        return delete(fishingContext) && insert(fishingContext);
    }

    @Override
    public boolean delete(FishingContext fishingContext) {
        PreparedStatement statement = null;
        String query = "delete from ACT_ASSOC "
                + " where C_BAT = ? and D_DBQ = ? and D_ACT = ? "
                + " and N_ACT= ? and N_ASSOC= ? and C_ASSOC= ?";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);
            statement.setInt(1, fishingContext.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(fishingContext.getLandingDate()));
            statement.setDate(3, DateTimeUtils.convertDate(fishingContext.getActivityDate()));
            statement.setInt(4, fishingContext.getActivityNumber());
            statement.setLong(5, fishingContext.getIndex());
            statement.setInt(6, fishingContext.getFishingContextType().getCode());
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

}
