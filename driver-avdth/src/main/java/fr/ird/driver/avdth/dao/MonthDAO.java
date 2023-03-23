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
package fr.ird.driver.avdth.dao;

import fr.ird.common.JDBCUtilities;
import fr.ird.driver.avdth.business.Month;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table <em>MOIS</em>.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class MonthDAO extends AbstractDAO<Month> {

    @Override
    public boolean insert(Month t) {
        throw new UnsupportedOperationException("Not supported because the «Month» class  represents a referentiel.");
    }

    @Override
    public boolean delete(Month t) {
        throw new UnsupportedOperationException("Not supported because the «Month» class  represents a referentiel.");
    }

    @Override
    protected Month factory(ResultSet rs) throws SQLException, AvdthDriverException {
        Month month = new Month();
        month.setNumber(rs.getInt("N_MOIS"));
        month.setShortName(rs.getString("L4C_MOIS"));
        month.setName(rs.getString("L_MOIS"));
        return month;
    }

    public Month findMonth(int code) throws AvdthDriverException {
        Month month = null;
        PreparedStatement statement = null;
        try {
            String query = "select * from MOIS "
                    + " where N_MOIS = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                month = factory(rs);
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
        return month;
    }

}
