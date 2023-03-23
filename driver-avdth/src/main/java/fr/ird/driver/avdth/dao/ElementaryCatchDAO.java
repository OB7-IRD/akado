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
import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.SizeCategory;
import fr.ird.driver.avdth.business.ElementaryCatch;
import fr.ird.driver.avdth.business.Trip;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import org.joda.time.DateTime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO permettant de faire des requêtes sur la table <em>CAPT_ELEM</em>.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @version 1.0
 * @date 24 sept. 2013
 *
 */
public class ElementaryCatchDAO extends AbstractDAO<ElementaryCatch> {

    public ElementaryCatchDAO() {
        super();
    }

    @Override
    public ElementaryCatch factory(ResultSet rs) throws SQLException, AvdthDriverException {
        ElementaryCatch elementaryCatch = new ElementaryCatch();
        elementaryCatch.setVessel((new VesselDAO().findVesselByCode(rs.getInt("C_BAT"))));
        elementaryCatch.setLandingDate(DateTimeUtils.convertDate(rs.getDate("D_DBQ")));
        elementaryCatch.setActivityDate(DateTimeUtils.convertDate(rs.getDate("D_ACT")));
        elementaryCatch.setActivityNumber(rs.getInt("N_ACT"));

        elementaryCatch.setIndex(rs.getInt("N_CAPT"));
        elementaryCatch.setCatchWeight(rs.getDouble("V_POIDS_CAPT"));
        SizeCategoryDAO categorieDeTailleDAO = new SizeCategoryDAO();
        SizeCategory categorieDeTaille = categorieDeTailleDAO.findSizeCategoryByCode(rs.getInt("C_ESP"), rs.getInt("C_CAT_T"));
        elementaryCatch.setSizeCategory(categorieDeTaille);

        return elementaryCatch;
    }

    public List<ElementaryCatch> findAllCatchesOfActivity(int vesselCode, DateTime landingDate, DateTime activityDate, int activityNumber) throws AvdthDriverException {
        ArrayList<ElementaryCatch> captureElementaires = null;

        String query = "select * from CAPT_ELEM "
                + " where C_BAT = ? and D_DBQ = ? "
                + " and D_ACT = ? and N_ACT = ? ";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, vesselCode);
            statement.setDate(2, DateTimeUtils.convertDate(landingDate));
            statement.setDate(3, DateTimeUtils.convertDate(activityDate));
            statement.setInt(4, activityNumber);

            ResultSet rs = statement.executeQuery();
            captureElementaires = new ArrayList<ElementaryCatch>();

            while (rs.next()) {
                captureElementaires.add(factory(rs));
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

        return captureElementaires;
    }

    public List<ElementaryCatch> findAllCatchesOfActivity(Activity activite) throws AvdthDriverException {
        ArrayList<ElementaryCatch> captureElementaires = null;

        String query = "select * from CAPT_ELEM "
                + " where C_BAT = ? and D_DBQ = ? "
                + " and D_ACT = ? and N_ACT = ? ";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, activite.getVessel().getCode());

            statement.setDate(2, DateTimeUtils.convertDate(activite.getLandingDate()));
            statement.setDate(3, DateTimeUtils.convertDate(activite.getDate()));
            statement.setInt(4, activite.getNumber());

            ResultSet rs = statement.executeQuery();
            captureElementaires = new ArrayList<ElementaryCatch>();

            while (rs.next()) {
                captureElementaires.add(factory(rs));
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
        return captureElementaires;
    }

    @Override
    public boolean insert(ElementaryCatch captureElementaire) {
        PreparedStatement statement = null;
        String query = "insert into CAPT_ELEM "
                + " (C_BAT, D_DBQ, D_ACT, N_ACT, N_CAPT, C_ESP, C_CAT_T, V_POIDS_CAPT) "
                + " values (?, ?, ?, ?, ?, ?, ?, ?) ";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, captureElementaire.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(captureElementaire.getLandingDate()));
            statement.setDate(3, DateTimeUtils.convertDate(captureElementaire.getActivityDate()));
            statement.setInt(4, captureElementaire.getActivityNumber());
            statement.setLong(5, captureElementaire.getIndex());
            statement.setInt(6, captureElementaire.getSizeCategory().getSpecies().getCode());
            statement.setInt(7, captureElementaire.getSizeCategory().getCode());
            statement.setDouble(8, captureElementaire.getCatchWeight());
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
    public boolean update(ElementaryCatch elementaryCatch) {
        return delete(elementaryCatch) && insert(elementaryCatch);
    }

    @Override
    public boolean delete(ElementaryCatch elementaryCatch) {
        PreparedStatement statement = null;
        String query = "delete from CAPT_ELEM "
                + " where C_BAT = ? and D_DBQ = ? "
                + " and D_ACT = ? and N_ACT = ? and N_CAPT = ?";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);
            statement.setInt(1, elementaryCatch.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(elementaryCatch.getLandingDate()));
            statement.setDate(3, DateTimeUtils.convertDate(elementaryCatch.getActivityDate()));
            statement.setInt(4, elementaryCatch.getActivityNumber());
            statement.setLong(5, elementaryCatch.getIndex());
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

    public ArrayList<ElementaryCatch> findAllCatchesOfTrip(Trip trip) throws AvdthDriverException {
        ArrayList<ElementaryCatch> catches = null;

        String query = "select * from CAPT_ELEM "
                + " where C_BAT = ? and D_DBQ = ? "
                + " order by N_CAPT asc";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, trip.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(trip.getLandingDate()));

            ResultSet rs = statement.executeQuery();
            catches = new ArrayList<ElementaryCatch>();

            while (rs.next()) {
                catches.add(factory(rs));
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
        return catches;
    }
}
