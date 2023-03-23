/*
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
package fr.ird.akado.avdth.sample;

import fr.ird.akado.avdth.common.AAProperties;
import static fr.ird.akado.avdth.Constant.CODE_SAMPLE_BIG_INF_THRESHOLD;
import static fr.ird.akado.avdth.Constant.CODE_SAMPLE_LITTLE_INF_THRESHOLD;
import static fr.ird.akado.avdth.Constant.LABEL_SAMPLE_BIG_INF_THRESHOLD;
import static fr.ird.akado.avdth.Constant.LABEL_SAMPLE_LITTLE_INF_THRESHOLD;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.result.SampleResult;
import fr.ird.akado.core.Inspector;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Sample;
import fr.ird.driver.avdth.business.SampleSpecies;
import fr.ird.driver.avdth.business.SampleSpeciesFrequency;
import java.util.ArrayList;

/**
 * Check if the percentage of little and big fish sampled is consistent.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 18 juin 2014
 */
public class LittleBigInspector extends Inspector<Sample> {

    public static double THRESHOLD = 0.9;

    public static double LD1_YFT = 24d;
    public static double LF_YFT = 80d;
    public static double LD1_BET = 25d;
    public static double LF_BET = 77d;
    public static double LD1_ALB = 23.5;
    public static double LF_ALB = 78d;

