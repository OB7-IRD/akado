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
import fr.ird.driver.avdth.business.WellDestiny;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table C_DEST.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 3.4.5
 * @date 26 mai 2014
 */
public class WellDestinyDAO extends AbstractDAO<WellDestiny> {

    public WellDestinyDAO() {
        super();
    }

    @Override
    protected WellDestiny factory(ResultSet rs) throws SQLException {
        WellDestiny wellDestiny = new WellDestiny();
        wellDestiny.setCode(rs.getInt("C_DEST"));
        wellDestiny.setName(rs.getString("L_DEST"));
        return wellDestiny;
    }

    public WellDestiny findWellDestinyByCode(int code) {
        WellDestiny wellDestiny = null;
        String query = "select * from DESTIN "
                + " where C_DEST = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                wellDestiny = factory(rs);
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
        return wellDestiny;
    }

    @Override
    public boolean insert(WellDestiny t) {
        throw new UnsupportedOperationException("Not supported because the «WellDestiny» class  represents a referentiel.");
    }

    @Override
    public boolean delete(WellDestiny t) {
        throw new UnsupportedOperationException("Not supported because the «WellDestiny» class  represents a referentiel.");
    }

}
