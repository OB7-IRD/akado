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

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveSampleInspector.class)
public class SuperSampleNumberConsistentInspector extends ObserveSampleInspector {

    public static boolean checkIfHasManySubSampling(Sample s) {
        for (SampleSpecies ss : s.getSampleSpecies()) {
            if (ss.getSubSampleNumber() == 0) {
                return false;
            }
        }
        return count(s.getSampleSpecies()) > 1;
    }

    public static boolean checkIfHasOnlyOneSubSampling(Sample s) {
        for (SampleSpecies ss : s.getSampleSpecies()) {
            if (ss.getSubSampleNumber() != 0) {
                return false;
            }
        }
        return true;
    }

    public static int count(List<SampleSpecies> sampleSpecies) {
        List<Integer> subSampleNumber = new ArrayList<>();
        for (SampleSpecies ss : sampleSpecies) {
            if (!subSampleNumber.contains(ss.getSubSampleNumber())) {
                subSampleNumber.add(ss.getSubSampleNumber());
            }
        }
        return subSampleNumber.size();

    }

    public SuperSampleNumberConsistentInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the sub-sample number is consistent.";
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();
        Sample s = get();


        if (s.getSampleSpecies().isEmpty()) {
            SampleResult r = createResult(s, Message.ERROR, Constant.CODE_SAMPLE_SUBSAMPLE_NO_SAMPLE_SPECIES, Constant.LABEL_SAMPLE_SUBSAMPLE_NO_SAMPLE_SPECIES, true,
                                          s.getTopiaId(),
                                          s.isSuperSample(),
                                          count(s.getSampleSpecies()));
            results.add(r);
            return results;
        }
        boolean onlyOneSubSampling = !s.isSuperSample() && checkIfHasOnlyOneSubSampling(s);
        boolean manySubSampling = s.isSuperSample() && checkIfHasManySubSampling(s);
        if (!(onlyOneSubSampling || manySubSampling)) {
            SampleResult r = createResult(s, Message.ERROR, Constant.CODE_SAMPLE_SUBSAMPLE_FLAG, Constant.LABEL_SAMPLE_SUBSAMPLE_FLAG, true,
                                          s.getTopiaId(),
                                          s.isSuperSample(),
                                          count(s.getSampleSpecies())
            );
            results.add(r);
        }
        for (SampleSpecies ss : s.getSampleSpecies()) {
            int count = count(s.getSampleSpecies());
            if (count == 1 && ss.getSubSampleNumber() == 1) {
                SampleResult r = createResult(s, Message.ERROR, Constant.CODE_SUBSAMPLE_NUMBER_INCONSISTENCY, Constant.LABEL_SUBSAMPLE_NUMBER_INCONSISTENCY, true,
                                              s.getTopiaId(),
                                              ss.getSubSampleNumber(),
                                              ss.getSpecies().getFaoCode());
                results.add(r);
            }
        }
        return results;
    }
}
