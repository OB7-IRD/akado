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
import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.Country;
import fr.ird.driver.avdth.business.ElementaryLanding;
import fr.ird.driver.avdth.business.Harbour;
import fr.ird.driver.avdth.business.Trip;
import fr.ird.driver.avdth.business.Vessel;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO permettant de faire des requêtes sur la table MAREE.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 *
 */
public class TripDAO extends AbstractDAO<Trip> {
    private static final Logger log = LogManager.getLogger(TripDAO.class);
    public TripDAO() {
        super();
    }

    public static boolean exist(Trip trip) {
        if (trip == null) {
            return false;
        }
        return exist(trip.getVessel(), trip.getLandingDate());
    }

    public static boolean exist(Vessel vessel, DateTime landingDate) {
        TripDAO aO = new TripDAO();
        return aO.findTripByVesselIdAndDate(vessel, landingDate) != null;
    }

    /**
     * Retourne la marée associée à un bateau et une date de débarquement.
     *
     * @param vessel
     * @param landingDate
     * @return the trip
     */
    public Trip findTripByVesselIdAndDate(Vessel vessel, DateTime landingDate) {
        if (vessel == null || landingDate == null) {
            return null;
        }
        return TripDAO.this.findTripByVesselIdAndDate(vessel.getCode(), landingDate);
    }

    /**
     *
     * @param vesselCode
     * @param landingDate
     * @return thr trip
     */
    public Trip findTripByVesselIdAndDate(int vesselCode, DateTime landingDate) {
        Trip maree = null;

        String query = "select * from MAREE "
                + " where C_BAT = ? and D_DBQ = ? ";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, vesselCode);
            statement.setDate(2, DateTimeUtils.convertDate(landingDate));

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                maree = factory(rs);
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        } catch (AvdthDriverException ex) {
            log.error(ex.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    JDBCUtilities.printSQLException(ex);
                }

            }
        }
        return maree;
    }

    /**
     * Retourne la marée associée à un bateau et un numéro de marée, au sens
     * ERS.
     *
     * @param vessel
     * @param tripNumber
     * @return
     */
    public Trip findTripByVesselIdAndTripNumber(Vessel vessel, String tripNumber) throws AvdthDriverException {
        Trip maree = null;

        String query = "select * from MAREE "
                + " where C_BAT = ? and C_ID_ERS = ? ";

        int codeBateau = vessel.getCode();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codeBateau);
            statement.setString(2, tripNumber);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                maree = factory(rs);
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
        return maree;
    }

    /**
     *
     * @param tripNumber
     * @return
     */
    public Boolean isTripInDB(String tripNumber) {
        Boolean isTripInDB = false;

        String query = "select * from MAREE "
                + " where C_ID_ERS = ? ";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, tripNumber);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                isTripInDB = true;
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
        return isTripInDB;
    }

    @Override
    public boolean insert(Trip t) {
//        System.out.println("INSERT TRIP " + t.getID());
        PreparedStatement statement = null;
        String query = "insert into MAREE "
                + " (C_BAT, D_DBQ, C_PORT_DBQ, V_TEMPS_M, V_TEMPS_P, V_POIDS_DBQ,"
                + " V_POIDS_FP, F_ENQ, C_PORT_DEP, D_DEPART, F_CAL_VID, "
                + " V_LOCH,  L_COM_M, C_ID_ERS, C_TOPIAID) "
                + " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, t.getVessel().getCode());

            statement.setDate(2, DateTimeUtils.convertDate(t.getLandingDate()));
            int code = 999;
            if (t.getLandingHarbour() != null) {
                code = t.getLandingHarbour().getCode();
            }
            statement.setInt(3, code);

            statement.setInt(4, t.getTimeAtSea());
            statement.setInt(5, t.getFishingTime());
            statement.setDouble(6, t.getTotalLandingWeight());

            statement.setFloat(7, t.getLocalMarketWeight());
            statement.setInt(8, t.getFlagEnquete());
            statement.setInt(9, t.getDepartureHarbour().getCode());

            Date date = null;
            if (t.getDepartureDate() != null) {
                date = DateTimeUtils.convertDate(t.getDepartureDate());
            }
            statement.setDate(10, date);
            statement.setInt(11, t.getFlagCaleVide());

            statement.setInt(12, t.getLoch());
            statement.setString(13, t.getCommentaireMaree());
            statement.setString(14, t.getNumeroMaree());
            statement.setString(15, t.getCodeObServeTopiaid());

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
    public boolean update(Trip t) {
        return delete(t) && insert(t);
    }

    @Override
    public boolean delete(Trip t) {
//        System.out.println("DELETE TRIP " + t.getID());
        PreparedStatement statement = null;
        String query = "delete from MAREE "
                + " where (C_BAT = ?  and D_DBQ = ?) OR C_ID_ERS = ?";
        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(query);

            statement.setInt(1, t.getVessel().getCode());
            statement.setDate(2, DateTimeUtils.convertDate(t.getLandingDate()));
            statement.setString(3, t.getNumeroMaree());
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
    protected Trip factory(ResultSet rs) throws SQLException, AvdthDriverException {
        Trip trip = new Trip();

//        System.out.println("DATE DE DEP in AVDTH (sql): " + rs.getDate("D_DEPART"));
//        System.out.println("DATE DE DEP in AVDTH : " + DateTimeUtils.convertDate(rs.getDate("D_DEPART")));
        trip.setVessel((new VesselDAO()).findVesselByCode(rs.getInt("C_BAT")));
        trip.setLandingDate(DateTimeUtils.convertDate(rs.getDate("D_DBQ")));

        trip.setFlagCaleVide(rs.getInt("F_CAL_VID"));
        HarbourDAO portDAO = new HarbourDAO();
        Harbour port = portDAO.findHarbourByCode(rs.getInt("C_PORT_DBQ"));
        trip.setLandingHarbour(port);
        trip.setTimeAtSea(rs.getInt("V_TEMPS_M"));
        trip.setFishingTime(rs.getInt("V_TEMPS_P"));
        trip.setLocalMarketWeight(rs.getFloat("V_POIDS_FP"));
        trip.setTotalLandingWeight(rs.getFloat("V_POIDS_DBQ"));

        trip.setFlagEnquete(rs.getInt("F_ENQ"));
        if (rs.getDate("D_DEPART") != null) {
            trip.setDepartureDate(DateTimeUtils.convertDate(rs.getDate("D_DEPART")));
        }
        port = portDAO.findHarbourByCode(rs.getInt("C_PORT_DEP"));
        trip.setDepartureHarbour(port);

        trip.setLoch(rs.getInt("V_LOCH"));
        trip.setCommentaireMaree(rs.getString("L_COM_M"));

        trip.setNumeroMaree(rs.getString("C_ID_ERS"));
        trip.setCodeObServeTopiaid(rs.getString("C_TOPIAID"));

        trip.setWellAreEmptyAtStart(rs.getInt("F_CAL_VID_DPT"));

        ActivityDAO activiteDAO = new ActivityDAO();
        ArrayList<Activity> activites = (ArrayList<Activity>) activiteDAO.findAllActivityOfTrip(trip);
        trip.setActivites(activites);

        ElementaryLandingDAO lotCommercialDAO = new ElementaryLandingDAO();
        ArrayList<ElementaryLanding> lotCommerciaux = (ArrayList<ElementaryLanding>) lotCommercialDAO.findAllElementaryLanding(trip);
        trip.setLotsCommerciaux(lotCommerciaux);
        return trip;
    }

    /**
     * Retourne la marée associée à un numéro de marée, au sens ERS.
     *
     * @param tripNumber
     * @return
     * @throws fr.ird.driver.avdth.common.exception.AvdthDriverException
     */

    public Trip findTripByTripNumber(String tripNumber) throws AvdthDriverException {

        Trip maree = null;

        String query = "select * from MAREE "
                + " where C_ID_ERS = ? ";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, tripNumber);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                maree = factory(rs);
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
        return maree;

    }

    public List<Trip> findTrips(List<Vessel> vessels, List<Country> countries, DateTime start, DateTime end) throws AvdthDriverException {
        List<Trip> trips = new ArrayList<Trip>();
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
            trips.addAll(findTrips(b, start, end));
        }
        return trips;
    }

    public List<Trip> findTrips(Vessel vessel, DateTime start, DateTime end) throws AvdthDriverException {
        List<Trip> trips = null;

        String query = "select * from MAREE where C_BAT = ? "
                + " AND D_DBQ BETWEEN ? AND ?"
                + " ORDER BY C_BAT asc, D_DBQ asc";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, vessel.getCode());
