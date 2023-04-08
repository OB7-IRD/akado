package fr.ird.akado.observe.inspector.sample;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.SampleResult;
import fr.ird.driver.observe.business.data.ps.common.Trip;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;
import fr.ird.driver.observe.business.data.ps.logbook.Well;
import fr.ird.driver.observe.business.data.ps.logbook.WellActivity;
import fr.ird.driver.observe.business.data.ps.logbook.WellActivitySpecies;

import java.util.Objects;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveSampleInspector.class)
public class DistributionInspector extends ObserveSampleInspector {

    public static boolean distributionIsInconsistent(Trip trip, Sample s) {
        Float m10Weight = 0f;
        Float p10Weight = 0f;
        String wellId = s.getWell();
        for (Well well : trip.getWell()) {
            if (Objects.equals(well.getWell(), wellId)) {
                for (WellActivity wellActivity : well.getWellActivity()) {
                    for (WellActivitySpecies wellActivitySpecies : wellActivity.getWellActivitySpecies()) {
                        if (wellActivitySpecies.isWeightCategoryMinus10()) {
                            m10Weight += wellActivitySpecies.getWeight();
                        }
                        if (wellActivitySpecies.isWeightCategoryUnknown() && wellActivitySpecies.getSpecies().isSKJ()) {
                            m10Weight += wellActivitySpecies.getWeight();
                        }
                        if (wellActivitySpecies.isWeightCategoryPlus10()) {
                            p10Weight += wellActivitySpecies.getWeight();
                        }
                    }
                }
            }
        }
        return !m10Weight.equals(s.getSmallsWeight()) || !p10Weight.equals(s.getBigsWeight());
    }

    public DistributionInspector() {
        this.description = "Compare la somme des +10/-10 saisie dans les plans de cuve avec celle saisie dans l'échantillon.";
    }

    @Override
    public Results execute() throws Exception {
        Sample sample = get();
        if (distributionIsInconsistent(getTrip(), sample)) {
            SampleResult r = createResult(MessageDescriptions.E_1335_SAMPLE_DISTRIBUTION_M10_P10, sample,
                                          sample.getID(getTrip()));
            return Results.of(r);
        }
        return null;
    }
}
