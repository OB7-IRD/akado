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
import fr.ird.common.log.LogService;
import fr.ird.driver.avdth.business.Harbour;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table PORT.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 *
 */
public class HarbourDAO extends AbstractDAO<Harbour> {

    public HarbourDAO() {
        super();
    }

    /**
     *
     * @param locode
     * @return
     * @throws fr.ird.driver.avdth.common.exception.AvdthDriverException
     */
    public int findCodeHarbourByLocode(String locode) throws AvdthDriverException {
        Harbour p = findHarbourByLocode(locode);
        if (p != null) {
            return p.getCode();
        }
        return -1;
    }

    /**
     *
     * @param locode
     * @return
     * @throws fr.ird.driver.avdth.common.exception.AvdthDriverException
     */
    public Harbour findHarbourByLocode(String locode) throws AvdthDriverException {
        Harbour harbour = null;

        String query = "select * from PORT "
                + " where C_LOCODE = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, locode);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                harbour = factory(rs);

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
        if (harbour == null) {
            //System.out.println("Il n'existe pas de port ayant pour locode " + locode);
        }
        return harbour;
    }

    /**
     *
     * @param code
     * @return
     */
    public Harbour findHarbourByCode(int code) {
        Harbour harbour = null;

        String query = "select * from PORT "
                + " where C_PORT = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, code);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                harbour = factory(rs);

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
        return harbour;
    }

    @Override
    public boolean insert(Harbour harbour) {

        PreparedStatement statement = null;
        String query = "insert into PORT (C_PORT, L_PORT, V_LAT_P, V_LON_P, L_COM_P, C_LOCODE) "
                + " values (?, ?, ?, ?, ?, ?) ";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, harbour.getCode());
            statement.setString(2, harbour.getName());
            statement.setFloat(3, harbour.getLatitude());
            statement.setFloat(4, harbour.getLongitude());
            statement.setString(5, harbour.getComment());
            statement.setString(6, harbour.getLocode());
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

    @Override
    public boolean update(Harbour harbour) {
        return delete(harbour) && insert(harbour);
    }

    @Override
    public boolean delete(Harbour harbour) {
        PreparedStatement statement = null;
        String query = "delete from PORT where C_PORT = ? ";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, harbour.getCode());
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

    @Override
    public Harbour factory(ResultSet rs) throws SQLException, AvdthDriverException {
        Harbour harbour = new Harbour();
        harbour.setCode(rs.getInt("C_PORT"));
        harbour.setName(rs.getString("L_PORT"));
        harbour.setLatitude(rs.getFloat("V_LAT_P"));
        harbour.setLongitude(rs.getFloat("V_LON_P"));
        harbour.setComment(rs.getString("L_COM_P"));
        harbour.setLocode(rs.getString("C_LOCODE"));
        harbour.setCountry((new CountryDAO()).findCountryByCode(rs.getInt("C_PAYS")));
        return harbour;
    }
}