//            statement.setString(2, DateTimeUtils.convertDate(((start == null ? 0 : start))));
//            statement.setString(3, DateTimeUtils.convertDate(((end == null ? (new Date()) : end))));
//            System.out.println(DateTimeUtils.convertDate(start));
            statement.setDate(2, DateTimeUtils.convertDate((start == null ? new DateTime(1900, 1, 1, 0, 0) : start)));
//            System.out.println(DateTimeUtils.convertDate((end == null ? new Date() : end)));
            statement.setDate(3, DateTimeUtils.convertDate((end == null ? new DateTime() : end)));

            ResultSet rs = statement.executeQuery();
            trips = new ArrayList<Trip>();

            while (rs.next()) {

                trips.add(factory(rs));

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
        return trips;
    }

    public Integer count() {
        String query = "select count(*) from (SELECT C_BAT, D_DBQ FROM MAREE)";

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

    public DateTime lastLandingDate() {
        String query = "select D_DBQ from (SELECT DISTINCT C_BAT, D_DBQ FROM MAREE) ORDER BY D_DBQ  DESC";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                return DateTimeUtils.convertDate(rs.getDate("D_DBQ"));
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

    public DateTime firstLandingDate() {
        String query = "select D_DBQ from (SELECT DISTINCT C_BAT, D_DBQ FROM MAREE) ORDER BY D_DBQ ASC";

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                return DateTimeUtils.convertDate(rs.getDate("D_DBQ"));
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

    public List<Trip> allTrips() throws AvdthDriverException {
        List<Trip> trips = null;

        String query = "select * from MAREE "
                + " ORDER BY C_BAT asc, D_DBQ asc";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);

            ResultSet rs = statement.executeQuery();
            trips = new ArrayList<Trip>();

            while (rs.next()) {

                trips.add(factory(rs));

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
        return trips;
    }

    public List<Trip> findAllTrips() throws AvdthDriverException {
        return allTrips();
    }

    /**
     * Find the previous trip of a vessel for the specified landing date.
     *
     * @param vessel
     * @param landingDate
     * @return the previous trip or null
     * @throws AvdthDriverException
     */
    public Trip findPreviousTrip(Vessel vessel, DateTime landingDate) throws AvdthDriverException {
        Trip trip = null;

        String query = "select top 1 * from MAREE "
                + " WHERE C_BAT = ? AND D_DBQ < ? "
                + " ORDER BY D_DBQ desc";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, vessel.getCode());
            statement.setDate(2, DateTimeUtils.convertDate(landingDate));
//        
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                trip = factory(rs);

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
        return trip;

    }

}
