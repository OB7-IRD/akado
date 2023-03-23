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
 */package fr.ird.driver.avdth.dao;

import fr.ird.common.DateTimeUtils;
import fr.ird.common.JDBCUtilities;
import fr.ird.driver.avdth.business.ElementaryLanding;
import fr.ird.driver.avdth.business.Trip;
import fr.ird.driver.avdth.business.WeightCategoryLanding;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO permettant de faire des requêtes sur la table <em>LOT_COM</em>.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @version 3.4.4
 * @date 11 déc. 2013
 *
 */
public class ElementaryLandingDAO extends AbstractDAO<ElementaryLanding> {

    public ElementaryLandingDAO() {
        super();
    }

    @Override
    public ElementaryLanding factory(ResultSet rs) throws SQLException {
        ElementaryLanding elementaryLanding = new ElementaryLanding();
        elementaryLanding.setVessel((new VesselDAO().findVesselByCode(rs.getInt("C_BAT"))));
        elementaryLanding.setLandingDate(DateTimeUtils.convertDate(rs.getDate("D_DBQ")));
        elementaryLanding.setIndex(rs.getInt("N_LOT"));
        elementaryLanding.setWeightLanding(rs.getDouble("V_POIDS_LC"));

        WeightCategoryLandingDAO categorieCommercialDAO = new WeightCategoryLandingDAO();
        WeightCategoryLanding categorieCommercial = categorieCommercialDAO.findCategorieCommercialeByCode(rs.getInt("C_ESP"), rs.getInt("C_CAT_C"));
        elementaryLanding.setWeightCategoryLanding(categorieCommercial);
        return elementaryLanding;
    }

    public List<ElementaryLanding> findAllElementaryLanding(Trip maree) {
        ArrayList<ElementaryLanding> lotsCommerciaux = null;

        String query = "select * from LOT_COM "
                + " where C_BAT = ? and D_DBQ = ? "
                + " order by N_LOT asc";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, maree.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(maree.getLandingDate()));

            ResultSet rs = statement.executeQuery();
            lotsCommerciaux = new ArrayList<ElementaryLanding>();

            while (rs.next()) {
                lotsCommerciaux.add(factory(rs));
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
        return lotsCommerciaux;

    }

    @Override
    public boolean insert(ElementaryLanding elementaryLanding) {
        PreparedStatement statement = null;
        String query = "insert into LOT_COM "
                + " (C_BAT, D_DBQ, N_LOT, C_ESP, C_CAT_C, V_POIDS_LC) "
                + " values (?, ?, ?, ?, ?, ?) ";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, elementaryLanding.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(elementaryLanding.getLandingDate()));
            statement.setLong(3, elementaryLanding.getIndex());
            statement.setInt(4, elementaryLanding.getWeightCategoryLanding().getSpecies().getCode());
            statement.setInt(5, elementaryLanding.getWeightCategoryLanding().getCode());
            statement.setDouble(6, elementaryLanding.getWeightLanding());
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
    public boolean update(ElementaryLanding elementaryLanding) {
        return delete(elementaryLanding) && insert(elementaryLanding);
    }

    @Override
    public boolean delete(ElementaryLanding elementaryLanding) {
        PreparedStatement statement = null;
        String query = "delete from LOT_COM "
                + " where C_BAT = ? and D_DBQ = ? "
                + " and N_LOT = ?";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, elementaryLanding.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(elementaryLanding.getLandingDate()));
            statement.setLong(3, elementaryLanding.getIndex());
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
