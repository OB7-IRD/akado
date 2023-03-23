/*
 * Copyright (C) 2014 Julien Lebranchu <julien.lebranchu@ird.fr>
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
package fr.ird.common.list.comprehesion;

/**
 * Cette interface permet de implémenter la fonctionnalité «list comprehension»
 * de Python. Classe issue d'une réponse de stackoverflow.
 *
 * @see
 * <a href="http://stackoverflow.com/questions/899138/python-like-list-comprehension-in-java">Python-like
 * list comprehension in Java</a>
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @param <In> le type du paramètre d'entrée
 * @param <Out> le type du paramètre de sortie
 * @since 1.1
 * @date 27 mai 2014
 *
 */
public interface Func<In, Out> {

    public Out apply(In in);
}
