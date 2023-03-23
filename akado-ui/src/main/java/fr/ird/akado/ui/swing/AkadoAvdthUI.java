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
package fr.ird.akado.ui.swing;

import fr.ird.akado.ui.AkadoAvdthProperties;
import fr.ird.akado.ui.Constant;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.core.common.AkadoException;
import fr.ird.common.log.LogService;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.*;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * The main class of Akado Avdth UI. Implements a file lock to disable a second
 * execution of this program.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 27 mai 2014
 */
public class AkadoAvdthUI {

    private static File f;
    private static FileChannel channel;
    private static FileLock lock;

    public static void main(String[] args) throws AkadoException {
        LogService.getService(AkadoAvdthUI.class).logApplicationInfo(
                "---------------------------------------------------------------");
        LogService.getService(AkadoAvdthUI.class).logApplicationInfo(Constant.APPLICATION_NAME + " " + Constant.APPLICATION_VERSION);
        LogService.getService(AkadoAvdthUI.class).logApplicationInfo(Constant.APPLICATION_AUTHOR + " " + Constant.APPLICATION_YEAR);

        LogService.getService(AkadoAvdthUI.class).logApplicationInfo(
                "---------------------------------------------------------------");
        AkadoAvdthProperties.getService().init();

        UIManager.getDefaults().addResourceBundle("AKaDo-UI");
        UIManager.getDefaults().setDefaultLocale(new Locale(AAProperties.L10N));

        try {
            f = new File("RingOnRequest.lock");
            // Check if the lock exist
            if (f.exists()) {
                // if exist try to delete it
                f.delete();
            }
            // Try to get the lock
            channel = new RandomAccessFile(f, "rw").getChannel();
            lock = channel.tryLock();
            if (lock == null) {
                // File is lock by other application
                channel.close();
                JOptionPane.showMessageDialog(new JFrame(),
                        UIManager.getString("ui.swing.only.one.instance"),
                        Constant.APPLICATION_NAME,
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
            // Add shutdown hook to release lock when application shutdown
            ShutdownHook shutdownHook = new ShutdownHook();
            Runtime.getRuntime().addShutdownHook(shutdownHook);

            new AkadoController();

        } catch (IOException e) {
            throw new AkadoException("Could not start process.", e);
        }

    }

    /**
     * Delete the file lock during the end of program execution..
     */
    public static void unlockFile() {
        // release and delete file lock
        try {
            if (lock != null) {
                lock.release();
                channel.close();
                f.delete();
            }
        } catch (IOException e) {
            LogService.getService(AkadoAvdthUI.class).logApplicationError(e.getLocalizedMessage());
        }
    }

    static class ShutdownHook extends Thread {

        @Override
        public void run() {
            unlockFile();
        }
    }
}
