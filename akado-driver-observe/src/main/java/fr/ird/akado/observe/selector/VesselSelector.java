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
import fr.ird.driver.observe.business.referential.common.Vessel;
import fr.ird.driver.observe.service.ObserveService;

import java.util.Objects;

/**
 * Criteria for selecting based on vessels.
 * <p>
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
public class VesselSelector extends AbstractSelectionCriteria<Vessel> {

    private final String code;

    public VesselSelector(Integer code) {
        this.code = Objects.requireNonNull(code).toString();
    }

    @Override
    public Vessel get() {
        return ObserveService.getService().getReferentialCache().getOne(Vessel.class, c -> Objects.equals(c.getCode(), code));
    }

}
