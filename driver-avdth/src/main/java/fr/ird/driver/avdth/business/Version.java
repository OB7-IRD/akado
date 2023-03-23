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

import org.joda.time.DateTime;

/**
 * Identifie le schéma, la version de la codification et sa date de mise en
 * vigueur.

 */
public class Version {

    /**
     * Identification de la version de schéma relationnel 3
     *
     * @pdOid 6dd1bf19-af3d-48f4-8440-67c87a249502
     */
    private Integer versionSchema;
    /**
     * Date de disponibilité de la base de donnée générique sur le serveur ftp .
     *
     * @pdOid 6af14530-a67a-4c33-a59d-32ae96b9becc
     */
    private DateTime date;
    /**
     * Le nom du fichier Excel de codification en cours dans cette base. Par
     * exemple : C24062002_Avdth.xls
     *
     * @pdOid 267c9be6-da7b-4120-b92f-474a7b5b3408
     */
    private String codificationFilename;
    /**
     * Le nom du fichier Excel qui contient la description des formats
     * d'exportation mis en oeuvre dans cette base. Par exemple :
     * F21032002_Avdth.xls
     *
     * @pdOid bea4df73-0f8e-447c-8038-54c2ff6161f3
     */
    private String exportFilename;

    public int getVersionSchema() {
        return versionSchema;
    }

    public void setVersionSchema(int versionSchema) {
        this.versionSchema = versionSchema;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public String getCodificationFilename() {
        return codificationFilename;
    }

    public void setCodificationFilename(String codificationFilename) {
        this.codificationFilename = codificationFilename;
    }

    public String getExportFilename() {
        return exportFilename;
    }

    public void setExportFilename(String exportFilename) {
        this.exportFilename = exportFilename;
    }

}
