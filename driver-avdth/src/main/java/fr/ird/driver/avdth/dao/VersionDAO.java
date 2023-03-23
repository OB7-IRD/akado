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

import fr.ird.common.DateTimeUtils;
import fr.ird.common.JDBCUtilities;
import fr.ird.common.log.LogService;
import fr.ird.driver.avdth.business.Version;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DAO permettant de faire des requêtes sur la table VERSION.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @version 1.0
 * @date 23 sept. 2013
 */
public class VersionDAO extends AbstractDAO<Version> {

    public VersionDAO() {
        super();
    }

    public Integer getVersionNumber() {
        Integer versionNumber = null;
        String query = "SELECT N_SCH_R from VERSION ";

        Statement stmt = null;
        try {
            stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                versionNumber = rs.getInt("N_SCH_R");
            }
            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    JDBCUtilities.printSQLException(ex);
                }

            }
        }
        return versionNumber;
    }

    public Version getVersion() {
        Version version = null;
        String query = "SELECT * from VERSION";

        Statement stmt = null;

        try {
            stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                version = factory(rs);
            }
            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        } catch (AvdthDriverException ex) {
            LogService.getService(this.getClass()).logApplicationError(ex.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    JDBCUtilities.printSQLException(ex);
                }

            }
        }
        return version;
    }

  
    @Override
    public boolean insert(Version t) {
        throw new UnsupportedOperationException("Not supported because the «Version» class  represents a referentiel.");
    }

    @Override
    public boolean delete(Version t) {
        throw new UnsupportedOperationException("Not supported because the «Version» class  represents a referentiel.");
    }

    @Override
    protected Version factory(ResultSet rs) throws SQLException, AvdthDriverException {
        Version version = new Version();

        version.setVersionSchema(rs.getInt("N_SCH_R"));
        version.setCodificationFilename(rs.getString("L_FIC_CODIF"));
        version.setExportFilename(rs.getString("L_FIC_EXPORT"));
        version.setDate(DateTimeUtils.convertDate(rs.getDate("D_DISPO_BD_GEN")));
        return version;
    }
}
