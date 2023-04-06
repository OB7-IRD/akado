/*
 * Copyright (C) 2016 Observatoire thonier, IRD
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
import fr.ird.akado.ui.swing.listener.InfoListener;
import fr.ird.akado.ui.swing.listener.InfoListeners;
import fr.ird.akado.ui.swing.view.TaskController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class InfoBar extends JPanel implements InfoListener {

//    JPanel avdthInfo = new JPanel();
//    JPanel classInfo = new JPanel();
//    JPanel anapoInfo = new JPanel();
    TaskController vtc;

    public InfoBar(TaskController vtc, InfoListeners listeners) {
        this.vtc = vtc;
        listeners.addInfoListener(this);
        this.setLayout(new BorderLayout());

        this.add(createDBInfo(), BorderLayout.LINE_START);
        this.add(createAnapoInfo(), BorderLayout.LINE_END);
        this.add(createAkadoInfo(), BorderLayout.CENTER);
    }

    private JPanel createDBInfo() {

        JPanel dbInfo = new JPanel();
        JLabel label = new JLabel("DB :");
        dbInfo.add(label);
        label = new JLabel();
        if (vtc == null) {
            label.setText("No database");
            label.setForeground(Color.RED);
        } else {
            label.setText(vtc.getDatabaseLabel());
            label.setForeground(Color.BLUE);
        }
        dbInfo.add(label);
        return dbInfo;
    }

    private JPanel createAnapoInfo() {
        JPanel anapoInfo = new JPanel();
        JLabel label = new JLabel("Anapo : ");
        anapoInfo.add(label);

        label = new JLabel();
        if (AAProperties.isAnapoInspectorEnabled()) {
            label.setText("ACTIVE");
            label.setForeground(Color.GREEN);
        } else {
            label.setText("DISABLE");
            label.setForeground(Color.RED);
        }
        anapoInfo.add(label);
        label = new JLabel(" [" + AAProperties.THRESHOLD_CLASS_ONE + " : " + AAProperties.THRESHOLD_CLASS_TWO + "]");
        label.setForeground(Color.BLUE);
        anapoInfo.add(label);

        label = new JLabel(" BD :");
        anapoInfo.add(label);
        String anapoFilePath = AAProperties.ANAPO_DB_URL;
        label = new JLabel();
        if (anapoFilePath == null || "".equals(anapoFilePath)) {
            label.setText("No database");
            label.setForeground(Color.RED);
        } else {
            label.setText(anapoFilePath.substring(anapoFilePath.lastIndexOf(File.separator) + 1));
            label.setForeground(Color.BLUE);
        }
        anapoInfo.add(label);
        return anapoInfo;

    }

    private JPanel createAkadoInfo() {
        JPanel akadoInfo = new JPanel();
        JLabel label = new JLabel("AKaDo :");
        akadoInfo.add(label);
        label = new JLabel();
        if (AAProperties.isAkadoInspectorEnabled()) {
            label.setText("ACTIVE");
            label.setForeground(Color.GREEN);
        } else {
            label.setText("DISABLE");
            label.setForeground(Color.RED);
        }
        akadoInfo.add(label);

        if (AAProperties.isAkadoInspectorEnabled()) {

            label = new JLabel("[");
            akadoInfo.add(label);
            label = new JLabel("T");
            if (AAProperties.isTripInspectorEnabled()) {
                label.setForeground(Color.GREEN);
            } else {
                label.setForeground(Color.RED);
            }
            akadoInfo.add(label);
            label = new JLabel(",");
            akadoInfo.add(label);

            label = new JLabel("A");
            if (AAProperties.isActivityInspectorEnabled()) {
                label.setForeground(Color.GREEN);
            } else {
                label.setForeground(Color.RED);
            }
            akadoInfo.add(label);
            label = new JLabel(",");
            akadoInfo.add(label);

            label = new JLabel("P");
            if (AAProperties.isPositionInspectorEnabled()) {
                label.setForeground(Color.GREEN);
            } else {
                label.setForeground(Color.RED);
            }
            akadoInfo.add(label);
            label = new JLabel(",");
            akadoInfo.add(label);

            label = new JLabel("W");
            if (AAProperties.isWellInspectorEnabled()) {
                label.setForeground(Color.GREEN);
            } else {
                label.setForeground(Color.RED);
            }
            akadoInfo.add(label);
            label = new JLabel(",");
            akadoInfo.add(label);

            label = new JLabel("S");
            if (AAProperties.isSampleInspectorEnabled()) {
                label.setForeground(Color.GREEN);
            } else {
                label.setForeground(Color.RED);
            }
            akadoInfo.add(label);
            label = new JLabel(",");
            akadoInfo.add(label);

            label = new JLabel("Wa");
            if (AAProperties.isWarningInspectorEnabled()) {
                label.setForeground(Color.GREEN);
            } else {
                label.setForeground(Color.RED);
            }
            akadoInfo.add(label);
            label = new JLabel("]");
            akadoInfo.add(label);

        }
        return akadoInfo;
    }

    @Override
    public void infoUpdated() {
        System.out.println("infoUpdated");
        this.removeAll();
        this.add(createDBInfo(), BorderLayout.LINE_START);
//        this.add(createClassInfo(), BorderLayout.CENTER);
        this.add(createAnapoInfo(), BorderLayout.LINE_END);
        this.add(createAkadoInfo(), BorderLayout.CENTER);
        this.validate();
    }

}
