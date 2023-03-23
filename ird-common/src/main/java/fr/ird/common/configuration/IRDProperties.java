/*
 * 
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
package fr.ird.common.configuration;

import fr.ird.common.log.LogService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * The <em>IRDProperties</em> class represents a persistent set of properties.
 * This properties are stored in a file.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 13 févr. 2014
 *
 */
public abstract class IRDProperties {

    /**
     * The name of the project.
     */
    protected static String PROJECT_NAME;
    /**
     *
     */
    protected static String PROJECT_CONFIG_FILENAME;
    /**
     * A description of the project.
     */
    protected static String PROJECT_CONFIG_COMMENT;
    /**
     * The absolute path of the project. This properties is filled via the {@link #createDefaultDirectory()
     * } method.
     */
    public static String PROJECT_CONFIG_ABSOLUTE_PATH;

    /**
     * Load all properties of an application.
     *
     * @return the property of IRD application
     * @throws FileNotFoundException
     * @throws IOException
     * @throws java.util.InvalidPropertiesFormatException
     *
     */
    public Properties loadProperties() throws FileNotFoundException, IOException, InvalidPropertiesFormatException, Exception {
        if (PROJECT_NAME == null && PROJECT_CONFIG_FILENAME == null) {
            throw new Exception("You must set PROJECT_CONFIG_FILENAME and PROJECT_NAME.");
        }

        Properties prop = new Properties();
        String filepath = AppConfig.getRelativeConfigPath(PROJECT_NAME);
        String filename = PROJECT_CONFIG_FILENAME;
        FileInputStream fis = new FileInputStream(AppConfig.getConfigFile(filepath, filename));
        prop.loadFromXML(fis);
        return prop;
    }

    /**
     *
     * Create the default configuration file.
     *
     * @throws Exception
     */
    public void createConfigFile() throws Exception {
        if (PROJECT_NAME == null && PROJECT_CONFIG_FILENAME == null) {
            throw new Exception("You must set PROJECT_CONFIG_FILENAME and PROJECT_NAME.");
        }
        String filepath = AppConfig.getRelativeConfigPath(PROJECT_NAME);
        String filename = PROJECT_CONFIG_FILENAME;
        if (!configFileExist()) {
            LogService.getService(IRDProperties.class).logApplicationInfo("Create the default configuration file");
            createDefaultDirectory();
            copyDefaultFile();

            try {
                FileOutputStream fos = new FileOutputStream(AppConfig.getConfigFile(filepath, filename));
                createDefaultProperties().storeToXML(fos, PROJECT_CONFIG_COMMENT);
                fos.close();
            } catch (IOException ex) {
                LogService.getService(IRDProperties.class).logApplicationError(ex.getMessage());
            }

        }
    }

    /**
     * Save all properties in the configuration file.
     *
     * @param properties the properties to save
     */
    public void saveProperties(Properties properties) {
        String filepath = AppConfig.getRelativeConfigPath(PROJECT_NAME);
        String filename = PROJECT_CONFIG_FILENAME;
        try {
            FileOutputStream fos = new FileOutputStream(AppConfig.getConfigFile(filepath, filename));
            properties.storeToXML(fos, PROJECT_CONFIG_COMMENT);
            fos.close();
        } catch (IOException ex) {
            LogService.getService(IRDProperties.class).logApplicationError(ex.getMessage());
        }
    }

    /**
     * Create the default properties.
     *
     * @return the property of IRD application
     */
    public abstract Properties createDefaultProperties();

    /**
     * Create the default properties.
     */
    public void createDefaultDirectory() {
        PROJECT_CONFIG_ABSOLUTE_PATH = AppConfig.getConfigDirectory(AppConfig.getRelativeConfigPath(PROJECT_NAME));
        boolean success = (new File(PROJECT_CONFIG_ABSOLUTE_PATH)).mkdirs();
        if (success) {
            LogService.getService(IRDProperties.class).logApplicationInfo("Directory: " + PROJECT_CONFIG_ABSOLUTE_PATH + " created");
        }
    }

    /**
     * Create the default properties.
     */
    public abstract void copyDefaultFile();

    /**
     * Tests if the configuration file exists in the appconfig directory.
     *
     * @return True if the file exists, or False
     */
    public boolean configFileExist() {
        String filepath = AppConfig.getRelativeConfigPath(PROJECT_NAME);
        String filename = PROJECT_CONFIG_FILENAME;
        boolean exist = AppConfig.getConfigFile(filepath, filename).exists();
        exist &= AppConfig.getConfigFile(filepath, filename).length() > 0;
        return exist;
    }

    /**
     * Tests if the configuration directory exists in the appconfig directory.
     *
     * @return True if the directory exists, or False
     */
    public boolean configDirectoryExist() {
        String filepath = AppConfig.getRelativeConfigPath(PROJECT_NAME);
        return new File(AppConfig.getConfigDirectory(filepath)).exists();
    }

}
