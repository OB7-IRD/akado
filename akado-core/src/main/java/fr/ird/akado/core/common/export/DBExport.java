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

import java.io.Serializable;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Spécifie les méthodes d'export vers une base de donnée. Attention cette
 * classe est très liée à la couche Akado DB et Spring.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @param <T> le type d'objet à traiter via le repository
 * @param <ID> le type de l'identifiant de l'objet à traiter via le repository
 * @since 2.0.1
 * @date 26 mars 2015
 *
 */
public interface DBExport<T, ID extends Serializable> {

    /**
     * Export des résultats dans une base de données.
     *
     * @param repository le repertoire permettant de traiter les opérations de
     * type CRUD de type T
     */
    public void exportToDB(CrudRepository<T, ID> repository);

    public List<T> extract(Class<T> classz);
}
