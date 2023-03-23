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
package fr.ird.common;

import fr.ird.common.list.comprehesion.Func;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/**
 * Classe utilitaire permettant la manipulation de liste.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.0
 * @date 18 févr. 2015
 */
public class ListUtils {

    /**
     * Flitre les éléments de type <T> d'une liste de type <E> .
     *
     * @param <T> le type des éléments à filtrer
     * @param <E> le type des éléments de la liste à filtrer
     * @param classz le nom de la classe
     * @param l une liste de type <T>
     * @return une liste contenant les éléments de type <T>
     */
    public static <T extends E, E> List<T> filter(Class<T> classz, List<E> l) {
        List<T> list = new ArrayList<T>();
        for (E e : l) {
            if (e.getClass().equals(classz)) {
                list.add((T) e);
            }
        }
        return list;
    }

    /**
     * Applique une fonction sur une liste. Par exemple, il est possible de
     * multiplier par deux toutes les valeurs d'une liste d'entier.
     *
     * @see {@link fr.ird.common.list.comprehesion.Func}
     * @param <T> le type genérique de la fonction
     * @param list la liste à traiter
     * @param f la fonction à appliquer
     */
    public static <T> void applyToListInPlace(List<T> list, Func<T, T> f) {
        ListIterator<T> itr = list.listIterator();
        while (itr.hasNext()) {
            T output = f.apply(itr.next());
            itr.set(output);
        }
    }

    /**
     * Creating a mapping from the input list to the output list.
     *
     * @return la nouvelle liste
     * @see {@link fr.ird.common.list.comprehesion.Func}
     * @param <In> le type du paramètre d'entrée
     * @param <Out> le type du paramètre de sortie
     * @param in la liste d'entrée
     * @param f la fonction à appliquer
     *
     */
    public static <In, Out> List<Out> map(List<In> in, Func<In, Out> f) {
        List<Out> out = new ArrayList<Out>(in.size());
        for (In inObj : in) {
            out.add(f.apply(inObj));
        }
        return out;
    }

    /**
     * Remove all elements in the first collection which are in the second.
     *
     * @param l1 the first collection
     * @param l2 the second collection
     * @return the list
     */
    public static List<String> difference(Collection<String> l1, Collection<String> l2) {
        l1.removeAll(l2);
        return new ArrayList<String>(l1);
    }

}
