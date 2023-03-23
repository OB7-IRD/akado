/*
 * Copyright (C) 2014 Julien Lebranchu <julien.lebranchu@ird.fr>
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
package fr.ird.common;

import static fr.ird.common.Utils.round;
import junit.framework.TestCase;

/**
 * Test certaines methods de la classe {@link fr.ird.common.OTUtils}.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 1.1
 * @date 19 juin 2014
 *
 */
public class OTUtilsTest extends TestCase {

   
    public void testDegreesDecimalToDegreesMinutes() {
        assertEquals(3830, OTUtils.degreesDecimalToDegreesMinutes(38.5));
        assertEquals(4000, OTUtils.degreesDecimalToDegreesMinutes(40d));
        assertEquals(2000, OTUtils.degreesDecimalToDegreesMinutes(-20.0));
        assertEquals(2000, OTUtils.degreesDecimalToDegreesMinutes(-20.0));
        assertEquals(1441, OTUtils.degreesDecimalToDegreesMinutes(14.68));
        assertEquals(4318, OTUtils.degreesDecimalToDegreesMinutes(43.2964));

    }

    public void testDegreesMinutesToDegreesDecimal() {
        assertEquals(38.5, round(OTUtils.degreesMinutesToDegreesDecimal(3830), 2));
        assertEquals(0.15, round(OTUtils.degreesMinutesToDegreesDecimal(9), 2));
        assertEquals(-0.15, round(OTUtils.degreesMinutesToDegreesDecimal(-9), 2));
        assertEquals(14.68, round(OTUtils.degreesMinutesToDegreesDecimal(1441), 2));
        assertEquals(17.23, round(OTUtils.degreesMinutesToDegreesDecimal(1714), 2));
        assertEquals(43.3, round(OTUtils.degreesMinutesToDegreesDecimal(4318), 2));
        
    }
    
    public void testDegreesDecimalToStringDegreesMinutes(){
        assertEquals("14°41'N", OTUtils.degreesDecimalToStringDegreesMinutes(14.68, true));
        assertEquals("17°14'W", OTUtils.degreesDecimalToStringDegreesMinutes(-17.24, false));
        assertEquals("55°37'E", OTUtils.degreesDecimalToStringDegreesMinutes(55.62, false));
    }

    public void testQuadrant(){
        assertEquals(1, OTUtils.getQuadrant(0, 170));
        assertEquals(2, OTUtils.getQuadrant(-4, 0));
        assertEquals(3, OTUtils.getQuadrant(-15, -170));
        assertEquals(4, OTUtils.getQuadrant(0, -170));
    }
}
