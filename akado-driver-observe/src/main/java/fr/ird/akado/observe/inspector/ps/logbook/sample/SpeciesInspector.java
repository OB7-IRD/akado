/*
 
 *
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
package fr.ird.akado.observe.inspector.ps.logbook.sample;

import com.google.auto.service.AutoService;
import fr.ird.akado.core.common.AbstractResults;
/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveSampleInspector.class)
public class SpeciesInspector extends ObserveSampleInspector {

    public SpeciesInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the specie must be sample.";
    }

    @Override
    public AbstractResults<?> execute() throws Exception {
        return null;
    }
}
