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
import fr.ird.akado.avdth.common.AAProperties;
import static fr.ird.akado.avdth.common.AAProperties.ACTIVE_VALUE;
import static fr.ird.akado.avdth.common.AAProperties.DATE_FORMAT_XLS;
import static fr.ird.akado.avdth.common.AAProperties.KEY_ACTIVITY_INSPECTOR;
import static fr.ird.akado.avdth.common.AAProperties.KEY_DATE_FORMAT_XLS;
import static fr.ird.akado.avdth.common.AAProperties.KEY_POSITION_INSPECTOR;
import static fr.ird.akado.avdth.common.AAProperties.KEY_SAMPLE_INSPECTOR;
import static fr.ird.akado.avdth.common.AAProperties.KEY_SHP_COUNTRIES_PATH;
import static fr.ird.akado.avdth.common.AAProperties.KEY_SHP_HARBOUR_PATH;
import static fr.ird.akado.avdth.common.AAProperties.KEY_SHP_OCEAN_PATH;
import static fr.ird.akado.avdth.common.AAProperties.KEY_STANDARD_DIRECTORY;
import static fr.ird.akado.avdth.common.AAProperties.KEY_TRIP_INSPECTOR;
import static fr.ird.akado.avdth.common.AAProperties.KEY_WARNING_INSPECTOR;
import static fr.ird.akado.avdth.common.AAProperties.KEY_WELL_INSPECTOR;
import static fr.ird.akado.avdth.common.AAProperties.SHP_COUNTRIES_PATH;
import static fr.ird.akado.avdth.common.AAProperties.SHP_HARBOUR_PATH;
import static fr.ird.akado.avdth.common.AAProperties.SHP_OCEAN_PATH;
import static fr.ird.akado.avdth.common.AAProperties.STANDARD_DIRECTORY;
import fr.ird.common.configuration.AppConfig;
import fr.ird.common.configuration.IRDProperties;
import fr.ird.common.log.LogService;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The AkadoAvdthProperties class represents a persistent set of properties.
 * This properties are stored in the file "akado-config.xml".
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 24 juin 2014
 *
 */
public final class AkadoAvdthProperties extends IRDProperties {

    public final static String PROTOCOL_JDBC_ACCESS = "jdbc:Access:///";

    private static final AkadoAvdthProperties service = new AkadoAvdthProperties();

    public static AkadoAvdthProperties getService() {
        return service;
    }
    public static final String KEY_LAST_DATABASE_LOADED = "last_database_loaded";
    public static final String KEY_THIRD_PARTY_DATASOURCE = "third_party_datasource";
    public static String THIRD_PARTY_DATASOURCE_NAME = "fr.ird.akado.avdth.AvdthInspector";
    public static Class<DataBaseInspector> THIRD_PARTY_DATASOURCE = null;

    public static final String KEY_JDBC_ACCESS_DRIVER = "jdbc_access_driver";
    public static String JDBC_ACCESS_DRIVER = "com.hxtt.sql.access.AccessDriver";
    public static final String STANDARD_RELATIVE_CONFIG_PATH = "db";
    public static String LAST_DATABASE_LOADED;

    private AkadoAvdthProperties() {
        PROJECT_NAME = "akado-avdth";
        PROJECT_CONFIG_FILENAME = "akado-config.xml";
        PROJECT_CONFIG_COMMENT = "AKaDo Avdth configuration's properties";

        PROJECT_CONFIG_ABSOLUTE_PATH = AppConfig.getConfigDirectory(AppConfig.getRelativeConfigPath(PROJECT_NAME));
    }

