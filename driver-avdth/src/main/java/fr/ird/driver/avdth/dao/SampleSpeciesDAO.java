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
import fr.ird.common.log.LogService;
import fr.ird.driver.avdth.business.Sample;
import fr.ird.driver.avdth.business.SampleSpecies;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO permettant de faire des requêtes sur la table ECH_ESP.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 3.4.5
 * @date 17 juin 2014
 *
 */
public class SampleSpeciesDAO extends AbstractDAO<SampleSpecies> {

    public SampleSpeciesDAO() {
        super();
    }

    @Override
    public boolean insert(SampleSpecies t) {
        PreparedStatement statement = null;
        String query = "insert into ECH_ESP "
                + " (C_BAT, D_DBQ, N_ECH, N_S_ECH, C_ESP, F_LDLF, V_NB_MES, V_NB_TOT) "
                + " values (?,?,?,?,?,?,?,?)";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, t.getVessel().getCode());

            statement.setDate(2, DateTimeUtils.convertDate(t.getLandingDate()));
            statement.setInt(3, t.getSampleNumber());
            statement.setInt(4, t.getSubSampleNumber());
            statement.setInt(5, t.getSpecies().getCode());
            statement.setInt(6, t.getLdlf());
            statement.setDouble(7, t.getMeasuredCount());
            statement.setDouble(8, t.getTotalCount());

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
    public boolean delete(SampleSpecies t) {
        PreparedStatement statement = null;
        String query = "delete from ECH_ESP "
                + " where C_BAT = ? and D_DBQ = ? and N_ECH = ? "
                + " and N_S_ECH = ? and C_ESP = ? and F_LDLF = ?";

        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, t.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(t.getLandingDate()));
            statement.setInt(3, t.getSampleNumber());
            statement.setInt(4, t.getSubSampleNumber());
            statement.setInt(5, t.getSpecies().getCode());
            statement.setInt(6, t.getLdlf());

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

    public List<SampleSpecies> findSampleSpecies(Sample sample) {
        List<SampleSpecies> sampleSpecieses = new ArrayList<SampleSpecies>();
        PreparedStatement statement = null;
        String query = "select * from ECH_ESP "
                + " where C_BAT = ? and D_DBQ = ? and N_ECH = ? ";

        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, sample.getVessel().getCode());

            statement.setDate(2, DateTimeUtils.convertDate(sample.getLandingDate()));
            statement.setInt(3, sample.getSampleNumber());

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                sampleSpecieses.add(factory(rs));
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
        return sampleSpecieses;
    }

    @Override
    public SampleSpecies factory(ResultSet rs) throws SQLException, AvdthDriverException {
        SampleSpecies sampleSpecies = new SampleSpecies();
        sampleSpecies.setVessel((new VesselDAO().findVesselByCode(rs.getInt("C_BAT"))));
        sampleSpecies.setLandingDate(DateTimeUtils.convertDate(rs.getDate("D_DBQ")));
        sampleSpecies.setSampleNumber(rs.getInt("N_ECH"));
        sampleSpecies.setSubSampleNumber(rs.getInt("N_S_ECH"));
        SpeciesDAO sdao = new SpeciesDAO();
        sampleSpecies.setSpecies(sdao.findSpeciesByCode(rs.getInt("C_ESP")));
        sampleSpecies.setLdlf(rs.getInt("F_LDLF"));
        sampleSpecies.setMeasuredCount(rs.getDouble("V_NB_MES"));
        sampleSpecies.setTotalCount(rs.getDouble("V_NB_TOT"));

        SampleSpeciesFrequencyDAO sampleSpeciesFrequencyDAO = new SampleSpeciesFrequencyDAO();
        sampleSpecies.setSampleSpeciesFrequencys(sampleSpeciesFrequencyDAO.findSampleSpeciesFrequency(sampleSpecies));

        return sampleSpecies;
    }

}
