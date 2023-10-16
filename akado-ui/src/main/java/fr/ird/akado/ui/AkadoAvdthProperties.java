/*
 * Copyright (C) 2014 Observatoire thonier, IRD
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
package fr.ird.akado.ui;

import fr.ird.akado.core.DataBaseInspector;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.common.configuration.AppConfig;
import fr.ird.common.configuration.IRDProperties;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static fr.ird.akado.core.common.AAProperties.ACTIVE_VALUE;
import static fr.ird.akado.core.common.AAProperties.DATE_FORMAT_XLS;
import static fr.ird.akado.core.common.AAProperties.KEY_ACTIVITY_INSPECTOR;
import static fr.ird.akado.core.common.AAProperties.KEY_DATE_FORMAT_XLS;
import static fr.ird.akado.core.common.AAProperties.KEY_POSITION_INSPECTOR;
import static fr.ird.akado.core.common.AAProperties.KEY_SAMPLE_INSPECTOR;
import static fr.ird.akado.core.common.AAProperties.KEY_SHP_COUNTRIES_PATH;
import static fr.ird.akado.core.common.AAProperties.KEY_SHP_HARBOUR_PATH;
import static fr.ird.akado.core.common.AAProperties.KEY_SHP_OCEAN_PATH;
import static fr.ird.akado.core.common.AAProperties.KEY_STANDARD_DIRECTORY;
import static fr.ird.akado.core.common.AAProperties.KEY_TRIP_INSPECTOR;
import static fr.ird.akado.core.common.AAProperties.KEY_WARNING_INSPECTOR;
import static fr.ird.akado.core.common.AAProperties.KEY_WELL_INSPECTOR;
import static fr.ird.akado.core.common.AAProperties.SHP_COUNTRIES_PATH;
import static fr.ird.akado.core.common.AAProperties.SHP_HARBOUR_PATH;
import static fr.ird.akado.core.common.AAProperties.SHP_OCEAN_PATH;
import static fr.ird.akado.core.common.AAProperties.STANDARD_DIRECTORY;

/**
 * The AkadoAvdthProperties class represents a persistent set of properties.
 * This properties are stored in the file "akado-config.xml".
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @date 24 juin 2014
 * @since 2.0
 */
public final class AkadoAvdthProperties extends IRDProperties {
    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(AkadoAvdthProperties.class);
    
    public final static String PROTOCOL_JDBC_ACCESS = "jdbc:Access:///";

    private static final AkadoAvdthProperties service = new AkadoAvdthProperties();

    public static AkadoAvdthProperties getService() {
        return service;
    }

    public static final String KEY_LAST_AVDTH_DATABASE_LOADED = "last_avdth_database_loaded";
    public static String LAST_AVDTH_DATABASE_LOADED;
    public static final String KEY_LAST_OBSERVE_DATABASE_BACKUP_LOADED = "last_observe_database_backup";
    public static String LAST_OBSERVE_DATABASE_BACKUP_LOADED;
    public static final String KEY_OBSERVE_JDBC_URL = "observe_jdbc_url";
    public static String OBSERVE_JDBC_URL;
    public static final String KEY_OBSERVE_JDBC_LOGIN = "observe_jdbc_login";
    public static String OBSERVE_JDBC_LOGIN;
    public static final String KEY_OBSERVE_JDBC_PASSWORD = "observe_jdbc_password";
    public static String OBSERVE_JDBC_PASSWORD;
    public static final String KEY_JDBC_ACCESS_DRIVER = "jdbc_access_driver";
    public static String JDBC_ACCESS_DRIVER = "com.hxtt.sql.access.AccessDriver";
    public static final String STANDARD_RELATIVE_CONFIG_PATH = "db";

    private AkadoAvdthProperties() {
        PROJECT_NAME = "akado";
        PROJECT_CONFIG_FILENAME = "akado.xml";
        PROJECT_CONFIG_COMMENT = "AKaDo configuration's properties";
        PROJECT_CONFIG_ABSOLUTE_PATH = AppConfig.getConfigDirectory(AppConfig.getRelativeConfigPath(PROJECT_NAME));
    }

