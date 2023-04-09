/*
 * $Id: GISHandlerAction.java 553 2015-03-20 11:04:12Z lebranch $
 *
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
package fr.ird.akado.ui.swing.action;

import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.core.spatial.GISHandler;
import fr.ird.akado.ui.swing.AkadoController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

/**
 * This class provides an action which creates the GIS database.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @date 27 mai 2014
 * <p>
 * $LastChangedDate: 2015-03-20 12:04:12 +0100 (ven., 20 mars 2015) $
 * <p>
 * $LastChangedRevision: 553 $
 * @since 2.0
 */
public class GISHandlerAction extends AbstractAction {
    private static final Logger log = LogManager.getLogger(GISHandlerAction.class);

    private final Boolean DEBUG = false;
    private AkadoController akadoController;

    public GISHandlerAction(AkadoController vpc) {
        this.akadoController = vpc;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        generateGisDB();
    }

    /**
     * Generate the H2Gis database.
     */
    private void generateGisDB() {

        int n = JOptionPane.showConfirmDialog(
                null,
                "Generate the GIS database?",
                "Important Question",
                JOptionPane.YES_NO_OPTION);

        if (n == JOptionPane.YES_OPTION) {
            try {
                log.info("Generate the gis DB - start");
                GISHandler.getService().init(AAProperties.STANDARD_DIRECTORY,
                                             AAProperties.SHP_COUNTRIES_PATH,
                                             AAProperties.SHP_OCEAN_PATH,
                                             AAProperties.SHP_HARBOUR_PATH,
                                             AAProperties.SHP_EEZ_PATH);
                if (GISHandler.getService().exists()) {
                    log.info("Generate the gis DB - delete old DB");
                    GISHandler.getService().delete();
                }
                GISHandler.getService().create();
                log.info("Generate the gis DB - done");
            } catch (Exception ex) {
                log.error(ex);
            }
        }
    }
}
