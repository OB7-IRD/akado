/* 
 * Copyright (C) 2013 Julien Lebranchu <julien.lebranchu@ird.fr>
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
package fr.ird.driver.avdth.business;

/**
 * Les types de balises utilisés pour les DCPs. Classe référentiel TYPE_BALISE.
 */
public class BuoyType {

    public static final int RADIOGONIOMETRE = 1;
    public static final int SATELLITE_ET_ECHOSONDEUR_INDETERMINE = 4;
    public static final int SATELLITE_SANS_ECHOSONDEUR = 5;
    public static final int BALISE_INCONNUE_OU_INDETERMINEE = 98;
    public static final int TYPE_A_PRECISER = 99;
    public static final int AUCUNE_BALISE = 999;

    private int code;
    private String topiad;
    private String name;
    private boolean status = true;

    public String getTopiad() {
        return topiad;
    }

    public void setTopiad(String topiad) {
        this.topiad = topiad;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
