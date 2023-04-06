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
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.SampleResult;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;
import fr.ird.driver.observe.business.data.ps.logbook.SampleSpecies;
import fr.ird.driver.observe.business.data.ps.logbook.SampleSpeciesMeasure;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveSampleInspector.class)
public class MeasureInspector extends ObserveSampleInspector {

    public static double sampleSpeciesMeasuredCount(Sample s) {
        double sampleSpeciesMeasuredCount = 0;
        for (SampleSpecies sampleSpecies : s.getSampleSpecies()) {
            sampleSpeciesMeasuredCount += sampleSpecies.getMeasuredCount();
        }
        return sampleSpeciesMeasuredCount;
    }

    public static double sampleSpeciesFrequencyCount(Sample s) {
        double sampleSpeciesFrequencyCount = 0;
        for (SampleSpecies sampleSpecies : s.getSampleSpecies()) {
            for (SampleSpeciesMeasure sampleSpeciesFrequency : sampleSpecies.getSampleSpeciesMeasure()) {
                sampleSpeciesFrequencyCount += sampleSpeciesFrequency.getCount();
            }
        }
        return sampleSpeciesFrequencyCount;
    }

    public MeasureInspector() {
        this.description = "Check if the sample species number is consistent with the measure number.";
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();
        Sample sample = get();
        int sampleSpeciesFrequencyCount = 0;
        int sampleSpeciesMeasuredCount = 0;
        for (SampleSpecies sampleSpecies : sample.getSampleSpecies()) {
            sampleSpeciesMeasuredCount += sampleSpecies.getMeasuredCount();
            for (SampleSpeciesMeasure sampleSpeciesFrequency : sampleSpecies.getSampleSpeciesMeasure()) {
                sampleSpeciesFrequencyCount += sampleSpeciesFrequency.getCount();
            }
        }

        if (sampleSpeciesFrequencyCount != sampleSpeciesMeasuredCount) {
            SampleResult r = createResult(MessageDescriptions.E_1323_SAMPLE_MEASURE_COUNT, sample,
                                          sample.getID(getTrip()),
                                          sampleSpeciesMeasuredCount,
                                          sampleSpeciesFrequencyCount);
            results.add(r);
        }

        return results;
    }
}
