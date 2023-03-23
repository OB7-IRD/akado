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

import static fr.ird.akado.avdth.Constant.CODE_SAMPLE_SPECIES_NOT_SAMPLE;
import static fr.ird.akado.avdth.Constant.LABEL_SAMPLE_SPECIES_NOT_SAMPLE;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.result.SampleResult;
import fr.ird.akado.core.Inspector;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Sample;
import fr.ird.driver.avdth.business.SampleSpecies;
import java.util.ArrayList;

/**
 * Check if the specie must be sample.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 18 juin 2014
 *
 *
 *
 *
 */
public class SpeciesInspector extends Inspector<Sample> {

    public SpeciesInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the specie must be sample.";
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Sample s = get();
        SampleResult r;
        for (SampleSpecies sampleSpecies : s.getSampleSpecies()) {
            if (!specieMustBeSampled(sampleSpecies.getSpecies().getCode())) {
                r = new SampleResult();
                r.set(s);
                r.setMessageType(Message.ERROR);
                r.setMessageCode(CODE_SAMPLE_SPECIES_NOT_SAMPLE);
                r.setMessageLabel(LABEL_SAMPLE_SPECIES_NOT_SAMPLE);

                ArrayList parameters = new ArrayList<>();
                parameters.add(s.getID());
                parameters.add(sampleSpecies.getSpecies().getCode());
                r.setMessageParameters(parameters);
                r.setInconsistent(true);

                results.add(r);
            }

        }

        return results;
    }

    public static boolean specieMustBeSampled(int code) {
        return (code >= 1 && code <= 6) || code == 9 || code == 10 || code == 11;
    }

}
