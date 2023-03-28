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
import fr.ird.akado.avdth.AvdthInspector;
import fr.ird.akado.core.AkadoCore;
import fr.ird.akado.core.DataBaseInspector;
import fr.ird.akado.core.Inspector;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.core.common.AkadoException;
import fr.ird.akado.core.common.AkadoMessages;
import fr.ird.akado.core.common.MessageListener;
import fr.ird.akado.observe.ObserveDataBaseInspector;
import fr.ird.akado.ui.swing.view.p.InfoBar;
import fr.ird.common.DateTimeUtils;
import fr.ird.common.log.LogService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * The TaskView class display the running button and the panel of the results.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @date 3 juin 2014
 * @see Inspector
 * @see Task
 * @since 2.0
 */
public class TaskView extends JPanel implements ActionListener {
    private static final Logger log = LogManager.getLogger(TaskView.class);
    private static final String startActionCommand = "ui.swing.start";
    private static final String endActionCommand = "ui.swing.stop";
    private final JButton startButton, stopButton;
    private final JTextArea taskOutput;
    private final DatePicker dpStartDate;
    private final DatePicker dpEndDate;
    private final TaskController vtc;
    final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    final JLabel elapsedLabel = new JLabel("", SwingUtilities.CENTER);
    private Task task;
    private long ref = 0;
    Timer timer = new javax.swing.Timer(1000, e -> {
        Date currentDate = new Date(System.currentTimeMillis() - ref);
        String formattedDate = timeFormat.format(currentDate);
        elapsedLabel.setText(UIManager.getString("ui.swing.elapsed.time", new Locale(AAProperties.L10N)) + " = " + formattedDate);
    });

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

    /**
     * The Task class represents a thread where the main validation is executed.
     *
     * @see DataBaseInspector
     * @see AkadoMessages
     */
    class Task extends SwingWorker<Void, Void> {
        private final AkadoCore akado;
        private final DataBaseInspector inspector;
        private final MessageListener messageListener;
        private DateTime startProcess;

        Task(TaskController controller) throws Exception {
            DateTime start = DateTimeUtils.convertLocalDate(dpStartDate.getDate());
            DateTime end = DateTimeUtils.convertLocalDate(dpEndDate.getDate());
            LogService.getService(this.getClass()).logApplicationInfo("" + start);
            LogService.getService(this.getClass()).logApplicationInfo("" + end);

            akado = new AkadoCore();

            switch (controller.getDatabaseType()) {
                case AVDTH:
                    inspector = new AvdthInspector(controller.getJdbcConfiguration());
                    break;
                case OBSERVE:
                    inspector = new ObserveDataBaseInspector(controller.getJdbcConfiguration(), controller.getBaseDirectory());
                    break;
                default:
                    throw new IllegalStateException(String.format("Can't manager database type: %s", controller.getDatabaseType()));
            }

            inspector.setTemporalConstraint(start, end);

            if (!akado.addDataBaseValidator(inspector)) {
                throw new AkadoException("Error during the inspector creation.");
            }

            messageListener = m -> taskOutput.append(m.getContent() + "\n");
            inspector.getAkadoMessages().addMessageListener(messageListener);
            inspector.info();
        }

        @Override
        public Void doInBackground() throws Exception {
            ref = System.currentTimeMillis();
            timer.start();
            startProcess = new DateTime();
            akado.execute();
            return null;
        }

        @Override
        public void done() {
            try {
                inspector.close();
            } catch (Exception e) {
                log.error("Could not close inspector", e);
            } finally {
                inspector.getAkadoMessages().removeMessageListener(messageListener);
            }
            timer.stop();
            Toolkit.getDefaultToolkit().beep();
            startButton.setEnabled(true);
            dpStartDate.setEnabled(true);
            dpEndDate.setEnabled(true);
            stopButton.setEnabled(false);
            try {

                if (!isCancelled()) {
                    get();
                }
            } catch (InterruptedException ignored) {
            } catch (ExecutionException e) {
                Throwable ex = e.getCause();
                log.error("Error while executing Akado", ex);
                String exceptionMessage = "" + ex.getLocalizedMessage();
                LogService.getService(this.getClass()).logApplicationError("DoInBG: " + exceptionMessage);
                JOptionPane.showMessageDialog(null, exceptionMessage, "Akado error", JOptionPane.ERROR_MESSAGE);
            } finally {
                DateTime endProcess = new DateTime();
                int duration = Seconds.secondsBetween(startProcess, endProcess).getSeconds();
                String exportOut = "Done in " + duration / 60 + " minute(s) and " + duration % 60 + " seconds ! There is " + inspector.getAkadoMessages().size() + " messages.\n";

                taskOutput.append(exportOut);

                task = null;

            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(startActionCommand)) {
            taskOutput.setText("");
            try {
                task = new Task(vtc);
            } catch (Exception ex) {
                log.error("Error in executing task", ex);
                String exceptionMessage = "" + ex.getMessage();
                LogService.getService(this.getClass()).logApplicationError(exceptionMessage);
                JOptionPane.showMessageDialog(null, exceptionMessage, "Akado error", JOptionPane.ERROR_MESSAGE);
            }
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            dpStartDate.setEnabled(false);
            dpEndDate.setEnabled(false);
            task.execute();
        }
        if (e.getActionCommand().equals(endActionCommand)) {
            if (task != null) {
                task.cancel(true);
            }
        }
    }
}
