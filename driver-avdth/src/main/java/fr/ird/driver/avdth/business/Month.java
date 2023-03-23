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
 */package fr.ird.driver.avdth.business;

/**
 * *********************************************************************
 * Module: Mois.java Author: Julien Lebranchu Purpose: Defines the Class Mois --
 * Classe référentiel
 * *********************************************************************
 */
/**
 * @pdOid 76fd149c-9ec3-43ef-af26-68259494372e
 *
 */
public class Month {

    /**
     * @pdOid 05a09f3d-5234-428e-9a05-b06743bdfdef
     */
    private int number;
    /**
     * @pdOid a6213c72-0612-44ce-bd0b-77c5f4ab50fb
     */
    private String shortName;
    /**
     * @pdOid 5ed82adf-55ff-4b9e-b6d8-5ad3f4727e62
     */
    private String name;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
