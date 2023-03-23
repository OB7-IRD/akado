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

import fr.ird.akado.avdth.common.AAProperties;
import static fr.ird.akado.avdth.Constant.CODE_SAMPLE_LDLF_M10;
import static fr.ird.akado.avdth.Constant.CODE_SAMPLE_LDLF_P10;
import static fr.ird.akado.avdth.Constant.CODE_SAMPLE_LDLF_SPECIES_FORBIDDEN;
import static fr.ird.akado.avdth.Constant.LABEL_SAMPLE_LDLF_M10;
import static fr.ird.akado.avdth.Constant.LABEL_SAMPLE_LDLF_P10;
import static fr.ird.akado.avdth.Constant.LABEL_SAMPLE_LDLF_SPECIES_FORBIDDEN;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.result.SampleResult;
import fr.ird.akado.core.Inspector;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Sample;
import fr.ird.driver.avdth.business.SampleSpecies;
import fr.ird.driver.avdth.business.Species;
import java.util.ArrayList;

/**
 * Check if the LDLF information for each sample species is consistent.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 18 juin 2014
 */
public class LDLFInspector extends Inspector<Sample> {

    public LDLFInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the LDLF information for each sample species is consistent.";
    }

    public static boolean ldlfSpeciesInconsistent(Species species, int ldlf) {
        return (species.getCode() == 2
                || species.getCode() == 5
                || species.getCode() == 6)
                && ldlf == SampleSpecies.SAMPLE_LENGTH_CLASS_FOR_DORSAL;
    }

    public static boolean ldlfP10(Sample s, SampleSpecies sampleSpecies) {
        return (sampleSpecies.getLdlf() == SampleSpecies.SAMPLE_LENGTH_CLASS_FOR_DORSAL || sampleSpecies.getLdlf() == SampleSpecies.SAMPLE_LENGTH_CLASS_FOR_DORSAL_ONE_CENTIMER_FREQUENCY)
                && !(s.getPlus10Weight() > 0 || s.getGlobalWeight() > 0);
    }

    public static boolean ldlfM10(Sample s, SampleSpecies sampleSpecies) {
        return sampleSpecies.getLdlf() == SampleSpecies.SAMPLE_LENGTH_CLASS_FOR_FORK
                && !(s.getMinus10Weight() > 0 || s.getGlobalWeight() > 0);
    }

    @Override
    public Results execute() {
        Results results = new Results();

        Sample s = get();
        SampleResult r;

        for (SampleSpecies sampleSpecies : s.getSampleSpecies()) {

            if (LDLFInspector.ldlfSpeciesInconsistent(sampleSpecies.getSpecies(), sampleSpecies.getLdlf())) {
                r = new SampleResult();
                r.set(s);
                r.setMessageType(Message.ERROR);
                r.setMessageCode(CODE_SAMPLE_LDLF_SPECIES_FORBIDDEN);
                r.setMessageLabel(LABEL_SAMPLE_LDLF_SPECIES_FORBIDDEN);

                ArrayList parameters = new ArrayList<>();
                parameters.add(s.getID());
                parameters.add(sampleSpecies.getSpecies().getCode());
                parameters.add(sampleSpecies.getLdlf());
                r.setMessageParameters(parameters);
                r.setInconsistent(true);

                results.add(r);
            }

            if (!AAProperties.WARNING_INSPECTOR.equals(AAProperties.DISABLE_VALUE)) {
                if (ldlfP10(s, sampleSpecies)) {
//                System.out.println("Sample Species " + sampleSpecies);
                    r = new SampleResult();
                    r.set(s);
                    r.setMessageType(Message.WARNING);
                    r.setMessageCode(CODE_SAMPLE_LDLF_P10);
                    r.setMessageLabel(LABEL_SAMPLE_LDLF_P10);

                    ArrayList parameters = new ArrayList<>();
                    parameters.add(s.getID());
                    parameters.add(sampleSpecies.getLdlf());
                    parameters.add(s.getPlus10Weight());
                    parameters.add(s.getGlobalWeight());
                    r.setMessageParameters(parameters);
                    r.setInconsistent(true);

                    results.add(r);
                }
                if (ldlfM10(s, sampleSpecies)) {
                    r = new SampleResult();
                    r.set(s);
                    r.setMessageType(Message.WARNING);
                    r.setMessageCode(CODE_SAMPLE_LDLF_M10);
                    r.setMessageLabel(LABEL_SAMPLE_LDLF_M10);

                    ArrayList parameters = new ArrayList<>();
                    parameters.add(s.getID());
                    parameters.add(sampleSpecies.getLdlf());
                    parameters.add(s.getMinus10Weight());
                    parameters.add(s.getGlobalWeight());
                    r.setMessageParameters(parameters);
                    r.setInconsistent(true);

                    results.add(r);
                }
            }
        }

        return results;
    }

}
