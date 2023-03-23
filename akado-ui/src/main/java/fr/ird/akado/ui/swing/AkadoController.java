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
package fr.ird.akado.ui.swing;

import java.io.File;

/**
 * The controller handles all request coming from the view or user interface.
 * The data flow to whole application is controlled by controller. It forwarded
 * the request to the appropriate handler. Only the controller is responsible
 * for accessing model and rendering it into various UIs.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 27 mai 2014
 */
public class AkadoController {

    private final AkadoView akadoView;

    public AkadoController() {
        //Init properties files

        akadoView = new AkadoView(this);
        akadoView.displayView();
    }

    /**
     * Set to the akado view an AVDTH database.
     *
     * @param file the database file.
     */
    public void setDataBase(File file) {
        akadoView.prepareValidating(file);
    }

}
