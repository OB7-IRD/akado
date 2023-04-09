/**
 * Copyright (C) 2014 Observatoire thonier, IRD
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
package fr.ird.driver.anapo.service;

import fr.ird.common.JDBCUtilities;
import fr.ird.driver.anapo.business.BatVMS;
import fr.ird.driver.anapo.business.PosVMS;
import fr.ird.driver.anapo.common.exception.ANAPODriverException;
import fr.ird.driver.anapo.dao.BatVMSDAO;
import fr.ird.driver.anapo.dao.PosVMSDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Service de connexion à la base de données ANAPO, via le dialect de la
 * librairie développée par HXTT.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @version 1.0
 * @date 14 fevr. 2014
 */
public class ANAPOService {
    private static final Logger log = LogManager.getLogger(ANAPOService.class);
    private static final ANAPOService service = new ANAPOService();    
//    private static final String PROTOCOLE = "jdbc:Access:///";
    private String url;
    private String user;
    private String password;
    private Connection connection;

    private ANAPOService() {
    }

    public void init(String url, String driver, String user, String password) throws ANAPODriverException {
//    public void init(String pathUrlDb) {
        this.url = url;
        this.user = user;
        this.password = password;

        try {
            Class.forName(driver).newInstance();
            // Connexion à la base
            log.debug("ANAPO SERVICE : initialisation");
            log.debug("ANAPO SERVICE : " + url);
            connection = DriverManager.getConnection(this.url, this.user, this.password);
            log.debug("ANAPO SERVICE : end");

        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
            throw new ANAPODriverException("ANAPO: The database connection has failed. See below for more information.\n\n\t«" + ex.getMessage() + "»", ex);
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
     * Save the position in ANAPO.
     *
     * @param posVMS the VMS position to save.
     * @return
     *
     */
    public boolean save(PosVMS posVMS) {
        PosVMSDAO posVMSDAO = new PosVMSDAO();
        return posVMSDAO.insert(posVMS);
    }

    /**
     * Update the position in ANAPO.
     *
     * @param posVMS the VMS position to update.
     * @return
     *
     */
    public boolean update(PosVMS posVMS) {
        PosVMSDAO posVMSDAO = new PosVMSDAO();
        return posVMSDAO.update(posVMS);
    }

    /**
     * Delete the position in ANAPO.
     *
     * @param posVMS the VMS position to delete.
     * @return
     *
     */
    public boolean delete(PosVMS posVMS) {
        PosVMSDAO posVMSDAO = new PosVMSDAO();
        return posVMSDAO.delete(posVMS);
    }

    /**
     * Save the vessel information in ANAPO.
     *
     * @param batVMS the vessel information to save.
     * @return
     *
     */
    public boolean save(BatVMS batVMS) {
        BatVMSDAO batVMSDAO = new BatVMSDAO();
        return batVMSDAO.insert(batVMS);
    }

    /**
     * Update the vessel information in ANAPO.
     *
     * @param batVMS the vessel information to update.
     * @return
     *
     */
    public boolean update(BatVMS batVMS) {
        BatVMSDAO batVMSDAO = new BatVMSDAO();
        return batVMSDAO.update(batVMS);
    }

    /**
     * Delete the vessel information in ANAPO.
     *
     * @param batVMS the vessel information to delete.
     * @return
     *
     */
    public boolean delete(BatVMS batVMS) {
        BatVMSDAO batVMSDAO = new BatVMSDAO();
        return batVMSDAO.delete(batVMS);
    }

    public static ANAPOService getService() {
        return service;
    }

    public boolean isClosed() throws SQLException {
        return getConnection().isClosed();
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
                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException e) {
            JDBCUtilities.printSQLException(e);
        }
    }

}
