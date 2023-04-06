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

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveSampleInspector.class)
public class SampleWithoutSpeciesInspector extends ObserveSampleInspector {

    public SampleWithoutSpeciesInspector() {
        this.description = "Check if the sample has at least one specie.";
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();
        Sample sample = get();
        if (sample.getSampleSpecies().isEmpty()) {
            SampleResult r = createResult(MessageDescriptions.E_1311_SAMPLE_NO_SAMPLE_SPECIES, sample,
                                          sample.getID(getTrip()));
            results.add(r);
        }
        return results;
    }

}
