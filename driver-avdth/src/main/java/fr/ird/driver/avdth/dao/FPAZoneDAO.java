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
import fr.ird.driver.avdth.business.Country;
import fr.ird.driver.avdth.business.FPAZone;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table <em>ZONE_FPA</em>.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class FPAZoneDAO extends AbstractDAO<FPAZone> {

    @Override
    public boolean insert(FPAZone t) {
        throw new UnsupportedOperationException("Not supported because the «FPAZone» class  represents a referentiel.");
    }

    @Override
    public boolean delete(FPAZone t) {
        throw new UnsupportedOperationException("Not supported because the «FPAZone» class  represents a referentiel.");
    }

    @Override
    protected FPAZone factory(ResultSet rs) throws SQLException, AvdthDriverException {
        FPAZone zone = new FPAZone();
        zone.setCode(rs.getInt("C_Z_FPA"));
        zone.setName(rs.getString("L_Z_FPA"));
        CountryDAO countryDAO = new CountryDAO();
        Country country = countryDAO.findCountryByCode(rs.getInt("C_PAYS"));
        zone.setCountry(country);
        zone.setSubdiv(rs.getString("C_SUBDIV"));
        zone.setStatus(rs.getBoolean("STATUT"));
        return zone;
    }

    public FPAZone findFPAZone(int code) throws AvdthDriverException {
        FPAZone zone = null;
        PreparedStatement statement = null;
        try {
            String query = "select * from ZONE_FPA "
                    + " where C_Z_FPA = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                zone = factory(rs);
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
        return zone;
    }

    public FPAZone findFPAZoneBySubdiv(String economicZone) throws AvdthDriverException {
        FPAZone zone = null;
        PreparedStatement statement = null;
        try {
            String query = "select * from ZONE_FPA "
                    + " where C_SUBDIV = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, economicZone);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                zone = factory(rs);
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
        return zone;
    }

    public FPAZone findFPAZoneByCountry(int code) throws AvdthDriverException {
        FPAZone zone = null;
        PreparedStatement statement = null;
        try {
            String query = "select * from ZONE_FPA "
                    + " where C_PAYS = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                zone = factory(rs);
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
        return zone;
    }

}
