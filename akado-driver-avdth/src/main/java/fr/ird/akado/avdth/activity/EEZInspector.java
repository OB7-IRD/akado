/*
 *
 * Copyright (C) 2017 Ob7, IRD
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

import static fr.ird.akado.avdth.Constant.CODE_ACTIVITY_OPERATION_EEZ_INCONSISTENCY;
import static fr.ird.akado.avdth.Constant.LABEL_ACTIVITY_OPERATION_EEZ_INCONSISTENCY;
import fr.ird.akado.core.common.AAProperties;
import fr.ird.akado.avdth.result.ActivityResult;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.core.Inspector;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Activity;
import java.util.ArrayList;

/**
 * Check if the EEZ reported is consistent with operation.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 16 mars 2017
 *
 */
public class EEZInspector extends Inspector<Activity> {

    public EEZInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the EEZ reported is consistent with operation.";
    }

    public static boolean operationAndEEZInconsistent(Activity a) {
        return (a.getFpaZone() == null || a.getFpaZone().getCode() == 0) && (a.getOperation().getCode() == 1 || a.getOperation().getCode() == 0);
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Activity a = get();
        ActivityResult r;
        if (AAProperties.WARNING_INSPECTOR.equals(AAProperties.ACTIVE_VALUE)) {
            if (operationAndEEZInconsistent(a)) {
                r = new ActivityResult();
                r.set(a);
                r.setMessageType(Message.WARNING);
                r.setMessageCode(CODE_ACTIVITY_OPERATION_EEZ_INCONSISTENCY);
                r.setMessageLabel(LABEL_ACTIVITY_OPERATION_EEZ_INCONSISTENCY);

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
        }
        return results;
    }
}
