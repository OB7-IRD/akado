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
package fr.ird.akado.observe.inspector.activity;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveActivityInspector.class)
public class OperationInspector extends ObserveActivityInspector {

    public OperationInspector() {
        this.description = "Check if the operation associated with activity is consistent with other information.";
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();

        Activity activity = get();

        boolean isFishing = activity.getVesselActivity().isFishing();

        double totalCatchWeightExpected = activity.totalCatchWeightFromCatches();

        int setCount = activity.getSetCount();
        if (!isFishing) {

            // can not have set count
            if (setCount != 0) {
                ActivityResult r = createResult(MessageDescriptions.E1220_ACTIVITY_NOT_FISHING_OPERATION_INCONSISTENCY_WITH_SET_COUNT, activity,
                                                activity.getID(getTrip(), getRoute()),
                                                activity.getVesselActivity().getCode(),
                                                setCount);
                results.add(r);
            }

            // can not have catch weight
            if (totalCatchWeightExpected > 0) {
                ActivityResult r = createResult(MessageDescriptions.E1222_ACTIVITY_NOT_FISHING_OPERATION_INCONSISTENCY_WITH_CATCH_WEIGHT, activity,
                                                activity.getID(getTrip(), getRoute()),
                                                activity.getVesselActivity().getCode(),
                                                totalCatchWeightExpected);
                results.add(r);
            }
            return results;
        }

        // on fishing operation

        if (setCount == 0) {
            ActivityResult r = createResult(MessageDescriptions.E1218_ACTIVITY_FISHING_OPERATION_INCONSISTENCY_WITH_SET_COUNT, activity,
                                            activity.getID(getTrip(), getRoute()),
                                            activity.getVesselActivity().getCode());
            results.add(r);
        }
        if (activity.getReasonForNoFishing() == null) {
            if (activity.getReasonForNullSet() == null) {
                if (totalCatchWeightExpected == 0) {
                    ActivityResult r = createResult(MessageDescriptions.E1216_ACTIVITY_FISHING_OPERATION_INCONSISTENCY_WITH_CATCH_WEIGHT, activity,
                                                    activity.getID(getTrip(), getRoute()),
                                                    activity.getVesselActivity().getCode(),
                                                    totalCatchWeightExpected);
                    results.add(r);
                }
            } else {
                if (totalCatchWeightExpected > 5) {
                    ActivityResult r = createResult(MessageDescriptions.W1215_ACTIVITY_FISHING_OPERATION_INCONSISTENCY_CATCH_WEIGHT, activity,
                                                    activity.getID(getTrip(), getRoute()),
                                                    activity.getVesselActivity().getCode(),
                                                    totalCatchWeightExpected);
                    results.add(r);
                }
            }
            return results;
        }
        // reasonForNoFishing != null
        if (totalCatchWeightExpected > 0) {
            ActivityResult r = createResult(MessageDescriptions.E1225_ACTIVITY_FISHING_OPERATION_AND_REASON_FOR_NO_FISHING_INCONSISTENCY_CATCH_WEIGHT, activity,
                                            activity.getTopiaId(),
                                            activity.getVesselActivity().getCode(),
                                            activity.getReasonForNoFishing().getCode(),
                                            totalCatchWeightExpected);
            results.add(r);
        }
        return results;
    }
}
