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
package fr.ird.akado.avdth.activity;

import static fr.ird.akado.avdth.Constant.CODE_ACTIVITY_OPERATION_INCONSISTENCY_WITH_SCHOOL_TYPE;
import fr.ird.akado.core.common.AAProperties;
import static fr.ird.akado.avdth.Constant.CODE_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY;
import static fr.ird.akado.avdth.Constant.CODE_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY_WITH_CATCH_WEIGHT;
import static fr.ird.akado.avdth.Constant.LABEL_ACTIVITY_OPERATION_INCONSISTENCY_WITH_SCHOOL_TYPE;
import static fr.ird.akado.avdth.Constant.LABEL_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY;
import static fr.ird.akado.avdth.Constant.LABEL_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY_WITH_CATCH_WEIGHT;
import fr.ird.akado.avdth.result.ActivityResult;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.core.Inspector;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Activity;
import java.util.ArrayList;

/**
 * Check if the operation associated with activity is consistent with other
 * informations.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 21 mai 2014
 *
 */
public class OperationtInspector extends Inspector<Activity> {

    public OperationtInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the operation associated with activity is "
                + "consistent with other informations.";
    }

    public static boolean operationNumberConsistent(Activity a) {
        return a.getOperation() == null && a.getOperationCount() > 0;
    }

    public static boolean activityAndOperationConsistent(Activity a) {
        double totalCatchWeightExpected = a.getCatchWeight();//WeightInspector.totalCatchWeight(a);
        boolean result = (totalCatchWeightExpected == 0 && (a.getOperation().getCode() == 1 || a.getOperation().getCode() == 2))
                || (totalCatchWeightExpected != 0 && (a.getOperation().getCode() == 0))
                || (totalCatchWeightExpected != 0 && (a.getOperation().getCode() == 3))
                || (totalCatchWeightExpected != 0 && (a.getOperation().getCode() == 12 || a.getOperation().getCode() == 13 || a.getOperation().getCode() == 14));
        return result;
    }

    public static boolean operationAndSchoolTypeConsistent(Activity a) {
        return a.getSchoolType().getCode() == 3 && (a.getOperation().getCode() == 1 || a.getOperation().getCode() == 0);
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Activity a = get();
        ActivityResult r;
        if (operationNumberConsistent(a)) {
            r = new ActivityResult();
            r.set(a);
            r.setMessageType(Message.ERROR);
            r.setMessageCode(CODE_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY);
            r.setMessageLabel(LABEL_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY);

            r.setInconsistent(true);

            ArrayList parameters = new ArrayList<>();
            parameters.add(a.getID());
            String operationCode = "-";
            if (a.getOperation() != null) {
                operationCode = "" + a.getOperation().getCode();
            }
            parameters.add(operationCode);
            parameters.add(a.getOperationCount());

            r.setMessageParameters(parameters);
            results.add(r);

        }
        if (operationAndSchoolTypeConsistent(a)) {
            r = new ActivityResult();
            r.set(a);
            r.setMessageType(Message.ERROR);
            r.setMessageCode(CODE_ACTIVITY_OPERATION_INCONSISTENCY_WITH_SCHOOL_TYPE);
            r.setMessageLabel(LABEL_ACTIVITY_OPERATION_INCONSISTENCY_WITH_SCHOOL_TYPE);

            r.setInconsistent(true);

            ArrayList parameters = new ArrayList<>();
            parameters.add(a.getID());
            parameters.add(a.getOperation().getCode());
            parameters.add(a.getCatchWeight());

            r.setMessageParameters(parameters);
            results.add(r);

        }
        if (AAProperties.isWarningInspectorEnabled()) {
            if (activityAndOperationConsistent(a)) {
                r = new ActivityResult();
                r.set(a);
                r.setMessageType(Message.WARNING);
                r.setMessageCode(CODE_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY_WITH_CATCH_WEIGHT);
                r.setMessageLabel(LABEL_ACTIVITY_OPERATION_NUMBER_INCONSISTENCY_WITH_CATCH_WEIGHT);

                r.setInconsistent(true);

                ArrayList parameters = new ArrayList<>();
                parameters.add(a.getID());
                parameters.add(a.getOperation().getCode());
                parameters.add(a.getCatchWeight());

                r.setMessageParameters(parameters);
                results.add(r);
            }
        }
        return results;
    }
}