    /**
     * Initialize the loading of properties.
     */
    public void init() {
        try {
            if (!configFileExist()) {
                log.debug("!configFileExist(): so create it!");
                createConfigFile();
            }
            Properties p = loadProperties();

            log.debug("Properties :");
            for (String k : p.stringPropertyNames()) {
                log.debug("Property : (" + k + "," + p.getProperty(k) + ")");
            }

            DataBaseInspector.CONFIGURATION_PROPERTIES = p;

            AAProperties.STANDARD_DIRECTORY = p.getProperty(AAProperties.KEY_STANDARD_DIRECTORY);
            AAProperties.SHP_COUNTRIES_PATH = p.getProperty(AAProperties.KEY_SHP_COUNTRIES_PATH);
            AAProperties.SHP_OCEAN_PATH = p.getProperty(AAProperties.KEY_SHP_OCEAN_PATH);
            AAProperties.SHP_HARBOUR_PATH = p.getProperty(AAProperties.KEY_SHP_HARBOUR_PATH);
            AAProperties.SHP_EEZ_PATH = p.getProperty(AAProperties.KEY_SHP_EEZ_PATH);
            AAProperties.DATE_FORMAT_XLS = p.getProperty(AAProperties.KEY_DATE_FORMAT_XLS);

            AAProperties.SAMPLE_INSPECTOR = p.getProperty(AAProperties.KEY_SAMPLE_INSPECTOR);
            AAProperties.WELL_INSPECTOR = p.getProperty(AAProperties.KEY_WELL_INSPECTOR);
            AAProperties.TRIP_INSPECTOR = p.getProperty(AAProperties.KEY_TRIP_INSPECTOR);
            AAProperties.POSITION_INSPECTOR = p.getProperty(AAProperties.KEY_POSITION_INSPECTOR);
            AAProperties.ACTIVITY_INSPECTOR = p.getProperty(AAProperties.KEY_ACTIVITY_INSPECTOR);

            AAProperties.WARNING_INSPECTOR = p.getProperty(AAProperties.KEY_WARNING_INSPECTOR);
            AAProperties.ANAPO_DB_URL = p.getProperty(AAProperties.KEY_ANAPO_DB_PATH);
            AAProperties.L10N = p.getProperty(AAProperties.KEY_L10N);

            AAProperties.THRESHOLD_CLASS_ONE = Double.parseDouble(p.getProperty(AAProperties.KEY_THRESHOLD_CLASS_ONE));
            AAProperties.THRESHOLD_CLASS_TWO = Double.parseDouble(p.getProperty(AAProperties.KEY_THRESHOLD_CLASS_TWO));

            AAProperties.NB_PROC = Integer.parseInt(p.getProperty(AAProperties.KEY_NB_PROC));

            AAProperties.ANAPO_INSPECTOR = p.getProperty(AAProperties.KEY_ANAPO_INSPECTOR);
            AAProperties.AKADO_INSPECTOR = p.getProperty(AAProperties.KEY_AKADO_INSPECTOR);
            AAProperties.ANAPO_VMS_COUNTRY = p.getProperty(AAProperties.KEY_ANAPO_VMS_COUNTRY);
            AAProperties.RESULTS_OUTPUT = p.getProperty(AAProperties.KEY_RESULTS_OUTPUT);
            AAProperties.PROTOCOL_JDBC_ACCESS = PROTOCOL_JDBC_ACCESS;

            AkadoAvdthProperties.JDBC_ACCESS_DRIVER = p.getProperty(KEY_JDBC_ACCESS_DRIVER);
            LAST_AVDTH_DATABASE_LOADED = p.getProperty(KEY_LAST_AVDTH_DATABASE_LOADED, "");
            LAST_OBSERVE_DATABASE_BACKUP_LOADED = p.getProperty(KEY_LAST_OBSERVE_DATABASE_BACKUP_LOADED, "");
            OBSERVE_JDBC_URL = p.getProperty(KEY_OBSERVE_JDBC_URL, "");
            OBSERVE_JDBC_LOGIN = p.getProperty(KEY_OBSERVE_JDBC_LOGIN, "");
            OBSERVE_JDBC_PASSWORD = p.getProperty(KEY_OBSERVE_JDBC_PASSWORD, "");
            AAProperties.OBSERVE_MODEL_MIN_VERSION = p.getProperty(AAProperties.KEY_OBSERVE_MODEL_MIN_VERSION, "9.2");
            AAProperties.OBSERVE_MODEL_MAX_VERSION = p.getProperty(AAProperties.KEY_OBSERVE_MODEL_MAX_VERSION, "9.2");


        } catch (Exception e) {

            log.error(e.getMessage(), e);
            log.error(e.toString());
        }
    }

