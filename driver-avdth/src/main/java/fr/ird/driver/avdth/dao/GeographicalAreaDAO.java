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
import fr.ird.common.log.LogService;
import fr.ird.driver.avdth.business.GeographicalArea;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table ZONE_GEO.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 3.4.5
 * @date 26 mai 2014
 */
public class GeographicalAreaDAO extends AbstractDAO<GeographicalArea> {

    public GeographicalAreaDAO() {
        super();
    }

    @Override
    public boolean insert(GeographicalArea t) {
        throw new UnsupportedOperationException("Not supported because the «GeographicalArea» class  represents a referentiel.");
    }

    @Override
    public boolean delete(GeographicalArea t) {
        throw new UnsupportedOperationException("Not supported because the «GeographicalArea» class  represents a referentiel.");
    }

    public GeographicalArea findGeographicalAreaByCode(int code) {
        GeographicalArea geographicalArea = null;
        String query = "select * from ZONE_GEO "
                + " where C_ZONE_GEO = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                geographicalArea = factory(rs);

            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        } catch (AvdthDriverException ex) {
            LogService.getService(GeographicalAreaDAO.class).logApplicationError(ex.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    JDBCUtilities.printSQLException(ex);
                }

            }
        }
        return geographicalArea;
    }

    @Override
    protected GeographicalArea factory(ResultSet rs) throws SQLException, AvdthDriverException {
        GeographicalArea geographicalArea = new GeographicalArea();
        geographicalArea.setCode(rs.getInt("C_ZONE_GEO"));
        geographicalArea.setName(rs.getString("L_ZONE_GEO"));
        return geographicalArea;
    }

}
