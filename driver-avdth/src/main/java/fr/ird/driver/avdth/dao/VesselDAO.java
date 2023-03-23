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

import fr.ird.common.DateTimeUtils;
import fr.ird.common.JDBCUtilities;
import fr.ird.common.log.LogService;
import fr.ird.driver.avdth.business.Country;
import fr.ird.driver.avdth.business.Vessel;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO permettant de faire des requêtes sur la table BATEAU.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 *
 */
public class VesselDAO extends AbstractDAO<Vessel> {

    public VesselDAO() {
        super();
    }

    /**
     * Cherche les bateaux par l'identifiant CFR qui sont encore actif.
     *
     * @param vesselCfr
     * @return
     */
    public Vessel findVesselByCfrIdentifiant(String vesselCfr) {
        Vessel bateau = null;

        String query = "select * from BATEAU "
                + " where STATUT = TRUE"
                + " and C_IMMAT_COMM = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, vesselCfr);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                bateau = factory(rs);
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
        return bateau;

    }

    /**
     * Donne tous les identifiants CFR des bateaux contenus dans AVDTH qui sont
     * encore actif.
     *
     * @return
     */
    public List<String> getAllVesselsCfrIdentifiant() {
        ArrayList<String> vesselsCfrIdentifiant = null;

        String query = "select C_IMMAT_COMM from BATEAU "
                + " where STATUT = TRUE"
                + " and C_IMMAT_COMM != null";
        Statement statement = null;
        try {
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);
            vesselsCfrIdentifiant = new ArrayList<String>();

            while (rs.next()) {
                vesselsCfrIdentifiant.add(rs.getString("C_IMMAT_COMM"));
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
        return vesselsCfrIdentifiant;
    }

    /**
     * Cherche les bateaux par le code bateau (C_BAT) d'AVDTH.
     *
     * @param codeBateau
     * @return
     */
    public Vessel findVesselByCode(int codeBateau) {
        Vessel bateau = null;

        String query = "select * from BATEAU "
                + " where C_BAT = ?";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);

            statement.setInt(1, codeBateau);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                bateau = factory(rs);
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
        return bateau;
    }

    @Override
    public boolean insert(Vessel v) {

        PreparedStatement statement = null;
        String query = "insert into BATEAU (C_BAT, C_PAYS, C_TYP_B, C_CAT_B, C_QUILLE, "
                + " C_FLOTTE, AN_SERV, D_CHGT_PAV, V_L_HT, V_CT_M3, V_P_CV, V_MAX_RECH, "
                + " D_DEBUT, D_FIN, C_IMMAT_NAT, C_IMMAT_COMM, "
                + " C_IMMAT_FAO, STATUT, L_BAT, L_COM_B, "
                + " L_PP, C_GEN, V_CHANT, V_MAX ) "
                + " values (?, ?, ?, ?, ?, "
                + " ?, ?, ?, ?, ?, ?, ?, "
                + " ?, ?, ?, ?, "
                + " ?, ?, ?, ?, "
                + " ?, ?, ?, ?) ";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, v.getCode());
            statement.setInt(2, v.getCountry().getCode());
            statement.setInt(3, v.getVesselType().getCode());
            statement.setInt(4, v.getVesselSizeCategory().getCodeCategorieBateau());
            statement.setInt(5, v.getKeelCode());
            statement.setInt(6, v.getFleetCode());
            statement.setInt(7, v.getYearService());

            java.sql.Date date = null;
            if (v.getFlagChangeDate() != null) {
                date = DateTimeUtils.convertDate(v.getFlagChangeDate());
            }
            statement.setDate(8, date);
            statement.setFloat(9, v.getLength());
            statement.setFloat(10, v.getCapacity());
            statement.setInt(11, v.getPower());
            statement.setFloat(12, v.getVitesseMaxDeProspection());

            date = null;
            if (v.getValidityStart() != null) {
                date = DateTimeUtils.convertDate(v.getValidityStart());
            }
            statement.setDate(13, date);
            date = null;
            if (v.getExpiry() != null) {
                date = DateTimeUtils.convertDate(v.getExpiry());
            }
            statement.setDate(14, date);
            statement.setString(15, v.getNationalID());
            statement.setString(16, v.getCommunautaryID());
            statement.setString(17, v.getFaoID());
            statement.setBoolean(18, v.isStatut());

