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
package fr.ird.akado.ui.swing.view.p;

import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.ui.AkadoAvdthProperties;
import fr.ird.akado.ui.Constant;
import fr.ird.akado.ui.swing.AkadoController;
import fr.ird.akado.ui.swing.action.GISHandlerAction;
import fr.ird.akado.ui.swing.action.LoadVMSDatabaseAction;
import fr.ird.akado.ui.swing.action.OpenAVDTHDatabaseAction;
import fr.ird.akado.ui.swing.action.OpenObserveBackupFile;
import fr.ird.akado.ui.swing.action.OpenObserveFromConfigurationAction;
import fr.ird.akado.ui.swing.listener.InfoListeners;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.util.Locale;

/**
 * Create the toolbar of AKaDo application. This class is based on
 * {@link JMenuBar}.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @date 27 mai 2014
 * @since 2.0
 */
public class ToolsBar extends JMenuBar implements Constant {

    private final VMSThresholdDialog vmsThresholdDialog;
    private final VMSCountryVesselTrackedDialog vmsCountryVesselTrackedDialog;
    private final VesselSelectorDialog vesselSelectorDialog;
    private final AkadoController akadoController;
    private final InfoListeners listeners;

    public ToolsBar(AkadoController akadoController, InfoListeners listeners) {
        this.listeners = listeners;
        this.akadoController = akadoController;
        vmsThresholdDialog = new VMSThresholdDialog(null, UIManager.getString("ui.swing.vms.threshold", new Locale(AAProperties.L10N)), AAProperties.THRESHOLD_CLASS_ONE, AAProperties.THRESHOLD_CLASS_TWO);
        vmsThresholdDialog.pack();
        vmsCountryVesselTrackedDialog = new VMSCountryVesselTrackedDialog(null, UIManager.getString("ui.swing.vms.country.vessel.tracked", new Locale(AAProperties.L10N)), AAProperties.ANAPO_VMS_COUNTRY);
        vmsCountryVesselTrackedDialog.pack();

        vesselSelectorDialog = new VesselSelectorDialog(null, UIManager.getString("ui.swing.selector.vessel.tracked", new Locale(AAProperties.L10N)), AAProperties.VESSEL_SELECTED);
        vesselSelectorDialog.pack();

        JMenu fileMenu = new JMenu(UIManager.getString("ui.swing.file", new Locale(AAProperties.L10N)));
        fileMenu.setActionCommand("file");

        JMenuItem openAvdthMenuItem = new JMenuItem(UIManager.getString("ui.swing.open.avdth", new Locale(AAProperties.L10N)), 'A');
        openAvdthMenuItem.setAction(new OpenAVDTHDatabaseAction(akadoController, listeners));
        openAvdthMenuItem.setActionCommand("open");
        openAvdthMenuItem.setText(UIManager.getString("ui.swing.open.avdth", new Locale(AAProperties.L10N)));
        openAvdthMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), true));
        fileMenu.add(openAvdthMenuItem);

        JMenuItem openObserveBackupItem = new JMenuItem(UIManager.getString("ui.swing.open.observe.backup", new Locale(AAProperties.L10N)), 'O');
        openObserveBackupItem.setAction(new OpenObserveBackupFile(akadoController, listeners));
        openObserveBackupItem.setActionCommand("openObserveServerMode");
        openObserveBackupItem.setText(UIManager.getString("ui.swing.open.observe.backup", new Locale(AAProperties.L10N)));
        openObserveBackupItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), true));
        fileMenu.add(openObserveBackupItem);

        JMenuItem openObserveJdbcItem = new JMenuItem(UIManager.getString("ui.swing.open.observe.jdbc", new Locale(AAProperties.L10N)), 'J');
        openObserveJdbcItem.setAction(new OpenObserveFromConfigurationAction(akadoController, listeners));
        openObserveJdbcItem.setActionCommand("openObserveServerMode");
        openObserveJdbcItem.setText(UIManager.getString("ui.swing.open.observe.jdbc", new Locale(AAProperties.L10N)));
        openObserveJdbcItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), true));
        fileMenu.add(openObserveJdbcItem);

        fileMenu.add(new JSeparator());
        JMenuItem quitMenuItem = new JMenuItem(UIManager.getString("ui.swing.quit", new Locale(AAProperties.L10N)), 'Q');
        quitMenuItem.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
        quitMenuItem.addActionListener(e -> {
            int resp = JOptionPane.showConfirmDialog(null,
                                                     String.format(UIManager.getString("ui.swing.quit.message", new Locale(AAProperties.L10N)), APPLICATION_NAME), UIManager.getString("ui.swing.quit", new Locale(AAProperties.L10N)),
                                                     JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (resp == JOptionPane.YES_OPTION) {
                AkadoAvdthProperties.getService().saveProperties();
                System.exit(0);
            }
        });
        fileMenu.add(quitMenuItem);

        //Creation du menu Option
        JMenu inspectorMenu = new JMenu(UIManager.getString("ui.swing.inspectors", new Locale(AAProperties.L10N)));
        inspectorMenu.setActionCommand("inspectors");

        addInspectorSelector(inspectorMenu);

        //Creation du menu gérant les VMS
        JMenu vmsMenu = new JMenu(UIManager.getString("ui.swing.vms", new Locale(AAProperties.L10N)));
        vmsMenu.setActionCommand("vms");
        addVMSMenuItem(vmsMenu);

        //Creation du menu Option
        JMenu optionMenu = new JMenu(UIManager.getString("ui.swing.option", new Locale(AAProperties.L10N)));
        optionMenu.setActionCommand("option");

        JMenuItem gisMenuItem = new JMenuItem(UIManager.getString("ui.swing.gis", new Locale(AAProperties.L10N)), 'G');
        gisMenuItem.setAction(new GISHandlerAction(akadoController));
        gisMenuItem.setActionCommand("gis");
        gisMenuItem.setText(UIManager.getString("ui.swing.gis", new Locale(AAProperties.L10N)));
        gisMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), true));
        optionMenu.add(gisMenuItem);
        optionMenu.add(new JSeparator());

        JMenuItem exportMenuItem = new JCheckBoxMenuItem(UIManager.getString("ui.swing.config.results.output.enable", new Locale(AAProperties.L10N)));
        exportMenuItem.setMnemonic(KeyEvent.VK_X);
        exportMenuItem.setSelected(AAProperties.RESULTS_OUTPUT.equals(AAProperties.ACTIVE_VALUE));
        exportMenuItem.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                AAProperties.RESULTS_OUTPUT = AAProperties.ACTIVE_VALUE;
            } else {
                AAProperties.RESULTS_OUTPUT = AAProperties.DISABLE_VALUE;
            }
        });
        optionMenu.add(exportMenuItem);
        optionMenu.add(new JSeparator());
        addL10NMenuItem(optionMenu);

        optionMenu.add(new JSeparator());
        addSelectorMenuItem(optionMenu);

        // Creation du menu Aide
        JMenu helpMenu = new JMenu(UIManager.getString("ui.swing.help", new Locale(AAProperties.L10N)));
        JMenuItem aboutMenuItem = new JMenuItem(UIManager.getString("ui.swing.about", new Locale(AAProperties.L10N)));
        aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), false));
        aboutMenuItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutMenuItem);

        this.add(fileMenu);
        this.add(inspectorMenu);
        this.add(vmsMenu);
        this.add(optionMenu);
        this.add(helpMenu);
    }

    /**
     * Display the about inside a {@link  JDialog}.
     */
    public void showAboutDialog() {
        JDialog about = new JDialog();
        AboutPanel a = new AboutPanel();
        about.setContentPane(a);
        about.setSize(a.getWidth(), a.getHeight());
        about.setResizable(false);
        about.setLocationRelativeTo(getParent());
        about.setVisible(true);
    }

    private JRadioButtonMenuItem generateL10NItem(final String lang) {
        JRadioButtonMenuItem myItem = new JRadioButtonMenuItem(UIManager.getString("ui.swing.l10n." + lang, new Locale(AAProperties.L10N)));
        if (AAProperties.L10N.equals(new Locale(lang).getLanguage())) {
            myItem.setSelected(true);
        }
        myItem.addActionListener(e -> {
            AAProperties.L10N = lang;
            JOptionPane.showMessageDialog(null, UIManager.getString("ui.swing.config.need.reboot", new Locale(AAProperties.L10N)));
        });
        return myItem;
    }

    final String inputCodeOfVesselTrackedOptionCommand = "codeOfVesselTrackedOC";

    private void addSelectorMenuItem(JMenu menu) {
        JMenuItem mi;
        mi = new JMenuItem(UIManager.getString("ui.swing.selector.vessel.tracked", new Locale(AAProperties.L10N)));
        mi.setActionCommand(inputCodeOfVesselTrackedOptionCommand);
        mi.addActionListener(ae -> {
            String command = ae.getActionCommand();
            if (command.equals(inputCodeOfVesselTrackedOptionCommand)) {
                vesselSelectorDialog.setLocationRelativeTo(null);
                vesselSelectorDialog.setVisible(true);

                String vesselTrackedSelected = vesselSelectorDialog.getVesselCode();
                if (vesselTrackedSelected != null) {
                    AAProperties.VESSEL_SELECTED = vesselTrackedSelected;
                    listeners.fireInfoUpdated();
                }
            }
        });
        menu.add(mi);

    }

    private void addL10NMenuItem(JMenu menu) {

        ButtonGroup myGroup = new ButtonGroup();
        JRadioButtonMenuItem myItem = generateL10NItem("fr");
        myGroup.add(myItem);
        menu.add(myItem);

        myItem = generateL10NItem("en");
        myGroup.add(myItem);
        menu.add(myItem);

//        myItem = generateL10NItem("es");
//        myGroup.add(myItem);
//        menu.add(myItem);
    }


    private void addInspectorSelector(JMenu menu) {

        activityMenuItem = new JCheckBoxMenuItem(UIManager.getString("ui.swing.config.activity.inspector.enable", new Locale(AAProperties.L10N)));
        activityMenuItem.setMnemonic(KeyEvent.VK_A);
        activityMenuItem.setSelected(AAProperties.ACTIVITY_INSPECTOR.equals(AAProperties.ACTIVE_VALUE));
        activityMenuItem.setEnabled(AAProperties.AKADO_INSPECTOR.equals(AAProperties.ACTIVE_VALUE));
        activityMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
                                                               Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), true));
        activityMenuItem.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                AAProperties.ACTIVITY_INSPECTOR = AAProperties.ACTIVE_VALUE;
            } else {
                AAProperties.ACTIVITY_INSPECTOR = AAProperties.DISABLE_VALUE;
            }
            listeners.fireInfoUpdated();
        });

        activityPositionMenuItem = new JCheckBoxMenuItem(UIManager.getString("ui.swing.config.activity.position.inspector.enable", new Locale(AAProperties.L10N)));
        activityPositionMenuItem.setSelected(AAProperties.POSITION_INSPECTOR.equals(AAProperties.ACTIVE_VALUE));
        activityPositionMenuItem.setMnemonic(KeyEvent.VK_P);
        activityPositionMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
                                                                       Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), true));
        activityPositionMenuItem.setEnabled(AAProperties.AKADO_INSPECTOR.equals(AAProperties.ACTIVE_VALUE));
        activityPositionMenuItem.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                AAProperties.POSITION_INSPECTOR = AAProperties.ACTIVE_VALUE;
            } else {
                AAProperties.POSITION_INSPECTOR = AAProperties.DISABLE_VALUE;
            }
            listeners.fireInfoUpdated();
        });

        tripMenuItem = new JCheckBoxMenuItem(UIManager.getString("ui.swing.config.trip.inspector.enable", new Locale(AAProperties.L10N)));
        tripMenuItem.setSelected(AAProperties.TRIP_INSPECTOR.equals(AAProperties.ACTIVE_VALUE));
