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
 */
package fr.ird.driver.avdth.dao;

import fr.ird.common.DateTimeUtils;
import fr.ird.common.JDBCUtilities;
import fr.ird.driver.avdth.business.*;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import org.joda.time.DateTime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static fr.ird.driver.avdth.common.AvdthUtils.createDate;

/**
 * DAO permettant de faire des requêtes sur la table <em>ACTIVITE</em>.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @date 27 aout 2013
 *
 */
public class ActivityDAO extends AbstractDAO<Activity> {

    public ActivityDAO() {
        super();
    }

    public Activity findActivityByTripAndDateOfActivityAndNum(Trip maree,
            DateTime dateActivite,
            int numActivite) throws AvdthDriverException {
        int vesselCode = maree.getVessel().getCode();
        DateTime landingDate = maree.getLandingDate();
        return findActivityByTripAndDateOfLandingAndNum(vesselCode, landingDate, dateActivite, numActivite);
    }

    /**
     *
     *
     * @param vesselCode
     * @param landingDate
     * @param dateActivite
     * @param numActivite
     * @return l'activité résultante
     * @throws fr.ird.driver.avdth.common.exception.AvdthDriverException
     */
    public Activity findActivityByTripAndDateOfLandingAndNum(int vesselCode,
            DateTime landingDate,
            DateTime dateActivite,
            int numActivite) throws AvdthDriverException {

        Activity activite = null;

//        System.out.println(vesselCode);
//        System.out.println(landingDate);
//        System.out.println(dateActivite);
//        System.out.println(numActivite);
        String query = "select * from ACTIVITE "
                + " where C_BAT = ? and D_DBQ = ? "
                + " and D_ACT = ? and N_ACT = ? ";
//        System.out.println("--- " + dateActivite);
//        if (dateActivite.getHourOfDay() != 0) {
//            query += " and H_ACT = " + dateActivite.getHourOfDay();
//        }
//        if (dateActivite.getMinuteOfHour() != 0) {
//            query += " and M_ACT = " + dateActivite.getMinuteOfHour();
//        }

        PreparedStatement statement = null;
        try {

            statement = connection.prepareStatement(query);
            statement.setInt(1, vesselCode);
            statement.setDate(2, DateTimeUtils.convertDate(landingDate));
            statement.setDate(3, DateTimeUtils.convertDate(dateActivite));
            statement.setInt(4, numActivite);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                activite = factory(rs);
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
        return activite;

    }

    public List<Activity> findActivitiesByTripAndDateOfLanding(Trip maree,
            DateTime dateActivite) throws AvdthDriverException {
        List<Activity> activites = null;

        String query = "select * from ACTIVITE "
                + " where C_BAT = ? and D_DBQ = ? "
                + " and D_ACT = ?";

        int codeBateau = maree.getVessel().getCode();
        DateTime landingDate = maree.getLandingDate();
        PreparedStatement statement = null;
        try {

            statement = connection.prepareStatement(query);
            statement.setInt(1, codeBateau);
            statement.setDate(2, DateTimeUtils.convertDate(landingDate));
            statement.setDate(3, DateTimeUtils.convertDate(dateActivite));
            ResultSet rs = statement.executeQuery();

            activites = new ArrayList<Activity>();

            while (rs.next()) {

                activites.add(factory(rs));

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
        return activites;

    }

    public List<Activity> findAllActivityOfTrip(Trip maree) throws AvdthDriverException {
        List<Activity> activites = null;

        String query = "select * from ACTIVITE "
                + " where C_BAT = ? and D_DBQ = ? "
                + " order by D_ACT asc, N_ACT asc";

        int codeBateau = maree.getVessel().getCode();
        DateTime landingDate = maree.getLandingDate();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codeBateau);
            statement.setDate(2, DateTimeUtils.convertDate(landingDate));

            ResultSet rs = statement.executeQuery();

            activites = new ArrayList<Activity>();

            while (rs.next()) {

                activites.add(factory(rs));

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
        return activites;

    }

    @Override
    public boolean insert(Activity activite) {
        PreparedStatement statement = null;
        String query = "insert into ACTIVITE "
                + " (C_BAT, D_DBQ, D_ACT, N_ACT, V_POIDS_CAP, H_ACT, M_ACT, "
                + " C_OCEA, Q_ACT, V_LAT, V_LON, V_TMER, V_TPEC, V_NB_OP, "
                + " F_DON_ORG, F_POS_COR, F_POS_VMS_D, F_CUVE_C, F_OBS, "
                + " F_EXPERT, V_TEMP_S, V_COUR_DIR, V_COUR_VIT, V_VENT_DIR, "
                + " V_VENT_VIT, F_DCP_ECO, F_PROP_BALISE, V_ID_BALISE, "
                + " L_COM_A, C_TBANC, C_OPERA, C_TYP_OBJET, "
                + " C_TYP_BALISE, V_POIDS_ESTIM_DCP, C_Z_FPA) "
                + " values (?, ?, ?, ?, ?, ?, ?, "
                + " ?, ?, ?, ?, ?, ?, ?, "
                + " ?, ?, ?, ?, ?,"
                + " ?, ?, ?, ?, ?, "
                + " ?, ?, ?, ?, "
                + " ?, ?, ?, ?, "
                + " ?, ?, ?);";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, activite.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(activite.getLandingDate()));
            statement.setDate(3, DateTimeUtils.convertDate(activite.getDate()));
            statement.setInt(4, activite.getNumber());
            statement.setDouble(5, activite.getCatchWeight());
            statement.setInt(6, DateTimeUtils.getHours(activite.getDate()));
            statement.setInt(7, DateTimeUtils.getMinutes(activite.getDate()));

            statement.setInt(8, activite.getCodeOcean());
            statement.setInt(9, activite.getQuadrant());
            statement.setInt(10, activite.getLatitude());
            statement.setInt(11, activite.getLongitude());
            statement.setInt(12, activite.getTimeAtSea());
            statement.setInt(13, activite.getFishingTime());
            statement.setInt(14, activite.getOperationCount());

            statement.setInt(15, activite.getOriginalDataFlag());
            statement.setInt(16, activite.getFlagCorrectedPosition());
            statement.setInt(17, activite.getFlagDivergentPositionVMS());
            statement.setInt(18, activite.getFlagCompatibilityWell());
            statement.setInt(19, activite.getFlagObserver());

            statement.setInt(20, activite.getFlagExpert());
            statement.setDouble(21, activite.getTemperatureSurface());
            statement.setInt(22, activite.getCurrentDirection());
            statement.setDouble(23, activite.getCurrentVelocity());
            statement.setInt(24, activite.getWindDirection());

            statement.setDouble(25, activite.getWindVelocity());
            statement.setInt(26, activite.getFlagEcoFAD());
            statement.setInt(27, activite.getBuoyBelongsVessel());
            statement.setString(28, activite.getBuoyId());

            statement.setString(29, activite.getComments());

            Integer code = null;
            if (activite.getSchoolType() != null) {
                code = activite.getSchoolType().getCode();
            } else if (code == null) {
                throw new SQLException("Le type de banc ne peut être nul.");
            }
            statement.setObject(30, code, java.sql.Types.INTEGER);

            code = null;
            if (activite.getOperation() != null) {
                code = activite.getOperation().getCode();
            } else if (code == null) {
                throw new SQLException("Le type de banc ne peut être nul.");
            }
            statement.setObject(31, code, java.sql.Types.INTEGER);

            code = null;
            if (activite.getLogType() != null) {
                code = activite.getLogType().getCode();
            }
            statement.setObject(32, code, java.sql.Types.INTEGER);

            code = null;
            if (activite.getBuoyType() != null) {
                code = activite.getBuoyType().getCode();
            }
            statement.setObject(33, code, java.sql.Types.INTEGER);

            statement.setDouble(34, activite.getEstimatedWeight());
            code = null;
            if (activite.getFpaZone() != null) {
                code = activite.getFpaZone().getCode();
            }
            statement.setObject(35, code, java.sql.Types.INTEGER);

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
    public boolean update(Activity activite) {
        return delete(activite) && insert(activite);
    }

    @Override
    public boolean delete(Activity activite) {
        PreparedStatement statement = null;
        String query = "delete from ACTIVITE "
                + " where C_BAT = ? and D_DBQ = ? "
                + " and D_ACT = ? and N_ACT= ?";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);
            statement.setInt(1, activite.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(activite.getLandingDate()));
            statement.setDate(3, DateTimeUtils.convertDate(activite.getDate()));
            statement.setInt(4, activite.getNumber());

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
    protected Activity factory(ResultSet rs) throws SQLException, AvdthDriverException {
        Activity activite = new Activity();
        activite.setVessel((new VesselDAO().findVesselByCode(rs.getInt("C_BAT"))));
        activite.setLandingDate(DateTimeUtils.convertDate(rs.getDate("D_DBQ")));
        activite.setDate(DateTimeUtils.convertDate(rs.getDate("D_ACT")));
        activite.setNumber(rs.getInt("N_ACT"));

        activite.setCatchWeight(rs.getDouble("V_POIDS_CAP"));
        activite.setHour(rs.getInt("H_ACT"));
        activite.setMinute(rs.getInt("M_ACT"));

        activite.setFullDate(createDate(rs.getDate("D_ACT"), rs.getInt("H_ACT"), rs.getInt("M_ACT")));

        activite.setCodeOcean(rs.getInt("C_OCEA"));
        activite.setQuadrant(rs.getInt("Q_ACT"));
        activite.setLatitude(rs.getInt("V_LAT"));
        activite.setLongitude(rs.getInt("V_LON"));
        activite.setTimeAtSea(rs.getInt("V_TMER"));
        activite.setFishingTime(rs.getInt("V_TPEC"));
        activite.setOperationCount(rs.getInt("V_NB_OP"));
        activite.setOriginalDataFlag(rs.getInt("F_DON_ORG"));
        activite.setFlagCorrectedPosition(rs.getInt("F_POS_COR"));
        activite.setFlagDivergentPositionVMS(rs.getInt("F_POS_VMS_D"));
        activite.setFlagCompatibilityWell(rs.getInt("F_CUVE_C"));
        activite.setFlagObserver(rs.getInt("F_OBS"));
        activite.setFlagExpert(rs.getInt("F_EXPERT"));

        activite.setTemperatureSurface(rs.getDouble("V_TEMP_S"));
        activite.setCurrentDirection(rs.getInt("V_COUR_DIR"));
        activite.setCurrentVelocity(rs.getDouble("V_COUR_VIT"));
        activite.setWindDirection(rs.getInt("V_VENT_DIR"));
        activite.setWindVelocity(rs.getDouble("V_VENT_VIT"));
        activite.setFlagEcoFAD(rs.getInt("F_DCP_ECO"));
        activite.setBuoyBelongsVessel(rs.getInt("F_PROP_BALISE"));

        activite.setBuoyId(rs.getString("V_ID_BALISE"));

        FishingContextDAO activiteAssociationDAO = new FishingContextDAO();
        ArrayList<FishingContext> activiteAssociations = (ArrayList<FishingContext>) activiteAssociationDAO.findFishingContextByActivity(activite);
        activite.setFishingContexts(activiteAssociations);

        ElementaryCatchDAO captureElementaireDAO = new ElementaryCatchDAO();
        ArrayList<ElementaryCatch> captureElementaires = (ArrayList<ElementaryCatch>) captureElementaireDAO.findAllCatchesOfActivity(activite);
        activite.setElementaryCatchs(captureElementaires);

        SchoolTypeDAO typeBancDAO = new SchoolTypeDAO();
        SchoolType tb = typeBancDAO.findSchoolTypeByCode(rs.getInt("C_TBANC"));
        activite.setSchoolType(tb);

        OperationDAO operationDAO = new OperationDAO();
        Operation operation = operationDAO.findOperationByCode(rs.getInt("C_OPERA"));
        activite.setOperation(operation);

        LogTypeDAO typeObjetDAO = new LogTypeDAO();
        LogType typeObjet = typeObjetDAO.findLogTypeByCode(rs.getInt("C_TYP_OBJET"));
        activite.setLogType(typeObjet);

        BuoyTypeDAO typeBaliseDAO = new BuoyTypeDAO();
        BuoyType balise = typeBaliseDAO.findBuoyTypeByCode(rs.getInt("C_TYP_BALISE"));
        activite.setBuoyType(balise);
        activite.setEstimatedWeight(rs.getDouble("V_POIDS_ESTIM_DCP"));
        activite.setComments(rs.getString("L_COM_A"));

        FPAZoneDAO zoneDAO = new FPAZoneDAO();

        FPAZone zone = zoneDAO.findFPAZone(rs.getInt("C_Z_FPA"));
        activite.setFpaZone(zone);

        return activite;
    }

    public List<Activity> findActivities(Vessel vessel, DateTime start, DateTime end) throws AvdthDriverException {
        List<Activity> activites = null;
        String query = "select * from ACTIVITE where C_BAT = ? "
                + "AND D_ACT BETWEEN ? AND ?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, vessel.getCode());
//            statement.setString(2, DateTimeUtils.convertDate(((start == null ? 0 : start))));
//            statement.setString(3, DateTimeUtils.convertDate(((end == null ? (new Date()) : end))));
//            System.out.println(DateTimeUtils.convertDate(start));
            statement.setDate(2, DateTimeUtils.convertDate((start == null ? new DateTime(1900, 1, 1, 0, 0) : start)));
//            System.out.println(DateTimeUtils.convertDate((end == null ? new DateTime() : end)));
            statement.setDate(3, DateTimeUtils.convertDate((end == null ? new DateTime() : end)));
            ResultSet rs = statement.executeQuery();

            activites = new ArrayList<Activity>();

            while (rs.next()) {

                activites.add(factory(rs));

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
        return activites;
    }

    public List<Activity> findActivities(List<Vessel> vessels, List<Country> countries, DateTime start, DateTime end) throws AvdthDriverException {
        List<Activity> activites = new ArrayList<Activity>();
        List<Vessel> vesselsForCountries = new ArrayList<Vessel>();
        if (countries != null && !countries.isEmpty()) {
            vesselsForCountries = (new VesselDAO()).findVessels(countries);
        }
//        System.out.println(vessels.size());
//        System.out.println(vesselsForCountries.size());
//        System.out.println(countries.size());
        if (vessels == null || vessels.isEmpty()) {
            vessels = (new VesselDAO()).getAllVessels();
        }
//        System.out.println(vessels.size());

        if (!vessels.isEmpty() && !vesselsForCountries.isEmpty()) {
            vessels.retainAll(vesselsForCountries);
        }
//        System.out.println(vessels.size());

        for (Vessel b : vessels) {
//            System.out.println(b);
            activites.addAll(findActivities(b, start, end));
        }
        return activites;
    }

    public Integer count() {
        String query = "select count(*) from (SELECT C_BAT, D_ACT FROM ACTIVITE)";

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

    public DateTime lastActivityDate() {
        String query = "select D_ACT from (SELECT DISTINCT C_BAT, D_ACT FROM ACTIVITE) ORDER BY D_ACT  DESC";
        DateTime dt = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                return DateTimeUtils.convertDate(rs.getDate("D_ACT"));
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
        return dt;
    }

    public DateTime lastActivityFullDate() {
        String query = "select C_BAT, D_ACT, H_ACT, M_ACT from (SELECT DISTINCT C_BAT, D_ACT, H_ACT, M_ACT FROM ACTIVITE) ORDER BY D_ACT  DESC";
        DateTime dt = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                return createDate(rs.getDate("D_ACT"), rs.getInt("H_ACT"), rs.getInt("M_ACT"));
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
        return dt;
    }

    public DateTime firstActivityDate() {
        String query = "select C_BAT, D_ACT from (SELECT DISTINCT C_BAT, D_ACT  FROM ACTIVITE) ORDER BY D_ACT ASC";
        DateTime dt = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                return DateTimeUtils.convertDate(rs.getDate("D_ACT"));
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
        return dt;
    }

    public DateTime firstActivityFullDate() {
        String query = "select C_BAT, D_ACT, H_ACT, M_ACT from (SELECT DISTINCT C_BAT, D_ACT, H_ACT, M_ACT FROM ACTIVITE) ORDER BY D_ACT ASC";
        DateTime dt = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                return createDate(rs.getDate("D_ACT"), rs.getInt("H_ACT"), rs.getInt("M_ACT"));
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
        return dt;
    }

    public List<Activity> findAllActivities() throws AvdthDriverException {
        List<Activity> activites = null;

        String query = "select * from ACTIVITE "
                + " order by D_DBQ asc, D_ACT asc, N_ACT asc";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            activites = new ArrayList<Activity>();

            while (rs.next()) {
                activites.add(factory(rs));
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
        return activites;

    }

    public boolean isExistAnActivityFor(Vessel vessel, DateTime date) {
        String query = "select * from ACTIVITE where C_BAT = ? "
                + "AND D_ACT = ? ";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, vessel.getCode());
            statement.setDate(2, DateTimeUtils.convertFilteredDate(date));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                return true;
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
        return false;

    }
}
