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
package fr.ird.akado.ui.swing.action;

import fr.ird.akado.ui.AkadoAvdthProperties;
import fr.ird.akado.ui.swing.AkadoController;
import fr.ird.akado.ui.swing.listener.InfoListeners;
import fr.ird.akado.ui.swing.utils.FileFilter;
import fr.ird.akado.ui.swing.utils.MSAccessExtensionFilter;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * This class provides an implementations to open a file filtered. In this
 * application, it's open a MSAccess file.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 27 mai 2014
 */
public class OpenAVDTHDatabaseAction extends AbstractAction {

    private final AkadoController akadoController;
    JFileChooser fileChooser;
    private final InfoListeners listeners;

    public OpenAVDTHDatabaseAction(AkadoController vpc, InfoListeners listeners) {
        this.listeners = listeners;
        this.akadoController = vpc;
        if (AkadoAvdthProperties.LAST_AVDTH_DATABASE_LOADED != null && !AkadoAvdthProperties.LAST_AVDTH_DATABASE_LOADED.equals("")) {
            fileChooser = new JFileChooser(AkadoAvdthProperties.LAST_AVDTH_DATABASE_LOADED);
        } else {
            fileChooser = new JFileChooser();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        openFile();
    }

    protected File openMenu(Component parent, FileFilter filter) {

        fileChooser.setFileFilter(filter);
        int response = JOptionPane.OK_OPTION;
        File file = null;
        while (response == JOptionPane.OK_OPTION) {
            int resultat = fileChooser.showOpenDialog(parent);
            if (resultat == JFileChooser.APPROVE_OPTION) {

                file = fileChooser.getSelectedFile();
                if (!filter.hasExtension(file)) {
                    file = new File(file.getAbsolutePath() + filter.getExtension());
                }
                if (!file.exists()) {
                    //System.out.println(file);
                    response = JOptionPane.showConfirmDialog(null,
                            UIManager.getString("ui.swing.open.file.error.message", fileChooser.getLocale()), UIManager.getString("ui.swing.open.file.error.title", fileChooser.getLocale()),
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    AkadoAvdthProperties.LAST_AVDTH_DATABASE_LOADED = file.getAbsolutePath();
                    return file;
                }
            } else {
                return null;
            }
        }
        return file;
    }

    /**
     * Open the file selected by the user.
     */
    private void openFile() {
        File file = openMenu(null, new MSAccessExtensionFilter());

        if (file != null) {
            this.akadoController.setAvdthDataBase(file);
            listeners.fireInfoUpdated();
        }
//        this.vpc.setNewView(this.handler);
////        System.out.print(this.handler.getModele());
    }
}
