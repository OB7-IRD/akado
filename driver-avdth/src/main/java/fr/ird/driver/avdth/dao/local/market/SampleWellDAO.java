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
import fr.ird.driver.avdth.business.local.market.SampleWell;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import fr.ird.driver.avdth.dao.AbstractDAO;
import fr.ird.driver.avdth.dao.VesselDAO;
import fr.ird.driver.avdth.dao.WellDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.joda.time.DateTime;

/**
 * DAO permettant de faire des requêtes sur la table <em>FP_ECH_CUVE</em>.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class SampleWellDAO extends AbstractDAO<SampleWell> {

    @Override
    public boolean insert(SampleWell t) {
        PreparedStatement statement = null;
        String query = "insert into FP_ECH_FREQT "
                + " (C_BAT, D_DBQ, N_ECH, N_CUVE, C_POSITION) "
                + " values (?,?,?,?,?)";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, t.getVessel().getCode());

            statement.setDate(2, DateTimeUtils.convertDate(t.getLandingDate()));
            statement.setString(3, t.getSampleNumber());

            int code = 0, position = 0;
            if (t.getWell() != null) {
                code = t.getWell().getNumber();
                position = t.getWell().getPosition();
            }
            statement.setInt(4, code);
            statement.setInt(5, position);

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
    public boolean delete(SampleWell t) {
        PreparedStatement statement = null;
        String query = "delete from FP_ECH_CUVE "
                + " where C_BAT = ? and D_DBQ = ? and N_ECH = ?"
                + " and N_CUVE = ? and C_POSITION = ?";

        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, t.getVessel().getCode());

            statement.setDate(2, DateTimeUtils.convertDate(t.getLandingDate()));
            statement.setString(3, t.getSampleNumber());

            int code = 0, position = 0;
            if (t.getWell() != null) {
                code = t.getWell().getNumber();
                position = t.getWell().getPosition();
            }
            statement.setInt(4, code);
            statement.setInt(5, position);

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
    protected SampleWell factory(ResultSet rs) throws SQLException, AvdthDriverException {
        SampleWell sampleWell = new SampleWell();
        int vesselCode = rs.getInt("C_BAT");
        DateTime landingDate = DateTimeUtils.convertDate(rs.getDate("D_DBQ"));
        sampleWell.setVessel((new VesselDAO().findVesselByCode(vesselCode)));
        sampleWell.setLandingDate(landingDate);
        sampleWell.setSampleNumber(rs.getString("N_ECH"));
        WellDAO wellDAO = new WellDAO();
        sampleWell.setWell(wellDAO.findWell(vesselCode, landingDate, rs.getInt("N_CUVE"), rs.getInt("C_POSITION")));
        return sampleWell;
    }

}
