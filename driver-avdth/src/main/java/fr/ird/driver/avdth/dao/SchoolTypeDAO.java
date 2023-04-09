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
import fr.ird.driver.avdth.business.SchoolType;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table TYPE_BANC.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 *
 */
public class SchoolTypeDAO extends AbstractDAO<SchoolType> {
    private static final Logger log = LogManager.getLogger(SchoolTypeDAO.class);
    public SchoolTypeDAO() {
        super();
    }

    public SchoolType findSchoolTypeByCode(int code) {
        SchoolType typeBanc = null;

        String query = "select * from TYPE_BANC "
                + " where C_TBANC = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                typeBanc = factory(rs);
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
        return typeBanc;
    }

    @Override
    public boolean insert(SchoolType t) {
        throw new UnsupportedOperationException("Not supported because the «SchoolType» class  represents a referentiel.");
    }

    @Override
    public boolean delete(SchoolType t) {
        throw new UnsupportedOperationException("Not supported because the «SchoolType» class  represents a referentiel.");
    }

    @Override
    public SchoolType factory(ResultSet rs) throws SQLException, AvdthDriverException {
        SchoolType st = new SchoolType();
        st.setCode(rs.getInt("C_TBANC"));
        st.setLibelle4(rs.getString("L_TBANC4L"));
        st.setLibelle(rs.getString("L_TBANC"));
        return st;
    }

}
