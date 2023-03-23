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
package fr.ird.akado.core.common;

/**
 * Classe abstraite représentant un message permettant de déléguer la
 * représentation d'un message au niveau de chaque module d'analyse.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0.1
 * @date 4 mars 2015
 *
 */
public abstract class AkadoMessage {

    /**
     * Returns the message content.
     *
     * @return the message content
     */
    public abstract String getContent();

    @Override
    public String toString() {
        return getContent();
    }

}
