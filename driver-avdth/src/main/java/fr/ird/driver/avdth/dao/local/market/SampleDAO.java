/*
 * Copyright (C) 2016 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.driver.avdth.dao.local.market;

import fr.ird.common.DateTimeUtils;
import fr.ird.common.JDBCUtilities;
import fr.ird.driver.avdth.business.local.market.Sample;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import fr.ird.driver.avdth.dao.AbstractDAO;
import fr.ird.driver.avdth.dao.VesselDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table <em>FP_ECHANTILLON</em>.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class SampleDAO extends AbstractDAO<Sample> {

    @Override
    public boolean insert(Sample s) {
        PreparedStatement statement = null;
        String query = "insert into FP_ECHANTILLON "
                + " (C_BAT, D_DBQ, N_ECH, D_ECH, C_BAT_REEL) "
                + " values (?,?,?,?,?)";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, s.getVessel().getCode());

            statement.setDate(2, DateTimeUtils.convertDate(s.getLandingDate()));
            statement.setString(3, s.getNumber());
            statement.setDate(4, DateTimeUtils.convertDate(s.getSampleDate()));
            statement.setInt(5, s.getRealVessel().getCode());

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
    public boolean delete(Sample t) {
        PreparedStatement statement = null;
        String query = "delete from FP_ECHANTILLON "
                + " where C_BAT = ? and D_DBQ = ? and N_ECH = ?";

        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, t.getVessel().getCode());

            statement.setDate(2, DateTimeUtils.convertDate(t.getLandingDate()));
            statement.setString(3, t.getNumber());
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
    public Sample factory(ResultSet rs) throws SQLException, AvdthDriverException {
        Sample sample = new Sample();

        sample.setVessel((new VesselDAO().findVesselByCode(rs.getInt("C_BAT"))));
        sample.setLandingDate(DateTimeUtils.convertDate(rs.getDate("D_DBQ")));
        sample.setNumber(rs.getString("N_ECH"));
        sample.setSampleDate(DateTimeUtils.convertDate(rs.getDate("D_D_ECH")));
        sample.setRealVessel((new VesselDAO().findVesselByCode(rs.getInt("C_BAT_REEL"))));

        return sample;
    }

}
