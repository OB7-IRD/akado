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

import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.SampleResult;
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
//FIXME No more used
//@AutoService(ObserveSampleInspector.class)
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
        Sample sample = get();

        if (sample.getSampleSpecies().isEmpty()) {
            SampleResult r = createResult(MessageDescriptions.E_1338_SAMPLE_SUBSAMPLE_NO_SAMPLE_SPECIES, sample,
                                          sample.getID(getTrip()),
                                          sample.isSuperSample(),
                                          count(sample.getSampleSpecies()));
            results.add(r);
            return results;
        }
        boolean onlyOneSubSampling = !sample.isSuperSample() && checkIfHasOnlyOneSubSampling(sample);
        boolean manySubSampling = sample.isSuperSample() && checkIfHasManySubSampling(sample);
        if (!(onlyOneSubSampling || manySubSampling)) {
            SampleResult r = createResult(MessageDescriptions.E_1331_SAMPLE_SUBSAMPLE_FLAG, sample,
                                          sample.getID(getTrip()),
                                          sample.isSuperSample(),
                                          count(sample.getSampleSpecies())
            );
            results.add(r);
        }
        for (SampleSpecies ss : sample.getSampleSpecies()) {
            int count = count(sample.getSampleSpecies());
            if (count == 1 && ss.getSubSampleNumber() == 1) {
                SampleResult r = createResult(MessageDescriptions.E_1315_SUBSAMPLE_NUMBER_INCONSISTENCY, sample,
                                              sample.getID(getTrip()),
                                              ss.getSubSampleNumber(),
                                              ss.getSpecies().getFaoCode());
                results.add(r);
            }
        }
        return results;
    }
}
