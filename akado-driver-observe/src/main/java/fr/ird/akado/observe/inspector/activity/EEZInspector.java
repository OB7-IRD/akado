package fr.ird.akado.observe.inspector.activity;

import com.google.auto.service.AutoService;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.common.message.Message;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;

import static fr.ird.akado.observe.Constant.CODE_ACTIVITY_OPERATION_EEZ_INCONSISTENCY;
import static fr.ird.akado.observe.Constant.LABEL_ACTIVITY_OPERATION_EEZ_INCONSISTENCY;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveActivityInspector.class)
public class EEZInspector extends ObserveActivityInspector {
    public static boolean operationAndEEZInconsistent(Activity a) {
        //FIXME vesselActivity code must be translated to ObServe
        return (a.getFpaZone() == null || a.getFpaZone().getCode().equals("0")) && (a.getVesselActivity().getCode().equals("1") || a.getVesselActivity().getCode().equals("1"));
    }

    public EEZInspector() {
        this.name = this.getClass().getName();
        this.description = "Check if the EEZ reported is consistent with operation.";
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();
        if (!AAProperties.WARNING_INSPECTOR.equals(AAProperties.ACTIVE_VALUE)) {
            return results;
        }
        Activity a = get();
        if (operationAndEEZInconsistent(a)) {
            ActivityResult r = createResult(a, Message.WARNING, CODE_ACTIVITY_OPERATION_EEZ_INCONSISTENCY, LABEL_ACTIVITY_OPERATION_EEZ_INCONSISTENCY, true,
                                            a.getTopiaId(), a.getVesselActivity().getCode(), a.getSetCount());
            results.add(r);
        }
        return results;
    }
}
