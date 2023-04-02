package fr.ird.akado.observe.inspector.sample;

import com.google.auto.service.AutoService;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.observe.Constant;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.SampleResult;
import fr.ird.common.message.Message;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;
import fr.ird.driver.observe.business.data.ps.logbook.SampleSpecies;
import fr.ird.driver.observe.business.referential.common.SizeMeasureType;
import fr.ird.driver.observe.business.referential.common.Species;

import java.util.Objects;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveSampleInspector.class)
public class LDLFInspector extends ObserveSampleInspector {

    public LDLFInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the LDLF information for each sample species is consistent.";
    }

    public static boolean ldlfSpeciesInconsistent(Species species, SizeMeasureType ldlf) {
        return (Objects.equals(species.getCode(), "2")
                || Objects.equals(species.getCode(), "5")
                || Objects.equals(species.getCode(), "6"))
                && Objects.equals(ldlf.getTopiaId(), SampleSpecies.SAMPLE_LENGTH_CLASS_FOR_DORSAL);
    }

    public static boolean ldlfP10(Sample s, SampleSpecies sampleSpecies) {
        SizeMeasureType sizeMeasureType = sampleSpecies.getSizeMeasureType();
        return (Objects.equals(sizeMeasureType.getTopiaId(), SampleSpecies.SAMPLE_LENGTH_CLASS_FOR_DORSAL) || Objects.equals(sizeMeasureType.getTopiaId(), SampleSpecies.SAMPLE_LENGTH_CLASS_FOR_DORSAL_ONE_CENTIMER_FREQUENCY))
                && s.getBigsWeight() == 0 && s.getTotalWeight() == 0;
    }

    public static boolean ldlfM10(Sample s, SampleSpecies sampleSpecies) {
        SizeMeasureType sizeMeasureType = sampleSpecies.getSizeMeasureType();
        return Objects.equals(sizeMeasureType.getTopiaId(), SampleSpecies.SAMPLE_LENGTH_CLASS_FOR_FORK)
                && s.getSmallsWeight() == 0 && s.getTotalWeight() == 0;
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();
        Sample s = get();

        for (SampleSpecies sampleSpecies : s.getSampleSpecies()) {

            SizeMeasureType sizeMeasureType = sampleSpecies.getSizeMeasureType();
            Species species = sampleSpecies.getSpecies();
            if (LDLFInspector.ldlfSpeciesInconsistent(species, sizeMeasureType)) {
                SampleResult r = createResult(s, Message.ERROR, Constant.CODE_SAMPLE_LDLF_SPECIES_FORBIDDEN, Constant.LABEL_SAMPLE_LDLF_SPECIES_FORBIDDEN, true,
                                              s.getTopiaId(),
                                              species.getCode(),
                                              sizeMeasureType.getCode());
                results.add(r);
            }

            if (!AAProperties.WARNING_INSPECTOR.equals(AAProperties.DISABLE_VALUE)) {
                if (ldlfP10(s, sampleSpecies)) {
                    SampleResult r = createResult(s, Message.WARNING, Constant.CODE_SAMPLE_LDLF_P10, Constant.LABEL_SAMPLE_LDLF_P10, true,
                                                  s.getTopiaId(),
                                                  sizeMeasureType.getCode(),
                                                  s.getBigsWeight(),
                                                  s.getTotalWeight());
                    results.add(r);
                }
                if (ldlfM10(s, sampleSpecies)) {
                    SampleResult r = createResult(s, Message.WARNING, Constant.CODE_SAMPLE_LDLF_M10, Constant.LABEL_SAMPLE_LDLF_M10, true,
                                                  s.getTopiaId(),
                                                  sizeMeasureType.getCode(),
                                                  s.getSmallsWeight(),
                                                  s.getTotalWeight());
                    results.add(r);
                }
            }
        }
        return results;
    }
}