            statement.setString(19, v.getLibelle());
            statement.setString(20, v.getComment());

            statement.setObject(21, v.getlPP(), Types.INTEGER);
            statement.setObject(22, v.getcGen(), Types.INTEGER);
            statement.setObject(23, v.getVitesseChantier(), Types.INTEGER);
            statement.setObject(24, v.getVitesseMax(), Types.INTEGER);

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
    public boolean update(Vessel v) {
        return delete(v) && insert(v);
    }

    @Override
    public boolean delete(Vessel v) {
        PreparedStatement statement = null;
        String query = "delete from BATEAU where C_BAT = ? ";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, v.getCode());
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

    /**
     *
     * @param rs
     * @return
     * @throws SQLException
     * @throws AvdthDriverException
     */
    @Override
    protected Vessel factory(ResultSet rs) throws SQLException, AvdthDriverException {
        Vessel bateau = new Vessel();
        bateau.setCode(rs.getInt("C_BAT"));
        bateau.setKeelCode(rs.getInt("C_QUILLE"));
        bateau.setFleetCode(rs.getInt("C_FLOTTE"));
        bateau.setYearService(rs.getInt("AN_SERV"));
        bateau.setFlagChangeDate(DateTimeUtils.convertDate(rs.getDate("D_CHGT_PAV")));
        bateau.setLength(rs.getFloat("V_L_HT"));
        bateau.setCapacity(rs.getFloat("V_CT_M3"));
        bateau.setPower(rs.getInt("V_P_CV"));
        bateau.setVitesseMaxDeProspection(rs.getFloat("V_MAX_RECH"));
        bateau.setLibelle(rs.getString("L_BAT"));
        bateau.setComment(rs.getString("L_COM_B"));
        bateau.setValidityStart(DateTimeUtils.convertDate(rs.getDate("D_DEBUT")));
        bateau.setExpiry(DateTimeUtils.convertDate(rs.getDate("D_FIN")));
        bateau.setNationalID(rs.getString("C_IMMAT_NAT"));
        bateau.setCommunautaryID(rs.getString("C_IMMAT_COMM"));
        bateau.setFaoID(rs.getString("C_IMMAT_FAO"));
        bateau.setStatut(rs.getBoolean("STATUT"));

        CountryDAO paysDAO = new CountryDAO();
        bateau.setCountry(paysDAO.findCountryByCode(rs.getInt("C_PAYS")));

        VesselTypeDAO typeBateauDAO = new VesselTypeDAO();
        bateau.setVesselType(typeBateauDAO.findVesselTypeByCode(rs.getInt("C_TYP_B")));

        VesselSizeCategoryDAO categorieBateauDAO = new VesselSizeCategoryDAO();
        bateau.setVesselSizeCategory(categorieBateauDAO.findVesselSizeCategoryByCode(rs.getInt("C_CAT_B")));

//        bateau.setcGen(rs.getInt("C_GEN"));
//        bateau.setlPP(rs.getInt("L_PP"));
//        bateau.setVitesseChantier(rs.getInt("V_CHAN"));
//        bateau.setVitesseMax(rs.getInt("V_MAX"));
        return bateau;
    }

    public List<Vessel> findVessels(List<Country> countries) {
        List<Vessel> vessels = new ArrayList<Vessel>();
        for (Country p : countries) {
            vessels.addAll(findVessels(p));
        }
        return vessels;
    }

    public List<Vessel> findVessels(Country country) {
        List<Vessel> vessels = new ArrayList<Vessel>();
        PreparedStatement statement = null;
        String query = "select * from BATEAU where C_PAYS IN (?) ";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, country.getCode());

            ResultSet rs = statement.executeQuery();
            vessels = new ArrayList<Vessel>();
            while (rs.next()) {
                vessels.add(factory(rs));
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
        return vessels;
    }

    List<Vessel> getAllVessels() {
        ArrayList<Vessel> vessels = null;

        String query = "select * from BATEAU "
                + " where STATUT = TRUE";
        Statement statement = null;
        try {
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(query);
            vessels = new ArrayList<Vessel>();

            while (rs.next()) {
                vessels.add(factory(rs));
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
        return vessels;
    }
}
