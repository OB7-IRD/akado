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

import fr.ird.akado.ui.Constant;
import fr.ird.common.log.LogService;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The AboutPanel displayed an about of Akado application. This class extends
 * {@link JPanel}.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 27 mai 2014
 *
 */
public class AboutPanel extends JPanel {

    private BufferedImage image;
    private Boolean DEBGUG = Boolean.FALSE;
    private Font font;

    public AboutPanel() {
        image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(Constant.SPLASH));
        } catch (IOException e) {
            LogService.getService(AboutPanel.class).logApplicationError(e.getMessage());
        }
        setSize(new Dimension(500, 250));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        double hAppVersion = 7.5 * getHeight() / 9;
        double wAppVersion = 0.5 * getWidth() / 6;

        double hAppAnnee = 8.5 * getHeight() / 9;
        double wAppAnnee = 0.5 * getWidth() / 6;

        g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        g2d.setFont(new Font("Arial", Font.PLAIN, 15));
        g2d.drawString("Version: " + Constant.APPLICATION_VERSION, (int) wAppVersion, (int) hAppVersion);

        g2d.setFont(new Font("Arial", Font.PLAIN, 15));
        g2d.drawString(Constant.APPLICATION_AUTHOR + ", " + Constant.APPLICATION_YEAR, (int) wAppAnnee, (int) hAppAnnee);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setSize(500, 250);
        f.setResizable(false);
        f.setContentPane(new AboutPanel());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

    }
}
