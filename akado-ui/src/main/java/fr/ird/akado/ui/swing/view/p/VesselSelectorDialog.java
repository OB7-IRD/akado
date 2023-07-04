/*
 * Copyright (C) 2015 Observatoire thonier, IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class VesselSelectorDialog extends JDialog
        implements ActionListener,
        PropertyChangeListener {

    public static final int COLS = 8;

    private JTextField textFieldC1;

    private JOptionPane optionPane;

    private String btnString1 = UIManager.getString("ui.swing.enter", new Locale(AAProperties.L10N));
    private String btnString2 = UIManager.getString("ui.swing.cancel", new Locale(AAProperties.L10N));
    private String vesselCode;

    /**
     * Returns null if the typed string was invalid; otherwise, returns the
     * string value as the user entered it.
     *
     * @return
     */
    public String getVesselCode() {
        return vesselCode;
    }

    /**
     * Creates the reusable dialog.
     *
     * @param aFrame
     * @param msg
     * @param currentVesselCode
     */
    public VesselSelectorDialog(Frame aFrame, String msg, String currentVesselCode) {
        super(aFrame, true);

        vesselCode = currentVesselCode;

        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new GridBagLayout());
        // Class one
        String labelTxt = UIManager.getString("ui.swing.selector.vessel", new Locale(AAProperties.L10N));
        mainPanel.add(new JLabel(labelTxt), createGbc(0, 0));
        textFieldC1 = new JTextField(COLS);
        textFieldC1.setText(vesselCode);
        mainPanel.add(textFieldC1, createGbc(0, 1));

        int optionType = JOptionPane.QUESTION_MESSAGE;
        int messageType = JOptionPane.YES_NO_OPTION;
        Icon icon = null;

        //Create an array specifying the number of dialog buttons
        //and their text.
        String[] options = {btnString1, btnString2};
        Object initialValue = options[0];
//        int reply = JOptionPane.showOptionDialog(null, mainPanel,
//                "Get User Information", optionType, messageType, icon, options,
//                initialValue);

//        Object[] array = {msg, textField};
        //Create the JOptionPane.
        optionPane = new JOptionPane(mainPanel,
                optionType,
                messageType,
                icon,
                options,
                initialValue);
        this.setTitle(msg);

        //Make this dialog display it.
        setContentPane(optionPane);

        //Handle window closing correctly.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                /*
                 * Instead of directly closing the window,
                 * we're going to change the JOptionPane's
                 * value property.
                 */
                optionPane.setValue(JOptionPane.CLOSED_OPTION);
            }
        });

        //Ensure the text field always gets the first focus.
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent ce) {
                textFieldC1.requestFocusInWindow();
            }
        });
        //Register an event handler that puts the text into the option pane.
        textFieldC1.addActionListener(this);

        //Register an event handler that reacts to option pane state changes.
        optionPane.addPropertyChangeListener(this);
    }

    /**
     * This method handles events for the text field.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        optionPane.setValue(btnString1);
    }

    final String Digits = "(\\p{Digit}+)";
    final String HexDigits = "(\\p{XDigit}+)";
    // an exponent is 'e' or 'E' followed by an optionally
    // signed decimal integer.
    final String Exp = "[eE][+-]?" + Digits;
    final String fpRegex
            = ("([0-9]\\|?)*[0-9]");

    /**
     * This method reacts to state changes in the option pane.
     *
     * @param e
     */
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String prop = e.getPropertyName();
        String typedTextC1;

        if (isVisible()
                && (e.getSource() == optionPane)
                && (JOptionPane.VALUE_PROPERTY.equals(prop)
                || JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
            Object value = optionPane.getValue();

            if (value == JOptionPane.UNINITIALIZED_VALUE) {
                //ignore reset
                return;
            }

            //Reset the JOptionPane's value.
            //If you don't do this, then if the user
            //presses the same button next time, no
            //property change event will be fired.
            optionPane.setValue(
                    JOptionPane.UNINITIALIZED_VALUE);

            if (btnString1.equals(value)) {
                typedTextC1 = textFieldC1.getText();
                if (Pattern.matches(fpRegex, typedTextC1)) {
                    if ("0".equals(typedTextC1)) {
                        vesselCode = null;
                    } else {
                        vesselCode = typedTextC1;
                    }
// Will not throw NumberFormatException
//we're done; clear and dismiss the dialog
                    clearAndHide();
                } else {

                    //text was invalid
                    textFieldC1.selectAll();
                    JOptionPane.showMessageDialog(VesselSelectorDialog.this,
                            "Sorry, \"" + typedTextC1 + "\" "
                            + "isn't a valid response.\n"
                            + "Please enter a list of vessel code like '25|407' or '0' for all vessels.",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                    textFieldC1.requestFocusInWindow();

                }
            } else { //user closed dialog or clicked cancel
                clearAndHide();
            }
        }
    }

    /**
     * This method clears the dialog and hides it.
     */
    public void clearAndHide() {
//        textFieldC1.setText(null);
//        textFieldC2.setText(null);
        setVisible(false);
    }

    public static GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = 1.0;
        gbc.weighty = gbc.weightx;
        if (x == 0) {
            gbc.anchor = GridBagConstraints.LINE_START;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(3, 3, 3, 8);
        } else {
            gbc.anchor = GridBagConstraints.LINE_END;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(3, 3, 3, 3);
        }
        return gbc;
    }
}
