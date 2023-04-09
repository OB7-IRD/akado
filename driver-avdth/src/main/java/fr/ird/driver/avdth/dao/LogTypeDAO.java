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
import fr.ird.driver.avdth.business.LogType;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table TYPE_OBJET.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class LogTypeDAO extends AbstractDAO<LogType> {
    private static final Logger log = LogManager.getLogger(LogTypeDAO.class);
    public LogTypeDAO() {
        super();
    }

    public LogType findLogTypeByCode(int code) {
        LogType typeObjet = null;

        String query = "SELECT * from TYPE_OBJET "
                + " where C_TYP_OBJET = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                typeObjet = factory(rs);
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
        return typeObjet;
    }

    @Override
    protected LogType factory(ResultSet rs) throws SQLException, AvdthDriverException {
        LogType typeObjet = new LogType();
        typeObjet.setCode(rs.getInt("C_TYP_OBJET"));
        typeObjet.setRfmoCode(rs.getString("C_RFMOS"));
        typeObjet.setLibelle(rs.getString("L_TYP_OBJET"));
        typeObjet.setStatus(rs.getBoolean("STATUT"));
        return typeObjet;
    }

    @Override
    public boolean insert(LogType t) {
        throw new UnsupportedOperationException("Not supported because the «LogType» class  represents a referentiel.");
    }

    @Override
    public boolean delete(LogType t) {
        throw new UnsupportedOperationException("Not supported because the «LogType» class  represents a referentiel.");
    }

}
