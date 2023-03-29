package fr.ird.akado.observe.inspector.sample;

import com.google.auto.service.AutoService;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.observe.Constant;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.SampleResult;
import fr.ird.common.message.Message;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;
import fr.ird.driver.observe.business.data.ps.logbook.SampleSpecies;
import fr.ird.driver.observe.business.data.ps.logbook.SampleSpeciesMeasure;

import java.util.Objects;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveSampleInspector.class)
public class LittleBigInspector extends ObserveSampleInspector {

    public static double THRESHOLD = 0.9;

    public static double LD1_YFT = 24d;
    public static double LF_YFT = 80d;
    public static double LD1_BET = 25d;
    public static double LF_BET = 77d;
    public static double LD1_ALB = 23.5;
    public static double LF_ALB = 78d;

    public static double littleFish(Sample s) {
        double eff = 0d;
        double little = 0d;
        for (SampleSpecies sampleSpecies : s.getSampleSpecies()) {
            for (SampleSpeciesMeasure sampleSpeciesMeasure : sampleSpecies.getSampleSpeciesMeasure()) {
                eff += sampleSpeciesMeasure.getCount();
                if (Objects.equals(sampleSpecies.getSpecies().getCode(), "1")) {
                    if ((sampleSpeciesMeasure.getSizeClass() < LD1_YFT && sampleSpecies.isLd())
                            || (sampleSpeciesMeasure.getSizeClass() < LF_YFT && sampleSpecies.isLf())) {
                        little += sampleSpeciesMeasure.getCount();
                    }
                } else if (Objects.equals(sampleSpecies.getSpecies().getCode(), "3")) {
                    if ((sampleSpeciesMeasure.getSizeClass() < LD1_BET && sampleSpecies.isLd())
                            || (sampleSpeciesMeasure.getSizeClass() < LF_BET && sampleSpecies.isLf())) {
                        little += sampleSpeciesMeasure.getCount();
                    }
                } else if (Objects.equals(sampleSpecies.getSpecies().getCode(), "4")) {
                    if ((sampleSpeciesMeasure.getSizeClass() < LD1_ALB && sampleSpecies.isLd())
                            || (sampleSpeciesMeasure.getSizeClass() < LF_ALB && sampleSpecies.isLf())) {
                        little += sampleSpeciesMeasure.getCount();
                    }
                } else {
                    little += sampleSpeciesMeasure.getCount();
                }

            }
        }
        return little / eff;
    }

    public static double bigFish(Sample s) {
        double eff = 0d;
        double big = 0d;
        for (SampleSpecies sampleSpecies : s.getSampleSpecies()) {
            for (SampleSpeciesMeasure sampleSpeciesMeasure : sampleSpecies.getSampleSpeciesMeasure()) {
                eff += sampleSpeciesMeasure.getCount();
                switch (sampleSpecies.getSpecies().getCode()) {
                    case "1":
                        if ((sampleSpeciesMeasure.getSizeClass() >= LD1_YFT && sampleSpecies.isLd())
                                || (sampleSpeciesMeasure.getSizeClass() >= LF_YFT && sampleSpecies.isLf())) {
                            big += sampleSpeciesMeasure.getCount();
                        }
                        break;
                    case "3":
                        if ((sampleSpeciesMeasure.getSizeClass() >= LD1_BET && sampleSpecies.isLd())
                                || (sampleSpeciesMeasure.getSizeClass() >= LF_BET && sampleSpecies.isLf())) {
                            big += sampleSpeciesMeasure.getCount();
                        }
                        break;
                    case "4":
                        if ((sampleSpeciesMeasure.getSizeClass() >= LD1_ALB && sampleSpecies.isLd())
                                || (sampleSpeciesMeasure.getSizeClass() >= LF_ALB && sampleSpecies.isLf())) {
                            big += sampleSpeciesMeasure.getCount();
                        }
                        break;
                    default:
                        break;
                }

            }
        }
        return big / eff;
    }

