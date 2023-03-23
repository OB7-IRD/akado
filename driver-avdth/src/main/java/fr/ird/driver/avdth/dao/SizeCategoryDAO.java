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

import fr.ird.common.JDBCUtilities;
import fr.ird.driver.avdth.business.Species;
import fr.ird.driver.avdth.business.SizeCategory;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table CAT_TAILLE.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class SizeCategoryDAO extends AbstractDAO<SizeCategory> {

    public SizeCategoryDAO() {
        super();
    }

    @Override
    public boolean insert(SizeCategory t) {
        throw new UnsupportedOperationException("Not supported because the «SizeCategory» class  represents a referentiel.");
    }

    @Override
    public boolean delete(SizeCategory t) {
        throw new UnsupportedOperationException("Not supported because the «SizeCategory» class  represents a referentiel.");
    }

    public SizeCategory findSizeCategoryBySpecies(Species espece, Integer categorieTaille) throws AvdthDriverException {
        return findSizeCategoryByCode(espece.getCode(), categorieTaille);

    }

    public SizeCategory findSizeCategoryByCode(int codeEspece, int codeCatTaille) throws AvdthDriverException {
        SizeCategory category = null;

        String query = "select * from CAT_TAILLE "
                + " where C_CAT_T = ? and C_ESP = ? ";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codeCatTaille);
            statement.setInt(2, codeEspece);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                category = factory(rs);
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
        return category;
    }

    @Override
    protected SizeCategory factory(ResultSet rs) throws SQLException, AvdthDriverException {
        SizeCategory category = new SizeCategory();
        category.setCode(rs.getInt("C_CAT_T"));
        SpeciesDAO especeDAO = new SpeciesDAO();
        Species espece = especeDAO.findSpeciesByCode(rs.getInt("C_ESP"));
        category.setSpecies(espece);
        category.setName(rs.getString("L_CAT_T"));
        category.setAverageWeight(rs.getFloat("V_POIDS_M"));
        category.setStatus(rs.getBoolean("STATUT"));
        return category;
    }

}
