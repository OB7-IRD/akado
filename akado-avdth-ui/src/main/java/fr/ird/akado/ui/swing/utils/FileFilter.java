/*
 *Copyright (C) 2014 Observatoire thonier, IRD
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
package fr.ird.akado.ui.swing.utils;

import java.io.File;

/**
 * FileFilter is an abstract class used by JFileChooser for filtering the set of
 * files shown to the user. It extends
 * {@link javax.swing.filechooser.FileFilter} with three methods.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 0.0
 * @date 2 juin 2014
 */
public abstract class FileFilter extends javax.swing.filechooser.FileFilter {

    /**
     * Tests if this file has an extension.
     *
     * @param file the file
     * @return True if the file has an extension, else False
     */
    public boolean hasExtension(File file) {
        return FileFilter.getExtension(file) != null;
    }

    /**
     * Returns the extension filtered of the file.
     *
     *
     * @return the extension of the specified file
     */
    public abstract String getExtension();

    /**
     * Tests if the extension of the file matchs with the extension filtered.
     *
     *
     * @param file the file
     * @return True if the extensions matches, else False
     */
    public abstract boolean isFiltered(File file);

    /**
     * Returns the extension of the file. The methods checks if the file name
     * contains a dot, and it gets the substring from the last index of the dot.
     *
     * @param file the file
     * @return the extension of the specified file, or null if it did not have
     * one
     */
    public static String getExtension(File file) {
        String ext = null;
        String s = file.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
}
