/* 
 *
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
 */package fr.ird.driver.avdth.business;

/**
 * Defines the Class Ocean -- Classe référentiel
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 *
 *
 */
public class Ocean {

    public static final int ATLANTIQUE = 1;
    public static final int INDIEN = 2;
    public static final int PACIFIQUE_OUEST = 3;
    public static final int PACIFIQUE_EST = 4;
    public static final int PACIFIQUE = 5;
    public static final int INDIEN_HEURE_DEBUT_PECHE = 6 * 60;
    public static final int INDIEN_HEURE_FIN_PECHE = 19 * 60;
    public static final int ATLANTIQUE_HEURE_DEBUT_PECHE = 6 * 60;
    public static final int ATLANTIQUE_HEURE_FIN_PECHE = 18 * 60;
    /**
     * Code numérique d'océan
     *
     * @pdOid 69a1cf55-0dfd-4070-80ff-74c84a48be31
     */
    private int code;
    /**
     * Nom de l'océan
     *
     * @pdOid fb68b30a-3931-4992-863e-32a4c2675dcc
     */
    private java.lang.String libelle;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public java.lang.String getLibelle() {
        return libelle;
    }

    public void setLibelle(java.lang.String libelle) {
        this.libelle = libelle;
    }
}
