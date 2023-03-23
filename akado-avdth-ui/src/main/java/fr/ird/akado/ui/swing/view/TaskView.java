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
package fr.ird.akado.ui.swing.view;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import fr.ird.akado.avdth.common.AAProperties;
import fr.ird.akado.core.AkadoCore;
import fr.ird.akado.core.DataBaseInspector;
import fr.ird.akado.core.Inspector;
import fr.ird.akado.core.common.AkadoMessage;
import fr.ird.akado.core.common.AkadoMessages;
import fr.ird.akado.core.common.MessageAdapter;
import fr.ird.akado.ui.AkadoAvdthProperties;
import fr.ird.akado.avdth.common.AkadoException;
import fr.ird.akado.ui.swing.view.p.InfoBar;
import fr.ird.common.DateTimeUtils;
import fr.ird.common.log.LogService;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.text.DefaultCaret;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

/**
 * The TaskView class display the running button and the panel of the results.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 3 juin 2014
 * @see Inspector
 * @see Task
 */
public class TaskView extends JPanel implements ActionListener,
        PropertyChangeListener {

    private Task task;

    private DateTime startProcess;

    /**
     * The Task class represents a thread where the main validation is executed.
     *
     * @see DataBaseInspector
     * @see AkadoMessages
     * @see SwingWorker
     */
    class Task
            extends SwingWorker<Void, Void> {

        DataBaseInspector inspector;

        private AkadoCore akado;
        int progress = 0;
        private String dataBasePath;
        String exceptionMessage = "";

        Task(String path) {
            DateTime start = DateTimeUtils.convertLocalDate(dpStartDate.getDate());
            DateTime end = DateTimeUtils.convertLocalDate(dpEndDate.getDate());
            LogService.getService(this.getClass()).logApplicationInfo("" + start);
            LogService.getService(this.getClass()).logApplicationInfo("" + end);

            try {
                Constructor ctor = AkadoAvdthProperties.THIRD_PARTY_DATASOURCE.getDeclaredConstructor(String.class, String.class, String.class, String.class);
                ctor.setAccessible(true);

                this.dataBasePath = path;
                akado = new AkadoCore();
                inspector = (DataBaseInspector) ctor.newInstance(AkadoAvdthProperties.PROTOCOL_JDBC_ACCESS + path, AkadoAvdthProperties.JDBC_ACCESS_DRIVER, "", "");
                inspector.setTemporalConstraint(start, end);

                if (!akado.addDataBaseValidator(inspector)) {
                    throw new AkadoException("Error during the AVDTHValidator creation.");
                }

                inspector.getAkadoMessages().addMessageListener(new MessageAdapter() {
                    @Override
                    public void messageAdded(AkadoMessage m) {
                        taskOutput.append(m.getContent() + "\n");
                    }
                });
                inspector.info();
            } catch (InvocationTargetException ex) {
                exceptionMessage = "" + ex.getTargetException().getMessage();
                LogService.getService(this.getClass()).logApplicationError(exceptionMessage);
                JOptionPane.showMessageDialog(null,
                        exceptionMessage,
                        "Akado error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                exceptionMessage = "" + ex.getMessage();
                LogService.getService(this.getClass()).logApplicationError(exceptionMessage);

                JOptionPane.showMessageDialog(null,
                        exceptionMessage,
                        "Akado error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            ref = System.currentTimeMillis();
            timer.start();
            startProcess = new DateTime();
            try {

                akado.execute();
            } catch (Exception ex) {
                exceptionMessage = "" + ex.getLocalizedMessage().toString();
                LogService.getService(this.getClass()).logApplicationError("DoInBG: " + exceptionMessage);
                JOptionPane.showMessageDialog(null,
                        exceptionMessage,
                        "Akado error",
                        JOptionPane.ERROR_MESSAGE);
            }
            export();
            return null;
        }
        private String exportOut = "";

        private void export() {
            DateTime endProcess = new DateTime();
            int duration = Seconds.secondsBetween(startProcess, endProcess).getSeconds();
            exportOut = "Done in " + duration / 60 + " minute(s) and " + duration % 60 + " seconds ! There is " + inspector.getAkadoMessages().size() + " messages.\n";
        }

        /*
         * Executed in event dispatch thread
         */
        @Override
        public void done() {
            inspector.close();
//            AvdthService.getService().close();
            taskOutput.append(exportOut);
            timer.stop();
            Toolkit.getDefaultToolkit().beep();
            startButton.setEnabled(true);
            dpStartDate.setEnabled(true);
            dpEndDate.setEnabled(true);
            stopButton.setEnabled(false);
//            if (exportNameWithExt != null) {
//                runExternalProgram(exportNameWithExt);
//            }
        }

        /**
         * Launch a registered application to open, edit or print a result file.
         *
         * @param exportNameWithExt the result file
         */
        private void runExternalProgram(String exportNameWithExt) {
            try {
                Desktop.getDesktop().open(new File(exportNameWithExt));
            } catch (IOException ex) {
                LogService.getService(this.getClass()).logApplicationError("***" + ex.getMessage());
                JOptionPane.showMessageDialog(null,
                        ex.getMessage(),
                        "Akado error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private final JButton startButton, stopButton;
    private final JTextArea taskOutput;

    private final DatePicker dpStartDate;
    private final DatePicker dpEndDate;

    private TaskController vtc;
    public static Boolean DEBUG = false;

    final SimpleDateFormat timef = new SimpleDateFormat("HH:mm:ss");
    final JLabel elapsedLabel = new JLabel("", SwingUtilities.CENTER);
    Timer timer = new javax.swing.Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Date currentDate = new Date(System.currentTimeMillis() - ref);
            String formattedDate = timef.format(currentDate);
            elapsedLabel.setText(UIManager.getString("ui.swing.elapsed.time", new Locale(AAProperties.L10N)) + " = " + formattedDate);
        }
    });
    static long ref = 0;

    private String startActionCommand = "ui.swing.start";
    private String endActionCommand = "ui.swing.stop";

    public TaskView(TaskController controller) {
        super(new BorderLayout());

        this.vtc = controller;

        DatePickerSettings dateSettings;
        dateSettings = new DatePickerSettings();
//        dateSettings.setFontValidDate(new Font("Monospaced", Font.ITALIC | Font.BOLD, 10));
        dateSettings.setColor(DatePickerSettings.DateArea.DatePickerTextValidDate, new Color(0, 100, 0));
        dpStartDate = new DatePicker(dateSettings);
//        dpStartDate.setDateToToday();

        dateSettings = new DatePickerSettings();
        dateSettings.setColor(DatePickerSettings.DateArea.DatePickerTextValidDate, new Color(0, 0, 100));
        dpEndDate = new DatePicker(dateSettings);
        dpEndDate.setDateToToday();

        startButton = new JButton(UIManager.getString("ui.swing.start", new Locale(AAProperties.L10N)));
        startButton.setActionCommand(startActionCommand);
        startButton.addActionListener(this);

        stopButton = new JButton(UIManager.getString("ui.swing.stop", new Locale(AAProperties.L10N)));
        stopButton.setActionCommand(endActionCommand);
        stopButton.addActionListener(this);
        stopButton.setEnabled(false);

        taskOutput = new JTextArea(5, 20);
        taskOutput.setFont(new Font("Arial", Font.PLAIN, 20));
        taskOutput.setMargin(new Insets(5, 5, 5, 5));
        taskOutput.setEditable(false);

        DefaultCaret caret = (DefaultCaret) taskOutput.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JPanel panel = new JPanel();
        JPanel panelDP = new JPanel();
        panelDP.add(new JLabel(UIManager.getString("ui.swing.dp.start.date.label", new Locale(AAProperties.L10N))));
        panelDP.add(dpStartDate);
        panel.add(panelDP);

        panelDP = new JPanel();
        panelDP.add(new JLabel(UIManager.getString("ui.swing.dp.end.date.label", new Locale(AAProperties.L10N))));
        panelDP.add(dpEndDate);
        panel.add(panelDP);

        panel.add(startButton);
        panel.add(elapsedLabel);
        panel.add(stopButton);
        InfoBar infoBar = new InfoBar(vtc, vtc.getListeners());

        add(panel, BorderLayout.PAGE_START);
        add(new JScrollPane(taskOutput), BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(infoBar, BorderLayout.PAGE_END);

    }

    public void displayView() {
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(startActionCommand)) {
            taskOutput.setText("");
            task = new Task(vtc.getPathFile());
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            dpStartDate.setEnabled(false);
            dpEndDate.setEnabled(false);
            task.addPropertyChangeListener(this);
            task.execute();
        }
        if (e.getActionCommand().equals(endActionCommand)) {
            task.cancel(true);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }
}
