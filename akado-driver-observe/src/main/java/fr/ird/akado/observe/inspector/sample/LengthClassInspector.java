package fr.ird.akado.observe.inspector.sample;

import com.google.auto.service.AutoService;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.SampleResult;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;
import fr.ird.driver.observe.business.data.ps.logbook.SampleSpecies;
import fr.ird.driver.observe.business.data.ps.logbook.SampleSpeciesMeasure;
import fr.ird.driver.observe.business.referential.common.Species;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveSampleInspector.class)
public class LengthClassInspector extends ObserveSampleInspector {

    public static final int LENGTH_CLASS_YFT = 90;
    public static final int LENGTH_CLASS_BET = 90;
    public static final int LENGTH_CLASS_ALB = 42;

    public static boolean lengthClassLimits(Species species, SampleSpeciesMeasure sampleSpeciesFrequency) {
        return (species.isYFT() && sampleSpeciesFrequency.getSizeClass() > LENGTH_CLASS_YFT)
                || (species.isBET() && sampleSpeciesFrequency.getSizeClass() > LENGTH_CLASS_BET)
                || (species.isALB() && sampleSpeciesFrequency.getSizeClass() > LENGTH_CLASS_ALB);
    }

    public LengthClassInspector() {
        this.description = "Check if the length class is consistent with each "
                + "length class of species (L=90cm for YFT and BET and L=42cm for ALB) and LD1.";
    }

    @Override
    public Results execute() throws Exception {
        if (!AAProperties.isWarningInspectorEnabled()) {
            return null;
        }
        Sample sample = get();
        Results results = new Results();
        for (SampleSpecies sampleSpecies : sample.getSampleSpecies()) {
            if (sampleSpecies.isLd()) {
                Species species = sampleSpecies.getSpecies();
                for (SampleSpeciesMeasure sampleSpeciesMeasure : sampleSpecies.getSampleSpeciesMeasure()) {
                    if (lengthClassLimits(species, sampleSpeciesMeasure)) {
                        SampleResult r = createResult(MessageDescriptions.W_1329_SAMPLE_LENGTH_CLASS, sample,
                                                      sample.getID(getTrip()),
                                                      species.getCode(),
                                                      sampleSpeciesMeasure.getSizeClass());

                        results.add(r);
                    }
                }
            }
        }
        return results;
    }
}
