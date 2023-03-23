/*
 * $Id: Table.java 433 2014-07-29 14:37:42Z lebranch $
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
 * *********************************************************************
 * Module: Table.java Author: Julien Lebranchu Purpose: Defines the Class Table
 * *********************************************************************
 */
/**
 * Les tables du modèles - usage réservé administration
 *
 * @pdOid 964e253a-78f3-4fdd-a3d3-7a4136a9b55f
 *
 * $LastChangedDate: 2014-07-29 16:37:42 +0200 (mar. 29 juil. 2014) $
 *
 * $LastChangedRevision: 433 $
 */
public class Table {

    /**
     * @pdOid 0c26c495-17c1-4d1a-b87f-a4576cbb3807
     */
    private java.lang.String codeTable;
    /**
     * @pdOid daaff72f-28e8-4813-9a94-9565cedc52c3
     */
    private boolean flagCodification;
    /**
     * @pdOid 475d25f2-ab26-4c28-ac80-b384c4fa5bd9
     */
    private int nombreDeChamps;

    public java.lang.String getCodeTable() {
        return codeTable;
    }

    public void setCodeTable(java.lang.String codeTable) {
        this.codeTable = codeTable;
    }

    public boolean isFlagCodification() {
        return flagCodification;
    }

    public void setFlagCodification(boolean flagCodification) {
        this.flagCodification = flagCodification;
    }

    public int getNombreDeChamps() {
        return nombreDeChamps;
    }

    public void setNombreDeChamps(int nombreDeChamps) {
        this.nombreDeChamps = nombreDeChamps;
    }
}
