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
package fr.ird.akado.core;

import fr.ird.akado.core.common.AbstractResults;

/**
 * Décrit une régle de métier à appliquer sur le type générique passé en
 * paramètre de la classe.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @param <T> the type of inspection
 * @since 2.0
 * @date 21 mai 2014
 */
public abstract class Inspector<T> {

    protected String name;
    protected String description;
    private T t;

    /**
     * Returns the description of the inspection.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the name of the inspection.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    public T get() {
        return t;
    }

    /**
     * Set the new description of the inspection.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set the new name of the inspection.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    public void set(T t) {
        this.t = t;
    }

    /**
     * Execute the inspection.
     *
     * @return the results of execution
     * @throws Exception
     */
    public abstract AbstractResults execute() throws Exception;

}
