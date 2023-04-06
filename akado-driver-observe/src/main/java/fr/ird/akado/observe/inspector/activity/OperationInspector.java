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

//    public static boolean operationNumberConsistent(Activity a) {
//        return a.getVesselActivity() == null && a.getSetCount() > 0;
//    }
//
//    public static boolean activityAndOperationConsistent(Activity a) {
//        float totalCatchWeightExpected = a.getTotalWeight();
//        return totalCatchWeightExpected == 0 && Objects.equals(a.getVesselActivity().getCode(), "1")
//                || totalCatchWeightExpected != 0 && Objects.equals(a.getVesselActivity().getCode(), "0")
//                || totalCatchWeightExpected != 0 && Objects.equals(a.getVesselActivity().getCode(), "3")
//                || totalCatchWeightExpected != 0 && (Objects.equals(a.getVesselActivity().getCode(), "12") || Objects.equals(a.getVesselActivity().getCode(), "13") || Objects.equals(a.getVesselActivity().getCode(), "14"));
//    }
//
//    public static boolean operationAndSchoolTypeConsistent(Activity a) {
//        return a.getSchoolType() != null && Objects.equals(a.getSchoolType().getCode(), "3") && (Objects.equals(a.getVesselActivity().getCode(), "1") || Objects.equals(a.getVesselActivity().getCode(), "0"));
//    }

    public OperationInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the operation associated with activity is consistent with other information.";
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();

        Activity activity = get();

        boolean isFishing = activity.getVesselActivity().getCode().equals("6");

        double totalCatchWeightExpected = activity.totalCatchWeightFromCatches();

        if (!isFishing) {

            //FIXME ajouter controle sur activity.setCount ==null

            // can not have catch weight
            if (totalCatchWeightExpected > 0) {
                //FIXME New message
                ActivityResult r = createResult(MessageDescriptions.E_1218_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY_WITH_CATCH_WEIGHT, activity,
                                                activity.getID(getTrip(), getRoute()),
                                                activity.getVesselActivity().getCode(),
                                                totalCatchWeightExpected);
                results.add(r);
            }
        } else {

            //FIXME ajouter controle sur activity.setCount !=null et >0

            if (activity.getReasonForNoFishing() == null) {
                if (activity.getReasonForNullSet() == null) {
                    if (totalCatchWeightExpected == 0) {
                        //FIXME New message
                        ActivityResult r = createResult(MessageDescriptions.E_1218_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY_WITH_CATCH_WEIGHT, activity,
                                                        activity.getID(getTrip(), getRoute()),
                                                        activity.getVesselActivity().getCode(),
                                                        totalCatchWeightExpected);
                        results.add(r);
                    }
                } else {
                    if (totalCatchWeightExpected > 5) {
                        //FIXME New message
                        ActivityResult r = createResult(MessageDescriptions.E_1218_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY_WITH_CATCH_WEIGHT, activity,
                                                        activity.getID(getTrip(), getRoute()),
                                                        activity.getVesselActivity().getCode(),
                                                        totalCatchWeightExpected);
                        results.add(r);
                    }
                }

            } else {
                if (totalCatchWeightExpected > 0) {
                    //FIXME New message
                    ActivityResult r = createResult(MessageDescriptions.E_1218_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY_WITH_CATCH_WEIGHT, activity,
                                                    activity.getTopiaId(),
                                                    activity.getVesselActivity().getCode(),
                                                    totalCatchWeightExpected);
                    results.add(r);
                }
            }
        }
        return results;
    }
}
