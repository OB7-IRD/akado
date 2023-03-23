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
import fr.ird.common.log.LogService;
import fr.ird.driver.avdth.business.Country;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table <em>PAYS</em>.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @version 1.0
 * @date 25 sept. 2013
 */
public class CountryDAO extends AbstractDAO<Country> {

    public CountryDAO() {
        super();
    }

    public Country findCountryByCode(int code) throws AvdthDriverException {
        Country pays = null;

        String query = "select * from PAYS "
                + " where C_PAYS = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                pays = factory(rs);
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
        return pays;
    }

    public Country findCountry(String codeIsoA2) {
        Country pays = null;

        String query = "select * from PAYS "
                + " where C_ISO3166_A2 = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, codeIsoA2);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                pays = factory(rs);
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        } catch (AvdthDriverException ex) {
            LogService.getService(this.getClass()).logApplicationError(ex.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    JDBCUtilities.printSQLException(ex);
                }

            }
        }
        return pays;
    }

    public Country findCountryByIso3(String codeIsoA3) {
        Country pays = null;

        String query = "select * from PAYS "
                + " where C_ISO3166_A3 = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, codeIsoA3);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                pays = factory(rs);
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        } catch (AvdthDriverException ex) {
            LogService.getService(this.getClass()).logApplicationError(ex.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    JDBCUtilities.printSQLException(ex);
                }

            }
        }
        return pays;
    }

    @Override
    public boolean insert(Country t) {
        throw new UnsupportedOperationException("Not supported because the «Country» class  represents a referentiel.");
    }

    @Override
    public boolean delete(Country t) {
        throw new UnsupportedOperationException("Not supported because the «Country» class  represents a referentiel.");
    }

    @Override
    public Country factory(ResultSet rs) throws SQLException, AvdthDriverException {
        Country country = new Country();
        country.setCode(rs.getInt("C_PAYS"));
        country.setName(rs.getString("L_PAYS"));
        country.setCodeIso2(rs.getString("C_ISO3166_A2"));
        country.setCodeIso3(rs.getString("C_ISO3166_A3"));
        return country;
    }

}
