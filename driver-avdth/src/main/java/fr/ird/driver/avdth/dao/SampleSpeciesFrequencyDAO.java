/* 
 * Copyright (C) 2014 Julien Lebranchu <julien.lebranchu@ird.fr>
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
import fr.ird.driver.avdth.business.SampleSpecies;
import fr.ird.driver.avdth.business.SampleSpeciesFrequency;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO permettant de faire des requêtes sur la table ECH_FREQT.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 3.4.5
 * @date 26 mai 2014
 */
public class SampleSpeciesFrequencyDAO extends AbstractDAO<SampleSpeciesFrequency> {

    public SampleSpeciesFrequencyDAO() {
        super();
    }

    @Override
    public boolean insert(SampleSpeciesFrequency t) {
        PreparedStatement statement = null;
        String query = "insert into ECH_FREQT "
                + " (C_BAT, D_DBQ, N_ECH, N_S_ECH, C_ESP, F_LDLF, V_LONG, V_EFF) "
                + " values (?,?,?,?,?,?,?,?)";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, t.getVessel().getCode());

            statement.setDate(2, DateTimeUtils.convertDate(t.getLandingDate()));
            statement.setInt(3, t.getSampleNumber());
            statement.setInt(4, t.getSuperSampleNumber());
            statement.setInt(5, t.getSpecies().getCode());
            statement.setInt(6, t.getLdlf());
            statement.setDouble(7, t.getLengthClass());
            statement.setInt(8, t.getNumber());

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
    public boolean delete(SampleSpeciesFrequency t) {
        PreparedStatement statement = null;
        String query = "delete from ECH_FREQT "
                + " where C_BAT = ? and D_DBQ = ? and N_ECH = ? "
                + " and N_S_ECH = ? and C_ESP = ? and F_LDLF = ?"
                + " and V_LONG = ?";

        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, t.getVessel().getCode());

            statement.setDate(2, DateTimeUtils.convertDate(t.getLandingDate()
            ));
            statement.setInt(3, t.getSampleNumber());
            statement.setInt(4, t.getSuperSampleNumber());
            statement.setInt(5, t.getSpecies().getCode());
            statement.setInt(6, t.getLdlf());
            statement.setDouble(7, t.getLengthClass());

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

    public List<SampleSpeciesFrequency> findSampleSpeciesFrequency(SampleSpecies sampleSpecies) {
        List<SampleSpeciesFrequency> sampleSpeciesFrequencys = new ArrayList<>();
        PreparedStatement statement = null;
        String query = "select * from ECH_FREQT "
                + " where C_BAT = ? and D_DBQ = ? and N_ECH = ? "
                + " and N_S_ECH = ? and C_ESP = ? and F_LDLF = ?";

        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, sampleSpecies.getVessel().getCode());

            statement.setDate(2, DateTimeUtils.convertDate(sampleSpecies.getLandingDate()));
            statement.setInt(3, sampleSpecies.getSampleNumber());

            statement.setInt(4, sampleSpecies.getSubSampleNumber());
            statement.setInt(5, sampleSpecies.getSpecies().getCode());
            statement.setInt(6, sampleSpecies.getLdlf());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                sampleSpeciesFrequencys.add(factory(rs));
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
        return sampleSpeciesFrequencys;
    }

    @Override
    public SampleSpeciesFrequency factory(ResultSet rs) throws SQLException {
        SampleSpeciesFrequency sampleSpeciesFrequency = new SampleSpeciesFrequency();
        sampleSpeciesFrequency.setVessel((new VesselDAO().findVesselByCode(rs.getInt("C_BAT"))));
        sampleSpeciesFrequency.setLandingDate(DateTimeUtils.convertDate(rs.getDate("D_DBQ")));
        sampleSpeciesFrequency.setSampleNumber(rs.getInt("N_ECH"));
        sampleSpeciesFrequency.setSuperSampleNumber(rs.getInt("N_S_ECH"));
        SpeciesDAO sdao = new SpeciesDAO();
        sampleSpeciesFrequency.setSpecies(sdao.findSpeciesByCode(rs.getInt("C_ESP")));
        sampleSpeciesFrequency.setLdlf(rs.getInt("F_LDLF"));
        sampleSpeciesFrequency.setLengthClass(rs.getInt("V_LONG"));
        sampleSpeciesFrequency.setNumber(rs.getInt("V_EFF"));
        return sampleSpeciesFrequency;
    }

}
