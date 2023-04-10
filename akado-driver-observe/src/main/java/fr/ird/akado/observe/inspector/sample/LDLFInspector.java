package fr.ird.akado.observe.inspector.sample;

import com.google.auto.service.AutoService;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.SampleResult;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;
import fr.ird.driver.observe.business.data.ps.logbook.SampleSpecies;
import fr.ird.driver.observe.business.referential.common.SizeMeasureType;
import fr.ird.driver.observe.business.referential.common.Species;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveSampleInspector.class)
public class LDLFInspector extends ObserveSampleInspector {

    public static boolean ldlfSpeciesInconsistent(SampleSpecies sampleSpecies) {
        Species species = sampleSpecies.getSpecies();
        return (species.isSKJ() || species.isLTA() || species.isFRI())
                && sampleSpecies.isLd();
    }

    public static boolean ldlfP10(Sample s, SampleSpecies sampleSpecies) {
        return sampleSpecies.isLd() && s.getBigsWeight() == 0 && s.getTotalWeight() == 0;
    }

    public static boolean ldlfM10(Sample s, SampleSpecies sampleSpecies) {
        return sampleSpecies.isLf() && s.getSmallsWeight() == 0 && s.getTotalWeight() == 0;
    }

    public LDLFInspector() {
        this.description = "Check if the LDLF information for each sample species is consistent.";
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();
        Sample sample = get();

        for (SampleSpecies sampleSpecies : sample.getSampleSpecies()) {

            SizeMeasureType sizeMeasureType = sampleSpecies.getSizeMeasureType();
            Species species = sampleSpecies.getSpecies();
            if (LDLFInspector.ldlfSpeciesInconsistent(sampleSpecies)) {
                SampleResult r = createResult(MessageDescriptions.E1334_SAMPLE_LDLF_SPECIES_FORBIDDEN, sample,
                                              sample.getID(getTrip()),
                                              species.getCode(),
                                              sizeMeasureType.getCode());
                results.add(r);
            }

            if (AAProperties.isWarningInspectorEnabled()) {
                if (ldlfP10(sample, sampleSpecies)) {
                    SampleResult r = createResult(MessageDescriptions.W1332_SAMPLE_LDLF_P10, sample,
                                                  sample.getID(getTrip()),
                                                  sizeMeasureType.getCode(),
                                                  sample.getBigsWeight(),
                                                  sample.getTotalWeight());
                    results.add(r);
                }
                if (ldlfM10(sample, sampleSpecies)) {
                    SampleResult r = createResult(MessageDescriptions.W1333_SAMPLE_LDLF_M10, sample,
                                                  sample.getID(getTrip()),
                                                  sizeMeasureType.getCode(),
                                                  sample.getSmallsWeight(),
                                                  sample.getTotalWeight());
                    results.add(r);
                }
            }
        }
        return results;
    }
}
