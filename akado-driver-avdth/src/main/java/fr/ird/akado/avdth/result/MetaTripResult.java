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
package fr.ird.akado.avdth.result;

import fr.ird.akado.avdth.metatrip.RaisingFactorInspector;
import fr.ird.akado.avdth.result.model.MetaTripDataSheet;
import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.Trip;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

/**
 * Présente les données à afficher d'une marée reconstitué qui a généré une
 * erreur durant de l'analyse d'AKaDo. Une marée reconstituée est une marée
 * incluant l'ensemble des marées où il y a eu un débarquement partiel.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 9 juil. 2014
 */
public class MetaTripResult extends Result<List<Trip>> {

    public MetaTripResult() {
        super();
    }

    public static List<MetaTripDataSheet> factory(List<Trip> trips) {
        MetaTripDataSheet metaTripDataSheet;
        List<MetaTripDataSheet> list = new ArrayList<>();
        for (Trip trip : trips) {
            metaTripDataSheet = new MetaTripDataSheet();
            metaTripDataSheet.setVesselCode(trip.getVessel().getCode());
            metaTripDataSheet.setFlag(trip.getVessel().getCountry().getName());
            metaTripDataSheet.setEngine(trip.getVessel().getVesselType().getName());
            metaTripDataSheet.setLandingDate(trip.getLandingDate());
            metaTripDataSheet.setDepartureDate(trip.getDepartureDate());

            Activity a = trip.firstActivity();
            DateTime d = null;
            if (a != null) {
                d = a.getDate();
            }
            metaTripDataSheet.setFirstActivityDate(d);
            a = trip.lastActivity();
            d = null;
            if (a != null) {
                d = a.getDate();
            }
            metaTripDataSheet.setLastActivityDate(d);
            metaTripDataSheet.setPartialLandingIndicator(trip.getFlagCaleVide());

            double subTotalCatchesWeight = RaisingFactorInspector.catchesWeight(trip);
            metaTripDataSheet.setHasCatches(subTotalCatchesWeight > 0);

            metaTripDataSheet.setRaisingFactor(RaisingFactorInspector.RaisingFactor1(trips));
//            metaTripDataSheet.setRaisingFactor1WithLocalMarket(RaisingFactorInspector.RaisingFactor1WithLocalMarket(trips));
            list.add(metaTripDataSheet);
        }
        return list;
    }

    @Override
    public List extractResults() {
        List<Object> list = new ArrayList<>();
        List<Trip> trips = get();
        list.addAll(factory(trips));
        return list;
    }

}
