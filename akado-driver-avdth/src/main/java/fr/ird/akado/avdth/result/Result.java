/*
 * Copyright (C) 2015 Observatoire thonier, IRD
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
package fr.ird.akado.avdth.result;

import fr.ird.akado.core.common.AbstractResult;

import java.util.List;

/**
 * Définit la classe de résultat d'une analyse d'Akado pour AVDTH.
 *
 * @param <T> le type de la donnée analysée
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @date 4 mars 2015
 * @since 2.0
 */
public abstract class Result<T> extends AbstractResult<T> {

    @Override
    protected AVDTHMessage createMessage(String messageCode, String messageLabel, List<?> list, String messageType) {
        return new AVDTHMessage(messageCode, messageLabel, list, messageType);
    }
}
