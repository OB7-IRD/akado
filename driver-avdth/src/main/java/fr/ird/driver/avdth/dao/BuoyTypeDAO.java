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
import fr.ird.driver.avdth.business.BuoyType;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table <em>TYPE_BALISE</em>.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @version 1.0
 * @date 26 sept. 2013
 */
public class BuoyTypeDAO extends AbstractDAO<BuoyType> {

    public BuoyTypeDAO() {
        super();
    }

    public BuoyType findBuoyTypeByCode(int code) throws AvdthDriverException {
        BuoyType typeBalise = null;

        String query = "select * from TYPE_BALISE "
                + " where C_TYP_BALISE = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                typeBalise = factory(rs);
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
        return typeBalise;
    }

    @Override
    public boolean insert(BuoyType t) {
        throw new UnsupportedOperationException("Not supported because the «BuoyType» class  represents a referentiel.");
    }

    @Override
    public boolean delete(BuoyType t) {
        throw new UnsupportedOperationException("Not supported because the «BuoyType» class  represents a referentiel.");
    }

    @Override
    protected BuoyType factory(ResultSet rs) throws SQLException, AvdthDriverException {
        BuoyType typeBalise = new BuoyType();
        typeBalise.setTopiad(rs.getString("C_TOPIAID"));
        typeBalise.setCode(rs.getInt("C_TYP_BALISE"));
        typeBalise.setName(rs.getString("L_TYP_BALISE"));
        typeBalise.setStatus(rs.getBoolean("STATUT"));
        return typeBalise;
    }
}
