package fr.ird.akado.observe.inspector.activities;

import com.google.auto.service.AutoService;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.core.spatial.GISHandler;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.common.message.Message;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.ird.akado.observe.Constant.CODE_ACTIVITY_OPERATION_EEZ_INCONSISTENCY;
import static fr.ird.akado.observe.Constant.LABEL_ACTIVITY_OPERATION_EEZ_INCONSISTENCY;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveActivityListInspector.class)
public class PositionsInEEZInspector extends ObserveActivityListInspector {
    public static Map<Activity, Boolean> activityPositionAndEEZInconsistent(List<Activity> l) {
        Map<Activity, Boolean> result = new HashMap<>();
        StringBuilder multiPoint = new StringBuilder();
        for (Activity a : l) {
            if (!multiPoint.toString().equals("")) {
                multiPoint.append(",");
            }
            Float latitude = a.getLatitude();
            Float longitude = a.getLongitude();
            if (latitude != null && longitude != null) {
                multiPoint.append("(").append(longitude).append(" ").append(latitude).append(")");
            }
        }
        List<String> eezList = GISHandler.getService().getEEZList(multiPoint.toString());
        for (Activity a : l) {
            if ((a.getFpaZone() == null || a.getFpaZone().getCode().equals("0") || a.getFpaZone().getCountry() == null)) {
                result.put(a, Boolean.FALSE);
            } else {
                String eezCountry = a.getFpaZone().getCountry().getIso3Code();
                boolean o = eezList.contains(eezCountry);
                result.put(a, (eezCountry != null && o));
            }
        }
        return result;
    }

    public PositionsInEEZInspector() {
        this.name = this.getClass().getName();
        this.description = "Check if the EEZ reported is consistent with the eez calculated from the position activity.";
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();

        if (!AAProperties.WARNING_INSPECTOR.equals(AAProperties.ACTIVE_VALUE)) {
            return results;
        }
        List<Activity> l = get();
        Map<Activity, Boolean> inconsistentActivities = activityPositionAndEEZInconsistent(l);
        for (Map.Entry<Activity, Boolean> entry : inconsistentActivities.entrySet()) {
            if (entry.getValue()) {
                Activity a = entry.getKey();
                ActivityResult r = createResult(a, Message.WARNING, CODE_ACTIVITY_OPERATION_EEZ_INCONSISTENCY, LABEL_ACTIVITY_OPERATION_EEZ_INCONSISTENCY, true,
                                                a.getTopiaId(),
                                                a.getVesselActivity().getCode(),
                                                a.getSetCount());
                results.add(r);

            }
        }
        return results;
    }
}
