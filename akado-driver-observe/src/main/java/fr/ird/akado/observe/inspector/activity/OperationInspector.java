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
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.common.message.Message;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;

import java.util.Objects;

import static fr.ird.akado.observe.Constant.CODE_ACTIVITY_OPERATION_INCONSISTENCY_WITH_SCHOOL_TYPE;
import static fr.ird.akado.observe.Constant.CODE_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY;
import static fr.ird.akado.observe.Constant.CODE_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY_WITH_CATCH_WEIGHT;
import static fr.ird.akado.observe.Constant.LABEL_ACTIVITY_OPERATION_INCONSISTENCY_WITH_SCHOOL_TYPE;
import static fr.ird.akado.observe.Constant.LABEL_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY;
import static fr.ird.akado.observe.Constant.LABEL_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY_WITH_CATCH_WEIGHT;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveActivityInspector.class)
public class OperationInspector extends ObserveActivityInspector {

    public static boolean operationNumberConsistent(Activity a) {
        return a.getVesselActivity() == null && a.getSetCount() > 0;
    }

    public static boolean activityAndOperationConsistent(Activity a) {
        Float totalCatchWeightExpected = a.getTotalWeight();
        return (totalCatchWeightExpected == null && (Objects.equals(a.getVesselActivity().getCode(), "1") || Objects.equals(a.getVesselActivity().getCode(), "2")))
                || (totalCatchWeightExpected != null && totalCatchWeightExpected != 0 && (Objects.equals(a.getVesselActivity().getCode(), "0")))
                || (totalCatchWeightExpected != null && totalCatchWeightExpected != 0 && (Objects.equals(a.getVesselActivity().getCode(), "3")))
                || (totalCatchWeightExpected != null && totalCatchWeightExpected != 0 && (Objects.equals(a.getVesselActivity().getCode(), "12") || Objects.equals(a.getVesselActivity().getCode(), "13") || Objects.equals(a.getVesselActivity().getCode(), "14")));
    }

    public static boolean operationAndSchoolTypeConsistent(Activity a) {
        return a.getSchoolType() != null && Objects.equals(a.getSchoolType().getCode(), "3") && (Objects.equals(a.getVesselActivity().getCode(), "1") || Objects.equals(a.getVesselActivity().getCode(), "0"));
    }

    public OperationInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the operation associated with activity is consistent with other information.";
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();

        Activity a = get();

        if (operationNumberConsistent(a)) {
            ActivityResult r = createResult(a, Message.ERROR, CODE_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY, LABEL_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY, true,
                                            a.getTopiaId(), a.getVesselActivity().getCode(), a.getSetCount());
            results.add(r);
        }
        if (operationAndSchoolTypeConsistent(a)) {
            ActivityResult r = createResult(a, Message.ERROR, CODE_ACTIVITY_OPERATION_INCONSISTENCY_WITH_SCHOOL_TYPE, LABEL_ACTIVITY_OPERATION_INCONSISTENCY_WITH_SCHOOL_TYPE, true,
                                            a.getTopiaId(), a.getVesselActivity().getCode(), a.getTotalWeight());
            results.add(r);

        }
        if (AAProperties.WARNING_INSPECTOR.equals(AAProperties.ACTIVE_VALUE)) {
            if (activityAndOperationConsistent(a)) {
                ActivityResult r = createResult(a, Message.WARNING, CODE_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY_WITH_CATCH_WEIGHT, LABEL_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY_WITH_CATCH_WEIGHT, true,
                                                a.getTopiaId(), a.getVesselActivity().getCode(), a.getTotalWeight());
                results.add(r);
            }
        }
        return results;
    }
}
