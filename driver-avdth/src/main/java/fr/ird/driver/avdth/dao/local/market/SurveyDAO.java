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
import fr.ird.driver.avdth.business.local.market.Survey;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import fr.ird.driver.avdth.dao.AbstractDAO;
import fr.ird.driver.avdth.dao.SpeciesDAO;
import fr.ird.driver.avdth.dao.VesselDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO permettant de faire des requêtes sur la table <em>FP_SONDAGE</em>.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class SurveyDAO extends AbstractDAO<Survey> {
    
    @Override
    public boolean insert(Survey s) {
        PreparedStatement statement = null;
        String query = "insert into FP_SONDAGE "
                + " (C_BAT, D_DBQ, N_SOND, C_ESP, V_PROP) "
                + " values (?, ?, ?,  ?, ?);";
        try {
            connection.setAutoCommit(false);
            
            statement = connection.prepareStatement(query);
            
            statement.setInt(1, s.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(s.getLandingDate()));
            statement.setString(3, s.getNumber());
            
            int code = -1;
            if (s.getSpecies() != null) {
                code = s.getSpecies().getCode();
            }
            statement.setInt(4, code);
            
            statement.setInt(5, s.getRatio());
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
    public boolean delete(Survey s) {
        PreparedStatement statement = null;
        String query = "delete from FP_SONDAGE "
                + " where C_BAT = ? and D_DBQ = ? "
                + " and N_SOND = ? AND C_ESP = ?";
        try {
            connection.setAutoCommit(false);
            
            statement = connection.prepareStatement(query);
            statement.setInt(1, s.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(s.getLandingDate()));
            statement.setString(3, s.getNumber());
            statement.setInt(4, s.getSpecies().getCode());
            
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
    protected Survey factory(ResultSet rs) throws SQLException, AvdthDriverException {
        Survey s = new Survey();
        VesselDAO vdao = new VesselDAO();
        s.setVessel(vdao.findVesselByCode(rs.getInt("C_BAT")));
        s.setLandingDate(DateTimeUtils.convertDate(rs.getDate("D_DBQ")));
        s.setNumber(rs.getString("N_SOND"));
        SpeciesDAO sdao = new SpeciesDAO();
        s.setSpecies(sdao.findSpeciesByCode(rs.getInt("C_ESP")));
        s.setRatio(rs.getInt("V_PROP"));
        return s;
    }
    
}
