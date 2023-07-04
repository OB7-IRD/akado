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
package fr.ird.akado.avdth.sample;

import static fr.ird.akado.avdth.Constant.CODE_SAMPLE_SUBSAMPLE_FLAG;
import static fr.ird.akado.avdth.Constant.CODE_SAMPLE_SUBSAMPLE_NO_SAMPLE_SPECIES;
import static fr.ird.akado.avdth.Constant.CODE_SUBSAMPLE_NUMBER_INCONSISTENCY;
import static fr.ird.akado.avdth.Constant.LABEL_SAMPLE_SUBSAMPLE_FLAG;
import static fr.ird.akado.avdth.Constant.LABEL_SAMPLE_SUBSAMPLE_NO_SAMPLE_SPECIES;
import static fr.ird.akado.avdth.Constant.LABEL_SUBSAMPLE_NUMBER_INCONSISTENCY;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.avdth.result.SampleResult;
import fr.ird.akado.core.Inspector;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Sample;
import fr.ird.driver.avdth.business.SampleSpecies;
import java.util.ArrayList;
import java.util.List;

/**
 * Check if the sub-sample number is consistent.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 18 juin 2014
 *
 *
 *
 *
 */
public class SuperSampleNumberConsistentInspector extends Inspector<Sample> {

    public SuperSampleNumberConsistentInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the sub-sample number is consistent.";
    }

    public static int countSubSampling(Sample s) {

        List<Integer> subSampleNumber = new ArrayList();
        for (SampleSpecies ss : s.getSampleSpecies()) {
            if (!subSampleNumber.contains(ss.getSubSampleNumber())) {
                subSampleNumber.add(ss.getSubSampleNumber());
            }
        }
        return subSampleNumber.size();
    }

    public static boolean checkIfHasManySubSampling(Sample s) {

        for (SampleSpecies ss : s.getSampleSpecies()) {
            if (ss.getSubSampleNumber() == 0) {
                return false;
            }
        }
        return count(s.getSampleSpecies()) > 1;
    }

    public static boolean checkIfHasOnlyOneSubSampling(Sample s) {
        for (SampleSpecies ss : s.getSampleSpecies()) {
            if (ss.getSubSampleNumber() != 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Sample s = get();
        SampleResult r;

        boolean onlyOneSubSampling = s.getSuperSampleFlag() == 0 && checkIfHasOnlyOneSubSampling(s);
        boolean manySubSampling = s.getSuperSampleFlag() == 1 && checkIfHasManySubSampling(s);
//        System.out.println("-- o " + onlyOneSubSampling);
//        System.out.println("-- m " + manySubSampling);

        if (!s.getSampleSpecies().isEmpty()) {
            if (!(onlyOneSubSampling || manySubSampling)) {
                r = new SampleResult();
                r.set(s);
                r.setMessageType(Message.ERROR);
                r.setMessageCode(CODE_SAMPLE_SUBSAMPLE_FLAG);
                r.setMessageLabel(LABEL_SAMPLE_SUBSAMPLE_FLAG);
                r.setInconsistent(true);

                ArrayList parameters = new ArrayList<>();
                parameters.add(s.getID());
                parameters.add(s.getSuperSampleFlag());
                parameters.add(count(s.getSampleSpecies()));
                r.setMessageParameters(parameters);
                results.add(r);
            }

            int count;
            for (SampleSpecies ss : s.getSampleSpecies()) {
                count = count(s.getSampleSpecies());
//                System.out.println("Count " + count);
                if (count == 1 && ss.getSubSampleNumber() == 1) {
                    r = new SampleResult();
                    r.set(s);
                    r.setMessageType(Message.ERROR);
                    r.setMessageCode(CODE_SUBSAMPLE_NUMBER_INCONSISTENCY);
                    r.setMessageLabel(LABEL_SUBSAMPLE_NUMBER_INCONSISTENCY);

                    r.setInconsistent(true);

//                    System.out.println("----> in");
                    ArrayList parameters = new ArrayList<>();
                    parameters.add(s.getID());
                    parameters.add(ss.getSubSampleNumber());
                    parameters.add(ss.getSpecies().getName());
                    parameters.add(ss.getLdlf());
                    r.setMessageParameters(parameters);
                    results.add(r);
                }
            }

        } else {
            r = new SampleResult();
            r.set(s);
            r.setMessageType(Message.ERROR);
            r.setMessageCode(CODE_SAMPLE_SUBSAMPLE_NO_SAMPLE_SPECIES);
            r.setMessageLabel(LABEL_SAMPLE_SUBSAMPLE_NO_SAMPLE_SPECIES);
            r.setInconsistent(true);

            ArrayList parameters = new ArrayList<>();
            parameters.add(s.getID());
            parameters.add(s.getSuperSampleFlag());
            parameters.add(count(s.getSampleSpecies()));
            r.setMessageParameters(parameters);
            results.add(r);
        }
        return results;
    }

//    public static int count(List<SampleSpecies> sampleSpecies) {
//        int count = 0;
//        int lastSuperSampleNumber = 0;
//        for (SampleSpecies s : sampleSpecies) {
//            if (s.getSubSampleNumber() > lastSuperSampleNumber) {
//                count++;
//                lastSuperSampleNumber = s.getSubSampleNumber();
//            }
//        }
//        return count;
//    }
    public static int count(List<SampleSpecies> sampleSpecies) {

        List<Integer> subSampleNumber = new ArrayList();
        for (SampleSpecies ss : sampleSpecies) {
            if (!subSampleNumber.contains(ss.getSubSampleNumber())) {
                subSampleNumber.add(ss.getSubSampleNumber());
            }
        }

        return subSampleNumber.size();

    }

}
