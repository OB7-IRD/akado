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
import fr.ird.driver.avdth.business.GeographicalAreaLimit;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
public class GeographicalAreaLimitDAO extends AbstractDAO<GeographicalAreaLimit> {
    private static final Logger log = LogManager.getLogger(GeographicalAreaLimitDAO.class);
    public GeographicalAreaLimitDAO() {
        super();
    }

    @Override
    public boolean insert(GeographicalAreaLimit t) {
        throw new UnsupportedOperationException("Not supported because the «GeographicalAreaLimit» class  represents a referentiel.");
    }

    @Override
    public boolean delete(GeographicalAreaLimit t) {
        throw new UnsupportedOperationException("Not supported because the «GeographicalAreaLimit» class  represents a referentiel.");
    }

    public GeographicalAreaLimit findGeographicalAreaLimit(int geographicalAreaCode, int number) {
        GeographicalAreaLimit geographicalAreaLimit = null;
        String query = "select * from LIM_ZOG "
                + " where C_ZONE_GEO = ? AND N_RECT = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, geographicalAreaCode);
            statement.setInt(2, number);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                geographicalAreaLimit = factory(rs);

            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        } catch (AvdthDriverException ex) {
            log.error(ex.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    JDBCUtilities.printSQLException(ex);
                }

            }
        }
        return geographicalAreaLimit;
    }

    @Override
    protected GeographicalAreaLimit factory(ResultSet rs) throws SQLException, AvdthDriverException {
        GeographicalAreaLimit geographicalAreaLimit = new GeographicalAreaLimit();
        GeographicalAreaDAO gadao = new GeographicalAreaDAO();
        geographicalAreaLimit.setGeographicalArea(gadao.findGeographicalAreaByCode(rs.getInt("C_ZONE_GEO")));
        geographicalAreaLimit.setNumber(rs.getInt("N_RECT"));
        geographicalAreaLimit.setOcean((new OceanDAO()).findOceanByCode(rs.getInt("C_OCEA")));
        geographicalAreaLimit.setZoneType(rs.getInt("C_TYP_Z"));
        geographicalAreaLimit.setQuadrant(rs.getInt("Q_RECT"));
        geographicalAreaLimit.setMinLatitude(rs.getInt("V_LAT_MN"));
        geographicalAreaLimit.setMaxLatitude(rs.getInt("V_LAT_MX"));
        geographicalAreaLimit.setMinLongitude(rs.getInt("V_LON_MN"));
        geographicalAreaLimit.setMaxLongitude(rs.getInt("V_LON_MX"));

        return geographicalAreaLimit;
    }

}