    @Override
    public Properties createDefaultProperties() {
        log.debug("createDefaultProperties");
        log.debug("Initialisation de properties ");

        Properties p = new Properties();
        p.setProperty(KEY_SAMPLE_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_TRIP_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_ACTIVITY_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_POSITION_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_WELL_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_WARNING_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_DATE_FORMAT_XLS, DATE_FORMAT_XLS);
        p.setProperty(KEY_JDBC_ACCESS_DRIVER, JDBC_ACCESS_DRIVER);
        p.setProperty(KEY_LAST_AVDTH_DATABASE_LOADED, "");
        p.setProperty(KEY_LAST_OBSERVE_DATABASE_BACKUP_LOADED, "");
        p.setProperty(KEY_OBSERVE_JDBC_URL, "");
        p.setProperty(KEY_OBSERVE_JDBC_LOGIN, "sa");
        p.setProperty(KEY_OBSERVE_JDBC_PASSWORD, "sa");
        p.setProperty(AAProperties.KEY_OBSERVE_MODEL_MIN_VERSION, "9.2");
        p.setProperty(AAProperties.KEY_OBSERVE_MODEL_MAX_VERSION, "9.2");
        p.setProperty(AAProperties.KEY_ANAPO_DB_PATH, "");
        p.setProperty(AAProperties.KEY_L10N, "fr");
        p.setProperty(AAProperties.KEY_THRESHOLD_CLASS_ONE, "10");
        p.setProperty(AAProperties.KEY_THRESHOLD_CLASS_TWO, "20");
        p.setProperty(AAProperties.KEY_NB_PROC, "1");

        p.setProperty(AAProperties.KEY_AKADO_INSPECTOR, AAProperties.ACTIVE_VALUE);
        p.setProperty(AAProperties.KEY_ANAPO_INSPECTOR, AAProperties.DISABLE_VALUE);
        p.setProperty(AAProperties.KEY_ANAPO_VMS_COUNTRY, AAProperties.ANAPO_VMS_COUNTRY);

        p.setProperty(AAProperties.KEY_RESULTS_OUTPUT, AAProperties.RESULTS_OUTPUT);

        log.debug("Creation de la property: " + PROJECT_CONFIG_ABSOLUTE_PATH + File.separator + STANDARD_RELATIVE_CONFIG_PATH);
        p.setProperty(KEY_STANDARD_DIRECTORY, PROJECT_CONFIG_ABSOLUTE_PATH + File.separator + STANDARD_RELATIVE_CONFIG_PATH);

        p.setProperty(KEY_SHP_OCEAN_PATH, new File(getInstallPath() + File.separator + "resources" + File.separator + "shp" + File.separator + "IHOSeasAndOceans.shp").getPath());
        p.setProperty(KEY_SHP_COUNTRIES_PATH, new File(getInstallPath() + File.separator + "resources" + File.separator + "shp" + File.separator + "countries.shp").getPath());
        p.setProperty(KEY_SHP_HARBOUR_PATH, new File(getInstallPath() + File.separator + "resources" + File.separator + "shp" + File.separator + "harbour.shp").getPath());
        p.setProperty(AAProperties.KEY_SHP_EEZ_PATH, new File(getInstallPath() + File.separator + "resources" + File.separator + "shp" + File.separator + "eez.shp").getPath());
        log.debug("**************************");
        log.debug(p.toString());
        log.debug("**************************");
        return p;
    }

    @Override
    public void createDefaultDirectory() {
        super.createDefaultDirectory();

        String dbPath = PROJECT_CONFIG_ABSOLUTE_PATH + File.separator + STANDARD_RELATIVE_CONFIG_PATH;
        boolean success = (new File(dbPath)).mkdirs();
        if (success) {
            log.debug("Directory: " + dbPath + " created");
        }
    }

