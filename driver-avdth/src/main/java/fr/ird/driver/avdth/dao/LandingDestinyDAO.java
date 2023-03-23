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
import fr.ird.driver.avdth.business.LandingDestiny;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table LOT_COM_DESTIN.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 3.5
 * @date 8 septembre 2014
 */
public class LandingDestinyDAO extends AbstractDAO<LandingDestiny> {

    public LandingDestinyDAO() {
        super();
    }

    @Override
    public LandingDestiny factory(ResultSet rs) throws SQLException {
        LandingDestiny landingDestiny = new LandingDestiny();
        landingDestiny.setCode(rs.getInt("C_DEST"));
        landingDestiny.setName(rs.getString("L_DEST"));
        landingDestiny.setStatut(rs.getBoolean("STATUT"));
        return landingDestiny;
    }

    public LandingDestiny findLandingDestinyByCode(int code) {
        LandingDestiny landingDestiny = null;
        String query = "select * from LOT_COM_DESTIN "
                + " where C_DEST = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                landingDestiny = factory(rs);
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
        return landingDestiny;
    }

    @Override
    public boolean insert(LandingDestiny t) {
        throw new UnsupportedOperationException("Not supported because the «LandingDestiny» class  represents a referentiel.");
    }

    @Override
    public boolean delete(LandingDestiny t) {
        throw new UnsupportedOperationException("Not supported because the «LandingDestiny» class  represents a referentiel.");
    }

}
