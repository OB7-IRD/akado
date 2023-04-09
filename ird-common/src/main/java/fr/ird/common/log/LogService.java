/*
 * Copyright (C) 2013 Observatoire thonier, IRD
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
package fr.ird.common.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Service de logs.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 0.9
 * @deprecated since 3.0.0 (the design of this service is not ok and does not work, logging should be done in each
 * class which required it).
 */
@Deprecated
public class LogService {

    private static final LogService service = new LogService();
    private static Logger logger = LogManager.getLogger(LogService.class);

    /**
     * Constructeur privé car LogService est un singleton.
     */
    private LogService() {
    }

    /**
     * Log d'erreur dans le mapping ou les propriétés hibernate.
     *
     * @param message le message a retourner
     */
    public void logHibernateException(String message) {
        logger.error("L'utilisation du mapping ou des propriétés du fichier hibernate.cfg.xml pose problème: "
                + message);
    }

    /**
     * Log indiquant que le fichier conf.properties n'a pas été trouvé.
     */
    public void logFilePropertiesNotFound() {
        logger.error("Le fichier conf.properties est introuvable");
    }

    /**
     * *
     * @return LogService le service
     */
    public static LogService getService() {
        logger =LogManager.getLogger(LogService.class);
        return service;
    }

    /**
     * *
     * @param classz
     * @return LogService le service
     */
    public static LogService getService(Class classz) {
        logger = LogManager.getLogger(classz);
        return service;
    }

    /**
     * Log d'erreur applicatif.
     *
     * @param message le message d'erreur
     */
    public void logApplicationError(String message) {
        logger.error(message);
    }

    /**
     * Log de debug applicatif.
     *
     * @param message le message de débug
     */
    public void logApplicationDebug(String message) {
        logger.debug(message);
    }

    /**
     * Log d'info applicatif.
     *
     * @param message le message d'information
     */
    public void logApplicationInfo(String message) {
        logger.info(message);
    }

 	/**
	 * Log d'erreur de parsage car le fichier n'a pas été trouvé.
	 * 
	 * @param fileName
	 * @param message
	 */
	public void logParseError(String fileName, String message) {
		logger.error("Le parsage du fichier " + fileName
				+ " a échoué car le parseur ne l'a pas trouvé :" + message);
	}
   /**
     * Log d'alerte de l'applicatif.
     *
     * @param message le message d'alerte
     */
    public void logApplicationWarn(String message) {
        logger.warn(message);
    }
}
