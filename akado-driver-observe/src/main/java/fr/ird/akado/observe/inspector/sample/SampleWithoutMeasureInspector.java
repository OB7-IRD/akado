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
package fr.ird.akado.observe.inspector.sample;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.Constant;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.SampleResult;
import fr.ird.common.message.Message;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;
import fr.ird.driver.observe.business.data.ps.logbook.SampleSpecies;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveSampleInspector.class)
public class SampleWithoutMeasureInspector extends ObserveSampleInspector {

    public SampleWithoutMeasureInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the sample has at least one measure.";
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();
        Sample s = get();
        if (s.getSampleSpecies().isEmpty()) {
            return results;
        }
        for (SampleSpecies sampleSpecies : s.getSampleSpecies()) {
            if (sampleSpecies.getSampleSpeciesMeasure().isEmpty()) {
                SampleResult r = createResult(s, Message.ERROR, Constant.CODE_SAMPLE_NO_SAMPLE_MEASURE, Constant.LABEL_SAMPLE_NO_SAMPLE_MEASURE, true,
                                              s.getTopiaId(),
                                              sampleSpecies.getSubSampleNumber(),
                                              sampleSpecies.getSpecies().getCode(),
                                              sampleSpecies.getSizeMeasureType().getCode());
                results.add(r);
            }
        }
        return results;
    }

}
