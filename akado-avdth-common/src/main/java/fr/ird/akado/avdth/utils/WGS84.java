/*
 * Copyright (C) 2016 Observatoire thonier, IRD
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
package fr.ird.akado.avdth.utils;

/**
 *Classe de test de calcul des distances non euclidiennes.
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 */
import java.text.DecimalFormat;

public class WGS84 {

    /**
     * @param args
     */
    public static void main(String[] args) {

        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance();
        decimalFormat.applyPattern("###0.##########");

        // r
        int r = 6356;

        double lat1 = 15;
        double lon1 =-17.1666666667;

        double lat2 = 15.0666666667;
        double lon2 = -17.4666666667;

        long tempsT1;
        long tempsT2;

        System.out.println(
                "Point A (lat/lon) : " + decimalFormat.format(lat1) + " " + decimalFormat.format(lon1) + "\n"
                + "Point B (lat/lon) : " + decimalFormat.format(lat2) + " " + decimalFormat.format(lon2)
        );

        /**
         * Conversion des entrées en ° vers en radian
         */
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        tempsT1 = System.nanoTime();
        double distance = distanceVolOiseauEntre2PointsAvecPrecision(lat1, lon1, lat2, lon2);
        tempsT2 = System.nanoTime();
        System.out.println("Temps (AvecPrécision) : " + String.format("%10d", (tempsT2 - tempsT1)) + " ns");
        double distanceEnKm = distance * r;

        tempsT1 = System.nanoTime();
        double distanceEloigne = distanceVolOiseauEntre2PointsSansPrecision(lat1, lon1, lat2, lon2);
        tempsT2 = System.nanoTime();
        System.out.println("Temps (SansPrécision) : " + String.format("%10d", (tempsT2 - tempsT1)) + " ns");
        double distanceEloigneEnKm = distanceEloigne * r;

        System.out.println(
                "Distance      : " + decimalFormat.format(distance) + " (" + distance + ")\n"
                + "Distance (km) calcul précis pour courtes distances         : " + decimalFormat.format(distanceEnKm) + " km (" + distanceEnKm + ")\n"
                + "Distance (km) calcul non précis pour distances non courtes : " + decimalFormat.format(distanceEloigneEnKm) + " km (" + distanceEloigneEnKm + ")\n"
                + ""
        );
        r = 3431;
        double distanceEnMilles = distance * r;
        double distanceEloigneEnMilles = distanceEloigne * r;
        System.out.println(
                "Distance      : " + decimalFormat.format(distance) + " (" + distance + ")\n"
                + "Distance (milles) calcul précis pour courtes distances         : " + decimalFormat.format(distanceEnMilles) + " milles (" + distanceEnMilles + ")\n"
                + "Distance (milles) calcul non précis pour distances non courtes : " + decimalFormat.format(distanceEloigneEnMilles) + " milles (" + distanceEloigneEnMilles + ")\n"
                + ""
        );

//		Point A (lat/lon) : 50,19473 6,83212
//		Point B (lat/lon) : 50,1948 6,83244
//		Temps (AvecPrécision) :      34297 ns
//		Temps (SansPrécision) :      24435 ns
//		Distance      : 0,0000037784 (3.7784109830459747E-6)
//		Distance (km) calcul précis pour courtes distances         : 0,0240533643 km (0.024053364318070675)
//		Distance (km) calcul non précis pour distances non courtes : 0,0240533376 km (0.024053337627628308)
    }

    /**
     * Distance entre 2 points GPS
     *
     * La distance mesurée le long d'un arc de grand cercle entre deux points
     * dont on connaît les coordonnées {lat1,lon1} et {lat2,lon2} est donnée par
     * : d=acos(sin(lat1)*sin(lat2)+cos(lat1)*cos(lat2)*cos(lon1-lon2)) Le tout
     * * 6366 pour l'avoir en km
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public static double distanceVolOiseauEntre2PointsSansPrecision(double lat1, double lon1, double lat2, double lon2) {

        // d=acos(sin(lat1)*sin(lat2)+cos(lat1)*cos(lat2)*cos(lon1-lon2))
        return Math.acos(
                Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2)
        );

    }

    /**
     * Distance entre 2 points GPS La distance mesurée le long d'un arc de grand
     * cercle entre deux points dont on connaît les coordonnées {lat1,lon1} et
     * {lat2,lon2} est donnée par : La formule, mathématiquement équivalente,
     * mais moins sujette aux erreurs d'arrondis pour les courtes distances est
     * :	* d=2*asin(sqrt((sin((lat1-lat2)/2))^2 +
     * cos(lat1)*cos(lat2)*(sin((lon1- lon2)/2))^2)) Le tout * 6366 pour l'avoir
     * en km
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public static double distanceVolOiseauEntre2PointsAvecPrecision(double lat1, double lon1, double lat2, double lon2) {
//        System.out.println(lat1 +" " + lon1 + " ; " + lat2 + " "+lon2  );
        // d=2*asin(sqrt((sin((lat1-lat2)/2))^2 + cos(lat1)*cos(lat2)*(sin((lon1- lon2)/2))^2))
        return 2 * Math.asin(
                Math.sqrt(
                        Math.pow((Math.sin((lat1 - lat2) / 2)), 2)
                        + Math.cos(lat1) * Math.cos(lat2)
                        * (Math.pow(
                                Math.sin(
                                        ((lon1 - lon2) / 2)
                                ), 2))
                )
        );

    }

}
