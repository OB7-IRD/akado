/* 
 * Copyright (C) 2013 Julien Lebranchu <julien.lebranchu@ird.fr>
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
package fr.ird.driver.avdth.common;

import fr.ird.common.DateTimeUtils;
import fr.ird.driver.avdth.business.Ocean;
import fr.ird.driver.avdth.business.Trip;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

/**
 * Permet de convertir les champs ERS vers AVDTH en adaptant les bonnes unités.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @version 1.0
 * @date 20 sept. 2013
 */
public class AvdthUtils {

    /**
     * Returns the ocean in which the coordinates is located.
     *
     * @param longitude
     * @param latitude
     * @return
     */
    public static int getOcean(Double longitude, Double latitude) {
        if (longitude >= 20 && longitude < 146.031389) {
            return Ocean.INDIEN;
        }
        if (longitude >= -67.271667 && longitude < 20) {
            return Ocean.ATLANTIQUE;
        }

//        if(longitude >= -180 && longitude < -67.271667 &&
//                longitude >= 146.031389 && longitude <= 180) return Ocean.PACIFIQUE;
        if (longitude >= 146.031389 && longitude <= 180) {
            return Ocean.PACIFIQUE_EST;
        }
        if (longitude >= -180 && longitude < -67.271667) {
            return Ocean.PACIFIQUE_OUEST;
        }
        return -1;
    }

    /**
     * Give the quadrant in which the position is located.
     *
     * @param longitude
     * @param latitude
     * @return
     */
    public static int getQuadrant(Double longitude, Double latitude) {
        if (longitude >= 0 && latitude >= 0) {
            return 1;
        }
        if (longitude >= 0 && latitude < 0) {
            return 2;
        }
        if (longitude < 0 && latitude < 0) {
            return 3;
        }
        if (longitude < 0 && latitude >= 0) {
            return 4;
        }
        return -1;
    }

    /**
     * Transforms some coordinates in Degrees Minutes to VMS format.
     *
     * @param coordsVMS
     * @return Integer
     */
    public static Integer vmsToDegresMinutes(Double coordsVMS) {
        coordsVMS = Math.abs(coordsVMS);
        long resultat = 100 * (int) Math.floor(coordsVMS);
        resultat += Math.round((coordsVMS * 60) % 60);
        return (int) resultat;
    }

//    public static String formatToMSAccess(DateTime date) {
////        System.out.println("<<<< " + date + " >>>>");
//        if (date == null) {
//            return "1900-01-01";
//        }
//        return String.format("%s-%02d-%02d", date.getYear(), date.getMonthOfYear(), date.getDayOfMonth());
//    }
    public static List<List<Trip>> buildExtendedTrips(List<Trip> trips) {
        List<List<Trip>> extendedTrips = new ArrayList<List<Trip>>();
        List<Trip> extendedTrip = new ArrayList<Trip>();
        Trip previous = null;
        for (Trip trip : trips) {
            if (previous == null && !trip.isPartialLanding()) {
                extendedTrip.add(trip);
                extendedTrips.add(extendedTrip);
                extendedTrip = new ArrayList<Trip>();
            } else if (previous == null && trip.isPartialLanding()) {
                extendedTrip.add(trip);
                previous = trip;
            } else if (!previous.getVessel().equals(trip.getVessel())) {
                extendedTrips.add(extendedTrip);
                extendedTrip = new ArrayList<Trip>();
                extendedTrip.add(trip);
                if (trip.isPartialLanding()) {
                    previous = trip;
                } else {
                    extendedTrips.add(extendedTrip);
                    extendedTrip = new ArrayList<Trip>();
                    previous = null;
                }
            } else if (previous.getVessel().equals(trip.getVessel())
                    && DateTimeUtils.dateAfter(trip.getLandingDate(), previous.getLandingDate())) {
                extendedTrip.add(trip);

                if (trip.isPartialLanding()) {
                    previous = trip;
                } else {
                    extendedTrips.add(extendedTrip);
                    extendedTrip = new ArrayList<Trip>();
                    previous = null;
                }

            } 
        }
        return extendedTrips;
    }

    public static DateTime createDate(Date date, int hour, int minute) {

        if (date != null) {
            DateTime dt = DateTimeUtils.convertDate(date);
            dt = dt.plusHours(hour);
            dt = dt.plusMinutes(minute);
            return dt;
        }
        return null;

    }

}
