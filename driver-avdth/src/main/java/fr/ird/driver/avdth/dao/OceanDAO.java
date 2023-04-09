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
 */package fr.ird.driver.avdth.dao;

import fr.ird.common.JDBCUtilities;
import fr.ird.driver.avdth.business.Ocean;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table OCEAN.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @version 1.0
 * @date 26 sept. 2013
 *
 */
public class OceanDAO extends AbstractDAO<Ocean> {
    private static final Logger log = LogManager.getLogger(OceanDAO.class);
    public OceanDAO() {
        super();
    }

    public Ocean findOceanByCode(int code) {
        Ocean ocean = null;
        String query = "select * from OCEAN "
                + " where C_OCEAN = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                ocean = factory(rs);
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
        return ocean;
    }

    @Override
    public boolean insert(Ocean t) {
        throw new UnsupportedOperationException("Not supported because the «Ocean» class represents a referentiel.");
    }

    @Override
    public boolean delete(Ocean t) {
        throw new UnsupportedOperationException("Not supported because the «Ocean» class represents a referentiel.");
    }

    @Override
    public Ocean factory(ResultSet rs) throws SQLException, AvdthDriverException {
        Ocean ocean = new Ocean();
        ocean.setCode(rs.getInt("C_OCEAN"));
        ocean.setLibelle(rs.getString("L_OCEAN"));
        return ocean;
    }

}
