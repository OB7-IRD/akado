package fr.ird.akado.observe.inspector.activity;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.Constant;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.akado.observe.result.Results;
import fr.ird.common.message.Message;
import fr.ird.driver.observe.business.data.ps.logbook.Activity;
import fr.ird.driver.observe.business.referential.ps.common.ObservedSystem;
import fr.ird.driver.observe.business.referential.ps.common.SchoolType;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created on 20/03/2023.
 *
 * @author Tony Chemit - dev@tchemit.fr
 * @since 1.0.0
 */
@AutoService(ObserveActivityInspector.class)
public class FishingContextInspector extends ObserveActivityInspector {

    public static boolean fishingContextIsConsistentWithArtificialSchool(ObservedSystem fishingContext) {
        return fishingContext.getSchoolType().isArtificial();
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

        SchoolType schoolType = a.getSchoolType();
        if (schoolType == null) {
            return results;
        }

        Set<ObservedSystem> observedSystem = a.getObservedSystem();

        if (schoolType.isArtificial()) {
            if (observedSystem.isEmpty()) {
                ActivityResult r = createResult(a, Message.ERROR, Constant.CODE_ACTIVITY_FISHING_CONTEXT_NULL_OR_EMPTY, Constant.LABEL_ACTIVITY_FISHING_CONTEXT_NULL_OR_EMPTY, true,
                                                a.getTopiaId(), schoolType.getCode());
                results.add(r);
                return results;
            }
            Set<String> requiredFadObservedSystem = observedSystem.stream().filter(os -> os.getSchoolType().isArtificial()).map(ObservedSystem::getCode).collect(Collectors.toSet());
            if (requiredFadObservedSystem.isEmpty()) {
                ActivityResult r = createResult(a, Message.ERROR, Constant.CODE_ACTIVITY_FISHING_CONTEXT_INCONSISTENCY_ARTIFICIAL_SCHOOL_TYPE, Constant.LABEL_ACTIVITY_FISHING_CONTEXT_INCONSISTENCY_ARTIFICIAL_SCHOOL_TYPE, true,
                                                a.getTopiaId(), schoolType.getCode());
                results.add(r);
            }
            return results;
        }
        if (schoolType.isFree()) {
            Set<String> forbiddenFadObservedSystem = observedSystem.stream().filter(os -> os.getSchoolType().isArtificial()).map(ObservedSystem::getCode).collect(Collectors.toSet());
            if (!forbiddenFadObservedSystem.isEmpty()) {
                ActivityResult r = createResult(a, Message.ERROR, Constant.CODE_ACTIVITY_FISHING_CONTEXT_INCONSISTENCY_FREE_SCHOOL_TYPE, Constant.LABEL_ACTIVITY_FISHING_CONTEXT_INCONSISTENCY_FREE_SCHOOL_TYPE, true,
                                                a.getTopiaId(), schoolType.getCode());
                results.add(r);
            }
        }
        return results;
    }
}
