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
package fr.ird.akado.avdth.activity;

import static fr.ird.akado.avdth.Constant.CODE_ACTIVITY_FISHING_CONTEXT_INCONSISTENCY_SCHOOL_TYPE;
import static fr.ird.akado.avdth.Constant.CODE_ACTIVITY_FISHING_CONTEXT_NULL_OR_EMPTY;
import static fr.ird.akado.avdth.Constant.LABEL_ACTIVITY_FISHING_CONTEXT_INCONSISTENCY_SCHOOL_TYPE;
import static fr.ird.akado.avdth.Constant.LABEL_ACTIVITY_FISHING_CONTEXT_NULL_OR_EMPTY;
import fr.ird.akado.avdth.result.ActivityResult;
import fr.ird.akado.avdth.result.Results;
import fr.ird.akado.core.Inspector;
import fr.ird.common.message.Message;
import fr.ird.driver.avdth.business.Activity;
import fr.ird.driver.avdth.business.FishingContext;
import fr.ird.driver.avdth.business.FishingContextType;
import fr.ird.driver.avdth.business.SchoolType;
import java.util.ArrayList;
import java.util.List;

/**
 * Check if the school type and the fishing context are consistent.
 *
 * @author Julien Lebranchu <julien.lebranchu@ird.fr>
 * @since 2.0
 * @date 21 mai 2014
 *
 */
public class FishingContextInspector extends Inspector<Activity> {

    public FishingContextInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the school type and the fishing context are consistent.";
    }

    /**
     * The fishing context must be empty if the school type is artificial
     *
     * @param s the school type
     * @param fishingContexts the list of fishing context
     * @return true if school type is artificial and the fishing context is
     * empty
     */
    public static boolean artificialSchoolAndEmpty(SchoolType s, List<FishingContext> fishingContexts) {
        return s.getCode() == SchoolType.ARTIFICIAL_SCHOOL && fishingContexts.isEmpty();
    }

    public static boolean fishingContextIsConsistentWithArtificialSchool(FishingContext fishingContext) {
        return fishingContext.getFishingContextType().getGroupCode() == FishingContextType.FISHING_CONTEXT_GROUP_FAD;
//        fishingContext.getFishingContextType().getCodeAssociation() == 10
//                || fishingContext.getFishingContextType().getCodeAssociation() == 60
//                || fishingContext.getFishingContextType().getCodeAssociation() == 81
//                || (fishingContext.getFishingContextType().getCodeAssociation() >= 20 && fishingContext.getFishingContextType().getCodeAssociation() <= 28);
    }

    @Override
    public Results execute() {
        Results results = new Results();
        Activity a = get();
        ActivityResult r;
        if (a.getSchoolType() != null) {
            if (artificialSchoolAndEmpty(a.getSchoolType(), a.getFishingContexts())) {
                r = new ActivityResult();

                r.set(a);
                r.setMessageType(Message.ERROR);
                r.setMessageCode(CODE_ACTIVITY_FISHING_CONTEXT_NULL_OR_EMPTY);
                r.setMessageLabel(LABEL_ACTIVITY_FISHING_CONTEXT_NULL_OR_EMPTY);

                r.setInconsistent(true);

                ArrayList parameters = new ArrayList<>();
                parameters.add(a.getID());
                parameters.add(a.getSchoolType().getLibelle());
                r.setMessageParameters(parameters);
                results.add(r);
            } else {
                boolean fishingContextIsNotConsistent = false;
                FishingContext fc = null;
                for (FishingContext aa : a.getFishingContexts()) {
                    fc = aa;
                    if (a.getSchoolType().getCode() == SchoolType.ARTIFICIAL_SCHOOL && fishingContextIsConsistentWithArtificialSchool(aa)) {
                        return results;
                    }
                    if (a.getSchoolType().getCode() == SchoolType.FREE_SCHOOL && fishingContextIsConsistentWithArtificialSchool(aa)) {
                        fishingContextIsNotConsistent = true;
                        break;
                    }
                    if (a.getSchoolType().getCode() == SchoolType.ARTIFICIAL_SCHOOL && !fishingContextIsConsistentWithArtificialSchool(aa)) {
                        fishingContextIsNotConsistent = true;
                    }
                }
                if (fishingContextIsNotConsistent) {
                    r = new ActivityResult();

                    r.set(a);
                    r.setMessageType(Message.ERROR);
                    r.setMessageCode(CODE_ACTIVITY_FISHING_CONTEXT_INCONSISTENCY_SCHOOL_TYPE);
                    r.setMessageLabel(LABEL_ACTIVITY_FISHING_CONTEXT_INCONSISTENCY_SCHOOL_TYPE);

                    r.setInconsistent(true);

                    ArrayList parameters = new ArrayList<>();
                    parameters.add(a.getID());
                    parameters.add(a.getSchoolType().getCode());
                    if (fc != null) {
                        parameters.add(fc.getFishingContextType().getCode());
                    } else {
                        parameters.add("-");
                    }
                    r.setMessageParameters(parameters);
                    results.add(r);
                }
            }
        }

        return results;
    }
}
