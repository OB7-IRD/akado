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
package fr.ird.akado.avdth.sample;

import static fr.ird.akado.avdth.Constant.CODE_SAMPLE_LENGTH_CLASS;
import static fr.ird.akado.avdth.Constant.LABEL_SAMPLE_LENGTH_CLASS;
import fr.ird.akado.avdth.common.AAProperties;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.result.SampleResult;
import fr.ird.akado.core.Inspector;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Sample;
import fr.ird.driver.avdth.business.SampleSpecies;
import fr.ird.driver.avdth.business.SampleSpeciesFrequency;
import java.util.ArrayList;

/**
 * Check if the length class is consistent with each length class of species
 * (L=90cm for YFT and BET and L=42cm for ALB) and LD1.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 18 juin 2014
 *
 */
public class LengthClassInspector extends Inspector<Sample> {

    public static final int LENGTH_CLASS_YFT = 90;
    public static final int LENGTH_CLASS_BET = 90;
    public static final int LENGTH_CLASS_ALB = 42;

    public LengthClassInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the length class is consistent with each "
                + "length class of species (L=90cm for YFT and BET and L=42cm for ALB) and LD1.";
    }

    @Override
    public Results execute() {
        Results results = new Results();
        if (!AAProperties.WARNING_INSPECTOR.equals(AAProperties.DISABLE_VALUE)) {
            Sample s = get();
            SampleResult r;
            for (SampleSpecies sampleSpecies : s.getSampleSpecies()) {
                if (sampleSpecies.getLdlf() == SampleSpecies.SAMPLE_LENGTH_CLASS_FOR_DORSAL) {
                    for (SampleSpeciesFrequency sampleSpeciesFrequency : sampleSpecies.getSampleSpeciesFrequencys()) {
                        if (lengthClassLimits(sampleSpeciesFrequency)) {

                            r = new SampleResult();
                            r.set(s);
                            r.setMessageType(Message.WARNING);
                            r.setMessageCode(CODE_SAMPLE_LENGTH_CLASS);
                            r.setMessageLabel(LABEL_SAMPLE_LENGTH_CLASS);

                            ArrayList parameters = new ArrayList<>();
                            parameters.add(s.getID());
                            parameters.add(sampleSpecies.getSpecies().getCode());
                            parameters.add(sampleSpeciesFrequency.getLengthClass());
                            r.setMessageParameters(parameters);
                            r.setInconsistent(true);

                            results.add(r);
                        }
                    }
                }

            }
        }

        return results;
    }

    public static boolean lengthClassLimits(SampleSpeciesFrequency sampleSpeciesFrequency) {

        return (sampleSpeciesFrequency.getSpecies().getCode() == 1 && sampleSpeciesFrequency.getLengthClass() > LENGTH_CLASS_YFT)
                || (sampleSpeciesFrequency.getSpecies().getCode() == 3 && sampleSpeciesFrequency.getLengthClass() > LENGTH_CLASS_BET)
                || (sampleSpeciesFrequency.getSpecies().getCode() == 4 && sampleSpeciesFrequency.getLengthClass() > LENGTH_CLASS_ALB);
    }

}
