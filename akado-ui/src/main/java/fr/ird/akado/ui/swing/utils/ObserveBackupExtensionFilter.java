package fr.ird.akado.ui.swing.utils;

import fr.ird.driver.observe.service.ObserveService;

import java.io.File;

/**
 * To open a backup from Observe (says a sql file gzipped).
 * <p>
 * Created on 28/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class ObserveBackupExtensionFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        return isFiltered(f);
    }

    @Override
    public String getDescription() {
        return "ObServe database backup file";
    }

    @Override
    public String getExtension() {
        return "." + ObserveService.SQL_GZ_EXTENSION;
    }

    @Override
    public boolean isFiltered(File f) {
        return f.getName().endsWith(ObserveService.SQL_GZ_EXTENSION);
    }

}
