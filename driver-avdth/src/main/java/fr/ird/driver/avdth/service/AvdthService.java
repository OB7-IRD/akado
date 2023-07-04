/**
 *
 * Copyright (C) 2013 Julien Lebranchu <julien.lebranchu@ird.fr>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.driver.avdth.service;

import fr.ird.common.JDBCUtilities;
import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.ElementaryCatch;
import fr.ird.driver.avdth.business.ElementaryLanding;
import fr.ird.driver.avdth.business.FishingContext;
import fr.ird.driver.avdth.business.Trip;
import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import fr.ird.driver.avdth.dao.ActivityDAO;
import fr.ird.driver.avdth.dao.CountryDAO;
import fr.ird.driver.avdth.dao.ElementaryCatchDAO;
import fr.ird.driver.avdth.dao.ElementaryLandingDAO;
import fr.ird.driver.avdth.dao.FishingContextDAO;
import fr.ird.driver.avdth.dao.SampleDAO;
import fr.ird.driver.avdth.dao.TripDAO;
import fr.ird.driver.avdth.dao.VesselDAO;
import fr.ird.driver.avdth.dao.WellDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Service de connexion à la base de données AVDTH, via le dialect de la
 * librairie développée par HXTT.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class AvdthService {
    private static final Logger log = LogManager.getLogger(AvdthService.class);
    private static final AvdthService SERVICE = new AvdthService();

    private String url;
    private Connection connection;

    private AvdthService() {
    }

    private String user;
    private String password;
    Properties p;

    public void init(String url, String driver, String user, String password) throws AvdthDriverException {
        this.url = url;
        this.user = user;
        this.password = password;
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
            log.debug("AVDTH SERVICE : initialisation");
            log.debug("AVDTH SERVICE : " + url);
            p = new Properties();
            p.setProperty("user", this.user);
            p.setProperty("password", this.password);
            p.setProperty("charSet", "utf-8");
            p.setProperty("lockType", "ACCESS");
            connection = DriverManager.getConnection(this.url, p);
            log.debug("AVDTH SERVICE : end");
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException |
                 NoSuchMethodException | InvocationTargetException ex) {
            throw new AvdthDriverException("The database connection has failed. See below for more information.\n\n\t«" + ex.getMessage() + "»", ex);
        }
    }

    /**
     *
     * @param connection
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     *
     * @return
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Enregistre une marée dans la base de donnée AVDTH.
     *
     * @param maree la marée à sauvegarder
     * @return
     *
     */
    public boolean save(Trip maree) {

        TripDAO mareeDAO = new TripDAO();
        boolean isOk = mareeDAO.insert(maree);

        for (Activity activite : maree.getActivites()) {
            log.debug(activite.toString());
            isOk &= save(activite);
        }
        for (ElementaryLanding el : maree.getLotsCommerciaux()) {
            log.debug(el.toString());
            isOk &= save(el);
        }
        return isOk;
    }

    /**
     * Enregistre une activité dans la base de donnée AVDTH.
     *
     * @param activite l'activité à sauvegarder
     * @return
     *
     */
    public boolean save(Activity activite) {

        ActivityDAO activiteDAO = new ActivityDAO();
        boolean isOk = activiteDAO.insert(activite);

        for (ElementaryCatch captureElementaire : activite.getElementaryCatchs()) {
            isOk &= save(captureElementaire);
        }

        for (FishingContext activiteAssociation : activite.getFishingContexts()) {
            isOk &= save(activiteAssociation);
        }
        return isOk;
    }

    /**
     * Enregistre un contexte de pêche dans la base de donnée AVDTH.
     *
     * @param activiteAssociation le contexte de pêche à sauvegarder
     * @return
     *
     */
    public boolean save(FishingContext activiteAssociation) {
        FishingContextDAO activiteAssociationDAO = new FishingContextDAO();
        return activiteAssociationDAO.insert(activiteAssociation);
    }

    /**
     * Enregistre la capture d'une activité dans la base de donnée AVDTH.
     *
     * @param captureElementaire la capture à sauvegarder
     * @return
     *
     */
    public boolean save(ElementaryCatch captureElementaire) {
        ElementaryCatchDAO captureElementaireDAO = new ElementaryCatchDAO();
        return captureElementaireDAO.insert(captureElementaire);
    }

    /**
     * Enregistre le débarquement dans la base de donnée AVDTH.
     *
     * @param elementaryLanding le débarquement à sauvegarder
     * @return
     *
     */
    public boolean save(ElementaryLanding elementaryLanding) {
        ElementaryLandingDAO landingDAO = new ElementaryLandingDAO();
        return landingDAO.insert(elementaryLanding);
    }

    /**
     * Met à jour une marée dans la base de donnée AVDTH.
     *
     * @param maree la marée à mettre à jour
     * @return
     *
     */
    public boolean update(Trip maree) {

        TripDAO mareeDAO = new TripDAO();
        boolean isOk = mareeDAO.update(maree);

        for (Activity activite : maree.getActivites()) {
            isOk &= update(activite);
        }
        for (ElementaryLanding el : maree.getLotsCommerciaux()) {
            isOk &= update(el);
        }
        return isOk;
    }

    /**
     * Met à jour une activité dans la base de donnée AVDTH.
     *
     * @param activite l'activité à mettre à jour
     * @return
     *
     */
    public boolean update(Activity activite) {
        ActivityDAO activiteDAO = new ActivityDAO();
        boolean isOk = activiteDAO.update(activite);

        for (ElementaryCatch captureElementaire : activite.getElementaryCatchs()) {
            isOk &= update(captureElementaire);
        }

        for (FishingContext activiteAssociation : activite.getFishingContexts()) {
            isOk &= update(activiteAssociation);
        }
        return isOk;
    }

    /**
     * Met à jour un contexte de pêche dans la base de donnée AVDTH.
     *
     * @param activiteAssociation le contexte de pêche à mettre à jour
     * @return
     *
     */
    public boolean update(FishingContext activiteAssociation) {
        FishingContextDAO activiteAssociationDAO = new FishingContextDAO();
        return activiteAssociationDAO.update(activiteAssociation);
    }

    /**
     * Met à jour la capture d'une activité dans la base de donnée AVDTH.
     *
     * @param captureElementaire la capture à mettre à jour
     * @return
     *
     */
    public boolean update(ElementaryCatch captureElementaire) {
        ElementaryCatchDAO captureElementaireDAO = new ElementaryCatchDAO();
        return captureElementaireDAO.update(captureElementaire);
    }

    public boolean update(ElementaryLanding elementaryLanding) {
        ElementaryLandingDAO landingDAO = new ElementaryLandingDAO();
        return landingDAO.insert(elementaryLanding);
    }

    /**
     * Supprime une marée dans la base de donnée AVDTH.
     *
     * @param maree la marée à supprimer
     * @return
     *
     */
    public boolean delete(Trip maree) {
        boolean isOk = true;
        TripDAO mareeDAO = new TripDAO();

        for (Activity activite : maree.getActivites()) {
            isOk &= delete(activite);
        }
        for (ElementaryLanding el : maree.getLotsCommerciaux()) {
            isOk &= delete(el);
        }

        isOk &= mareeDAO.delete(maree);
        return isOk;
    }

    /**
     * Supprime une activité dans la base de donnée AVDTH.
     *
     * @param activite l'activité à supprimer
     * @return
     *
     */
    public boolean delete(Activity activite) {
        boolean isOk = true;
        ActivityDAO activiteDAO = new ActivityDAO();

        for (ElementaryCatch captureElementaire : activite.getElementaryCatchs()) {
            isOk &= delete(captureElementaire);
        }

        for (FishingContext activiteAssociation : activite.getFishingContexts()) {
            isOk &= delete(activiteAssociation);
        }
        isOk &= activiteDAO.delete(activite);
        return isOk;
    }

    /**
     * Supprime un contexte de pêche dans la base de donnée AVDTH.
     *
     * @param activiteAssociation le contexte de pêche à supprimer
     * @return
     *
     */
    public boolean delete(FishingContext activiteAssociation) {
        FishingContextDAO activiteAssociationDAO = new FishingContextDAO();
        return activiteAssociationDAO.delete(activiteAssociation);
    }

    /**
     * Supprime la capture d'une activité dans la base de donnée AVDTH.
     *
     * @param captureElementaire la capture à supprimer
     * @return
     *
     */
    public boolean delete(ElementaryCatch captureElementaire) {
        ElementaryCatchDAO captureElementaireDAO = new ElementaryCatchDAO();
        return captureElementaireDAO.delete(captureElementaire);
    }

    public boolean delete(ElementaryLanding elementaryLanding) {
        ElementaryLandingDAO elementaryLandingDAO = new ElementaryLandingDAO();
        return elementaryLandingDAO.delete(elementaryLanding);
    }

    public static AvdthService getService() {
        return SERVICE;
    }

    public boolean isClosed() throws SQLException {
        return getConnection() != null && getConnection().isClosed();
    }

    public void close() {

        try {
            if (!isClosed()) {
                getConnection().close();
            }
        } catch (SQLException ex) {
            JDBCUtilities.printSQLException(ex);
        }
    }

    public void open() {
        try {
            if (isClosed()) {
                connection = DriverManager.getConnection(url, p);
            }
        } catch (SQLException e) {
            JDBCUtilities.printSQLException(e);
        }
    }

    public CountryDAO getCountryDAO() {
        return new CountryDAO();
    }

    public ActivityDAO getActivityDAO() {
        return new ActivityDAO();
    }

    public TripDAO getTripDAO() {
        return new TripDAO();
    }

    public VesselDAO getVesselDAO() {
        return new VesselDAO();
    }

    public SampleDAO getSampleDAO() {
        return new SampleDAO();
    }

    public WellDAO getWellDAO() {
        return new WellDAO();
    }

}
