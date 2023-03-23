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
package fr.ird.akado.core.selector;

import fr.ird.akado.core.selector.AbstractSelectionCriteria;
import org.joda.time.DateTime;

/**
 * Selection criteria based on temporal limits.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 21 mai 2014
 */
public class TemporalSelector extends AbstractSelectionCriteria<DateTime> {

    private DateTime start;
    private DateTime end;

    public TemporalSelector() {
        start = null;
        end = null;
    }

    public TemporalSelector(DateTime start, DateTime end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Returns the start date of the selection.
     *
     * @return the start date
     */
    public DateTime getStart() {
        return start;
    }

    /**
     * Returns the end date of the selection.
     *
     * @return the end date
     */
    public DateTime getEnd() {
        return end;
    }

}
