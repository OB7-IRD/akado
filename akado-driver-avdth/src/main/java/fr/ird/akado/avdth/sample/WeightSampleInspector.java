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

import static fr.ird.akado.avdth.Constant.CODE_SAMPLE_WEIGHT_INCONSISTENCY;
import static fr.ird.akado.avdth.Constant.LABEL_SAMPLE_WEIGHT_INCONSISTENCY;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.result.SampleResult;
import fr.ird.akado.core.Inspector;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Sample;
import java.util.ArrayList;

/**
 * Check if the sample weight (m10 and p10) is consistent with the global weight
 * sample.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 21 mai 2014
 */
public class WeightSampleInspector extends Inspector<Sample> {

    public WeightSampleInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the sample weight (m10 and p10) is consistent with the global weight sample.";
    }

    @Override
    public Results execute() {

        Results results = new Results();
        Sample s = get();
        SampleResult r;
        if ((s.getMinus10Weight() + s.getPlus10Weight() == 0
                && s.getGlobalWeight() == 0) || (s.getMinus10Weight() + s.getPlus10Weight() > 0 && s.getGlobalWeight() != 0)) {
            r = new SampleResult();
            r.set(s);
            r.setMessageType(Message.ERROR);
            r.setMessageCode(CODE_SAMPLE_WEIGHT_INCONSISTENCY);
            r.setMessageLabel(LABEL_SAMPLE_WEIGHT_INCONSISTENCY);

            ArrayList parameters = new ArrayList<>();
            parameters.add(s.getID());

            parameters.add(s.getMinus10Weight() + s.getPlus10Weight());
            parameters.add(s.getGlobalWeight());

            r.setMessageParameters(parameters);
            r.setInconsistent(true);

            results.add(r);
        }

        return results;
    }
}
