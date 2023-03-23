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
package fr.ird.akado.avdth.well;

import static fr.ird.akado.avdth.Constant.CODE_WELL_NO_TRIP;
import static fr.ird.akado.avdth.Constant.LABEL_WELL_NO_TRIP;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.result.WellResult;
import fr.ird.akado.core.Inspector;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Well;
import fr.ird.driver.avdth.dao.TripDAO;
import java.util.ArrayList;

/**
 * Check if the well is associated at one trip existing.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 18 juin 2014
 */
public class WellWithoutTripInspector extends Inspector<Well> {

    public WellWithoutTripInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the well is associated at one trip existing.";
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Well w = get();

        if (!TripDAO.exist(w.getVessel(), w.getLandingDate())) {
            WellResult r = new WellResult();
            r.set(w);
            r.setMessageType(Message.ERROR);
            r.setMessageCode(CODE_WELL_NO_TRIP);
            r.setMessageLabel(LABEL_WELL_NO_TRIP);

            r.setInconsistent(true);

            ArrayList parameters = new ArrayList<>();
            parameters.add(w.getID());

            r.setMessageParameters(parameters);
            results.add(r);
        }
        return results;
    }
}
