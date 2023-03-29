package fr.ird.akado.observe.inspector.activity;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.common.message.Message;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.referential.ps.common.ObservedSystem;
import fr.ird.driver.observe.business.referential.ps.common.SchoolType;

import java.util.Collection;
import java.util.Objects;

import static fr.ird.akado.observe.Constant.CODE_ACTIVITY_FISHING_CONTEXT_INCONSISTENCY_SCHOOL_TYPE;
import static fr.ird.akado.observe.Constant.CODE_ACTIVITY_FISHING_CONTEXT_NULL_OR_EMPTY;
import static fr.ird.akado.observe.Constant.LABEL_ACTIVITY_FISHING_CONTEXT_INCONSISTENCY_SCHOOL_TYPE;
import static fr.ird.akado.observe.Constant.LABEL_ACTIVITY_FISHING_CONTEXT_NULL_OR_EMPTY;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveActivityInspector.class)
public class FishingContextInspector extends ObserveActivityInspector {

    /**
     * The fishing context must be empty if the school type is artificial
     *
     * @param s               the school type
     * @param fishingContexts the list of fishing context
     * @return true if school type is artificial and the fishing context is
     * empty
     */
    public static boolean artificialSchoolAndEmpty(SchoolType s, Collection<ObservedSystem> fishingContexts) {
        return s.getCode().equals(SchoolType.ARTIFICIAL_SCHOOL) && fishingContexts.isEmpty();
    }

    public static boolean fishingContextIsConsistentWithArtificialSchool(ObservedSystem fishingContext) {
        return Objects.equals(fishingContext.getSchoolType().getCode(), SchoolType.ARTIFICIAL_SCHOOL);
    }

    public FishingContextInspector() {
        super();
        this.name = this.getClass().getName();
        this.description = "Check if the school type and the fishing context are consistent.";
    }

    @Override
    public Results execute() throws Exception {
        Results results = new Results();

        Activity a = get();

        if (a.getSchoolType() != null) {
            if (artificialSchoolAndEmpty(a.getSchoolType(), a.getObservedSystem())) {
                ActivityResult r = createResult(a, Message.ERROR, CODE_ACTIVITY_FISHING_CONTEXT_NULL_OR_EMPTY, LABEL_ACTIVITY_FISHING_CONTEXT_NULL_OR_EMPTY, true,
                                                a.getTopiaId(), a.getSchoolType().getLabel2());
                results.add(r);
            } else {
                boolean fishingContextIsNotConsistent = false;
                ObservedSystem fc = null;
                for (ObservedSystem aa : a.getObservedSystem()) {
                    fc = aa;
                    if (Objects.equals(a.getSchoolType().getCode(), SchoolType.ARTIFICIAL_SCHOOL) && fishingContextIsConsistentWithArtificialSchool(aa)) {
                        return results;
                    }
                    if (Objects.equals(a.getSchoolType().getCode(), SchoolType.FREE_SCHOOL) && fishingContextIsConsistentWithArtificialSchool(aa)) {
                        fishingContextIsNotConsistent = true;
                        break;
                    }
                    if (Objects.equals(a.getSchoolType().getCode(), SchoolType.ARTIFICIAL_SCHOOL) && !fishingContextIsConsistentWithArtificialSchool(aa)) {
                        fishingContextIsNotConsistent = true;
                    }
                }
                if (fishingContextIsNotConsistent) {
                    //FIXME was fc.getFishingContextType().getCode()
                    String fcCode = "-";
                    if (fc != null) {
                        fcCode = fc.getSchoolType().getCode();
                    }
                    ActivityResult r = createResult(a, Message.ERROR, CODE_ACTIVITY_FISHING_CONTEXT_INCONSISTENCY_SCHOOL_TYPE, LABEL_ACTIVITY_FISHING_CONTEXT_INCONSISTENCY_SCHOOL_TYPE, true,
                                                    a.getTopiaId(), a.getSchoolType().getLabel2(), fcCode);
                    results.add(r);
                }
            }
        }
        return results;
    }
}
