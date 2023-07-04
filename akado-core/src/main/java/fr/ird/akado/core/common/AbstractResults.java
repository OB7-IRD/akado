/*
 * Copyright (C) 2014 Observatoire thonier, IRD
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

import fr.ird.akado.core.common.export.XLSExport;
import fr.ird.akado.core.common.export.XMLExport;

import javax.swing.event.EventListenerList;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Représente une liste de résultat. Chaque module d'analyse pourra y ajouter sa
 * propre définition d'un résultat.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @param <T> the type of elements in this result list
 * @since 2.0
 * @date 9 juil. 2014
 */
public abstract class AbstractResults<T> extends ArrayList<T> implements XLSExport, XMLExport {

    // un seul objet pour tous les types d'écouteurs
    private final EventListenerList listeners = new EventListenerList();

    protected void fireResultAdded(T t) {
        for (ResultListener listener : getResultListeners()) {
            listener.resultAdded(t);
        }
    }

    @Override
    public boolean add(T t) {
        boolean added = super.add(t);
        fireResultAdded(t);
        return added;
    }

    public ResultListener[] getResultListeners() {
        return listeners.getListeners(ResultListener.class);
    }

    public void addResultListener(ResultListener listener) {
        listeners.add(ResultListener.class, listener);
    }

    public void removeResultListener(ResultListener listener) {
        listeners.remove(ResultListener.class, listener);
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        boolean added = true;
        for (T t : collection) {
            added &= this.add(t);
        }
        return added;
    }

    @Override
    public void exportToXLS(String filename) {
    }

    @Override
    public void exportToXML(String filename) {
    }

}
