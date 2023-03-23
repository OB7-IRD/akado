/*
 * Copyright (C) 2016 Observatoire thonier, IRD
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
package fr.ird.akado.ui.swing.listener;

import javax.swing.event.EventListenerList;

/**
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class InfoListeners {
    // un seul objet pour tous les types d'écouteurs

    private final EventListenerList listeners = new EventListenerList();

    public void fireInfoUpdated() {
        for (InfoListener listener : getListeners()) {
            listener.infoUpdated();
        }
    }

    public InfoListener[] getListeners() {
        return listeners.getListeners(InfoListener.class);
    }

    public void addInfoListener(InfoListener listener) {
        System.out.println(listeners);
        listeners.add(InfoListener.class, listener);
    }

    public void removeInfoListener(InfoListener listener) {
        listeners.remove(InfoListener.class, listener);
    }
}