    @Override
    public void copyDefaultFile() {
    }

    public void saveProperties() {
        Properties p = new Properties();
        p.setProperty(KEY_SAMPLE_INSPECTOR, AAProperties.SAMPLE_INSPECTOR);
        p.setProperty(KEY_TRIP_INSPECTOR, AAProperties.TRIP_INSPECTOR);
        p.setProperty(KEY_ACTIVITY_INSPECTOR, AAProperties.ACTIVITY_INSPECTOR);
        p.setProperty(KEY_POSITION_INSPECTOR, AAProperties.POSITION_INSPECTOR);
        p.setProperty(KEY_WELL_INSPECTOR, AAProperties.WELL_INSPECTOR);
        p.setProperty(KEY_WARNING_INSPECTOR, AAProperties.WARNING_INSPECTOR);

        p.setProperty(KEY_STANDARD_DIRECTORY, STANDARD_DIRECTORY);
        p.setProperty(KEY_SHP_COUNTRIES_PATH, SHP_COUNTRIES_PATH);
        p.setProperty(KEY_SHP_OCEAN_PATH, SHP_OCEAN_PATH);
        p.setProperty(KEY_SHP_HARBOUR_PATH, SHP_HARBOUR_PATH);
        p.setProperty(AAProperties.KEY_SHP_EEZ_PATH, AAProperties.SHP_EEZ_PATH);
        p.setProperty(KEY_DATE_FORMAT_XLS, DATE_FORMAT_XLS);

        p.setProperty(AAProperties.KEY_ANAPO_DB_PATH, AAProperties.ANAPO_DB_URL);
        p.setProperty(AAProperties.KEY_L10N, AAProperties.L10N);

        p.setProperty(AAProperties.KEY_THRESHOLD_CLASS_ONE, Double.toString(AAProperties.THRESHOLD_CLASS_ONE));
        p.setProperty(AAProperties.KEY_THRESHOLD_CLASS_TWO, Double.toString(AAProperties.THRESHOLD_CLASS_TWO));
        p.setProperty(AAProperties.KEY_NB_PROC, Integer.toString(AAProperties.NB_PROC));
        p.setProperty(KEY_LAST_AVDTH_DATABASE_LOADED, LAST_AVDTH_DATABASE_LOADED);
        p.setProperty(KEY_LAST_OBSERVE_DATABASE_BACKUP_LOADED, LAST_OBSERVE_DATABASE_BACKUP_LOADED);
        p.setProperty(KEY_OBSERVE_JDBC_URL, OBSERVE_JDBC_URL);
        p.setProperty(KEY_OBSERVE_JDBC_LOGIN, OBSERVE_JDBC_LOGIN);
        p.setProperty(KEY_OBSERVE_JDBC_PASSWORD, OBSERVE_JDBC_PASSWORD);
        p.setProperty(KEY_JDBC_ACCESS_DRIVER, JDBC_ACCESS_DRIVER);
        p.setProperty(AAProperties.KEY_OBSERVE_MODEL_MIN_VERSION, AAProperties.OBSERVE_MODEL_MIN_VERSION);
        p.setProperty(AAProperties.KEY_OBSERVE_MODEL_MAX_VERSION, AAProperties.OBSERVE_MODEL_MAX_VERSION);

        p.setProperty(AAProperties.KEY_AKADO_INSPECTOR, AAProperties.AKADO_INSPECTOR);
        p.setProperty(AAProperties.KEY_ANAPO_INSPECTOR, AAProperties.ANAPO_INSPECTOR);
        p.setProperty(AAProperties.KEY_ANAPO_VMS_COUNTRY, AAProperties.ANAPO_VMS_COUNTRY);
        p.setProperty(AAProperties.KEY_RESULTS_OUTPUT, AAProperties.RESULTS_OUTPUT);
        saveProperties(p);
    }

    public String getInstallPath() {
        try {
            File file = new File(AkadoAvdthProperties.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            return file.getParentFile().getParentFile().getAbsolutePath();
        } catch (URISyntaxException ex) {
            Logger.getLogger(AkadoAvdthProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

}
