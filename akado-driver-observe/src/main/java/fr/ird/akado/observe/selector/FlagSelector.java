/*
 * Copyright (C) 2014 Observatoire thonier, IRD
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
package fr.ird.akado.observe.selector;

import fr.ird.akado.core.selector.AbstractSelectionCriteria;
import fr.ird.driver.observe.business.referential.common.Country;
import fr.ird.driver.observe.service.ObserveService;

import java.util.Objects;

/**
 * Selection criteria based on the vessel flag.
 * <p>
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class FlagSelector extends AbstractSelectionCriteria<Country> {

    private final String flag;

    public FlagSelector(String flag) {
        this.flag = flag;
    }

    @Override
    public Country get() {
        return ObserveService.getService().getReferentialCache().getOne(Country.class, c -> Objects.equals(c.getIso2Code(), flag));
    }

}
