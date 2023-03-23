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
package fr.ird.common;

import static fr.ird.common.DateTimeUtils.*;
import java.util.regex.Matcher;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * Test certaines methods de la classe {@link fr.ird.common.DateTimeUtils}.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
public class DateTimeUtilsTest extends TestCase {

    public void testDateIsTheNextDay() {
        DateTime dt = new DateTime(2014, 1, 1, 0, 0);
        DateTime dtmone = new DateTime(2013, 12, 31, 23, 0);

        DateTime d = new DateTime(2014, 6, 25, 0, 0);

        assertFalse(dateIsTheNextDay(dtmone, d));
        assertFalse(dateIsTheNextDay(d, dtmone));
        assertTrue(dateIsTheNextDay(dtmone, dt));
    }

    public void testDateEqual() {
        DateTime dt = new DateTime(2014, 1, 1, 0, 0);
        DateTime dtplus = new DateTime(2014, 1, 1, 23, 0);

        DateTime d = new DateTime(2014, 6, 25, 0, 0);

        assertFalse(dateEqual(dtplus, d));

        assertTrue(dateEqual(dtplus, dt));
    }

    public void testDateTimeMatch() {

        String field = "1899-12-30 15:57:00.0";
        Matcher matcher = pDateTime.matcher(field);
        assertTrue(matcher.matches());
        assertEquals("1899-12-30", matcher.group("date"));
        assertEquals("15:57:00.0", matcher.group("time"));

    }

    public void testAddTimeTo() {
        String field = "00:57:00";
        DateTime dt = new DateTime(2016, 05, 16, 0, 0);
        DateTime newDt = addTimeTo(dt, field);
        DateTime res = new DateTime(2016, 05, 16, 00, 57, DateTimeZone.forID("UTC"));
        assertEquals(newDt.getYear(), res.getYear());       
        assertEquals(newDt.getMonthOfYear(), res.getMonthOfYear());       
        assertEquals(newDt.getDayOfMonth(), res.getDayOfMonth());       
        assertEquals(newDt.getHourOfDay(), res.getHourOfDay());       
        assertEquals(newDt.getMinuteOfDay(), res.getMinuteOfDay());       
        assertEquals(newDt.getZone(), res.getZone());       
        
    }
}
