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
import fr.ird.driver.avdth.business.Operation;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table OPERA.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class OperationDAO extends AbstractDAO<Operation> {

    public OperationDAO() {
        super();
    }

    @Override
    public boolean insert(Operation t) {
        throw new UnsupportedOperationException("Not supported because the «Operation» class  represents a referentiel.");
    }

    @Override
    public boolean delete(Operation t) {
        throw new UnsupportedOperationException("Not supported because the «Operation» class  represents a referentiel.");
    }

    /**
     *
     * @param code
     * @return
     */
    public Operation findOperationByCode(int code) throws AvdthDriverException {
        Operation operation = null;
        PreparedStatement statement = null;
        try {

            String query = "select * from OPERA "
                    + " where C_OPERA = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                operation = factory(rs);

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
        return operation;
    }

    @Override
    protected Operation factory(ResultSet rs) throws SQLException, AvdthDriverException {
        Operation operation = new Operation();
        operation.setCode(rs.getInt("C_OPERA"));
        operation.setShortName(rs.getString("L_OP8L"));
        operation.setName(rs.getString("L_OPERA"));
        return operation;
    }

}
