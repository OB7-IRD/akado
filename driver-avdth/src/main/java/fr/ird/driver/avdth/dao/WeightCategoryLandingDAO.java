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
import fr.ird.driver.avdth.business.Species;
import fr.ird.driver.avdth.business.WeightCategoryLanding;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table CAT_COM.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @version 3.4.4
 * @date 11 déc. 2013
 */
public class WeightCategoryLandingDAO extends AbstractDAO<WeightCategoryLanding> {

    public WeightCategoryLandingDAO() {
        super();
    }

    public WeightCategoryLanding findCategorieCommercialeBySpecies(Species espece, Integer codeCategorieCommerciale) {
        return findCategorieCommercialeByCode(espece.getCode(), codeCategorieCommerciale);

    }

    @Override
    protected WeightCategoryLanding factory(ResultSet rs) throws SQLException {
        WeightCategoryLanding categorieCommerciale = new WeightCategoryLanding();
        categorieCommerciale.setCode(rs.getInt("C_CAT_C"));
        SpeciesDAO especeDAO = new SpeciesDAO();
        Species espece = especeDAO.findSpeciesByCode(rs.getInt("C_ESP"));
        categorieCommerciale.setSpecies(espece);
        categorieCommerciale.setSovName(rs.getString("L_CC_SOV"));
        categorieCommerciale.setStarName(rs.getString("L_CC_STAR"));
        categorieCommerciale.setStatus(rs.getBoolean("STATUT"));
        return categorieCommerciale;
    }

    public WeightCategoryLanding findCategorieCommercialeByCode(int codeEspece, int codeCatCom) {
        WeightCategoryLanding categorieCommerciale = null;

        String query = "select * from CAT_COM "
                + " where C_CAT_C = ? and C_ESP = ? ";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codeCatCom);
            statement.setInt(2, codeEspece);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                categorieCommerciale = factory(rs);
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
        return categorieCommerciale;
    }

    @Override
    public boolean insert(WeightCategoryLanding t) {
        throw new UnsupportedOperationException("Not supported because the «WeightCategoryLanding» class  represents a referentiel."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(WeightCategoryLanding t) {
        throw new UnsupportedOperationException("Not supported because the «WeightCategoryLanding» class  represents a referentiel."); //To change body of generated methods, choose Tools | Templates.
    }
}