    public static double lfMeasure(Sample s) {
        double eff = 0d;
        double lf = 0d;
        for (SampleSpecies sampleSpecies : s.getSampleSpecies()) {
            for (SampleSpeciesMeasure sampleSpeciesMeasure : sampleSpecies.getSampleSpeciesMeasure()) {
                eff += sampleSpeciesMeasure.getCount();
                if (sampleSpecies.isLf()) {
                    lf += sampleSpeciesMeasure.getCount();
                }
            }
        }
        return lf / eff;
    }

    public static double ld1Measure(Sample s) {
        double eff = 0d;
        double ld1 = 0d;
        for (SampleSpecies sampleSpecies : s.getSampleSpecies()) {
            for (SampleSpeciesMeasure sampleSpeciesMeasure : sampleSpecies.getSampleSpeciesMeasure()) {
                eff += sampleSpeciesMeasure.getCount();
                if (sampleSpecies.isLd()) {
                    ld1 += sampleSpeciesMeasure.getCount();
                }
            }
        }
        return ld1 / eff;

    }

    public static boolean littleIsInfThreshold(Sample s) {
        return littleIsInfThreshold(s, littleFish(s), lfMeasure(s), ld1Measure(s));
    }

    public static boolean bigIsInfThreshold(Sample s) {
        return bigIsInfThreshold(s, bigFish(s), lfMeasure(s), ld1Measure(s));
    }

    private static boolean littleIsInfThreshold(Sample s, double little, double lf, double ld1) {
        return (s.getSmallsWeight() != null && s.getSmallsWeight() > 0 && (s.getBigsWeight() == null || s.getBigsWeight() == 0) && little < THRESHOLD)
                || (((s.getTotalWeight() != null && s.getTotalWeight() != 0) || (s.getSmallsWeight() != null && s.getSmallsWeight() > 0 && s.getBigsWeight() != null && s.getBigsWeight() > 0)) && lf > ld1 && little < THRESHOLD);
    }

    private static boolean bigIsInfThreshold(Sample s, double big, double lf, double ld1) {
        return ((s.getSmallsWeight() == null || s.getSmallsWeight() == 0) && s.getBigsWeight()!=null && s.getBigsWeight() > 0 && big < THRESHOLD)
                || (((s.getTotalWeight() != null && s.getTotalWeight() != 0) || (s.getSmallsWeight() != null && s.getSmallsWeight() > 0 && s.getBigsWeight() != null && s.getBigsWeight() > 0)) && lf < ld1 && big < THRESHOLD);
    }

    public LittleBigInspector() {
        this.name = this.getClass().getName();
        this.description = "Check if the percentage of little and big fish sampled is consistent.";
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();
        if (AAProperties.WARNING_INSPECTOR.equals(AAProperties.DISABLE_VALUE)) {
            return results;
        }
        Sample s = get();
        double little = littleFish(s);
        double big = bigFish(s);
        double ld1 = ld1Measure(s);
        double lf = lfMeasure(s);
        if (littleIsInfThreshold(s, little, lf, ld1)) {
            SampleResult r = createResult(s, Message.WARNING, Constant.CODE_SAMPLE_LITTLE_INF_THRESHOLD, Constant.LABEL_SAMPLE_LITTLE_INF_THRESHOLD, true,
                                          s.getTopiaId(),
                                          little,
                                          THRESHOLD,
                                          lf,
                                          ld1);
            results.add(r);
        }
        if (bigIsInfThreshold(s, big, lf, ld1)) {
            SampleResult r = createResult(s, Message.WARNING, Constant.CODE_SAMPLE_BIG_INF_THRESHOLD, Constant.LABEL_SAMPLE_BIG_INF_THRESHOLD, true,
                                          s.getTopiaId(),
                                          big,
                                          THRESHOLD,
                                          lf,
                                          ld1);
            results.add(r);
        }
        return results;
    }
}
