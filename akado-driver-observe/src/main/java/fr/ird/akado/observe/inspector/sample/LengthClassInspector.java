package fr.ird.akado.observe.inspector.sample;

import com.google.auto.service.AutoService;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.SampleResult;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;
import fr.ird.driver.observe.business.data.ps.logbook.SampleSpecies;
import fr.ird.driver.observe.business.data.ps.logbook.SampleSpeciesMeasure;
import fr.ird.driver.observe.business.referential.common.SizeMeasureType;
import fr.ird.driver.observe.business.referential.common.Species;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveSampleInspector.class)
public class LengthClassInspector extends ObserveSampleInspector {

    /**
     * Defines all error/warning bounds for a type of measure for a species.
     */
    static class LengthClassBound {
        final int minError;
        final int minWarning;
        final int maxWarning;
        final int maxError;

        public LengthClassBound(int minError, int minWarning, int maxWarning, int maxError) {
            this.minError = minError;
            this.minWarning = minWarning;
            this.maxWarning = maxWarning;
            this.maxError = maxError;
        }

        public boolean isError(double lengthClass) {
            return lengthClass < minError || lengthClass > maxError;
        }

        public boolean isWarning(double lengthClass) {
            return lengthClass < minWarning || lengthClass > maxWarning;
        }
    }

    public static final LengthClassBound ALB_FL = new LengthClassBound(15, 30, 80, 120);
    public static final LengthClassBound BET_FL = new LengthClassBound(15, 20, 80, 190);
    public static final LengthClassBound FRI_FL = new LengthClassBound(15, 20, 80, 90);
    public static final LengthClassBound FRZ_FL = new LengthClassBound(15, 30, 45, 50);
    public static final LengthClassBound KAW_FL = new LengthClassBound(15, 22, 80, 90);
    public static final LengthClassBound LTA_FL = new LengthClassBound(15, 25, 75, 90);
    public static final LengthClassBound SKJ_FL = new LengthClassBound(15, 18, 80, 110);
    public static final LengthClassBound YFT_FL = new LengthClassBound(15, 18, 80, 200);
    public static final LengthClassBound ALB_PD1 = new LengthClassBound(15, 20, 45, 50);
    public static final LengthClassBound BET_PD1 = new LengthClassBound(10, 15, 60, 65);
    public static final LengthClassBound SKJ_PD1 = new LengthClassBound(15, 20, 30, 35);
    public static final LengthClassBound YFT_PD1 = new LengthClassBound(8, 10, 60, 70);
    public static final LengthClassBound OTHER_LF = new LengthClassBound(5, 10, 80, 400);
    public static final LengthClassBound OTHER_PD1 = new LengthClassBound(5, 10, 70, 100);
    public static final int LENGTH_CLASS_YFT = 90;
    public static final int LENGTH_CLASS_BET = 90;
    public static final int LENGTH_CLASS_ALB = 42;

    public static LengthClassBound getLengthClassBound(SampleSpecies sampleSpecies) {

        Species species = sampleSpecies.getSpecies();
        if (species.isALB()) {
            if (sampleSpecies.isLf()) {
                return ALB_FL;
            }
            if (sampleSpecies.isLd()) {
                return ALB_PD1;
            }
            return null;
        }
        if (species.isBET()) {
            if (sampleSpecies.isLf()) {
                return BET_FL;
            }
            if (sampleSpecies.isLd()) {
                return BET_PD1;
            }
            return null;
        }
        if (species.isSKJ()) {
            if (sampleSpecies.isLf()) {
                return SKJ_FL;
            }
            if (sampleSpecies.isLd()) {
                return SKJ_PD1;
            }
            return null;
        }
        if (species.isYFT()) {
            if (sampleSpecies.isLf()) {
                return YFT_FL;
            }
            if (sampleSpecies.isLd()) {
                return YFT_PD1;
            }
            return null;
        }
        if (species.isFRI()) {
            if (sampleSpecies.isLf()) {
                return FRI_FL;
            }
            return null;
        }
        if (species.isFRZ()) {
            if (sampleSpecies.isLf()) {
                return FRZ_FL;
            }
            return null;
        }
        if (species.isKAW()) {
            if (sampleSpecies.isLf()) {
                return KAW_FL;
            }
            return null;
        }
        if (species.isLTA()) {
            if (sampleSpecies.isLf()) {
                return LTA_FL;
            }
            return null;
        }
        if (sampleSpecies.isLf()) {
            return OTHER_LF;
        }
        if (sampleSpecies.isLd()) {
            return OTHER_PD1;
        }
        return null;
    }

    public static boolean lengthClassLimits(Species species, SampleSpeciesMeasure sampleSpeciesFrequency) {
        return (species.isYFT() && sampleSpeciesFrequency.getSizeClass() > LENGTH_CLASS_YFT)
                || (species.isBET() && sampleSpeciesFrequency.getSizeClass() > LENGTH_CLASS_BET)
                || (species.isALB() && sampleSpeciesFrequency.getSizeClass() > LENGTH_CLASS_ALB);
    }

    public LengthClassInspector() {
        this.description = "Check if the length class is consistent for all sample species measures.";
    }

    @Override
    public Results execute() throws Exception {
        if (!AAProperties.isWarningInspectorEnabled()) {
            return null;
        }
        Sample sample = get();
        Results results = new Results();
        for (SampleSpecies sampleSpecies : sample.getSampleSpecies()) {
            LengthClassBound lengthClassBound = getLengthClassBound(sampleSpecies);
            Species species = sampleSpecies.getSpecies();
            SizeMeasureType sizeMeasureType = sampleSpecies.getSizeMeasureType();
            if (lengthClassBound == null) {
                SampleResult r = createResult(MessageDescriptions.E1338_SAMPLE_LENGTH_CLASS_UNDEFINED, sample,
                                              sample.getID(getTrip()),
                                              species.getCode(),
                                              sizeMeasureType.getLabel2());
                results.add(r);
                continue;
            }
            for (SampleSpeciesMeasure sampleSpeciesMeasure : sampleSpecies.getSampleSpeciesMeasure()) {
                checkLengthClassBound(results,
                                      sample,
                                      species,
                                      sizeMeasureType,
                                      lengthClassBound,
                                      sampleSpeciesMeasure.getSizeClass());
            }
        }
        return results;
    }

    private void checkLengthClassBound(Results results,
                                       Sample sample,
                                       Species species,
                                       SizeMeasureType sizeMeasureType,
                                       LengthClassBound lengthClassBound,
                                       double sizeClass) {
        if (lengthClassBound.isError(sizeClass)) {
            // Size class < min error or > max error
            results.add(createResult(MessageDescriptions.E1339_SAMPLE_LENGTH_CLASS, sample,
                                     sample.getID(getTrip()),
                                     species.getCode(),
                                     sizeMeasureType.getLabel2(),
                                     sizeClass,
                                     lengthClassBound.minError,
                                     lengthClassBound.maxError
                                    ));
            return;
        }
        if (lengthClassBound.isWarning(sizeClass)) {
            // Size class < min warning or > max warning
            results.add(createResult(MessageDescriptions.W1340_SAMPLE_LENGTH_CLASS, sample,
                                     sample.getID(getTrip()),
                                     species.getCode(),
                                     sizeMeasureType.getLabel2(),
                                     sizeClass,
                                     lengthClassBound.minWarning,
                                     lengthClassBound.maxWarning
                                    ));
        }
    }
}
