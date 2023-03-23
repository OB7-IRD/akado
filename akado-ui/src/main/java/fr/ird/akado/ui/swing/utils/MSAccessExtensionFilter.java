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
 * MSAccessExtensionFilter filters the set of Microsoft Access files. It extends
 * {@link FileFilter} and filters the files with the extension ".mdb".
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 02 juin 2014
 */
public class MSAccessExtensionFilter extends FileFilter {

    private final String MDB = "mdb";

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        return isFiltered(f);
    }

    @Override
    public String getDescription() {
        return "MS Access File";
    }

    @Override
    public String getExtension() {
        return "." + MDB;
    }

    @Override
    public boolean isFiltered(File f) {
        String extension = FileFilter.getExtension(f);
        return MDB.equals(extension);
    }
}