    /**
     * Initialize the loading of properties.
     */
    public void init() {
        try {
            if (!configFileExist()) {
                LogService.getService(AkadoAvdthProperties.class).logApplicationDebug("!configFileExist(): so create it!");
                createConfigFile();
            }
            Properties p = loadProperties();

            LogService.getService(AkadoAvdthProperties.class).logApplicationDebug("Properties :");
            for (String k : p.stringPropertyNames()) {
                LogService.getService(AkadoAvdthProperties.class).logApplicationDebug("Property : (" + k + "," + p.getProperty(k) + ")");
            }
            try {
                THIRD_PARTY_DATASOURCE = (Class<DataBaseInspector>) Class.forName(p.getProperty(KEY_THIRD_PARTY_DATASOURCE));

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

                AAProperties.THRESHOLD_CLASS_ONE = Double.valueOf(p.getProperty(AAProperties.KEY_THRESHOLD_CLASS_ONE));
                AAProperties.THRESHOLD_CLASS_TWO = Double.valueOf(p.getProperty(AAProperties.KEY_THRESHOLD_CLASS_TWO));

                AAProperties.NB_PROC = Integer.valueOf(p.getProperty(AAProperties.KEY_NB_PROC));

                AAProperties.ANAPO_INSPECTOR = p.getProperty(AAProperties.KEY_ANAPO_INSPECTOR);
                AAProperties.AKADO_INSPECTOR = p.getProperty(AAProperties.KEY_AKADO_INSPECTOR);
                AAProperties.ANAPO_VMS_COUNTRY = p.getProperty(AAProperties.KEY_ANAPO_VMS_COUNTRY);
                AAProperties.RESULTS_OUTPUT = p.getProperty(AAProperties.KEY_RESULTS_OUTPUT);
                AAProperties.PROTOCOL_JDBC_ACCESS = PROTOCOL_JDBC_ACCESS;

            } catch (ClassNotFoundException e) {
                LogService.getService(this.getClass()).logApplicationError(e.toString());
                LogService.getService(this.getClass()).logApplicationError(e.getMessage() + "\nThe value of " + KEY_THIRD_PARTY_DATASOURCE + " key is " + THIRD_PARTY_DATASOURCE);

            }
//            AkadoAvdthProperties.JDBC_URL = p.getProperty(KEY_JDBC_URL);
            AkadoAvdthProperties.JDBC_ACCESS_DRIVER = p.getProperty(KEY_JDBC_ACCESS_DRIVER);
            LAST_DATABASE_LOADED = p.getProperty(KEY_LAST_DATABASE_LOADED);
//            AkadoAvdthProperties.JDBC_USER = p.getProperty(KEY_JDBC_USER);
//            AkadoAvdthProperties.JDBC_PWD = p.getProperty(KEY_JDBC_PWD);

        } catch (Exception e) {

            LogService.getService(this.getClass()).logApplicationError(e.getMessage());
            LogService.getService(this.getClass()).logApplicationError(e.toString());
        }
    }

    @Override
    public Properties createDefaultProperties() {
        LogService.getService(AkadoAvdthProperties.class).logApplicationDebug("createDefaultProperties");
        LogService.getService(AkadoAvdthProperties.class).logApplicationDebug("Initialisation de properties ");

        Properties p = new Properties();
        p.setProperty(KEY_SAMPLE_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_TRIP_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_ACTIVITY_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_POSITION_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_WELL_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_WARNING_INSPECTOR, ACTIVE_VALUE);
        LogService.getService(AkadoAvdthProperties.class).logApplicationDebug("**************************");
        LogService.getService(AkadoAvdthProperties.class).logApplicationDebug(p.toString());

        p.setProperty(KEY_DATE_FORMAT_XLS, DATE_FORMAT_XLS);
        LogService.getService(AkadoAvdthProperties.class).logApplicationDebug("**************************");
        LogService.getService(AkadoAvdthProperties.class).logApplicationDebug(p.toString());
        p.setProperty(KEY_THIRD_PARTY_DATASOURCE, THIRD_PARTY_DATASOURCE_NAME);
        LogService.getService(AkadoAvdthProperties.class).logApplicationDebug("**************************");
        LogService.getService(AkadoAvdthProperties.class).logApplicationDebug(p.toString());
        p.setProperty(KEY_JDBC_ACCESS_DRIVER, JDBC_ACCESS_DRIVER);
        LogService.getService(AkadoAvdthProperties.class).logApplicationDebug("**************************");
        LogService.getService(AkadoAvdthProperties.class).logApplicationDebug(p.toString());

        p.setProperty(KEY_LAST_DATABASE_LOADED, "");
        p.setProperty(AAProperties.KEY_ANAPO_DB_PATH, "");
        p.setProperty(AAProperties.KEY_L10N, "fr");
        LogService.getService(AkadoAvdthProperties.class).logApplicationDebug("**************************");
        LogService.getService(AkadoAvdthProperties.class).logApplicationDebug(p.toString());

        p.setProperty(AAProperties.KEY_THRESHOLD_CLASS_ONE, "10");
        p.setProperty(AAProperties.KEY_THRESHOLD_CLASS_TWO, "20");
        p.setProperty(AAProperties.KEY_NB_PROC, "1");
        LogService.getService(AkadoAvdthProperties.class).logApplicationDebug("**************************");
        LogService.getService(AkadoAvdthProperties.class).logApplicationDebug(p.toString());

        p.setProperty(AAProperties.KEY_AKADO_INSPECTOR, AAProperties.ACTIVE_VALUE);
        p.setProperty(AAProperties.KEY_ANAPO_INSPECTOR, AAProperties.DISABLE_VALUE);
        p.setProperty(AAProperties.KEY_ANAPO_VMS_COUNTRY, AAProperties.ANAPO_VMS_COUNTRY);

        p.setProperty(AAProperties.KEY_RESULTS_OUTPUT, AAProperties.RESULTS_OUTPUT);

        LogService.getService(AkadoAvdthProperties.class).logApplicationDebug("Creation de la property: " + PROJECT_CONFIG_ABSOLUTE_PATH + File.separator + STANDARD_RELATIVE_CONFIG_PATH);
        p.setProperty(KEY_STANDARD_DIRECTORY, PROJECT_CONFIG_ABSOLUTE_PATH + File.separator + STANDARD_RELATIVE_CONFIG_PATH);

        p.setProperty(KEY_SHP_OCEAN_PATH, new File(getInstallPath() + File.separator + "resources" + File.separator + "shp" + File.separator + "IHOSeasAndOceans.shp").getPath());
        p.setProperty(KEY_SHP_COUNTRIES_PATH, new File(getInstallPath() + File.separator + "resources" + File.separator + "shp" + File.separator + "countries.shp").getPath());
        p.setProperty(KEY_SHP_HARBOUR_PATH, new File(getInstallPath() + File.separator + "resources" + File.separator + "shp" + File.separator + "harbour.shp").getPath());
        p.setProperty(AAProperties.KEY_SHP_EEZ_PATH, new File(getInstallPath() + File.separator + "resources" + File.separator + "shp" + File.separator + "eez.shp").getPath());
        LogService.getService(AkadoAvdthProperties.class).logApplicationDebug("**************************");
        LogService.getService(AkadoAvdthProperties.class).logApplicationDebug(p.toString());
        LogService.getService(AkadoAvdthProperties.class).logApplicationDebug("**************************");
        return p;
    }

