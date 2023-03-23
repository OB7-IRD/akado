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

import fr.ird.common.JDBCUtilities;
import fr.ird.driver.avdth.business.WeightCategoryWellPlan;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table CAT_POIDS.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 0.0
 * @date 17 juin 2014
 */
public class WeightCategoryWellPlanDAO extends AbstractDAO<WeightCategoryWellPlan> {

    public WeightCategoryWellPlanDAO() {
        super();
    }

    @Override
    public boolean insert(WeightCategoryWellPlan t) {
        throw new UnsupportedOperationException("Not supported because the «WeightCategoryWellPlan» class  represents a referentiel.");
    }

    @Override
    public boolean delete(WeightCategoryWellPlan t) {
        throw new UnsupportedOperationException("Not supported because the «WeightCategoryWellPlan» class  represents a referentiel.");
    }

    public WeightCategoryWellPlan findWeightCategoryByCode(int code) {

        WeightCategoryWellPlan weightCategoryWellPlan = new WeightCategoryWellPlan();

        String query = "select * from CAT_POIDS "
                + " where C_CAT_POIDS = ? ";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                weightCategoryWellPlan = factory(rs);
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
        return weightCategoryWellPlan;
    }

    @Override
    protected WeightCategoryWellPlan factory(ResultSet rs) throws SQLException {
        WeightCategoryWellPlan wcwp = new WeightCategoryWellPlan();
        wcwp.setCode(rs.getInt("C_CAT_POIDS"));
        wcwp.setLibelle(rs.getString("L_CAT_POIDS"));
        return wcwp;
    }

}