    public LittleBigInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the percentage of little and big fish sampled is consistent.";
    }

    public static double littleFish(Sample s) {
        double eff = 0d;
        double little = 0d;
        for (SampleSpecies sampleSpecies : s.getSampleSpecies()) {
            for (SampleSpeciesFrequency sampleSpeciesFrequency : sampleSpecies.getSampleSpeciesFrequencys()) {
                eff += sampleSpeciesFrequency.getNumber();
                if (sampleSpecies.getSpecies().getCode() == 1) {
                    if ((sampleSpeciesFrequency.getLengthClass() < LD1_YFT && sampleSpecies.getLdlf() == 1)
                            || (sampleSpeciesFrequency.getLengthClass() < LF_YFT && sampleSpecies.getLdlf() == 2)) {
                        little += sampleSpeciesFrequency.getNumber();
                    }
                } else if (sampleSpecies.getSpecies().getCode() == 3) {
                    if ((sampleSpeciesFrequency.getLengthClass() < LD1_BET && sampleSpecies.getLdlf() == 1)
                            || (sampleSpeciesFrequency.getLengthClass() < LF_BET && sampleSpecies.getLdlf() == 2)) {
                        little += sampleSpeciesFrequency.getNumber();
                    }
                } else if (sampleSpecies.getSpecies().getCode() == 4) {
                    if ((sampleSpeciesFrequency.getLengthClass() < LD1_ALB && sampleSpecies.getLdlf() == 1)
                            || (sampleSpeciesFrequency.getLengthClass() < LF_ALB && sampleSpecies.getLdlf() == 2)) {
                        little += sampleSpeciesFrequency.getNumber();
                    }
                } else {
                    little += sampleSpeciesFrequency.getNumber();
                }

            }
        }
        return little / eff;
    }

    public static double bigFish(Sample s) {
        double eff = 0d;
        double big = 0d;
        for (SampleSpecies sampleSpecies : s.getSampleSpecies()) {
            for (SampleSpeciesFrequency sampleSpeciesFrequency : sampleSpecies.getSampleSpeciesFrequencys()) {
                eff += sampleSpeciesFrequency.getNumber();
                switch (sampleSpecies.getSpecies().getCode()) {
                    case 1:
                        if ((sampleSpeciesFrequency.getLengthClass() >= LD1_YFT && sampleSpecies.getLdlf() == 1)
                                || (sampleSpeciesFrequency.getLengthClass() >= LF_YFT && sampleSpecies.getLdlf() == 2)) {
                            big += sampleSpeciesFrequency.getNumber();
                        }
                        break;
                    case 3:
                        if ((sampleSpeciesFrequency.getLengthClass() >= LD1_BET && sampleSpecies.getLdlf() == 1)
                                || (sampleSpeciesFrequency.getLengthClass() >= LF_BET && sampleSpecies.getLdlf() == 2)) {
                            big += sampleSpeciesFrequency.getNumber();
                        }
                        break;
                    case 4:
                        if ((sampleSpeciesFrequency.getLengthClass() >= LD1_ALB && sampleSpecies.getLdlf() == 1)
                                || (sampleSpeciesFrequency.getLengthClass() >= LF_ALB && sampleSpecies.getLdlf() == 2)) {
                            big += sampleSpeciesFrequency.getNumber();
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
            for (SampleSpeciesFrequency sampleSpeciesFrequency : sampleSpecies.getSampleSpeciesFrequencys()) {
                eff += sampleSpeciesFrequency.getNumber();
                if (sampleSpecies.getLdlf() == 2) {
                    lf += sampleSpeciesFrequency.getNumber();
                }
            }
        }
        return lf / eff;
    }

    public static double ld1Measure(Sample s) {
        double eff = 0d;
        double ld1 = 0d;
        for (SampleSpecies sampleSpecies : s.getSampleSpecies()) {
            for (SampleSpeciesFrequency sampleSpeciesFrequency : sampleSpecies.getSampleSpeciesFrequencys()) {
                eff += sampleSpeciesFrequency.getNumber();
                if (sampleSpecies.getLdlf() == 1) {
                    ld1 += sampleSpeciesFrequency.getNumber();
                }
            }
        }
        return ld1 / eff;

    }

    @Override
    public Results execute() {
        Results results = new Results();
        if (AAProperties.WARNING_INSPECTOR.equals(AAProperties.DISABLE_VALUE)) {
            return results;
        }
        Sample s = get();
        double little = littleFish(s);
        double big = bigFish(s);
        double ld1 = ld1Measure(s);
        double lf = lfMeasure(s);

        SampleResult r;
        if (littleIsInfThreshold(s, little, lf, ld1)) {
            r = new SampleResult();
            r.set(s);
            r.setMessageType(Message.WARNING);
            r.setMessageCode(CODE_SAMPLE_LITTLE_INF_THRESHOLD);
            r.setMessageLabel(LABEL_SAMPLE_LITTLE_INF_THRESHOLD);

            ArrayList parameters = new ArrayList<>();
            parameters.add(s.getID());
            parameters.add(little);
            parameters.add(THRESHOLD);
            parameters.add(lf);
            parameters.add(ld1);
            r.setMessageParameters(parameters);
            r.setInconsistent(true);

            results.add(r);
        }
        if (bigIsInfThreshold(s, big, lf, ld1)) {
            r = new SampleResult();
            r.set(s);
            r.setMessageType(Message.WARNING);
            r.setMessageCode(CODE_SAMPLE_BIG_INF_THRESHOLD);
            r.setMessageLabel(LABEL_SAMPLE_BIG_INF_THRESHOLD);

            ArrayList parameters = new ArrayList<>();
            parameters.add(s.getID());
            parameters.add(big);
            parameters.add(THRESHOLD);
            parameters.add(lf);
            parameters.add(ld1);
            r.setMessageParameters(parameters);
            r.setInconsistent(true);

            results.add(r);
        }
        return results;
    }

    public static boolean littleIsInfThreshold(Sample s) {
        return littleIsInfThreshold(s, littleFish(s), lfMeasure(s), ld1Measure(s));
    }

    public static boolean bigIsInfThreshold(Sample s) {
        return bigIsInfThreshold(s, bigFish(s), lfMeasure(s), ld1Measure(s));
    }

    private static boolean littleIsInfThreshold(Sample s, double little, double lf, double ld1) {
        return (s.getMinus10Weight() > 0 && s.getPlus10Weight() == 0 && little < THRESHOLD)
                || ((s.getGlobalWeight() != 0 || (s.getMinus10Weight() > 0 && s.getPlus10Weight() > 0)) && lf > ld1 && little < THRESHOLD);
    }

    private static boolean bigIsInfThreshold(Sample s, double big, double lf, double ld1) {
        return (s.getMinus10Weight() == 0 && s.getPlus10Weight() > 0 && big < THRESHOLD)
                || ((s.getGlobalWeight() != 0 || (s.getMinus10Weight() > 0 && s.getPlus10Weight() > 0)) && lf < ld1 && big < THRESHOLD);
    }

}
