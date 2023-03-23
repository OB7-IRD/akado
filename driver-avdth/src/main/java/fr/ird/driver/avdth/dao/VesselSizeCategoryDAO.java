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

import fr.ird.common.JDBCUtilities;
import fr.ird.driver.avdth.business.VesselSizeCategory;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO permettant de faire des requêtes sur la table CAT_BATEAU.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 *
 */
public class VesselSizeCategoryDAO extends AbstractDAO<VesselSizeCategory> {

    public VesselSizeCategoryDAO() {
        super();
    }

    public List<VesselSizeCategory> getAllVesselSizeCategory() {
        ArrayList<VesselSizeCategory> vesselSizeCategories = null;

        String query = "select * from CAT_BATEAU ";
        Statement statement = null;
        try {
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);
            vesselSizeCategories = new ArrayList<VesselSizeCategory>();
            VesselSizeCategory categorieBateau;
            while (rs.next()) {
                categorieBateau = new VesselSizeCategory();
                categorieBateau.setCodeCategorieBateau(rs.getInt("C_CAT_B"));
                categorieBateau.setLibelleCapacite(rs.getString("L_CAPAC"));
                categorieBateau.setLibelleJauge(rs.getString("L_JAUGE"));
                vesselSizeCategories.add(categorieBateau);
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
        return vesselSizeCategories;

    }

    VesselSizeCategory findVesselSizeCategoryByCode(int code) throws AvdthDriverException {
        VesselSizeCategory vesselSizeCategory = null;
        PreparedStatement statement = null;

        String query = "select * from CAT_BATEAU where C_CAT_B = ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                vesselSizeCategory = factory(rs);
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
        return vesselSizeCategory;
    }
    @Override
    public boolean insert(VesselSizeCategory t) {
        throw new UnsupportedOperationException("Not supported because the «VesselSizeCategory» class  represents a referentiel.");
    }

    @Override
    public boolean delete(VesselSizeCategory t) {
        throw new UnsupportedOperationException("Not supported because the «VesselSizeCategory» class  represents a referentiel.");
    }
    @Override
    protected VesselSizeCategory factory(ResultSet rs) throws SQLException, AvdthDriverException {
        VesselSizeCategory vesselSizeCategory = new VesselSizeCategory();
        vesselSizeCategory.setCodeCategorieBateau(rs.getInt("C_CAT_B"));
        vesselSizeCategory.setLibelleCapacite(rs.getString("L_CAPAC"));
        vesselSizeCategory.setLibelleJauge(rs.getString("L_JAUGE"));
        return vesselSizeCategory;
    }

}