    @Override
    public void createDefaultDirectory() {
        super.createDefaultDirectory();

        String dbPath = PROJECT_CONFIG_ABSOLUTE_PATH + File.separator + STANDARD_RELATIVE_CONFIG_PATH;
        boolean success = (new File(dbPath)).mkdirs();
        if (success) {
            LogService.getService(AkadoAvdthProperties.class).logApplicationDebug("Directory: " + dbPath + " created");
        }
    }

    @Override
    public void copyDefaultFile() {
    }

    public static Properties getDefaultProperties() {
        Properties p = new Properties();
        p.setProperty(KEY_SAMPLE_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_TRIP_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_ACTIVITY_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_POSITION_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_WELL_INSPECTOR, ACTIVE_VALUE);
        p.setProperty(KEY_WARNING_INSPECTOR, ACTIVE_VALUE);

        p.setProperty(KEY_STANDARD_DIRECTORY, STANDARD_DIRECTORY);
        p.setProperty(KEY_SHP_COUNTRIES_PATH, SHP_COUNTRIES_PATH);
        p.setProperty(KEY_SHP_OCEAN_PATH, SHP_OCEAN_PATH);
        p.setProperty(KEY_SHP_HARBOUR_PATH, SHP_HARBOUR_PATH);
        p.setProperty(AAProperties.KEY_SHP_EEZ_PATH, AAProperties.SHP_EEZ_PATH);
        p.setProperty(KEY_DATE_FORMAT_XLS, DATE_FORMAT_XLS);

        p.setProperty(AAProperties.KEY_THRESHOLD_CLASS_ONE, "10");
        p.setProperty(AAProperties.KEY_THRESHOLD_CLASS_TWO, "20");
        p.setProperty(AAProperties.KEY_NB_PROC, "1");
        p.setProperty(AAProperties.KEY_AKADO_INSPECTOR, AAProperties.ACTIVE_VALUE);
        p.setProperty(AAProperties.KEY_ANAPO_INSPECTOR, AAProperties.DISABLE_VALUE);
        p.setProperty(AAProperties.KEY_ANAPO_VMS_COUNTRY, AAProperties.ANAPO_VMS_COUNTRY);
        p.setProperty(AAProperties.KEY_RESULTS_OUTPUT, AAProperties.RESULTS_OUTPUT);

        return p;
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
        p.setProperty(KEY_LAST_DATABASE_LOADED, LAST_DATABASE_LOADED);
        p.setProperty(KEY_JDBC_ACCESS_DRIVER, JDBC_ACCESS_DRIVER);
        p.setProperty(KEY_THIRD_PARTY_DATASOURCE, THIRD_PARTY_DATASOURCE_NAME);

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
