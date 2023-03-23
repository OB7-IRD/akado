/**
 * Copyright (C) 2014 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.driver.anapo.dao;

import fr.ird.common.JDBCUtilities;
import fr.ird.driver.anapo.business.BatVMS;
import fr.ird.driver.anapo.service.ANAPOService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO to make queries on the table <em>BatVMS</em>.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @version 1.0
 * @since 1.0
 * @date 14 févr. 2014
 */
public class BatVMSDAO {

    private final Connection connection;

    public BatVMSDAO() {
        this.connection = ANAPOService.getService().getConnection();
    }

    public boolean insert(BatVMS batVMS) {

        PreparedStatement statement = null;
        String query = "insert into BATVMS (C_BAT, D_DEB, D_FIN, L_BAT ) "
                + " values (?, ?, ?, ?) ";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);
            statement.setInt(1, batVMS.getVesselId());
            statement.setDate(2, new java.sql.Date(batVMS.getBeginningDate().toDate().getTime()));
            statement.setDate(3, new java.sql.Date(batVMS.getEndingDate().toDate().getTime()));
            statement.setString(4, batVMS.getVesselName());

            statement.execute();
            statement.close();

            connection.commit();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
            try {
                connection.rollback();
            } catch (SQLException e) {
                JDBCUtilities.printSQLException(e);
            }
            return false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                JDBCUtilities.printSQLException(e);
                return false;
            }
        }
        return true;

    }

    public boolean update(BatVMS batVMS) {
        return delete(batVMS) && insert(batVMS);
    }

    public boolean delete(BatVMS batVMS) {
        PreparedStatement statement = null;
        String query = "delete from BATVMS "
                + " where C_BAT = ? and D_DEB = ? and D_FIN = ? and L_BAT= ?";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);
            statement.setInt(1, batVMS.getVesselId());
            statement.setDate(2, new java.sql.Date(batVMS.getBeginningDate().toDate().getTime()));
            statement.setDate(3, new java.sql.Date(batVMS.getEndingDate().toDate().getTime()));
            statement.setString(4, batVMS.getVesselName());

            statement.execute();
            statement.close();

        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
            try {
                connection.rollback();
            } catch (SQLException e) {
                JDBCUtilities.printSQLException(e);
            }
            return false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                JDBCUtilities.printSQLException(e);
                return false;
            }
        }
        return true;
    }

    public boolean isExist(BatVMS batVMS) {
        boolean isExist = false;
        PreparedStatement statement = null;
        String query = "select * from BATVMS "
                + " where C_BAT = ? and D_DEB = ? and D_FIN = ? and L_BAT= ?";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);
            statement.setInt(1, batVMS.getVesselId());
            statement.setDate(2, new java.sql.Date(batVMS.getBeginningDate().toDate().getTime()));
            statement.setDate(3, new java.sql.Date(batVMS.getEndingDate().toDate().getTime()));
            statement.setString(4, batVMS.getVesselName());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                isExist = true;
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
        return isExist;
    }

}
