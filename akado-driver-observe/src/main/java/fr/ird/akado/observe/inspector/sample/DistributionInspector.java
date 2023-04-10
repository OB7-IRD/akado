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

    public static double wellM10Weight(Trip trip, Sample sample) {
        double m10Weight = 0d;
        String wellId = sample.getWell();
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
                    }
                }
            }
        }
        return m10Weight;
    }

    public static double wellP10Weight(Trip trip, Sample sample) {
        double p10Weight = 0d;
        String wellId = sample.getWell();
        for (Well well : trip.getWell()) {
            if (Objects.equals(well.getWell(), wellId)) {
                for (WellActivity wellActivity : well.getWellActivity()) {
                    for (WellActivitySpecies wellActivitySpecies : wellActivity.getWellActivitySpecies()) {
                        if (wellActivitySpecies.isWeightCategoryPlus10()) {
                            p10Weight += wellActivitySpecies.getWeight();
                        }
                    }
                }
            }
        }
        return p10Weight;
    }

    public static boolean distributionIsInconsistent(Trip trip, Sample sample) {
        double wellM10Weight = wellM10Weight(trip, sample);
        double wellP10Weight = wellP10Weight(trip, sample);
        double smallsWeight = sample.getSmallsWeight();
        double bigsWeight = sample.getBigsWeight();
        return !equals(wellM10Weight, smallsWeight) || !equals(wellP10Weight, bigsWeight);
    }

    public DistributionInspector() {
        this.description = "Compare la somme des +10/-10 saisie dans les plans de cuve avec celle saisie dans l'échantillon.";
    }

    @Override
    public Results execute() throws Exception {
        Sample sample = get();
        double wellM10Weight = wellM10Weight(getTrip(), sample);
        double wellP10Weight = wellP10Weight(getTrip(), sample);
        double smallsWeight = sample.getSmallsWeight();
        double bigsWeight = sample.getBigsWeight();
        if (!equals(wellM10Weight, smallsWeight) || !equals(wellP10Weight, bigsWeight)) {
            SampleResult r = createResult(MessageDescriptions.E1335_SAMPLE_DISTRIBUTION_M10_P10, sample,
                                          sample.getID(getTrip()),
                                          smallsWeight,
                                          bigsWeight,
                                          sample.getWell(),
                                          wellM10Weight,
                                          wellP10Weight);
            return Results.of(r);
        }
        return null;
    }


}
