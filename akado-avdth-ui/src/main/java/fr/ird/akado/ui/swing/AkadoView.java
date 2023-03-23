/*
 * $Id: AkadoView.java 515 2015-03-04 08:19:10Z lebranch $
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
package fr.ird.akado.ui.swing;

import fr.ird.akado.avdth.common.AAProperties;
import fr.ird.akado.ui.AkadoAvdthProperties;
import fr.ird.akado.ui.Constant;
import static fr.ird.akado.ui.Constant.APPLICATION_NAME;
import fr.ird.akado.ui.swing.listener.InfoListeners;
import fr.ird.akado.ui.swing.view.TaskController;
import fr.ird.akado.ui.swing.view.TaskView;
import fr.ird.akado.ui.swing.view.p.InfoBar;
import fr.ird.akado.ui.swing.view.p.SplashPanel;
import fr.ird.akado.ui.swing.view.p.ToolsBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.PopupMenu;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * AkadoView class is the main frame of the application.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 27 mai 2014
 */
public class AkadoView extends JFrame implements Constant {

    private final ToolsBar toolbar;
    private TaskView taskView;
    private TaskController vtc;

    private final InfoListeners listeners;

    AkadoView(AkadoController controller) {
        listeners = new InfoListeners();
        this.toolbar = new ToolsBar(controller, listeners);
        this.setJMenuBar(toolbar);
        this.add(new SplashPanel(), BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                int resp = JOptionPane.showConfirmDialog(null,
                        String.format(UIManager.getString("ui.swing.quit.message", getLocale()), APPLICATION_NAME), UIManager.getString("ui.swing.quit", getLocale()),
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (resp == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public static int DEFAULT_MAX_HEIGHT = 1280;
    public static int DEFAULT_MAX_WIDTH = 1920;

    /**
     * Display the view.
     */
    public void displayView() {
        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int hauteur = DEFAULT_MAX_HEIGHT, largeur = DEFAULT_MAX_WIDTH;

        if (tailleEcran.getWidth() < DEFAULT_MAX_WIDTH) {
            hauteur = (int) tailleEcran.getHeight();
            largeur = (int) tailleEcran.getWidth();
        }
        hauteur = hauteur * 4 / 5;
        largeur = largeur * 4 / 5;

        Dimension tailleMinimal = new Dimension(largeur, hauteur);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setPreferredSize(tailleMinimal);
        setMinimumSize(tailleMinimal);

        setLocationRelativeTo(getParent());
        setTitle(APPLICATION_NAME + " - " + APPLICATION_VERSION);
        ImageIcon monIcone = new ImageIcon(getClass().getResource(Constant.APPLICATION_ICON));
        setIconImage(monIcone.getImage());
        setVisible(true);
    }

    /**
     * Prepare the validation of an AVDTH database. This methods instanciate a
     * new {@link TaskController} and set the task view associate on the content
     * pane.
     *
     * @param file the file
     */
    public void prepareValidating(File file) {
//        System.out.println("prepareValidating " + listeners);
        vtc = new TaskController(file, listeners);
        this.setContentPane(vtc.getTaskView());
        this.validate();
    }

    /**
     * Getter of the task view.
     *
     * @return the controller
     */
    public TaskView getTaskView() {
        return taskView;
    }

    /**
     * Setter of the new task view.
     *
     * @param tv the task view ,
     */
    public void setTaskView(TaskView tv) {
        taskView = tv;
    }

    /**
     * Getter of the task controller.
     *
     * @return the controller
     */
    public TaskController getTaskController() {
        return vtc;
    }
}
