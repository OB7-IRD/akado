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
import fr.ird.driver.avdth.business.Species;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table ESPECE.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class SpeciesDAO extends AbstractDAO<Species> {

    public SpeciesDAO() {
        super();
    }

    /**
     * Select species with FAO code but remove the discard species from the
     * selection.
     *
     * @param codeSpecieFAO the fao code to find
     * @return the species or null
     */
    public Species findSpeciesByFAOCode(String codeSpecieFAO) {
        Species espece = null;
        String query = "select * from ESPECE "
                + " where C_ESP_3L = ? and (C_ESP < 800 or C_ESP > 999)";
        PreparedStatement statement = null;
        try {

            statement = connection.prepareStatement(query);
            statement.setString(1, codeSpecieFAO);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                espece = factory(rs);
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
        return espece;

    }

    /**
     * Select species with AVDTH code.
     *
     * @param code the avdth ID
     * @return a species or null
     */
    public Species findSpeciesByCode(int code) {
        Species espece = null;
        String query = "select * from ESPECE "
                + " where C_ESP = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                espece = new Species();
                espece.setCode(rs.getInt("C_ESP"));
                espece.setCodeISO(rs.getString("C_ESP_3L"));
                espece.setName(rs.getString("L_ESP"));
                espece.setScientificName(rs.getString("L_ESP_S"));

                espece.setStatus(rs.getBoolean("STATUT"));

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
        return espece;
    }

    @Override
    public boolean insert(Species t) {
        throw new UnsupportedOperationException("Not supported because the «Species» class  represents a referentiel.");
    }

    @Override
    public boolean delete(Species t) {
        throw new UnsupportedOperationException("Not supported because the «Species» class  represents a referentiel.");
    }

    @Override
    protected Species factory(ResultSet rs) throws SQLException, AvdthDriverException {
        Species s = new Species();
        s.setCode(rs.getInt("C_ESP"));
        s.setCodeISO(rs.getString("C_ESP_3L"));
        s.setName(rs.getString("L_ESP"));
        s.setScientificName(rs.getString("L_ESP_S"));

        s.setStatus(rs.getBoolean("STATUT"));
        return s;
    }
}
