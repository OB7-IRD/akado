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
package fr.ird.akado.avdth.sample;

import fr.ird.akado.avdth.Constant;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.result.SampleResult;
import fr.ird.akado.core.Inspector;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Sample;
import java.util.ArrayList;

/**
 * Check if the sample has at least one specie.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 18 juin 2014
 *
 *
 *
 *
 */
public class SampleWithoutSpeciesInspector extends Inspector<Sample> {

    public SampleWithoutSpeciesInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the sample has at least one specie.";
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Sample s = get();
        SampleResult r;
        if (s.getSampleSpecies().isEmpty()) {
            r = new SampleResult();
            r.set(s);
            r.setMessageType(Message.ERROR);
            r.setMessageCode(Constant.CODE_SAMPLE_NO_SAMPLE_SPECIES);
            r.setMessageLabel(Constant.LABEL_SAMPLE_NO_SAMPLE_SPECIES);

            r.setInconsistent(true);

            ArrayList parameters = new ArrayList<>();
            parameters.add(s.getID());

            r.setMessageParameters(parameters);
            results.add(r);
        }
        return results;
    }

}
