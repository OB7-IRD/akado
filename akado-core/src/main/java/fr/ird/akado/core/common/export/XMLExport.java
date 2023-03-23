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
package fr.ird.akado.core.common.export;

/**
 * Spécifie les méthodes d'export vers un fichier XML. *
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0.1
 * @date 26 mars 2015
 *
 */
public interface XMLExport {

    /**
     * Export des résultats au format XML. Fonction actuellement prévue
     * uniquement pour le module AVDTH.
     *
     * @param filename le nom de fichier où enregistrer
     */
    public void exportToXML(String filename);

}
