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
import fr.ird.driver.avdth.business.Country;
import fr.ird.driver.avdth.business.Sample;
import fr.ird.driver.avdth.business.Vessel;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import org.joda.time.DateTime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO permettant de faire des requêtes sur la table ECHANTILLON.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 3.4.5
 * @date 26 mai 2014
 *
 */
public class SampleDAO extends AbstractDAO<Sample> {

    public SampleDAO() {
        super();
    }

    public List<Sample> findSample(List<Vessel> vessels, List<Country> countries, DateTime start, DateTime end) throws AvdthDriverException {
        List<Sample> samplings = new ArrayList<Sample>();
        List<Vessel> vesselsForCountries = new ArrayList<Vessel>();
        if (countries != null && !countries.isEmpty()) {
            vesselsForCountries = (new VesselDAO()).findVessels(countries);
        }
        if (vessels == null || vessels.isEmpty()) {
            vessels = (new VesselDAO()).getAllVessels();
        }

        if (!vessels.isEmpty() && !vesselsForCountries.isEmpty()) {
            vessels.retainAll(vesselsForCountries);
        }
        for (Vessel b : vessels) {
//            System.out.println(b);
            samplings.addAll(findSample(b, start, end));
        }
        return samplings;
    }

    public List<Sample> findSample(Vessel vessel, DateTime start, DateTime end) throws AvdthDriverException {
        List<Sample> samplings = null;

        String query = "select * from ECHANTILLON where C_BAT = ? "
                + "AND D_DBQ BETWEEN ? AND ?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, vessel.getCode());
//            System.out.println(DateTimeUtils.convertDate(start));
            statement.setDate(2, DateTimeUtils.convertDate((start == null ? new DateTime(1900, 1, 1, 0, 0) : start)));
//            System.out.println(DateTimeUtils.convertDate((end == null ? new Date() : end)));
            statement.setDate(3, DateTimeUtils.convertDate((end == null ? new DateTime() : end)));

            ResultSet rs = statement.executeQuery();
            samplings = new ArrayList<Sample>();

            while (rs.next()) {

                samplings.add(factory(rs));

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
        return samplings;
    }

    @Override
    public Sample factory(ResultSet rs) throws SQLException, AvdthDriverException {
        Sample sample = new Sample();

        sample.setVessel((new VesselDAO().findVesselByCode(rs.getInt("C_BAT"))));
        sample.setLandingDate(DateTimeUtils.convertDate(rs.getDate("D_DBQ")));
        sample.setLandingHarbour((new HarbourDAO().findHarbourByCode(rs.getInt("C_PORT_DBQ"))));

        sample.setSampleNumber(rs.getInt("N_ECH"));

        sample.setSampleQuality((new SampleQualityDAO()).findSampleQualityByCode(rs.getInt("C_QUAL_ECH")));
        sample.setSampleType((new SampleTypeDAO()).findSampleTypeByCode(rs.getInt("C_TYP_ECH")));

        sample.setWell(new WellDAO().findWell(rs.getInt("C_BAT"), DateTimeUtils.convertDate(rs.getDate("D_DBQ")), rs.getInt("N_CUVE"), rs.getInt("F_POS_CUVE")));
        sample.setSuperSampleFlag(rs.getInt("F_S_ECH"));
        sample.setMinus10Weight(rs.getDouble("V_POIDS_M10"));
        sample.setPlus10Weight(rs.getDouble("V_POIDS_P10"));
        sample.setGlobalWeight(rs.getDouble("V_POIDS_ECH"));

        SampleSpeciesDAO ssdao = new SampleSpeciesDAO();
        sample.setSampleSpecies(ssdao.findSampleSpecies(sample));

        SampleWellDAO swdao = new SampleWellDAO();
        sample.setSampleWells(swdao.findSampleWell(sample));

        return sample;
    }

    public Integer count() {
        String query = "select count(*) from (SELECT C_BAT, D_DBQ FROM ECHANTILLON)";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                return rs.getInt(1);
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
        return null;
    }

    public List<Sample> findAllSamples() throws AvdthDriverException {
        List<Sample> samplings = null;

        String query = "select * from ECHANTILLON";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);

            ResultSet rs = statement.executeQuery();
            samplings = new ArrayList<Sample>();

            while (rs.next()) {

                samplings.add(factory(rs));

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
        return samplings;
    }

    public List<Sample> findSampleByVessefIdAndDate(Vessel vessel, DateTime landingDate) throws AvdthDriverException {
        return SampleDAO.this.findSampleByVessefIdAndDate(vessel.getCode(), landingDate);
    }

    public List<Sample> findSampleByVessefIdAndDate(int vesselCode, DateTime landingDate) throws AvdthDriverException {
        List<Sample> samplings = null;

        String query = "select * from ECHANTILLON where C_BAT = ? "
                + "AND D_DBQ = ?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, vesselCode);
            statement.setDate(2, DateTimeUtils.convertDate(landingDate));

            ResultSet rs = statement.executeQuery();
            samplings = new ArrayList<Sample>();

            while (rs.next()) {

                samplings.add(factory(rs));

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
        return samplings;
    }

    public Sample findSampleByVessefIdAndDate(Vessel vessel, DateTime landingDate, int samplingNumber) throws AvdthDriverException {
        return findSampleByVessefIdAndDate(vessel.getCode(), landingDate, samplingNumber);
    }

    public Sample findSampleByVessefIdAndDate(int vesselCode, DateTime landingDate, int samplingNumber) throws AvdthDriverException {
        Sample sampling = null;
        String query = "select * from ECHANTILLON "
                + " where C_BAT = ? and D_DBQ = ?  and N_ECH = ? ";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, vesselCode);
            statement.setDate(2, DateTimeUtils.convertDate(landingDate));
            statement.setInt(3, samplingNumber);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                sampling = factory(rs);
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
        return sampling;
    }

    @Override
    public boolean insert(Sample t) {
        PreparedStatement statement = null;
        String query = "insert into ECHANTILLON "
                + " (C_BAT, D_DBQ, N_ECH, C_QUAL_ECH, C_TYP_ECH, C_PORT_DBQ, N_CUVE, F_POS_CUVE, F_S_ECH, V_POIDS_M10, V_POIDS_P10, V_POIDS_ECH) "
                + " values (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, t.getVessel().getCode());

            statement.setDate(2, DateTimeUtils.convertDate(t.getLandingDate()));
            statement.setInt(3, t.getSampleNumber());
            statement.setInt(4, t.getSampleQuality().getCode());
            statement.setInt(5, t.getSampleType().getCode());
            statement.setInt(6, t.getLandingHarbour().getCode());
            int wellNumber = 0;
            int wellPosition = 0;
            if (t.getWell() != null) {
                wellNumber = t.getWell().getNumber();
                wellPosition = t.getWell().getPosition();
            }
            statement.setInt(7, wellNumber);
            statement.setInt(8, wellPosition);

            statement.setInt(9, t.getSuperSampleFlag());
            statement.setDouble(10, t.getMinus10Weight());
            statement.setDouble(11, t.getPlus10Weight());
            statement.setDouble(12, t.getGlobalWeight());

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
        String query = "delete from ECHANTILLON "
                + " where C_BAT = ? and D_DBQ = ? and N_ECH = ?";

        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, t.getVessel().getCode());

            statement.setDate(2, DateTimeUtils.convertDate(t.getLandingDate()));
            statement.setInt(3, t.getSampleNumber());
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

}
