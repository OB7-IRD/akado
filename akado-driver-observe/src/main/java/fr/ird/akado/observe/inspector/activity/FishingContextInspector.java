package fr.ird.akado.observe.inspector.activity;

import com.google.auto.service.AutoService;
import fr.ird.akado.observe.MessageDescriptions;
import fr.ird.akado.observe.result.ActivityResult;
import fr.ird.akado.observe.result.Results;
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
        this.description = "Check if the school type and the fishing context are consistent.";
    }

    @Override
    public Results execute() throws Exception {

        Activity a = get();
        Set<ObservedSystem> observedSystem = a.getObservedSystem();
        if (observedSystem.isEmpty()) {
            ActivityResult r = createResult(MessageDescriptions.E1219_ACTIVITY_FISHING_CONTEXT_NULL_OR_EMPTY, a,
                                            a.getID(getTrip(), getRoute()));
            return Results.of(r);
        }
        if (a.withoutSchoolType()) {
            return null;
        }
        SchoolType schoolType = a.getSchoolType();
        if (schoolType.isArtificial()) {

            Set<String> requiredFadObservedSystem = observedSystem.stream().filter(os -> os.getSchoolType().isArtificial()).map(ObservedSystem::getCode).collect(Collectors.toSet());
            if (requiredFadObservedSystem.isEmpty()) {
                ActivityResult r = createResult(MessageDescriptions.E1240_ACTIVITY_FISHING_CONTEXT_INCONSISTENCY_ARTIFICIAL_SCHOOL_TYPE, a,
                                                a.getID(getTrip(), getRoute()),
                                                schoolType.getLabel2());
                return Results.of(r);
            }
            return null;
        }
        if (schoolType.isFree()) {
            Set<String> forbiddenFadObservedSystem = observedSystem.stream().filter(os -> os.getSchoolType().isArtificial()).map(ObservedSystem::getCode).collect(Collectors.toSet());
            if (!forbiddenFadObservedSystem.isEmpty()) {
                ActivityResult r = createResult(MessageDescriptions.E1241_ACTIVITY_FISHING_CONTEXT_INCONSISTENCY_FREE_SCHOOL_TYPE, a,
                                                a.getID(getTrip(), getRoute()),
                                                schoolType.getLabel2());
                return Results.of(r);
            }
        }
        return null;
    }
}
