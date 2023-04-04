package fr.ird.akado.observe.inspector.sample;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.Constant;
import fr.ird.akado.observe.result.Results;
import fr.ird.akado.observe.result.SampleResult;
import fr.ird.common.message.Message;
import fr.ird.driver.observe.business.data.ps.logbook.Sample;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveSampleInspector.class)
public class DistributionInspector extends ObserveSampleInspector {
    public DistributionInspector() {
        this.name = getClass().getName();
        this.description = "Compare la somme des +10/-10 saisie dans les plans de cuve avec celle saisie dans l'échantillon.";
    }

    public static boolean distributionIsInconsistent(Sample s) {
        Float m10Weight = 0f;
        Float p10Weight = 0f;
        //FIXME
//        for (WellPlan wp : s.getWell().getWellPlans()) {
//            if (wp.getWeightCategory().getCode() == WeightCategoryWellPlan.M10) {
//                m10Weight += wp.getWeight();
//            }
//            if (wp.getWeightCategory().getCode() == WeightCategoryWellPlan.UNKNOWN
//                    && wp.getSpecies().getCode() == 2) {
//                m10Weight += wp.getWeight();
//            }
//            if (wp.getWeightCategory().getCode() == WeightCategoryWellPlan.P10) {
//                p10Weight += wp.getWeight();
//            }
//        }
        return !m10Weight.equals(s.getSmallsWeight()) || !p10Weight.equals(s.getBigsWeight());
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();
        Sample s = get();
        if (s.getWell() == null) {
            return results;
        }
        if (distributionIsInconsistent(s)) {
            SampleResult r = createResult(s, Message.ERROR, Constant.CODE_SAMPLE_DISTRIBUTION_M10_P10, Constant.LABEL_SAMPLE_DISTRIBUTION_M10_P10, true,
                                          s.getTopiaId());
            results.add(r);
        }
        return results;
    }
}