//        configMenuItem.setMnemonic(KeyEvent.VK_T);
        tripMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
                                                           Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), true));
        tripMenuItem.setEnabled(AAProperties.AKADO_INSPECTOR.equals(AAProperties.ACTIVE_VALUE));
        tripMenuItem.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                AAProperties.TRIP_INSPECTOR = AAProperties.ACTIVE_VALUE;
            } else {
                AAProperties.TRIP_INSPECTOR = AAProperties.DISABLE_VALUE;
            }
            listeners.fireInfoUpdated();
        });

        sampleMenuItem = new JCheckBoxMenuItem(UIManager.getString("ui.swing.config.sample.inspector.enable", new Locale(AAProperties.L10N)));
        sampleMenuItem.setSelected(AAProperties.SAMPLE_INSPECTOR.equals(AAProperties.ACTIVE_VALUE));
        sampleMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                                                             Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), true));
        sampleMenuItem.setEnabled(AAProperties.AKADO_INSPECTOR.equals(AAProperties.ACTIVE_VALUE));
        sampleMenuItem.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                AAProperties.SAMPLE_INSPECTOR = AAProperties.ACTIVE_VALUE;
            } else {
                AAProperties.SAMPLE_INSPECTOR = AAProperties.DISABLE_VALUE;
            }
            listeners.fireInfoUpdated();
        });

        wellMenuItem = new JCheckBoxMenuItem(UIManager.getString("ui.swing.config.well.inspector.enable", new Locale(AAProperties.L10N)));
        wellMenuItem.setSelected(AAProperties.WELL_INSPECTOR.equals(AAProperties.ACTIVE_VALUE));
        wellMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
                                                           Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), true));
        wellMenuItem.setEnabled(AAProperties.AKADO_INSPECTOR.equals(AAProperties.ACTIVE_VALUE));
        wellMenuItem.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                AAProperties.WELL_INSPECTOR = AAProperties.ACTIVE_VALUE;
            } else {
                AAProperties.WELL_INSPECTOR = AAProperties.DISABLE_VALUE;
            }
            listeners.fireInfoUpdated();
        });

        warningMenuItem = new JCheckBoxMenuItem(UIManager.getString("ui.swing.config.warning.inspector.enable", new Locale(AAProperties.L10N)));
        warningMenuItem.setSelected(AAProperties.WARNING_INSPECTOR.equals(AAProperties.ACTIVE_VALUE));
        warningMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
                                                              Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx(), true));
        warningMenuItem.setEnabled(AAProperties.AKADO_INSPECTOR.equals(AAProperties.ACTIVE_VALUE));
        warningMenuItem.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                AAProperties.WARNING_INSPECTOR = AAProperties.ACTIVE_VALUE;
            } else {
                AAProperties.WARNING_INSPECTOR = AAProperties.DISABLE_VALUE;
            }
            listeners.fireInfoUpdated();
        });

        akadoMenuItem = new JCheckBoxMenuItem(UIManager.getString("ui.swing.config.akado.inspector.enable", new Locale(AAProperties.L10N)));

        akadoMenuItem.setSelected(AAProperties.AKADO_INSPECTOR.equals(AAProperties.ACTIVE_VALUE));
        akadoMenuItem.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                AAProperties.AKADO_INSPECTOR = AAProperties.ACTIVE_VALUE;
                tripMenuItem.setEnabled(true);
                activityMenuItem.setEnabled(true);
                activityPositionMenuItem.setEnabled(true);
                sampleMenuItem.setEnabled(true);
                wellMenuItem.setEnabled(true);
                warningMenuItem.setEnabled(true);
            } else {
                AAProperties.AKADO_INSPECTOR = AAProperties.DISABLE_VALUE;
                tripMenuItem.setEnabled(false);
                activityMenuItem.setEnabled(false);
                activityPositionMenuItem.setEnabled(false);
                sampleMenuItem.setEnabled(false);
                wellMenuItem.setEnabled(false);
                warningMenuItem.setEnabled(false);
            }
            listeners.fireInfoUpdated();
        });

        menu.add(akadoMenuItem);
        menu.add(new JSeparator());
        menu.add(tripMenuItem);

        menu.add(activityMenuItem);
        menu.add(activityPositionMenuItem);

        menu.add(sampleMenuItem);
        menu.add(wellMenuItem);
        menu.add(warningMenuItem);
    }

    JMenuItem akadoMenuItem;
    JMenuItem tripMenuItem;
    JMenuItem activityMenuItem;
    JMenuItem activityPositionMenuItem;
    JMenuItem sampleMenuItem;
    JMenuItem wellMenuItem;
    JMenuItem warningMenuItem;

    final String inputThresholdOptionCommand = "thresholdOC";
    final String inputCountryOfVesselTrackedOptionCommand = "countryOfVesselTrackedOC";

    private void addVMSMenuItem(JMenu menu) {
        JMenuItem mi;
        mi = new JCheckBoxMenuItem(UIManager.getString("ui.swing.config.anapo.inspector.enable", new Locale(AAProperties.L10N)));
        mi.setSelected(AAProperties.ANAPO_INSPECTOR.equals(AAProperties.ACTIVE_VALUE));
//        mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
//                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), true));
        mi.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                AAProperties.ANAPO_INSPECTOR = AAProperties.ACTIVE_VALUE;
            } else {
                AAProperties.ANAPO_INSPECTOR = AAProperties.DISABLE_VALUE;
            }
            listeners.fireInfoUpdated();
        });
        menu.add(mi);
        menu.add(new JSeparator());
        mi = new JMenuItem(UIManager.getString("ui.swing.vms.load.database", new Locale(AAProperties.L10N)), 'O');
        mi.setAction(new LoadVMSDatabaseAction(akadoController, listeners));
        mi.setActionCommand("open");
        mi.setText(UIManager.getString("ui.swing.vms.load.database", new Locale(AAProperties.L10N)));
        menu.add(mi);
        menu.add(new JSeparator());

        mi = new JMenuItem(UIManager.getString("ui.swing.vms.threshold", new Locale(AAProperties.L10N)));
        mi.setActionCommand(inputThresholdOptionCommand);
        mi.addActionListener(ae -> {
            String command = ae.getActionCommand();
            if (command.equals(inputThresholdOptionCommand)) {
                vmsThresholdDialog.setLocationRelativeTo(null);
                vmsThresholdDialog.setVisible(true);

                Double thresholdOne = vmsThresholdDialog.getValidatedThresholdOne();
                Double thresholdTwo = vmsThresholdDialog.getValidatedThresholdTwo();
                if (thresholdOne != null && thresholdTwo != null) {
                    AAProperties.THRESHOLD_CLASS_ONE = thresholdOne;
                    AAProperties.THRESHOLD_CLASS_TWO = thresholdTwo;
                    listeners.fireInfoUpdated();
                }
            }
        });
        menu.add(mi);
        mi = new JMenuItem(UIManager.getString("ui.swing.vms.country.vessel.tracked", new Locale(AAProperties.L10N)));
        mi.setActionCommand(inputCountryOfVesselTrackedOptionCommand);
        mi.addActionListener(ae -> {
            String command = ae.getActionCommand();
            if (command.equals(inputCountryOfVesselTrackedOptionCommand)) {
                vmsCountryVesselTrackedDialog.setLocationRelativeTo(null);
                vmsCountryVesselTrackedDialog.setVisible(true);

                String vmsCountryVesselTracked = vmsCountryVesselTrackedDialog.getVMSCountryVesselTracked();
                if (vmsCountryVesselTracked != null) {
                    AAProperties.ANAPO_VMS_COUNTRY = vmsCountryVesselTracked;
                    listeners.fireInfoUpdated();
                }
            }
        });
        menu.add(mi);
    }
}
