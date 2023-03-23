/*
 * Copyright (C) 2016 Observatoire thonier/UMR 248 MARBEC/IRD
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
package fr.ird.akado.avdth.common;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class AkadoException extends Exception {

    public AkadoException(String message) {
        super(message);
    }

    public AkadoException(String message, Exception e) {
        super(message, e);
    }

}
