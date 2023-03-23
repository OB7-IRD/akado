/*
 * Copyright (C) 2016 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.driver.avdth.dao.local.market;

import fr.ird.common.JDBCUtilities;
import fr.ird.driver.avdth.business.local.market.PackagingType;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import fr.ird.driver.avdth.dao.AbstractDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table <em>FP_TYPE_COND</em>.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class PackagingTypeDAO extends AbstractDAO<PackagingType> {

    @Override
    public boolean insert(PackagingType t) {
        throw new UnsupportedOperationException("Not supported because the «PackagingType» class  represents a referentiel.");
    }

    @Override
    public boolean delete(PackagingType t) {
        throw new UnsupportedOperationException("Not supported because the «PackagingType» class  represents a referentiel.");
    }

    PackagingType findPackagingTypeByCode(int code) throws AvdthDriverException {
        PackagingType packagingType = null;
        String query = "select * from FP_TYPE_COND "
                + " where C_TYP_CON = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                packagingType = factory(rs);
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
        return packagingType;
    }

    @Override
    protected PackagingType factory(ResultSet rs) throws SQLException, AvdthDriverException {
        PackagingType type = new PackagingType();
        type.setCode(rs.getInt("C_TYP_CON"));
        type.setName(rs.getString("L_TYP_COND"));
        return type;
    }

}
