/**
 * Copyright (C) 2014 Julien Lebranchu <julien.lebranchu@ird.fr>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ird.driver.avdth.dao;

import fr.ird.driver.avdth.common.exception.AvdthDriverException;
import fr.ird.driver.avdth.service.AvdthService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Couche abstraite définissant les méthodes pour les requêtes sur une table.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @param <T> le type de la table à requêter
 * @since 3.4.5
 * @date 16 juin 2014
 */
public abstract class AbstractDAO<T> {

    protected Connection connection;

    public AbstractDAO() {
        this.connection = AvdthService.getService().getConnection();
    }

//    public abstract T factory(ResultSet rs);
    public abstract boolean insert(T t);

    public abstract boolean delete(T t);

    public boolean update(T t) {
        return delete(t) && insert(t);
    }

    protected abstract T factory(ResultSet rs) throws SQLException, AvdthDriverException;
}
