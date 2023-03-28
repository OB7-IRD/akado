package fr.ird.akado.ui.swing.action;

import fr.ird.akado.ui.AkadoAvdthProperties;
import fr.ird.akado.ui.swing.AkadoController;
import fr.ird.akado.ui.swing.listener.InfoListeners;
import fr.ird.akado.ui.swing.utils.FileFilter;
import fr.ird.akado.ui.swing.utils.ObserveBackupExtensionFilter;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * To open an Observe database from a backup file.
 * <p>
 * Created on 28/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class OpenObserveBackupFile extends AbstractAction {

    private final AkadoController akadoController;
    JFileChooser fileChooser;
    private final InfoListeners listeners;

    public OpenObserveBackupFile(AkadoController vpc, InfoListeners listeners) {
        this.listeners = listeners;
        this.akadoController = vpc;
        if (AkadoAvdthProperties.LAST_OBSERVE_DATABASE_BACKUP_LOADED != null && !AkadoAvdthProperties.LAST_OBSERVE_DATABASE_BACKUP_LOADED.equals("")) {
            fileChooser = new JFileChooser(AkadoAvdthProperties.LAST_OBSERVE_DATABASE_BACKUP_LOADED);
        } else {
            fileChooser = new JFileChooser();
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        openFile();
    }

    protected File openMenu(FileFilter filter) {

        fileChooser.setFileFilter(filter);
        int response = JOptionPane.OK_OPTION;
        File file = null;
        while (response == JOptionPane.OK_OPTION) {
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {

                file = fileChooser.getSelectedFile();
                if (!filter.hasExtension(file)) {
                    file = new File(file.getAbsolutePath() + filter.getExtension());
                }
                if (!file.exists()) {
                    response = JOptionPane.showConfirmDialog(null,
                                                             UIManager.getString("ui.swing.open.file.error.message", fileChooser.getLocale()), UIManager.getString("ui.swing.open.file.error.title", fileChooser.getLocale()),
                                                             JOptionPane.OK_CANCEL_OPTION,
                                                             JOptionPane.ERROR_MESSAGE);
                } else {
                    AkadoAvdthProperties.LAST_OBSERVE_DATABASE_BACKUP_LOADED = file.getAbsolutePath();
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
        File file = openMenu(new ObserveBackupExtensionFilter());
        if (file != null) {
            this.akadoController.setObserveDataBase(file);
            listeners.fireInfoUpdated();
        }
    }
}